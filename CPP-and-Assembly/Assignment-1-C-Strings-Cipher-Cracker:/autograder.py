#!/usr/bin/env python
# -*- coding: utf-8 -*-
"""
Autograder for COMP 201 - Spring 2026 - Assignment 1
=====================================================
Compiles the student's main.c, runs all 5 tasks against a chosen test case,
and diffs each output against the expected ground truth.

Usage:
    python autograder.py                    # runs all 3 test cases
    python autograder.py easy               # runs only the easy test case
    python autograder.py medium
    python autograder.py hard
"""

from __future__ import print_function

import os, sys, subprocess, difflib, shutil

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
MAIN_C   = os.path.join(BASE_DIR, "main.c")
ENIGMA   = os.path.join(BASE_DIR, "enigma")

# Points per task
POINTS = {
    "task1": 10,
    "task2a": 5,
    "task2b": 5,
    "task3_decrypted": 10,
    "task3_results": 10,
    "task4_decrypted": 15,
    "task4_results": 15,
    "task5_decrypted": 15,
    "task5_results": 15,
}

# ANSI colors
GREEN  = "\033[92m"
RED    = "\033[91m"
YELLOW = "\033[93m"
CYAN   = "\033[96m"
BOLD   = "\033[1m"
RESET  = "\033[0m"

# ======================================================================
#  Helpers
# ======================================================================

def compile_program():
    """Compile main.c -> enigma. Returns True on success."""
    print("\n%s%s%s" % (BOLD, "=" * 70, RESET))
    print("%s  Compiling main.c ...%s" % (BOLD, RESET))
    print("%s%s%s" % (BOLD, "=" * 70, RESET))
    proc = subprocess.Popen(
        ["gcc", "-std=c99", MAIN_C, "-o", ENIGMA],
        stdout=subprocess.PIPE, stderr=subprocess.PIPE
    )
    stdout, stderr = proc.communicate()
    if proc.returncode != 0:
        print("%s  ✗ Compilation FAILED%s" % (RED, RESET))
        print(stderr.decode("utf-8", errors="replace"))
        return False
    print("%s  ✓ Compiled successfully%s" % (GREEN, RESET))
    return True


def run_enigma(args, label=""):
    """Run ./enigma with given args. Returns (success, stdout, stderr)."""
    cmd = [ENIGMA] + args
    try:
        proc = subprocess.Popen(cmd, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        stdout, stderr = proc.communicate()
        stdout = stdout.decode("utf-8", errors="replace")
        stderr = stderr.decode("utf-8", errors="replace")
        if proc.returncode != 0:
            print("  %s✗ %s — program returned exit code %d%s" % (RED, label, proc.returncode, RESET))
            if stderr.strip():
                print("    stderr: %s" % stderr.strip())
            return False, stdout, stderr
        return True, stdout, stderr
    except OSError:
        print("  %s✗ %s — could not run program%s" % (RED, label, RESET))
        return False, "", "error"


def read_file(path):
    """Read file contents, return None if missing."""
    try:
        with open(path, "r") as f:
            return f.read()
    except IOError:
        return None


def compare_files(expected_path, actual_path, label):
    """
    Compare expected vs actual file. Returns (passed: bool, details: str).
    On mismatch, prints a character-level diff showing exactly where they diverge.
    """
    expected = read_file(expected_path)
    actual   = read_file(actual_path)

    if actual is None:
        return False, "Output file not created: %s" % actual_path

    if expected is None:
        return False, "Expected file missing: %s" % expected_path

    if expected == actual:
        return True, ""

    # -- Build detailed mismatch report --
    details = []

    # Line-level unified diff
    exp_lines = expected.splitlines(True)
    act_lines = actual.splitlines(True)
    diff = list(difflib.unified_diff(
        exp_lines, act_lines,
        fromfile="expected", tofile="actual", lineterm=""
    ))
    if diff:
        details.append("  Line-level diff (first 30 lines):")
        for line in diff[:30]:
            line = line.rstrip("\n")
            if line.startswith("+") and not line.startswith("+++"):
                details.append("    %s%s%s" % (GREEN, line, RESET))
            elif line.startswith("-") and not line.startswith("---"):
                details.append("    %s%s%s" % (RED, line, RESET))
            else:
                details.append("    %s" % line)

    # Character-level: find first mismatch
    min_len = min(len(expected), len(actual))
    first_diff_pos = None
    for i in range(min_len):
        if expected[i] != actual[i]:
            first_diff_pos = i
            break
    if first_diff_pos is None and len(expected) != len(actual):
        first_diff_pos = min_len  # one string is a prefix of the other

    if first_diff_pos is not None:
        # Count line/col of first mismatch
        line_no = expected[:first_diff_pos].count("\n") + 1
        last_nl = expected[:first_diff_pos].rfind("\n")
        col = first_diff_pos - last_nl - 1 if last_nl >= 0 else first_diff_pos

        details.append("")
        details.append("  %sFirst mismatch at character position %d "
                        "(line %d, col %d):%s" % (YELLOW, first_diff_pos, line_no, col, RESET))

        # Show context around the mismatch
        ctx_start = max(0, first_diff_pos - 20)
        ctx_end   = min(max(len(expected), len(actual)), first_diff_pos + 40)

        def show_char(c):
            if c == "\n":   return "\\n"
            if c == "\t":   return "\\t"
            if c == " ":    return "·"
            return c

        exp_ctx = "".join(show_char(c) for c in expected[ctx_start:ctx_end])
        act_ctx = "".join(show_char(c) for c in actual[ctx_start:min(len(actual), ctx_end)])

        # Pointer to mismatch position within context
        pointer_offset = sum(
            len(show_char(c)) for c in expected[ctx_start:first_diff_pos]
        )

        details.append("    Expected: ...%s..." % exp_ctx)
        details.append("    Actual:   ...%s..." % act_ctx)
        details.append("              %s^" % (" " * (pointer_offset + 3)))

        if first_diff_pos < len(expected) and first_diff_pos < len(actual):
            exp_ch = repr(expected[first_diff_pos])
            act_ch = repr(actual[first_diff_pos])
            details.append("    Expected char: %s  |  Actual char: %s" % (exp_ch, act_ch))
        elif first_diff_pos >= len(actual):
            details.append("    Actual file is shorter (len=%d) "
                           "than expected (len=%d)" % (len(actual), len(expected)))
        else:
            details.append("    Actual file is longer (len=%d) "
                           "than expected (len=%d)" % (len(actual), len(expected)))

    if len(expected) != len(actual):
        details.append("  File lengths differ: expected=%d, actual=%d" % (len(expected), len(actual)))

    return False, "\n".join(details)


# ======================================================================
#  Grade one test case
# ======================================================================

def get_test_params(difficulty):
    """Return task-specific parameters (keys, known words, etc.)."""
    params = {
        "easy": {
            "task1_known_word": "ATTACK",
            "task2_key": "KEY",
            "task4_key_length": "3",
        },
        "medium": {
            "task1_known_word": "RETREAT",
            "task2_key": "CIPHER",
            "task4_key_length": "6",
        },
        "hard": {
            "task1_known_word": "ENIGMA",
            "task2_key": "BLETCHLEY",
            "task4_key_length": "6",
        },
    }
    return params[difficulty]


def grade_test_case(difficulty):
    """Grade all tasks for a given difficulty. Returns score dict."""
    test_dir = os.path.join(BASE_DIR, "test_%s" % difficulty)
    inp_dir  = os.path.join(test_dir, "input")
    exp_dir  = os.path.join(test_dir, "expected_output")
    out_dir  = os.path.join(test_dir, "student_output")

    # Clean & prepare output directory
    if os.path.exists(out_dir):
        shutil.rmtree(out_dir)
    os.makedirs(out_dir)

    params = get_test_params(difficulty)
    scores = {}

    print("\n%s%s%s" % (BOLD + CYAN, "=" * 70, RESET))
    print("%s  Grading: %s test case%s" % (BOLD + CYAN, difficulty.upper(), RESET))
    print("%s%s%s" % (BOLD + CYAN, "=" * 70, RESET))

    # -- Task 1 --
    print("\n%s  Task 1: Caesar Known-Word Attack (10 pts)%s" % (BOLD, RESET))
    ok, _, _ = run_enigma([
        "caesar-known",
        os.path.join(inp_dir, "message1_encrypted.txt"),
        params["task1_known_word"],
        os.path.join(out_dir, "message1_decrypted.txt"),
    ], "Task 1")
    if ok:
        passed, details = compare_files(
            os.path.join(exp_dir, "message1_decrypted.txt"),
            os.path.join(out_dir, "message1_decrypted.txt"),
            "Task 1"
        )
        pts = POINTS["task1"] if passed else 0
        scores["task1"] = pts
        if passed:
            print("  %s✓ PASSED — %d/%d pts%s" % (GREEN, pts, POINTS["task1"], RESET))
        else:
            print("  %s✗ FAILED — 0/%d pts%s" % (RED, POINTS["task1"], RESET))
            print(details)
    else:
        scores["task1"] = 0

    # -- Task 2a --
    print("\n%s  Task 2a: Vigenere Encrypt (5 pts)%s" % (BOLD, RESET))
    ok, _, _ = run_enigma([
        "vigenere-encrypt",
        os.path.join(inp_dir, "message2_plaintext.txt"),
        params["task2_key"],
        os.path.join(out_dir, "message2_encrypted.txt"),
    ], "Task 2a")
    if ok:
        passed, details = compare_files(
            os.path.join(exp_dir, "message2_encrypted.txt"),
            os.path.join(out_dir, "message2_encrypted.txt"),
            "Task 2a"
        )
        pts = POINTS["task2a"] if passed else 0
        scores["task2a"] = pts
        if passed:
            print("  %s✓ PASSED — %d/%d pts%s" % (GREEN, pts, POINTS["task2a"], RESET))
        else:
            print("  %s✗ FAILED — 0/%d pts%s" % (RED, POINTS["task2a"], RESET))
            print(details)
    else:
        scores["task2a"] = 0

    # -- Task 2b --
    print("\n%s  Task 2b: Vigenere Decrypt (5 pts)%s" % (BOLD, RESET))
    ok, _, _ = run_enigma([
        "vigenere-decrypt",
        os.path.join(inp_dir, "message3_encrypted.txt"),
        params["task2_key"],
        os.path.join(out_dir, "message3_decrypted.txt"),
    ], "Task 2b")
    if ok:
        passed, details = compare_files(
            os.path.join(exp_dir, "message3_decrypted.txt"),
            os.path.join(out_dir, "message3_decrypted.txt"),
            "Task 2b"
        )
        pts = POINTS["task2b"] if passed else 0
        scores["task2b"] = pts
        if passed:
            print("  %s✓ PASSED — %d/%d pts%s" % (GREEN, pts, POINTS["task2b"], RESET))
        else:
            print("  %s✗ FAILED — 0/%d pts%s" % (RED, POINTS["task2b"], RESET))
            print(details)
    else:
        scores["task2b"] = 0

    # -- Task 3 --
    print("\n%s  Task 3: Caesar Frequency Analysis (20 pts)%s" % (BOLD, RESET))
    ok, _, _ = run_enigma([
        "caesar-freq",
        os.path.join(inp_dir, "message4_encrypted.txt"),
        os.path.join(out_dir, "message4_decrypted.txt"),
        os.path.join(out_dir, "message4_results.txt"),
    ], "Task 3")
    if ok:
        # Decrypted text (10 pts)
        passed_d, details_d = compare_files(
            os.path.join(exp_dir, "message4_decrypted.txt"),
            os.path.join(out_dir, "message4_decrypted.txt"),
            "Task 3 decrypted"
        )
        pts_d = POINTS["task3_decrypted"] if passed_d else 0
        scores["task3_decrypted"] = pts_d
        if passed_d:
            print("  %s✓ Decrypted text PASSED — %d/%d pts%s" % (GREEN, pts_d, POINTS["task3_decrypted"], RESET))
        else:
            print("  %s✗ Decrypted text FAILED — 0/%d pts%s" % (RED, POINTS["task3_decrypted"], RESET))
            print(details_d)

        # Results file (10 pts)
        passed_r, details_r = compare_files(
            os.path.join(exp_dir, "message4_results.txt"),
            os.path.join(out_dir, "message4_results.txt"),
            "Task 3 results"
        )
        pts_r = POINTS["task3_results"] if passed_r else 0
        scores["task3_results"] = pts_r
        if passed_r:
            print("  %s✓ Results file PASSED — %d/%d pts%s" % (GREEN, pts_r, POINTS["task3_results"], RESET))
        else:
            print("  %s✗ Results file FAILED — 0/%d pts%s" % (RED, POINTS["task3_results"], RESET))
            print(details_r)
    else:
        scores["task3_decrypted"] = 0
        scores["task3_results"]   = 0

    # -- Task 4 --
    print("\n%s  Task 4: Vigenere Key Recovery (30 pts)%s" % (BOLD, RESET))
    ok, _, _ = run_enigma([
        "vigenere-freq",
        os.path.join(inp_dir, "message5_encrypted.txt"),
        params["task4_key_length"],
        os.path.join(out_dir, "message5_decrypted.txt"),
        os.path.join(out_dir, "message5_results.txt"),
    ], "Task 4")
    if ok:
        passed_d, details_d = compare_files(
            os.path.join(exp_dir, "message5_decrypted.txt"),
            os.path.join(out_dir, "message5_decrypted.txt"),
            "Task 4 decrypted"
        )
        pts_d = POINTS["task4_decrypted"] if passed_d else 0
        scores["task4_decrypted"] = pts_d
        if passed_d:
            print("  %s✓ Decrypted text PASSED — %d/%d pts%s" % (GREEN, pts_d, POINTS["task4_decrypted"], RESET))
        else:
            print("  %s✗ Decrypted text FAILED — 0/%d pts%s" % (RED, POINTS["task4_decrypted"], RESET))
            print(details_d)

        passed_r, details_r = compare_files(
            os.path.join(exp_dir, "message5_results.txt"),
            os.path.join(out_dir, "message5_results.txt"),
            "Task 4 results"
        )
        pts_r = POINTS["task4_results"] if passed_r else 0
        scores["task4_results"] = pts_r
        if passed_r:
            print("  %s✓ Results file PASSED — %d/%d pts%s" % (GREEN, pts_r, POINTS["task4_results"], RESET))
        else:
            print("  %s✗ Results file FAILED — 0/%d pts%s" % (RED, POINTS["task4_results"], RESET))
            print(details_r)
    else:
        scores["task4_decrypted"] = 0
        scores["task4_results"]   = 0

    # -- Task 5 --
    print("\n%s  Task 5: Caesar Word Scoring (30 pts)%s" % (BOLD, RESET))
    ok, _, _ = run_enigma([
        "caesar-words",
        os.path.join(inp_dir, "message6_encrypted.txt"),
        os.path.join(inp_dir, "common_words.txt"),
        os.path.join(out_dir, "message6_decrypted.txt"),
        os.path.join(out_dir, "message6_results.txt"),
    ], "Task 5")
    if ok:
        passed_d, details_d = compare_files(
            os.path.join(exp_dir, "message6_decrypted.txt"),
            os.path.join(out_dir, "message6_decrypted.txt"),
            "Task 5 decrypted"
        )
        pts_d = POINTS["task5_decrypted"] if passed_d else 0
        scores["task5_decrypted"] = pts_d
        if passed_d:
            print("  %s✓ Decrypted text PASSED — %d/%d pts%s" % (GREEN, pts_d, POINTS["task5_decrypted"], RESET))
        else:
            print("  %s✗ Decrypted text FAILED — 0/%d pts%s" % (RED, POINTS["task5_decrypted"], RESET))
            print(details_d)

        passed_r, details_r = compare_files(
            os.path.join(exp_dir, "message6_results.txt"),
            os.path.join(out_dir, "message6_results.txt"),
            "Task 5 results"
        )
        pts_r = POINTS["task5_results"] if passed_r else 0
        scores["task5_results"] = pts_r
        if passed_r:
            print("  %s✓ Results file PASSED — %d/%d pts%s" % (GREEN, pts_r, POINTS["task5_results"], RESET))
        else:
            print("  %s✗ Results file FAILED — 0/%d pts%s" % (RED, POINTS["task5_results"], RESET))
            print(details_r)
    else:
        scores["task5_decrypted"] = 0
        scores["task5_results"]   = 0

    # -- Summary --
    total_earned   = sum(scores.values())
    total_possible = sum(POINTS.values())
    print("\n%s%s%s" % (BOLD, "-" * 70, RESET))
    print("%s  %s TOTAL: %d / %d%s" % (BOLD, difficulty.upper(), total_earned, total_possible, RESET))

    # Per-task summary
    task_groups = [
        ("Task 1 (Caesar Known-Word)", ["task1"]),
        ("Task 2 (Vigenere Enc+Dec)",  ["task2a", "task2b"]),
        ("Task 3 (Caesar Frequency)",  ["task3_decrypted", "task3_results"]),
        ("Task 4 (Vigenere Recovery)", ["task4_decrypted", "task4_results"]),
        ("Task 5 (Caesar Word Score)", ["task5_decrypted", "task5_results"]),
    ]
    for name, keys in task_groups:
        earned   = sum(scores[k] for k in keys)
        possible = sum(POINTS[k] for k in keys)
        if earned == possible:
            status = "%s✓%s" % (GREEN, RESET)
        else:
            status = "%s✗%s" % (RED, RESET)
        print("    %s %s: %d/%d" % (status, name, earned, possible))

    print("%s%s%s" % (BOLD, "-" * 70, RESET))
    return scores


# ======================================================================
#  Main
# ======================================================================

if __name__ == "__main__":
    if not compile_program():
        sys.exit(1)

    if len(sys.argv) > 1:
        targets = list(sys.argv[1:])
    else:
        targets = ["easy", "medium", "hard"]

    all_scores = {}
    for t in targets:
        if t not in ("easy", "medium", "hard"):
            print("Unknown difficulty: %s" % t)
            sys.exit(1)
        if not os.path.isdir(os.path.join(BASE_DIR, "test_%s" % t)):
            print("Test case directory test_%s/ not found. "
                  "Run generate_ground_truth.py first." % t)
            sys.exit(1)
        all_scores[t] = grade_test_case(t)

    # Overall summary
    if len(targets) > 1:
        print("\n%s%s%s" % (BOLD + CYAN, "=" * 70, RESET))
        print("%s  OVERALL SUMMARY%s" % (BOLD + CYAN, RESET))
        print("%s%s%s" % (BOLD + CYAN, "=" * 70, RESET))
        for t in targets:
            earned = sum(all_scores[t].values())
            possible = sum(POINTS.values())
            pct = earned * 100 // possible
            if pct == 100:
                color = GREEN
            elif pct >= 50:
                color = YELLOW
            else:
                color = RED
            print("    %s%-8s: %3d/%d (%d%%)%s" % (color, t.upper(), earned, possible, pct, RESET))
        print()

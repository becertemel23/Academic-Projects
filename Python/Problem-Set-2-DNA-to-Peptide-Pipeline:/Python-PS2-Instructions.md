[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/APGmk8Kb)
# COMP100 2025S PS2: Peptide Synthesis from DNA
### Deadline day, April 10, 2025 11:59 PM

Although sample test cases are provided, be aware that additional test cases may be used during grading.

## Overview
In this assignment, you will simulate the remarkable biological process by which cells convert DNA into small proteins called peptides. Peptides play critical roles in the body: modulating blood pressure, fighting microbes, building muscle, and more. About 10% of pharmaceuticals are peptide-based.

Your DNA carries the instructions for making peptides and all other proteins as well.

**Constraints:**  
- **Allowed Constructs:** Only conditionals, loops, and string operations  
- **Disallowed:** Lists, dictionaries, sets, file I/O, object-oriented programming

---

### Background

- DNA is made of four nucleotides: A, G, T, and C.

- It is transcribed into messenger RNA (mRNA), which uses U (Uracil) instead of T.

- mRNA is then read in groups of 3 nucleotides (codons), each specifying one amino acid.

- Ribosomes read the codons, and link the corresponding amino acids to
build proteins. Ribosomes start building at a START codon (AUG → M)
and stop at a STOP codon (UAA, UAG, UGA → *).

We will simulate this using only **Strings and Tuples** — **No Lists, Dictionaries, or sets allowed**.

---

### Q1: Transcribe DNA to mRNA

Write a function

```python
def transcribe_dna_to_mrna(dna: str) -> str:
    ...
```

which takes a DNA sequence and returns an mRNA sequence

- If the DNA contains `'ATG'`, treat it as a **coding strand**: replace all `'T'` with `'U'`.
- If not, treat it as a **template strand** using this mapping:
    - A → U
    - T → A
    - C → G
    - G → C

**Example:**

```python
transcribe_dna_to_mrna("ATGGCC") → "AUGGCC"
transcribe_dna_to_mrna("TACCGG") → "AUGGCC"
```

---

### Q2: Codons and Amino Acids

#### Q2a: Convert Codon to Amino Acid

Write a function

```python
def codon_to_amino(codon: str) -> str:
    ...
```

which takes a 3 letter mRNA codon and maps it to the corresponding
amino acid using the following table of Amino acids and the
codons associated with them:

- Phenylalanine (F): UUU, UUC
- Leucine (L): UUA, UUG, CUU, CUC, CUA, CUG
- Isoleucine (I): AUU, AUC, AUA
- Methionine (M): AUG (Start codon)
- Valine (V): GUU, GUC, GUA, GUG
- Serine (S): UCU, UCC, UCA, UCG, AGU, AGC
- Proline (P): CCU, CCC, CCA, CCG
- Threonine (T): ACU, ACC, ACA, ACG
- Alanine (A): GCU, GCC, GCA, GCG
- Tyrosine (Y): UAU, UAC
- Histidine (H): CAU, CAC
- Glutamine (Q): CAA, CAG
- Asparagine (N): AAU, AAC
- Lysine (K): AAA, AAG
- Aspartic Acid (D): GAU, GAC
- Glutamic Acid (E): GAA, GAG
- Cysteine (C): UGU, UGC
- Tryptophan (W): UGG
- Arginine (R): CGU, CGC, CGA, CGG, AGA, AGG
- Glycine (G): GGU, GGC, GGA, GGG

Start and Stop:

- Start Codon (M): AUG
- Stop Codons (*): UAA, UAG, UGA

**Example:**

```python
codon_to_amino("AUG") → "M"
codon_to_amino("UAA") → "*"
codon_to_amino("GCU") → "A"
```

---


#### Q2b: Extract Codons from mRNA

```python
def get_codons(mrna: str) -> tuple:
    ...
```

which takes an mRNA sequence, and simply reads the codons "in-frame" which means in goups of 3, and produces a tuple of codons

**Example:**

```python
get_codons("AUGGCUUAA") → ("AUG", "GCU", "UAA")
```

---

#### Q2c: Translate Codons to Amino Acid Sequence

Write a function

```python
def translate_codons_to_amino_acids(codons: tuple) -> str:
    ...
```

which takes a tuple of codons (eg as produced in Q2b) and returns a single string of amino-acid letters using the function the you wrote in Q2a

- Use your `codon_to_amino()` function to convert codons to a string of amino acids.

**Example:**

```python
translate_codons_to_amino_acids(("AUG", "GCU", "UAA")) → "MA*"
```

---

### Q3: Build and Process Peptides

#### Q3a: Extract Peptides from Amino Acid Sequence

Write a function

```python
def build_peptides_from_amino_sequence(amino_acids: str) -> tuple:
    ...
```

that takes a string of amino acids (as produced by your Q2c function), and returns a tuple of peptides.

Here you are simulating the action of the ribosome, 

- Scan through the amino acid string.
- Start a peptide at `'M'`, end at `'*'` (stop codon).
- Include `'M'`, exclude `'*'`.

**Example:**

```python
build_peptides_from_amino_sequence("MA*MG*L*") → ("MA", "MG")
```

---

#### Q3b: Post-translational Modification

After the peptides have been produced an enzyme comes along and chops
off the leading M only if the next amino acid is one of 'ACGPSTV'.
This is called a peptide post-translational modification.

Write a function:

```python
def maybe_remove_initial_m(peptide: str) -> str:
    ...
```

- If the peptide starts with `'M'` and the next letter is in `'ACGPSTV'`, remove the `'M'`.
- Otherwise, leave it unchanged.

**Example:**

```python
maybe_remove_initial_m("MAIV") → "AIV"
maybe_remove_initial_m("MLIV") → "MLIV"
```

---

### Q4: Full Pipeline

Here you will put together the functions you have written above

Write a function

```python
def dna_to_peptides(dna: str) -> tuple:
    ...
```

Which takes a dna string and finally returns a tuple of peptides after
the post-translational step in Q3b.

- Transcribe DNA to mRNA
- Convert mRNA to codons
- Translate codons to amino acids
- Extract peptides
- Apply post-translational modification

This function should call all previous functions and return the final peptides.

**Example:**

```python
dna_to_peptides("ATGGCCATTGTAATGGGCCGCTGAATGCGTGA") → ("AIVMGR",)
```

(Assuming `'M'` is removed in the first case due to `'A'` following)

---

### Q5: Peptides Frequency Count

To show a cleaner output, write a function

```python
def peptides_freq_count(peptides: tuple) -> tuple:
    ...
```

Which takes a tuple of possibly recurring peptides and returns a tuple of tuples of each unique peptide and the number of times it occurs.

**Hint:** Write a helper function `count_occurences(p: str, peptides: tuple)` that takes a peptide p and returns how many times it occurs in the peptides tuple

**Example:**

```python
peptides_freq_count(("GL", "MRL", "MFL", "MMM", "MMM", "MFL")) → (("GL", 1), ("MRL", 1), ("MFL", 2), ("MMM", 2))
```
---

All functions must be implemented using only **strings and tuples**, no lists or dictionaries allowed.

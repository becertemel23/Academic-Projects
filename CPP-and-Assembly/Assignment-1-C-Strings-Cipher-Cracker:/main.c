/**
 * COMP 201 - Spring 2026
 * Assignment 1 - Strings in C: The Imitation Game
 * This file contains the main function for the cipher cracking program.
 * The main function parses command-line arguments and calls
 * the functions to be implemented by the students.
 */

#include <ctype.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_TEXT_SIZE 100000
#define MAX_LINE_LEN 2048
#define MAX_WORD_LEN 64
#define MAX_WORD_COUNT 50
#define ALPHABET_SIZE 26

/**
 * Task 1: Known-Plaintext Caesar Attack
 *
 * Decrypt a Caesar cipher when a known word appears in the plaintext.
 * Try all 26 possible shifts and check if the known word appears in the result.
 *
 * @param encrypted_path: path to the encrypted file
 * @param known_word: a word guaranteed to appear in the plaintext
 * @param output_path: path to write the decrypted text
 * @return 0 on success, non-zero on failure
 */
static char file_buffer[MAX_TEXT_SIZE];
static char decrypted_temp_buf[MAX_TEXT_SIZE];
static char copy_decrypted_temp_buf[MAX_TEXT_SIZE];
static char copy_known_word_buf[MAX_WORD_LEN];

char shift_char(char c, int shift){// Implement a helper function to shift chars
  if (c >= 'A' && c <= 'Z'){
    return ((c - 'A' - shift + 26) % 26) + 'A';
  }
  else if (c >= 'a' && c <= 'z'){
    return ((c - 'a' - shift + 26) % 26) + 'a';
  }
  else{
    return c;
  }
  
}
char* read_file(const char* path, long* size){// Impelement a helper function to read file
  FILE *fp;
  long fsize;

  fp = fopen(path, "r");
  if (fp == NULL){
    return NULL;
  }

  fseek(fp, 0, SEEK_END);
  fsize = ftell(fp);
  rewind(fp);

  if (fsize >= MAX_TEXT_SIZE) fsize = MAX_TEXT_SIZE - 1;

  fread(file_buffer, 1, fsize, fp);
  file_buffer[fsize] = '\0';

  fclose(fp);

  if (size != NULL){
    *size = fsize;
  }

  return file_buffer;
}
int write_file(const char* path, const char* content) {// Impelement a helper function to write file
    FILE *fp;

    fp = fopen(path, "w");
    if (fp == NULL) {
        return -1;
    }

    if (fputs(content, fp) == EOF) {
        fclose(fp);
        return -1;
    }

    fclose(fp);
    return 0;
}
int caesar_known_word(const char *encrypted_path, const char *known_word,
                      const char *output_path) {
  // TEMP: echo arguments to verify wiring. Delete this block after testing.
  printf("[caesar-known] encrypted=\"%s\" known_word=\"%s\" output=\"%s\"\n",
         encrypted_path, known_word, output_path);
  // TODO: Implement according to description.
  long fsize;
  char *buffer;
  int length_known_word;
  int i;
  int s;

  buffer = read_file(encrypted_path, &fsize);
  if (buffer == NULL){
    return -1;
  }
  // Allocate memory for variables
  length_known_word = strlen(known_word);
  if (length_known_word >= MAX_WORD_LEN) length_known_word = MAX_WORD_LEN - 1;

  // Translate the known word to case insensitive
  for ( i = 0; i < length_known_word; i++){
    copy_known_word_buf[i] = tolower((unsigned char)known_word[i]);
  }
  copy_known_word_buf[length_known_word] = '\0';
  // Use brute force to find known word in file
  for ( s = 0; s <= 25; s++){
    for ( i = 0; i < fsize; i++){
      decrypted_temp_buf[i] = shift_char(buffer[i],s);
      copy_decrypted_temp_buf[i] = tolower((unsigned char)decrypted_temp_buf[i]);
    }
    decrypted_temp_buf[fsize] = '\0';
    copy_decrypted_temp_buf[fsize] = '\0';
    // Check known word in shifted text
    if (strstr(copy_decrypted_temp_buf, copy_known_word_buf) != NULL){
        
        write_file(output_path, decrypted_temp_buf);
        return 0;
    }
  }

  return 1;
}

/**
 * Task 2a: Vigenère Encryption
 *
 * Encrypt plaintext using the Vigenère cipher with a given key.
 *
 * @param plaintext_path: path to the plaintext file
 * @param key: encryption key (case-insensitive)
 * @param output_path: path to write the encrypted text
 * @return 0 on success, non-zero on failure
 */
static char file_buffer[MAX_TEXT_SIZE];
static char encrypted_text_buf[MAX_TEXT_SIZE];

int vigenere_encrypt(const char *plaintext_path, const char *key,
                     const char *output_path) {
  // TEMP: echo arguments to verify wiring. Delete this block after testing.
  printf("[vigenere-encrypt] plaintext=\"%s\" key=\"%s\" output=\"%s\"\n",
         plaintext_path, key, output_path);
  // TODO: Implement according to description.
  // Initialize variables
  long fsize;
  char *buffer;
  char *encrypted_text;
  int key_len;
  int i;
  int j;

  // Read file
  buffer = read_file(plaintext_path, &fsize);
  if (buffer == NULL) {
    return -1;
  }
  // Allocate memory for text
  encrypted_text = encrypted_text_buf;
  
  key_len = strlen(key);
  j = 0;
  // With key encrypt the text 
  for (i = 0; i < fsize; i++) {
      char p = buffer[i];

      if (isalpha((unsigned char)p)) {
          int shift = tolower((unsigned char)key[j % key_len]) - 'a';
          if (isupper((unsigned char)p)) {
              encrypted_text[i] = ((p - 'A' + shift) % 26) + 'A';
          }
          else {
              encrypted_text[i] = ((p - 'a' + shift) % 26) + 'a';
          }

          j++;
        }
        else {
          encrypted_text[i] = p;
      }
    }
  encrypted_text[fsize] = '\0';
  // Write text to file 
  write_file(output_path, encrypted_text);
  return 0;
}

/**
 * Task 2b: Vigenère Decryption
 *
 * Decrypt Vigenère ciphertext with a known key.
 *
 * @param encrypted_path: path to the encrypted file
 * @param key: decryption key (case-insensitive)
 * @param output_path: path to write the decrypted text
 * @return 0 on success, non-zero on failure
 */
static char file_buffer[MAX_TEXT_SIZE];
static char decrypted_text_buf[MAX_TEXT_SIZE];

int vigenere_decrypt(const char *encrypted_path, const char *key,
                     const char *output_path) {
  // TEMP: echo arguments to verify wiring. Delete this block after testing.
  printf("[vigenere-decrypt] encrypted=\"%s\" key=\"%s\" output=\"%s\"\n",
         encrypted_path, key, output_path);
  // TODO: Implement according to description.
  // Define variables at beginning of the function
  long fsize;
  char *buffer;
  char *decrypted_text;
  int key_len;
  int i;
  int j;

  // Read the encrypted file into a buffer
  buffer = read_file(encrypted_path, &fsize);
  if (buffer == NULL) {
    return -1;
  }

  // Allocate memory for the decrypted text
  decrypted_text = decrypted_text_buf;

  key_len = strlen(key);
  j = 0; // Initialize key index

  // Decrypt each character in the buffer
  for (i = 0; i < fsize; i++) {
      char c = buffer[i];

      if (isalpha((unsigned char)c)) {
          int shift = tolower((unsigned char)key[j % key_len]) - 'a';

          if (isupper((unsigned char)c)) {
              decrypted_text[i] = ((c - 'A' - shift + 26) % 26) + 'A';
          }
          else {
              decrypted_text[i] = ((c - 'a' - shift + 26) % 26) + 'a';
          }

          j++;
      }
      else {
          decrypted_text[i] = c;
      }
    }
  decrypted_text[fsize] = '\0';

  // Write the decrypted text to the output file
  write_file(output_path, decrypted_text);
  return 0;
}

/**
 * Task 3: Frequency-Based Caesar Cracking
 *
 * Crack a Caesar cipher using letter frequency analysis.
 * Try all 26 shifts and compute chi-squared statistic for each.
 * The shift with the lowest chi-squared value is most likely correct.
 *
 * @param encrypted_path: path to the encrypted file
 * @param output_path: path to write the decrypted text
 * @param results_path: path to write chi-squared scores for all shifts
 * @return 0 on success, non-zero on failure
 */
int caesar_frequency(const char *encrypted_path, const char *output_path,
                     const char *results_path) {
  // TEMP: echo arguments to verify wiring. Delete this block after testing.
  printf("[caesar-freq] encrypted=\"%s\" output=\"%s\" results=\"%s\"\n",
         encrypted_path, output_path, results_path);
  // TODO: Implement according to description.
  long fsize, total_letters = 0;
  char *buffer, *decrypted_text;
  int counts[26] = {0};
  double chi2_values[26];
  int best_shift = 0;
  double min_chi2;
  int i, s;

  // Homework formula: English letter frequencies (in percentage)
  double english_freqs[26] = {
      8.17, 1.29, 2.78, 4.25, 12.70, 2.23, 2.02, 6.09, 6.97, 0.15, 0.77, 4.03, 
      2.41, 6.75, 7.51, 1.93, 0.10, 5.99, 6.33, 9.06, 2.76, 0.98, 2.36, 0.15, 
      1.97, 0.07
  };

  buffer = read_file(encrypted_path, &fsize);
  if (buffer == NULL) {
    return -1;
  }

  // Count letter frequencies in the encrypted text
  for (i = 0; i < fsize; i++) {
      if (isalpha((unsigned char)buffer[i])) {
          counts[tolower((unsigned char)buffer[i]) - 'a']++;
          total_letters++;
      }
  }

  if (total_letters == 0) { 
    return -1; 
  }

  // For each possible shift compute the chi-squared statistic
  for (s = 0; s < 26; s++) {
      chi2_values[s] = 0.0;
      for (i = 0; i < 26; i++) {
          double Ei = (english_freqs[i] / 100.0) * (double)total_letters;
            
          double Oi = (double)counts[(i + s) % 26];
            
          chi2_values[s] += ((Oi - Ei) * (Oi - Ei)) / Ei;
      }
  }

  
  min_chi2 = chi2_values[0];
  for (s = 1; s < 26; s++) {
      if (chi2_values[s] < min_chi2) {
          min_chi2 = chi2_values[s];
          best_shift = s;
      }
  }

  // Create results file
  FILE *rf = fopen(results_path, "w");
  if (rf) {
      for (s = 0; s < 26; s++) {
          fprintf(rf, "Shift %d: chi2 = %.2f", s, chi2_values[s]);
          if (s == best_shift) {
              fprintf(rf, " <-- best");
          }
          fprintf(rf, "\n");
      }
      fprintf(rf, "Best shift: %d (chi2 = %.2f)\n", best_shift, min_chi2);
      fclose(rf);
  }

  // Decrypt using best shift
  decrypted_text = decrypted_text_buf;
  for (i = 0; i < fsize; i++) {
      decrypted_text[i] = shift_char(buffer[i], best_shift);
  }
  decrypted_text[fsize] = '\0';
  write_file(output_path, decrypted_text);

  return 0;
}

/**
 * Task 4: Vigenère Key Recovery via Frequency Analysis
 *
 * Recover the Vigenère key when its length is known.
 * Split ciphertext into groups based on key length and apply
 * frequency analysis to each group independently.
 *
 * @param encrypted_path: path to the encrypted file
 * @param key_length: length of the encryption key
 * @param output_path: path to write the decrypted text
 * @param results_path: path to write the recovered key
 * @return 0 on success, non-zero on failure
 */
static char file_buffer[MAX_TEXT_SIZE];
static char decrypted_text_buf[MAX_TEXT_SIZE];
static char alpha_only_buf[MAX_TEXT_SIZE];
static char recovered_key_buf[MAX_WORD_LEN];

int vigenere_frequency(const char *encrypted_path, int key_length,
                       const char *output_path, const char *results_path) {
  // TEMP: echo arguments to verify wiring. Delete this block after testing.
  printf("[vigenere-freq] encrypted=\"%s\" key_length=%d output=\"%s\" "
         "results=\"%s\"\n",
         encrypted_path, key_length, output_path, results_path);
  // TODO: Implement according to description.
  long fsize, alpha_count = 0;
  char *buffer, *alpha_only, *decrypted_text, *recovered_key;
  int i, j, s, best_shift;
  double min_chi2, current_chi2;
    
  double english_freqs[26] = {
        8.17, 1.29, 2.78, 4.25, 12.70, 2.23, 2.02, 6.09, 6.97, 0.15, 0.77, 4.03, 
        2.41, 6.75, 7.51, 1.93, 0.10, 5.99, 6.33, 9.06, 2.76, 0.98, 2.36, 0.15, 
        1.97, 0.07
  };

  buffer = read_file(encrypted_path, &fsize);
  if (!buffer) {
    return -1;
  }

    
  alpha_only = alpha_only_buf;
  for (i = 0; i < fsize; i++) {
      if (isalpha((unsigned char)buffer[i])) {
          alpha_only[alpha_count++] = tolower((unsigned char)buffer[i]);
      }
  }
  alpha_only[alpha_count] = '\0';

  recovered_key = recovered_key_buf;

  // For each group of letters corresponding to a key position
  for (i = 0; i < key_length; i++) {
      int group_counts[26] = {0};
      int group_total = 0;

      for (j = i; j < alpha_count; j += key_length) {
          group_counts[alpha_only[j] - 'a']++;
          group_total++;
      }

      // Find the best shift
      best_shift = 0;
      min_chi2 = -1.0;

      for (s = 0; s < 26; s++) {
          current_chi2 = 0.0;
          for (int k = 0; k < 26; k++) {
              double Ei = (english_freqs[k] / 100.0) * (double)group_total;
              double Oi = (double)group_counts[(k + s) % 26];
              current_chi2 += ((Oi - Ei) * (Oi - Ei)) / Ei;
          }

          if (min_chi2 < 0 || current_chi2 < min_chi2) {
              min_chi2 = current_chi2;
              best_shift = s;
          }
      }
      recovered_key[i] = 'A' + best_shift;
  }
  recovered_key[key_length] = '\0';

  // Decrypt the text
  decrypted_text = decrypted_text_buf;
  int key_idx = 0;
  for (i = 0; i < fsize; i++) {
      if (isalpha((unsigned char)buffer[i])) {
          int shift = recovered_key[key_idx % key_length] - 'A';
          decrypted_text[i] = shift_char(buffer[i], shift);
          key_idx++;
      } 
      else {
          decrypted_text[i] = buffer[i];
      }
  }
  decrypted_text[fsize] = '\0';

  // Write results to files
  FILE *rf = fopen(results_path, "w");
  if (rf) {
      fprintf(rf, "Key length: %d\n", key_length);
      fprintf(rf, "Recovered key: %s\n", recovered_key);
      fclose(rf);
  }
  write_file(output_path, decrypted_text);

  return 0;
}

/**
 * Task 5: Caesar Cracking via Common Word Scoring
 *
 * Crack a Caesar cipher by scoring candidate decryptions based on
 * how many common English words they contain.
 *
 * @param encrypted_path: path to the encrypted file
 * @param wordlist_path: path to the common words list with scores
 * @param output_path: path to write the decrypted text
 * @param results_path: path to write scores for all shifts
 * @return 0 on success, non-zero on failure
 */
int caesar_word_score(const char *encrypted_path, const char *wordlist_path,
                      const char *output_path, const char *results_path) {
  // TEMP: echo arguments to verify wiring. Delete this block after testing.
  printf("[caesar-words] encrypted=\"%s\" wordlist=\"%s\" output=\"%s\" "
         "results=\"%s\"\n",
         encrypted_path, wordlist_path, output_path, results_path);
  // TODO: Implement according to description.
  long fsize;
  char *buffer, *temp_decrypted;
  static char dictionary_words[50][64];
  static int dictionary_scores[50];
  int dict_size = 0;
  int i, s, j;
  int shift_scores[26] = {0};
  int best_shift = 0, max_score = -1;
  FILE *wf, *rf;

  // Read wordlist
  wf = fopen(wordlist_path, "r");
  if (!wf) {
    return -1;
  }
  while (dict_size < 50 && fscanf(wf, "%63s %d", dictionary_words[dict_size], &dictionary_scores[dict_size]) == 2) {
      // Translate dictionary words to lowercase for case-insensitive comparison
      for (int k = 0; dictionary_words[dict_size][k]; k++) {
          dictionary_words[dict_size][k] = tolower((unsigned char)dictionary_words[dict_size][k]);
      }
      dict_size++;
  }
  fclose(wf);

  // Read encrypted file
  buffer = read_file(encrypted_path, &fsize);
  if (!buffer) {
    return -1;
  }
  temp_decrypted = decrypted_text_buf;

  // For each shift, decrypt and score
  for (s = 0; s < 26; s++) {
      for (i = 0; i < fsize; i++) {
          temp_decrypted[i] = shift_char(buffer[i], s);
      }
      temp_decrypted[fsize] = '\0';

      // Extract words and score
      for (i = 0; i < fsize; i++) {
          if (isalpha((unsigned char)temp_decrypted[i])) {
              char current_word[128];
              int len = 0;
              while (i < fsize && isalpha((unsigned char)temp_decrypted[i])) {
                  if (len < 127) {
                      // Translate to lowercase
                      current_word[len++] = tolower((unsigned char)temp_decrypted[i]);
                  }
                  i++;
              }
              current_word[len] = '\0';

              // Compare with dictionary and score  
              for (j = 0; j < dict_size; j++) {
                  if (strcmp(current_word, dictionary_words[j]) == 0) {
                      shift_scores[s] += dictionary_scores[j];
                  }
                }
            }
        }
    }

    // Find best shift 
    for (s = 0; s < 26; s++) {
        if (shift_scores[s] > max_score) {
            max_score = shift_scores[s];
            best_shift = s;
        }
    }

    // Save scores
    rf = fopen(results_path, "w");
    if (rf) {
        for (s = 0; s < 26; s++) {
            fprintf(rf, "Shift %d: score = %d%s\n", s, shift_scores[s], (s == best_shift ? " <-- best" : ""));
        }
        fprintf(rf, "Best shift: %d (score = %d)\n", best_shift, max_score);
        fclose(rf);
    }

    // Decrypt best shift and write output
    for (i = 0; i < fsize; i++) temp_decrypted[i] = shift_char(buffer[i], best_shift);
    write_file(output_path, temp_decrypted);

    return 0;
}

void print_usage(const char *prog_name) {
  fprintf(stderr, "Usage:\n");
  fprintf(stderr,
          "  %s caesar-known <encrypted_file> <known_word> <output_file>\n",
          prog_name);
  fprintf(stderr,
          "  %s vigenere-encrypt <plaintext_file> <key> <output_file>\n",
          prog_name);
  fprintf(stderr,
          "  %s vigenere-decrypt <encrypted_file> <key> <output_file>\n",
          prog_name);
  fprintf(stderr,
          "  %s caesar-freq <encrypted_file> <output_file> <results_file>\n",
          prog_name);
  fprintf(stderr,
          "  %s vigenere-freq <encrypted_file> <key_length> <output_file> "
          "<results_file>\n",
          prog_name);
  fprintf(stderr,
          "  %s caesar-words <encrypted_file> <wordlist_file> <output_file> "
          "<results_file>\n",
          prog_name);
}

int main(int argc, char *argv[]) {
  // Check for minimum number of arguments
  if (argc < 2) {
    fprintf(stderr, "Error: No subcommand provided.\n");
    print_usage(argv[0]);
    return EXIT_FAILURE;
  }

  const char *command = argv[1];

  if (strcmp(command, "caesar-known") == 0) {
    // Command: ./enigma caesar-known message1_encrypted.txt ATTACK
    // message1_decrypted.txt
    if (argc != 5) {
      fprintf(stderr, "Error: Invalid arguments for 'caesar-known'\n");
      print_usage(argv[0]);
      return EXIT_FAILURE;
    }
    return caesar_known_word(argv[2], argv[3], argv[4]);

  } else if (strcmp(command, "vigenere-encrypt") == 0) {
    // Command: ./enigma vigenere-encrypt message2_plaintext.txt ENIGMA
    // message2_encrypted.txt
    if (argc != 5) {
      fprintf(stderr, "Error: Invalid arguments for 'vigenere-encrypt'\n");
      print_usage(argv[0]);
      return EXIT_FAILURE;
    }
    return vigenere_encrypt(argv[2], argv[3], argv[4]);

  } else if (strcmp(command, "vigenere-decrypt") == 0) {
    // Command: ./enigma vigenere-decrypt message3_encrypted.txt ENIGMA
    // message3_decrypted.txt
    if (argc != 5) {
      fprintf(stderr, "Error: Invalid arguments for 'vigenere-decrypt'\n");
      print_usage(argv[0]);
      return EXIT_FAILURE;
    }
    return vigenere_decrypt(argv[2], argv[3], argv[4]);

  } else if (strcmp(command, "caesar-freq") == 0) {
    // Command: ./enigma caesar-freq message4_encrypted.txt
    // message4_decrypted.txt message4_results.txt
    if (argc != 5) {
      fprintf(stderr, "Error: Invalid arguments for 'caesar-freq'\n");
      print_usage(argv[0]);
      return EXIT_FAILURE;
    }
    return caesar_frequency(argv[2], argv[3], argv[4]);

  } else if (strcmp(command, "vigenere-freq") == 0) {
    // Command: ./enigma vigenere-freq message5_encrypted.txt 6
    // message5_decrypted.txt message5_results.txt
    if (argc != 6) {
      fprintf(stderr, "Error: Invalid arguments for 'vigenere-freq'\n");
      print_usage(argv[0]);
      return EXIT_FAILURE;
    }

    int key_length = atoi(argv[3]);
    if (key_length <= 0) {
      fprintf(stderr, "Error: Invalid key_length. Value must be positive.\n");
      return EXIT_FAILURE;
    }

    return vigenere_frequency(argv[2], key_length, argv[4], argv[5]);

  } else if (strcmp(command, "caesar-words") == 0) {
    // Command: ./enigma caesar-words message6_encrypted.txt common_words.txt
    // message6_decrypted.txt message6_results.txt
    if (argc != 6) {
      fprintf(stderr, "Error: Invalid arguments for 'caesar-words'\n");
      print_usage(argv[0]);
      return EXIT_FAILURE;
    }
    return caesar_word_score(argv[2], argv[3], argv[4], argv[5]);

  } else {
    fprintf(stderr, "Error: Unknown subcommand '%s'\n", command);
    print_usage(argv[0]);
    return EXIT_FAILURE;
  }

  return 0;
}

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "timeline.h"

#define MAX_LINE_LENGTH 1024

// --- PROTOTYPES ---
void process_commands(const char* filename);

// --- MAIN ---
int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <commands_file.txt>\n", argv[0]);
        return 1;
    }

    process_commands(argv[1]);

    return 0;
}

// --- PARSER IMPLEMENTATION ---
void process_commands(const char* filename) {
    FILE* file = fopen(filename, "r");
    if (!file) {
        printf("Error: Cannot open file %s\n", filename);
        return;
    }

    char line_buffer[MAX_LINE_LENGTH];
    TimelineNode* root_timeline = NULL; // Pointer to the Prime Timeline

    while (fgets(line_buffer, sizeof(line_buffer), file)) {
        // Remove trailing newline characters
        line_buffer[strcspn(line_buffer, "\r\n")] = 0;

        // Skip empty lines
        if (strlen(line_buffer) == 0 || line_buffer[0] == '#') continue;

        // --- STUDENT IMPLEMENTATION REQUIRED HERE ---
        
        // Step 1: Identify the command (INIT, ADD_EVENT, BRANCH, PRUNE, PRINT)
        // Step 2: Extract the arguments from the string.
        // HINT for ADD_EVENT: Extracting the string between double quotes can be done by manual pointer manipulation (strchr).
        // Step 3: Find the target timeline (you may need a helper search function).
        // Step 4: Call the appropriate function from timeline.h.

        char command[32];
        sscanf(line_buffer, "%s", command);

        if (strcmp(command, "INIT") == 0) {
            // int id;
            int id;
            // Extract ID and create root_timeline
            sscanf(line_buffer, "INIT %d", &id);
            root_timeline = create_timeline(NULL, id);
        } 
        else if (strcmp(command, "ADD_EVENT") == 0) {
            // Extract timeline_id, event_id, description string, and impact_score
            // BE CAREFUL: The description contains spaces!
            int timeline_id, event_id, impact_score;
            sscanf(line_buffer, "ADD_EVENT %d %d", &timeline_id, &event_id);
            char* first_quote = strchr(line_buffer, '\"');
            char* last_quote = strrchr(line_buffer, '\"');
            
            if (first_quote != NULL && last_quote != NULL && first_quote != last_quote){
                int length = last_quote - first_quote - 1;
                char temp_desc[512]; // Add_event function allocate memory for description we don't have to allocate memoory again
                strncpy(temp_desc, first_quote + 1, length);
                temp_desc[length] = '\0';
                sscanf(last_quote + 1," %d", &impact_score );

                TimelineNode* target = find_timeline(root_timeline, timeline_id);
                if (target != NULL){
                    add_event(target, event_id, temp_desc, impact_score);
                } else {
                    printf("Timeline %d not found.\n", timeline_id);
                }
            }

        }
        else if (strcmp(command, "BRANCH") == 0) {
            // Extract parent_id and new_id
            // Find parent timeline, then call branch_timeline
            int parent_id, new_id;
            if (sscanf(line_buffer, "BRANCH %d %d", &parent_id, &new_id) == 2) {
                TimelineNode* parent_Node = find_timeline(root_timeline, parent_id);
                if (parent_Node != NULL) {
                    branch_timeline(parent_Node, new_id);
                } else {
                    printf("Parent timeline %d not found.\n", parent_id);
                }
            }
        }
        else if (strcmp(command, "PRUNE") == 0) {
            // Extract target_id
            // Call prune_branch
            int target_id;
            sscanf(line_buffer, "PRUNE %d", &target_id);
            TimelineNode* target_Node = find_timeline(root_timeline, target_id);
            if (target_Node == NULL) {
                printf("Timeline %d not found.\n", target_id);
                continue;
            }
            if (target_Node->parent == NULL){
                destroy_timeline(root_timeline);
                root_timeline = NULL;
            } else{
                TimelineNode* parent_Node = target_Node->parent;
                int index = -1;
                for (int i = 0; i < parent_Node->branch_count; i++){
                    if (parent_Node->branches[i] == target_Node) {
                        index = i;
                        break;
                    }        
                }
                
                if (index != -1){
                    for (int i = index; i < parent_Node->branch_count - 1; i++){
                        parent_Node->branches[i] = parent_Node->branches[i + 1];
                    }
                    parent_Node->branches--;
                }

                destroy_timeline(target_Node);
            }

        }
        else if (strcmp(command, "PRINT") == 0) {
            // Implement a tree traversal to print the timeline state
            printf("--- BTA TIMALINE REPORT ---\n");
            print_helper(root_timeline, 0);
            printf("---------------------------\n");
        }
        else {
            printf("Unknown command: %s\n", command);
        }
    }

    // --- FINAL CLEANUP ---
    // Make sure to completely destroy the root_timeline here before the program exits
    // Otherwise, Valgrind will report memory leaks!
    if (root_timeline != NULL){
        destroy_timeline(root_timeline);
    }

    fclose(file);
}
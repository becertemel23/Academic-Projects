#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "timeline.h"

TimelineNode* create_timeline(TimelineNode* parent, int id) {
    // 1. Allocate memory for a new TimelineNode.
    TimelineNode *newNode = (TimelineNode *) malloc(sizeof(TimelineNode));
    if (newNode == NULL) return NULL;
    // 2. Allocate memory for the events array using INITIAL_CAPACITY.
    newNode->event_capacity = INITIAL_CAPACITY;
    newNode->events = (Event **) malloc(newNode->event_capacity * sizeof(Event *));
    if (newNode->events == NULL) return NULL;
    // 3. Allocate memory for the branches array using INITIAL_CAPACITY.
    newNode->branch_capacity = INITIAL_CAPACITY;
    newNode->branches = (TimelineNode **) malloc(newNode->branch_capacity * sizeof(TimelineNode *));
    if (newNode->branches == NULL) return NULL;
    // 4. Initialize counts to 0 and capacities to INITIAL_CAPACITY.
    newNode->event_count = 0;
    newNode->branch_count = 0;
    // 5. Set the parent pointer and timeline_id.
    newNode->timeline_id = id;
    newNode->parent = parent;
    // 6. Return the newly created node.
    
    return newNode; // Placeholder
}

void add_event(TimelineNode* node, int event_id, const char* desc, int impact) {
    // 1. Check if the events array is full (event_count == event_capacity).
    // 2. If full, use realloc() to double the capacity.
    if (node->event_count == node->event_capacity){
        node->event_capacity *= 2;
        node->events = (Event **) realloc(node->events,node->event_capacity * sizeof(Event *));
    }
    // 3. Allocate memory for a new Event struct.
    Event *newEvent = (Event *) malloc(sizeof(Event));
    // 4. CRITICAL: Perform a DEEP COPY of the 'desc' string (malloc exact space + strcpy).
    char *copyDesc = (char *) malloc((strlen(desc) + 1) * sizeof(char));
    strcpy(copyDesc, desc);
    // 5. Set event_id and impact_score.
    newEvent->event_id = event_id;
    newEvent->description = copyDesc;
    newEvent->impact_score = impact;
    // 6. Add the new event pointer to the events array and increment the count.
    node->events[node->event_count] = newEvent;
    node->event_count++;
}

TimelineNode* branch_timeline(TimelineNode* parent_node, int new_timeline_id) {
    // 1. Check if the parent_node's branches array is full.
    // 2. If full, use realloc() to double the capacity.
    if (parent_node->branch_count == parent_node->branch_capacity){
        parent_node->branch_capacity *= 2;
        parent_node->branches = (TimelineNode **) realloc(parent_node->branches, parent_node->branch_capacity * sizeof(TimelineNode *));
    }
    // 3. Create a new timeline node using create_timeline().
    TimelineNode *newTimeLine = create_timeline(parent_node, new_timeline_id);
    // 4. Add the new timeline pointer to the parent's branches array.
    parent_node->branches[parent_node->branch_count] = newTimeLine;
    // 5. Increment the parent's branch_count.
    parent_node->branch_count++;
    // 6. Return the new timeline node.
    
    return newTimeLine; // Placeholder
}

// Optional helper function to find a specific node
TimelineNode* find_timeline(TimelineNode* root, int target_timeline_id) {
    // Implement a search (e.g., Depth-First Search) to find the node.
    if (root == NULL) return NULL;
    if (root->timeline_id == target_timeline_id) return root;
    for (int i = 0; i < root->branch_count; i++){
        TimelineNode* result = find_timeline(root->branches[i], target_timeline_id);
        if (result != NULL) {
            return result;
        }
    }

    return NULL; // Placeholder
}

void prune_branch(TimelineNode* root, int target_timeline_id) {
    // 1. Find the target timeline node using its ID.
    TimelineNode *target = find_timeline(root, target_timeline_id);
    // 2. If found, locate its pointer in its parent's branches array.
    // 3. Remove it from the parent's array.
    // 4. CRITICAL: Shift the remaining elements in the parent's array to the left to close the gap.
    // 5. Call destroy_timeline() on the target node to free all its memory.
    if (target == NULL) return;
    if (target->parent == NULL){
        destroy_timeline(target);
        return;
    }

    TimelineNode *parent = target->parent;
    int index = -1;

    for (int i = 0; i < parent->branch_count; i++){
        if (parent->branches[i] == target){
            index = i;
            break;
        }
    }

    if (index != -1){
        for (int i = index; i < parent->branch_count - 1; i++){
            parent->branches[i] = parent->branches[i + 1];
        }
        parent->branch_count--;
    }
    destroy_timeline(target);
}

void destroy_timeline(TimelineNode* node) {
    // 1. Recursively call destroy_timeline() on all child branches.
    if (node == NULL) return;
    for (int i = 0; i < node->branch_count; i++){
        destroy_timeline(node->branches[i]);
    }
    // 2. Free the branches array.
    // 3. Iterate through all events in the events array:
    //    a. Free the dynamically allocated description string.
    //    b. Free the Event struct itself.
    // 4. Free the events array.
    // 5. Free the TimelineNode itself.
    for (int i = 0; i < node->event_count; i++){
        free(node->events[i]->description);
        free(node->events[i]);
    }
    free(node->events);
    free(node->branches);
    free(node);
}

void print_helper(TimelineNode* node, int depth){
    if (node == NULL) return;
    printf("%*sTimeline [%d]:\n", depth * 2, "", node->timeline_id);
    for (int i = 0; i < node->event_count; i++){
        printf("%*sEvent [%d]: %s (Impact: %d)\n",
        (depth * 1) * 2, "",
        node->events[i]->event_id,
        node->events[i]->description,
        node->events[i]->impact_score);
    }
    for (int i = 0; i < node->branch_count; i++){
        printf("%*s-> Branch:\n", (depth + 1) * 2, "");
        print_helper(node->branches[i], depth + 1);
    }
}
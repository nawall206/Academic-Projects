/* undo.h
 * a linked list stack of game states for undo functionality.
 */

#ifndef UNDO_H
#define UNDO_H

/* 
 * Struct representing a single node in the undo stack.
 * - state: pointer to a generic copy of the game state (or any data)
 * - next: pointer to the next node in the stack (linked list)
 */
typedef struct UndoNode {
    void *state;              /* generic pointer to hold any state */
    struct UndoNode *next;    /* next node in the stack */
} UndoNode;

/* 
 * Pushes a new state onto the undo stack.
 * - stack: current top of the undo stack
 * - data: pointer to data to copy and store in the stack
 * - size: size in bytes of the data to copy
 * Returns: new top of the undo stack (the pushed node)
 */
UndoNode *push_undo(UndoNode *stack, void *data, size_t size);

/* 
 * Pops the top state from the undo stack.
 * - stack: current top of the undo stack
 * - popped_data: pointer to receive the popped data pointer
 *   (if NULL, the data is freed instead)
 * Returns: new top of the undo stack after pop
 */
UndoNode *pop_undo(UndoNode *stack, void **popped_data);

/* 
 * Frees the entire undo stack and all associated data.
 * - stack: top of the undo stack to free
 */
void free_undo_stack(UndoNode *stack);

#endif

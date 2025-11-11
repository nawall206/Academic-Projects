#include <stdlib.h>
#include <string.h>
#include "undo.h"
#include "game.h"  

/* Helper to deep copy Game struct data */
static Game *deep_copy_game(Game *orig) {
    int r;
    Game *copy = malloc(sizeof(Game));
    if (copy == NULL) return NULL;

    /* Copy all fields except pointers first (shallow copy) */
    memcpy(copy, orig, sizeof(Game));

    /* Allocate map pointer array */
    copy->map = malloc(orig->rows * sizeof(int *));
    copy->enemy_dir = malloc(orig->rows * sizeof(int *));
    if (copy->map == NULL || copy->enemy_dir == NULL) {
        free(copy->map);
        free(copy->enemy_dir);
        free(copy);
        return NULL;
    }

    /* Allocate each row and copy data */
    for (r = 0; r < orig->rows; r++) {
        copy->map[r] = malloc(orig->cols * sizeof(int));
        copy->enemy_dir[r] = malloc(orig->cols * sizeof(int));
        if (copy->map[r] == NULL || copy->enemy_dir[r] == NULL) {
            /* Free all allocated memory on failure */
            int i;
            for (i = 0; i < r; i++) {
                free(copy->map[i]);
                free(copy->enemy_dir[i]);
            }
            free(copy->map);
            free(copy->enemy_dir);
            free(copy);
            return NULL;
        }
        memcpy(copy->map[r], orig->map[r], orig->cols * sizeof(int));
        memcpy(copy->enemy_dir[r], orig->enemy_dir[r], orig->cols * sizeof(int));
    }

    /* We do not copy undo_stack pointer - undo states are independent */

    return copy;
}

/* Helper to free map and enemy_dir arrays inside a Game */
void free_game_arrays(Game *game) {
    int r;
    if (game->map != NULL) {
        for (r = 0; r < game->rows; r++) {
            free(game->map[r]);
        }
        free(game->map);
        game->map = NULL;
    }
    if (game->enemy_dir != NULL) {
        for (r = 0; r < game->rows; r++) {
            free(game->enemy_dir[r]);
        }
        free(game->enemy_dir);
        game->enemy_dir = NULL;
    }
}

/* Push undo state with deep copy */
UndoNode *push_undo(UndoNode *stack, void *data, size_t size) {
    UndoNode *new_node;
    Game *new_state;
    UndoNode *result = stack;

    /* Allocate new node */
    new_node = (UndoNode *)malloc(sizeof(UndoNode));
    if (new_node == NULL) return result;

    /* Deep copy game state */
    new_state = deep_copy_game((Game *)data);
    if (new_state == NULL) {
        free(new_node);
        return result;
    }

    new_node->state = new_state;
    new_node->next = stack;

    return new_node;
}

/* Pop undo state and replace current game state */
UndoNode *pop_undo(UndoNode *stack, void **popped_data) {
    UndoNode *next_node;
    UndoNode *result = NULL;

    if (stack != NULL) {
        if (popped_data != NULL) {
            *popped_data = stack->state;
        } else {
            /* If no one will take ownership, free everything */
            Game *g = (Game *)stack->state;
            free_game_arrays(g);
            free(g);
        }
        next_node = stack->next;
        free(stack);
        result = next_node;
    }

    return result;
}

/* Free entire undo stack */
void free_undo_stack(UndoNode *stack) {
    UndoNode *next_node;

    while (stack != NULL) {
        next_node = stack->next;

        /* Free game arrays and struct */
        if (stack->state != NULL) {
            Game *g = (Game *)stack->state;
            free_game_arrays(g);
            free(g);
        }

        free(stack);
        stack = next_node;
    }
}

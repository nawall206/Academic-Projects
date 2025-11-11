#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "game.h"
#include "undo.h"
#include "terminal.h"
#include "random.h"
#include "fileio.h"    

int main(int argc, char *argv[]) {
    Game game;            /* Main game state struct */
    char input;           /* User input character */
    int result;           /* Result of processing user input */
    int exit_flag = 0;    /* Flag to control game loop termination */
    int error_flag = 0;   /* Flag for argument or initialization error */
    Game *prev_state;     /* Pointer to store popped undo game state */
    void *tmp;            /* Temporary pointer for undo cleanup */
    FILE *fp;             /* Temporary file pointer to verify file access */

    game.undo_stack = NULL;  

    /* Check for proper usage */
    if (argc != 2) {
        fprintf(stderr, "Error: Incorrect number of arguments.\n");
        fprintf(stderr, "Usage: %s mapfile.txt\n", argv[0]);
        return 1;
    } else {
        /* Try opening the map file to check if accessible */
        fp = fopen(argv[1], "r");
        if (fp == NULL) {
            perror("Error opening map file");
            error_flag = 1;
        } else {
            fclose(fp);
        }
    }

    if (error_flag == 0) {
        initRandom();
        init_game(&game, argv[1]);
        print_game(&game);
        disableBuffer();

        while (!exit_flag) {
            printf("\nPress 'w' to move up\n"
                   "Press 's' to move down\n"
                   "Press 'a' to move left\n"
                   "Press 'd' to move right\n"
                   "Press 'u' to undo\n"
                   "Press 'q' to quit\n"
                   "Enter your move: ");

            input = getchar();

            if (input == 'q') {
                exit_flag = 1;  /* Quit Game */
            } else if (input == 'u') {
                if (game.undo_stack == NULL) {
                    printf("Nothing to undo.\n");
                } else {
                    prev_state = NULL;
                    game.undo_stack = pop_undo(game.undo_stack, (void **)&prev_state);

                   if (prev_state != NULL) {
                        UndoNode *old_stack = game.undo_stack;

                         /* Free current dynamic arrays before overwriting */
                        free_game(&game);

                        /* Copy popped state into current game */
                        game = *prev_state;

                        /* Restore undo stack manually */
                        game.undo_stack = old_stack;

                        /* Free the arrays in the popped state */
                        free_game_arrays(prev_state);

                        /* Free popped state memory */
                        free(prev_state);

                        printf("Undo successful.\n");
                        }
                    }
                print_game(&game);
            } else {
                /* Push current state before attempting a move */
                game.undo_stack = push_undo(game.undo_stack, &game, sizeof(Game));

                result = process_input(&game, input);

                if (result == 1) {
                    print_game(&game);  
                } else if (result == 0) {
                    /* Invalid move, pop undo to discard pushed state */
                    tmp = NULL;
                    game.undo_stack = pop_undo(game.undo_stack, &tmp);
                    if (tmp != NULL) free(tmp);

                    printf("Invalid move or no move made.\n");
                    print_game(&game);
                } else if (result == 2) {
                    print_game(&game);
                    printf("YOU WIN!\n");
                    exit_flag = 1;
                } else if (result == -1) {
                    print_game(&game);
                    printf("YOU LOSE!\n");
                    exit_flag = 1;
                }
            }
        }

        enableBuffer();
        free_undo_stack(game.undo_stack);
        free_game(&game);
    }

    return error_flag ? 1 : 0;
}

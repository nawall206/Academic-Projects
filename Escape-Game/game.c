#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include "newSleep.h"
#include "game.h"
#include "undo.h"
#include "random.h"
#include "enemy.h"
#include "fileio.h"
/*
 * Prints the current state of the game board to the terminal.
 * Player is shown as 'P', walls as 'O', goal as 'G', enemies as arrows, and empty spaces as blanks.
 *
 * Parameters:
 * - game: pointer to the current Game state to display
 */
void print_game(Game *game) {

    int r;
    int c;
    int val;
    Direction d;

    clearScreen();  

    /* Print top border */
    printf(" ");
    for (c = 0; c < game->cols; c++) {
        printf("*");
    }
    printf("\n");

    /* Print each row */
    for (r = 0; r < game->rows; r++) {
        printf("*"); /* Left border */

        for (c = 0; c < game->cols; c++) {
            /* Print player if on this cell */
            if (r == game->player.row && c == game->player.col) {
                printf("P");
            } else {
                val = game->map[r][c];
                /* Print based on the cell value */
                if (val == EMPTY_VALUE) {
                    printf(" ");
                } else if (val == WALL_VALUE) {
                    printf("O");
                } else if (val == GOAL_VALUE) {
                    printf("G");
                } else if (val == ENEMY_VALUE) {
                    d = game->enemy_dir[r][c];
                    if (d >= 0 && d <= 3) {
                        /* Print enemy direction as arrow */
                        char symbols[] = {'^', '>', 'v', '<'};
                        printf("%c", symbols[d]);
                    } else {
                        /* Invalid direction fallback */
                        printf("?");
                    }
                } else {
                    /* Unknown cell value fallback */
                    printf("?");
                }
            }
        }

        printf("*\n"); /* Right border */
    }

    /* Print bottom border */
    printf(" ");
    for (c = 0; c < game->cols; c++) {
        printf("*");
    }
    printf("\n");
}

/*
 * Processes a player's input command and updates the game state accordingly.
 * Handles movement, checks for collisions, goal, enemies, and updates enemy rotations.
 *
 * Parameters:
 * - game: pointer to the current Game state
 * - input: character representing player input ('w', 'a', 's', 'd' to move; 'u' for undo)
 *
 * Returns:
 * -  1 if player moved successfully
 * -  2 if player reached the goal (win)
 * -  0 if invalid move or no move made
 * - -1 if player encountered enemy or was detected (lose)
 */
int process_input(Game *game, char input) {
    int new_r, new_c;
    int dest;
    int result;

    /* Initialize new position as current player position */
    new_r = game->player.row;
    new_c = game->player.col;
    result = 0; /* Default: no move */

    /* Determine intended move based on input */
    if (input == 'w') {
        new_r = new_r - 1; /* Move up */
    } else if (input == 's') {
        new_r = new_r + 1; /* Move down */
    } else if (input == 'a') {
        new_c = new_c - 1; /* Move left */
    } else if (input == 'd') {
        new_c = new_c + 1; /* Move right */
    } else if (input == 'u') {
        /* Undo input handled elsewhere, return 0 (no move) */
        result = 0;
    } else {
        /* Invalid input, no move */
        result = 0;
    }

    /* If undo or move outside boundaries, return no move */
    if (input == 'u' || (new_r < 0 || new_r >= game->rows || new_c < 0 || new_c >= game->cols)) {
        return result;
    }

    /* Get the value at the intended destination cell */
    dest = game->map[new_r][new_c];

    /* Process destination cell */
    if (dest == WALL_VALUE) {
        /* Wall blocks movement */
        result = 0;
    } else if (dest == GOAL_VALUE) {
        /* Player reaches the goal */

        /* Clear old player position */
        game->map[game->player.row][game->player.col] = EMPTY_VALUE;

        /* Update player position */
        game->player.row = new_r;
        game->player.col = new_c;

        /* Mark new position as player */
        game->map[new_r][new_c] = PLAYER_VALUE;

        /* Rotate enemies 90 degrees clockwise */
        rotate_enemies(game);

        /* Check if player is now detected by enemies */
        if (player_in_enemy_sight(game)) {
            result = -1; /* Player loses */
        } else {
            result = 2;  /* Player wins */
        }
    } else if (dest == ENEMY_VALUE) {
        /* Player moves into enemy - lose */
        result = -1;
    } else {
        /* Normal move to empty cell */

        /* Clear old player position */
        game->map[game->player.row][game->player.col] = EMPTY_VALUE;

        /* Update player position */
        game->player.row = new_r;
        game->player.col = new_c;

        /* Mark new position as player */
        game->map[new_r][new_c] = PLAYER_VALUE;

        /* Rotate enemies 90 degrees clockwise */
        rotate_enemies(game);

        /* Check if player is detected after move */
        if (player_in_enemy_sight(game)) {
            result = -1; /* Player loses */
        } else {
            result = 1;  /* Successful move */
        }
    }

    /* Return the result of the move */
    return result;
}

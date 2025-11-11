#include <stdio.h>
#include <stdlib.h>
#include "fileio.h"
#include "random.h"
#include "enemy.h"

/* Clears the terminal screen */
void clearScreen(void) {
    printf("\033[2J\033[H");
    fflush(stdout);
}

/* Initialize the game state by reading map and allocating memory */
void init_game(Game *game, const char *filename) {
    FILE *fp;
    int r;
    int c;
    int value;

    fp = fopen(filename, "r");
    if (fp == NULL) {
        perror("Failed to open map file");
        exit(EXIT_FAILURE);
    }

    if (fscanf(fp, "%d %d", &game->rows, &game->cols) != 2) {
        fprintf(stderr, "Invalid map dimensions in file\n");
        fclose(fp);
        exit(EXIT_FAILURE);
    }

    game->undo_stack = NULL;

    game->map = (int **)malloc(game->rows * sizeof(int *));
    game->enemy_dir = (int **)malloc(game->rows * sizeof(int *));
    if (game->map == NULL || game->enemy_dir == NULL) {
        fprintf(stderr, "Memory allocation failed\n");
        fclose(fp);
        exit(EXIT_FAILURE);
    }

    for (r = 0; r < game->rows; r = r + 1) {
        game->map[r] = (int *)malloc(game->cols * sizeof(int));
        game->enemy_dir[r] = (int *)malloc(game->cols * sizeof(int));
        if (game->map[r] == NULL || game->enemy_dir[r] == NULL) {
            fprintf(stderr, "Memory allocation failed at row %d\n", r);
            fclose(fp);
            exit(EXIT_FAILURE);
        }
    }

    for (r = 0; r < game->rows; r = r + 1) {
        for (c = 0; c < game->cols; c = c + 1) {
            if (fscanf(fp, "%d", &value) != 1) {
                fprintf(stderr, "Invalid map data at (%d, %d)\n", r, c);
                fclose(fp);
                exit(EXIT_FAILURE);
            }
            game->map[r][c] = value;

            if (value == PLAYER_VALUE) {
                game->player.row = r;
                game->player.col = c;
            }

            if (value == ENEMY_VALUE) {
                game->enemy_dir[r][c] = random_UCP(0, 3);  
            } else {
                game->enemy_dir[r][c] = -1; 
            }
        }
    }

    fclose(fp);
}

/* Free dynamically allocated memory used by the game */
void free_game(Game *game) {
    int r;

    for (r = 0; r < game->rows; r = r + 1) {
        free(game->map[r]);
        free(game->enemy_dir[r]);
    }
    free(game->map);
    free(game->enemy_dir);
}

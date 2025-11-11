#include <stdio.h>
#include "enemy.h"
#include "newSleep.h"
#include "game.h"

/* 
 * Rotate all enemies 90 degrees clockwise.
 * Enemy directions are stored as integers 0-3 corresponding to UP, RIGHT, DOWN, LEFT.
 * This function cycles the direction by incrementing and wrapping around with modulo 4.
 */
void rotate_enemies(Game *game) {
    int r, c;
    for (r = 0; r < game->rows; r++) {
        for (c = 0; c < game->cols; c++) {
            if (game->map[r][c] == ENEMY_VALUE) {
                /* Increment direction and wrap using modulo 4 */
                game->enemy_dir[r][c] = (game->enemy_dir[r][c] + 1) % 4;
            }
        }
    }
}

/* 
 * Check if the player is currently in the line of sight of any enemy.
 * Returns 1 if player detected, 0 otherwise.
 *
 * For enemies facing LEFT or RIGHT:
 *   - Scan horizontally both left and right from the enemy’s position until a wall or player is found.
 * For enemies facing UP or DOWN:
 *   - Scan vertically both up and down from the enemy’s position until a wall or player is found.
 *
 * If player detected, triggers an animation where the enemy moves step-by-step toward the player.
 * If enemy reaches player, returns 1 indicating player caught.
 */
int player_in_enemy_sight(Game *game) {
    int pr = game->player.row;   /* Player row */
    int pc = game->player.col;   /* Player column */
    int r, c;
    int detected = 0;            /* Flag if player detected */
    int enemy_found = 0;         /* Flag for first enemy found that detects player */
    int er, ec;                  /* Enemy row and column for animation */
    Direction dir;
    int cc, rr;
    int wall_found;

    /* Scan all enemies for line of sight detection */
    for (r = 0; r < game->rows && !detected; r++) {
        for (c = 0; c < game->cols && !detected; c++) {
            if (game->map[r][c] == ENEMY_VALUE) {
                dir = game->enemy_dir[r][c];

                if (dir == RIGHT || dir == LEFT) {
                    /* Scan horizontally right */
                    cc = c + 1;
                    wall_found = 0;
                    while (cc < game->cols && !wall_found && !detected) {
                        if (game->map[r][cc] == WALL_VALUE) {
                            wall_found = 1;  
                        } else if (r == pr && cc == pc) {
                            detected = 1;   
                        }
                        cc++;
                    }

                    /* Scan horizontally left */
                    cc = c - 1;
                    wall_found = 0;
                    while (cc >= 0 && !wall_found && !detected) {
                        if (game->map[r][cc] == WALL_VALUE) {
                            wall_found = 1;
                        } else if (r == pr && cc == pc) {
                            detected = 1;
                        }
                        cc--;
                    }
                } else if (dir == UP || dir == DOWN) {
                    /* Scan vertically up */
                    rr = r - 1;
                    wall_found = 0;
                    while (rr >= 0 && !wall_found && !detected) {
                        if (game->map[rr][c] == WALL_VALUE) {
                            wall_found = 1;
                        } else if (rr == pr && c == pc) {
                            detected = 1;
                        }
                        rr--;
                    }

                    /* Scan vertically down */
                    rr = r + 1;
                    wall_found = 0;
                    while (rr < game->rows && !wall_found && !detected) {
                        if (game->map[rr][c] == WALL_VALUE) {
                            wall_found = 1;
                        } else if (rr == pr && c == pc) {
                            detected = 1;
                        }
                        rr++;
                    }
                }
            }
        }
    }

    /* If no detection, return 0 */
    if (!detected) {
        return 0;
    }

    /* Animate enemy movement toward player for the first enemy that detects the player */
    for (r = 0; r < game->rows && !enemy_found; r++) {
        for (c = 0; c < game->cols && !enemy_found; c++) {
            if (game->map[r][c] == ENEMY_VALUE) {
                dir = game->enemy_dir[r][c];
                wall_found = 0;

                if (dir == RIGHT || dir == LEFT) {
                    /* Check right side for player */
                    for (cc = c + 1; cc < game->cols && !wall_found; cc++) {
                        if (game->map[r][cc] == WALL_VALUE) {
                            wall_found = 1;
                        } else if (r == pr && cc == pc) {
                            enemy_found = 1;
                            break;
                        }
                    }
                    /* Check left side for player if not found yet */
                    if (!enemy_found) {
                        wall_found = 0;
                        for (cc = c - 1; cc >= 0 && !wall_found; cc--) {
                            if (game->map[r][cc] == WALL_VALUE) {
                                wall_found = 1;
                            } else if (r == pr && cc == pc) {
                                enemy_found = 1;
                                break;
                            }
                        }
                    }
                } else if (dir == UP || dir == DOWN) {
                    /* Check upwards */
                    for (rr = r - 1; rr >= 0 && !wall_found; rr--) {
                        if (game->map[rr][c] == WALL_VALUE) {
                            wall_found = 1;
                        } else if (rr == pr && c == pc) {
                            enemy_found = 1;
                            break;
                        }
                    }
                    /* Check downwards if not found */
                    if (!enemy_found) {
                        wall_found = 0;
                        for (rr = r + 1; rr < game->rows && !wall_found; rr++) {
                            if (game->map[rr][c] == WALL_VALUE) {
                                wall_found = 1;
                            } else if (rr == pr && c == pc) {
                                enemy_found = 1;
                                break;
                            }
                        }
                    }
                }

                if (enemy_found) {
                    /* Enemy detected player and will animate moving toward player */
                    er = r;
                    ec = c;

                    printf("Player detected at (%d,%d)! Beginning animation...\n", pr, pc);

                    /* Move enemy stepwise until reaching player's position */
                    while (er != pr || ec != pc) {
                        /* Clear current enemy position */
                        game->map[er][ec] = EMPTY_VALUE;

                        /* Move horizontally closer if needed */
                        if (ec < pc) {
                            ec++;
                        } else if (ec > pc) {
                            ec--;
                        }
                        /* Otherwise move vertically closer */
                        else if (er < pr) {
                            er++;
                        } else if (er > pr) {
                            er--;
                        }

                        /* Set enemy on new position */
                        game->map[er][ec] = ENEMY_VALUE;

                        /* Update enemy direction based on movement */
                        if (er < r) {
                            game->enemy_dir[er][ec] = UP;
                        } else if (er > r) {
                            game->enemy_dir[er][ec] = DOWN;
                        } else if (ec < c) {
                            game->enemy_dir[er][ec] = LEFT;
                        } else if (ec > c) {
                            game->enemy_dir[er][ec] = RIGHT;
                        }

                        /* Update variables for next step */
                        r = er;
                        c = ec;

                        /* Redraw game and pause to animate */
                        print_game(game);
                        newSleep(0.5);
                    }

                    /* Enemy reached player - game over */
                    printf("Enemy reached player. YOU LOSE!\n");
                    return 1;
                }
            }
        }
    }

    /* No enemy reached player */
    return 0;
}

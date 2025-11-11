#ifndef FILEIO_H
#define FILEIO_H

#include "game.h"

/* Clears the terminal screen */
void clearScreen(void);

/* Initialize game by reading map file and allocating memory */
void init_game(Game *game, const char *filename);

/* Free memory allocated by init_game */
void free_game(Game *game);

#endif 

#ifndef ENEMY_H
#define ENEMY_H

#include "game.h"

/* Rotate all enemies 90 degrees clockwise */
void rotate_enemies(Game *game);

/* Check if player is in enemy line of sight and animate enemy if detected*/ 
/* Returns 1 if player detected (game lost), 0 otherwise */
int player_in_enemy_sight(Game *game);

#endif 

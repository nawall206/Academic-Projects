#ifndef GAME_H
#define GAME_H
#include "undo.h"  

#define MAX_ROWS 100
#define MAX_COLS 100

#define ENEMY_VALUE 4
#define GOAL_VALUE 2
#define WALL_VALUE 3
#define PLAYER_VALUE 1
#define EMPTY_VALUE 0

typedef enum {
    UP = 0,
    RIGHT = 1,
    DOWN = 2,
    LEFT = 3
} Direction;

typedef struct {
    int row;
    int col;
} Position;

typedef struct {
    int rows, cols;
    int **map;              
    int **enemy_dir;       
    Position player;
    UndoNode *undo_stack;
} Game;

void init_game(Game *game, const char *filename);
void print_game(Game *game);
int process_input(Game *game, char input);
int is_walkable(int tile_value);
int is_wall(int tile_value);
void rotate_enemies(Game *game);
int player_in_enemy_sight(Game *game);
void free_game(Game *game);
void free_game_arrays(Game *game);


#endif

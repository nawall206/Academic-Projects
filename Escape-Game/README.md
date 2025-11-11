# Escape Game (C Programming)
A terminal-based game where the player 'P' must reach the goal 'G' while avoiding enemies and walls.

## Key Features
- Player and enemy movement logic in C89
- Undo system using a linked list
- Enemy AI rotation and collision detection
- Modular files: main.c, game.c, undo.c, terminal.c, etc.

## Tools
C89, GCC, Makefile, Valgrind

## My Role
Designed player logic, enemy rotation, and memory-safe linked list undo system.

## How to Run
1. Open terminal in project folder
2. Compile using:
   gcc -ansi -pedantic -Wall -o escape main.c game.c enemy.c terminal.c newSleep.c random.c
3. Run the game:
   ./escape

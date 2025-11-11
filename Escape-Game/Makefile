# Makefile for Escape Game

# === Variables ===
CC = cc
CFLAGS = -Wall -ansi -pedantic
OBJFILES = main.o game.o fileio.o enemy.o undo.o newSleep.o terminal.o random.o
TARGET = escape

# === Default Target ===
all: $(TARGET)

# === Linking Target ===
$(TARGET): $(OBJFILES)
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJFILES)

# === Compilation Targets ===
main.o: main.c game.h enemy.h undo.h terminal.h random.h fileio.h
	$(CC) $(CFLAGS) -c main.c

game.o: game.c game.h terminal.h enemy.h undo.h random.h
	$(CC) $(CFLAGS) -c game.c

fileio.o: fileio.c fileio.h game.h random.h
	$(CC) $(CFLAGS) -c fileio.c

enemy.o: enemy.c enemy.h game.h newSleep.h
	$(CC) $(CFLAGS) -c enemy.c

undo.o: undo.c undo.h
	$(CC) $(CFLAGS) -c undo.c

terminal.o: terminal.c terminal.h
	$(CC) $(CFLAGS) -c terminal.c

newSleep.o: newSleep.c newSleep.h
	$(CC) $(CFLAGS) -c newSleep.c

random.o: random.c random.h
	$(CC) $(CFLAGS) -c random.c

clean:
	rm -f *.o $(TARGET)

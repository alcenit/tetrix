/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.service;

import com.cenit.tetris1.model.Board;
import com.cenit.tetris1.model.GameState;
import com.cenit.tetris1.model.Tetromino;


/**
 *
 * @author Usuario
 */
public class GameEngine {
    private Board board;
    private GameState gameState;
    private Tetromino currentPiece;
    private Tetromino nextPiece;
    
    // Constructor modificado
    public GameEngine(Board board) {
        this.board = board;  // Usar la instancia proporcionada
        this.gameState = new GameState();
        this.nextPiece = TetrominoFactory.createRandomTetromino();
        spawnNewPiece();
    }
     public void spawnNewPiece() {
    currentPiece = nextPiece;
    nextPiece = TetrominoFactory.createRandomTetromino();

    int startX = board.getWidth() / 2 - currentPiece.getMatrix()[0].length / 2;
    currentPiece.setPosition(startX, 0);

    //  CollisionDetector .isValidPosition
    if (!CollisionDetector.isValidPosition(board, currentPiece, currentPiece.getX(), currentPiece.getY())) {
        gameState.setGameOver(true);
    }
}

public boolean rotate() {
    Tetromino original = clonePiece(currentPiece);
    currentPiece.rotate();

    // Usar CollisionDetector
    if (!CollisionDetector.isValidPosition(board, currentPiece, currentPiece.getX(), currentPiece.getY())) {
        // Intentar ajustes de posición si la rotación no es válida
        boolean adjusted = adjustPositionAfterRotation();
        if (!adjusted) {
            currentPiece = original; // Revertir la rotación
            return false;
        }
    }

    return true;
}

private boolean adjustPositionAfterRotation() {
    for (int i = 1; i <= 2; i++) {
        // Usar CollisionDetector
        if (CollisionDetector.isValidPosition(board, currentPiece, currentPiece.getX() - i, currentPiece.getY())) {
            currentPiece.setPosition(currentPiece.getX() - i, currentPiece.getY());
            return true;
        }

        // Usar CollisionDetector
        if (CollisionDetector.isValidPosition(board, currentPiece, currentPiece.getX() + i, currentPiece.getY())) {
            currentPiece.setPosition(currentPiece.getX() + i, currentPiece.getY());
            return true;
        }
    }

    return false;
}

     public boolean moveLeft() {
        if (CollisionDetector.isValidPosition(board, currentPiece, currentPiece.getX() - 1, currentPiece.getY())) {
            currentPiece.setPosition(currentPiece.getX() - 1, currentPiece.getY());
            return true;
        }
        return false;
    }
    
    public boolean moveRight() {
        if (CollisionDetector.isValidPosition(board,currentPiece, currentPiece.getX() + 1, currentPiece.getY())) {
            currentPiece.setPosition(currentPiece.getX() + 1, currentPiece.getY());
            return true;
        }
        return false;
    }
    
    public boolean moveDown() {
        if (CollisionDetector.isValidPosition(board,currentPiece, currentPiece.getX(), currentPiece.getY() + 1)) {
            currentPiece.setPosition(currentPiece.getX(), currentPiece.getY() + 1);
            return true;
        }
        return false;
    }    
    
    public boolean drop() {
        boolean moved;
        do {
            moved = moveDown();
        } while (moved);
        
        return lockPiece();
    }
    
   public void togglePause() {
    boolean paused = getGameState().isPaused();
    getGameState().setPaused(!paused);
    System.out.println("Pausa: " + getGameState().isPaused()); // Debe alternar entre true y false
    
    // Notificar a la vista sobre el cambio de estado de pausa
    // Esto se hace a través del GameController en handleKeyPress
   }
    
    public boolean lockPiece() {
    // Colocar la pieza en el tablero
    board.placePiece(currentPiece);
       
    
    
    
    // Limpiar líneas completas
    int linesCleared = board.clearLines();
    
    // Verificar el estado después de limpiar líneas
   // System.out.println("Estado del tablero después de clearLines:");
   // board.printBoardState();
    
    if (linesCleared > 0) {
        updateScore(linesCleared);
       
    }
    
    // Generar nueva pieza
    spawnNewPiece();
    
    // Verificar si el juego ha terminado
    if (CollisionDetector.isGameOver(board, currentPiece)) {
        gameState.setGameOver(true);
        System.out.println("<<<<<<<<<<<<<<<<<<------->>>>>>>>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<<<<<<<--------->>>>>>>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<<<<<< GAME OVER >>>>>>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<<<<<<<--------->>>>>>>>>>>>>>>>>>>>");
        System.out.println("<<<<<<<<<<<<<<<<<<------->>>>>>>>>>>>>>>>>>>>>");
    }
    
    return linesCleared > 0;
}
    
    private void updateScore(int linesCleared) {
        int points = 0;
        switch (linesCleared) {
            case 1: points = 100; break;
            case 2: points = 300; break;
            case 3: points = 500; break;
            case 4: points = 800; break;
        }
        
        gameState.addScore(points * gameState.getLevel());
        gameState.addLines(linesCleared);
    }
    /*
    private Tetromino clonePiece(Tetromino piece) {
        Tetromino clone = new Tetromino(piece.getShape());
        clone.setPosition(piece.getX(), piece.getY());
        return clone;
    }*/
    //reemplazo de anterior 
    private Tetromino clonePiece(Tetromino piece) {
    return piece.copy();
}
    
    // Getters
    public Board getBoard() { return board; }
    public GameState getGameState() { return gameState; }
    public Tetromino getCurrentPiece() { return currentPiece; }
    public Tetromino getNextPiece() { return nextPiece; }

    

    
    
}

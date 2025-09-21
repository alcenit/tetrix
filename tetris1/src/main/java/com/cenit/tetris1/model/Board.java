/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.model;

import javafx.scene.paint.Color;

/**
 *
 * @author Usuario
 */
public class Board {
    
    private static final int WIDTH = 10;
    private static final int HEIGHT = 20;
    
    private Cell[][] grid;
    
    public Board() {
        grid = new Cell[HEIGHT][WIDTH];
        initialize();
    }
    
    private void initialize() {
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = new Cell();
            }
        }
    }
    
    public int getWidth() { return WIDTH; }
    public int getHeight() { return HEIGHT; }
    public Cell getCell(int row, int col) { return grid[row][col]; }
    
    public boolean isValidPosition(Tetromino piece, int x, int y) {
        int[][] matrix = piece.getMatrix();
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    int boardX = x + j;
                    int boardY = y + i;
                    
                    if (boardX < 0 || boardX >= WIDTH || boardY >= HEIGHT) {
                        return false;
                    }
                    
                    if (boardY >= 0 && grid[boardY][boardX].isFilled()) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    public void placePiece(Tetromino piece) {
    int[][] matrix = piece.getMatrix();
    Color color = piece.getColor();
    int x = piece.getX();
    int y = piece.getY();
    
    for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[i].length; j++) {
            if (matrix[i][j] != 0) {
                int boardY = y + i;
                int boardX = x + j;
                
                // Verificar ambos límites: superior e inferior
                if (boardY >= 0 && boardY < grid.length && 
                    boardX >= 0 && boardX < grid[0].length) {
                    
                    grid[boardY][boardX].setFilled(true);
                    grid[boardY][boardX].setColor(color);
                    
                    
                }
            }
        }
    }
    
    
}    
    public int clearLines() {
        int linesCleared = 0;
        
        for (int i = HEIGHT - 1; i >= 0; i--) {
            if (isLineComplete(i)) {
                removeLine(i);
                linesCleared++;
                i++; // Revisar la misma posición again
            }
        }
        
        return linesCleared;
    }
    
    private boolean isLineComplete(int row) {
        for (int j = 0; j < WIDTH; j++) {
            if (!grid[row][j].isFilled()) {
                return false;
            }
        }
        return true;
    }
    
    private void removeLine(int row) {
        for (int i = row; i > 0; i--) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = grid[i-1][j];
            }
        }
        
        // Clear the top line
        for (int j = 0; j < WIDTH; j++) {
            grid[0][j] = new Cell();
        }
    }
    
    //metodo para debuggear
    public void printBoardState() {
    System.out.println("=== ESTADO DEL TABLERO ===");
    for (int i = 0; i < HEIGHT; i++) {
        for (int j = 0; j < WIDTH; j++) {
            Cell cell = getCell(i, j);
            if (cell.isFilled()) {
                System.out.print("X ");
            } else {
                System.out.print(". ");
            }
        }
        System.out.println();
    }
    System.out.println("==========================");
}

public int countFilledCells() {
    int count = 0;
    for (int i = 0; i < HEIGHT; i++) {
        for (int j = 0; j < WIDTH; j++) {
            if (getCell(i, j).isFilled()) {
                count++;
            }
        }
    }
    return count;
}

    
}

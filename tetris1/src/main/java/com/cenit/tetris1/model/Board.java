/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.model;

import com.cenit.tetris1.util.GameConstants;
import javafx.scene.paint.Color;

/**
 *
 * @author Usuario
 */
public class Board {
    
    private final int width;
    private final int height;
    private Cell[][] grid;
    
    public Board() {
        this.width = GameConstants.BOARD_WIDTH;
        this.height = GameConstants.BOARD_HEIGHT;
        this.grid = new Cell[height][width];
        initializeBoard();
    }
    
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    // public Cell[][] getGrid() { return grid; }
    public Cell getCell(int row, int col) { return grid[row][col]; }
    private void initializeBoard() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = new Cell(); 
            }
        }
    }
     
    
    public boolean isValidPosition(Tetromino piece, int x, int y) {
        int[][] matrix = piece.getMatrix();
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    int boardX = x + j;
                    int boardY = y + i;
                    
                    if (boardX < 0 || boardX >= width || boardY >= height) {
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
        
        for (int i = height - 1; i >= 0; i--) {
            if (isLineComplete(i)) {
                removeLine(i);
                linesCleared++;
                i++; // Revisar la misma posición again
            }
        }
        
        return linesCleared;
    }
    
    private boolean isLineComplete(int row) {
        for (int j = 0; j < width; j++) {
            if (!grid[row][j].isFilled()) {
                return false;
            }
        }
        return true;
    }
    
    private void removeLine(int row) {
        for (int i = row; i > 0; i--) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = grid[i-1][j];
            }
        }
        
        // Clear the top line
        for (int j = 0; j < width; j++) {
            grid[0][j] = new Cell();
        }
    }
    
    //metodo para debuggear
    public void printBoardState() {
    System.out.println("=== ESTADO DEL TABLERO ===");
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
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
    for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
            if (getCell(i, j).isFilled()) {
                count++;
            }
        }
    }
    return count;
}

    
}

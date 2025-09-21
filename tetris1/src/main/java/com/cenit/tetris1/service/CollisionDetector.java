/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.service;

import com.cenit.tetris1.model.Board;
import com.cenit.tetris1.model.Tetromino;

/**
 *
 * @author Usuario
 */
public class CollisionDetector {
 
    public static boolean isValidPosition(Board board, Tetromino piece, int x, int y) {
        int[][] matrix = piece.getMatrix();
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    int boardX = x + j;
                    int boardY = y + i;
                    
                    if (boardX < 0 || boardX >= board.getWidth() || boardY >= board.getHeight()) {
                        return false;
                    }
                    
                    if (boardY >= 0 && board.getCell(boardY, boardX).isFilled()) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    public static boolean isGameOver(Board board, Tetromino piece) {
    // Obtener la matriz de la pieza
    int[][] matrix = piece.getMatrix();
    
    // La posición inicial de una nueva pieza está en la parte superior del tablero
    int startX = board.getWidth() / 2 - matrix[0].length / 2;
    int startY = 0;
    
    // Verificar si la pieza puede colocarse en su posición inicial
    // Si no puede colocarse, significa que el juego ha terminado
    return !isValidPosition(board, piece, startX, startY);
}

    
    
    
    
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.view;

import com.cenit.tetris1.model.Board;
import com.cenit.tetris1.model.Cell;
import com.cenit.tetris1.model.Tetromino;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


/**
 *
 * @author Usuario
 */
public class BoardView extends Canvas {
    
    private static final int CELL_SIZE = 30;
    private Board board;
    private Tetromino currentPiece;
    private boolean isPaused;
    private boolean isGameOver; // Nuevo campo para el estado de game over
    
    public BoardView(Board board, int width, int height) {
        super(width * CELL_SIZE, height * CELL_SIZE);
        this.board = board;
        this.isPaused = false;
        this.isGameOver = false;
    }
    
    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }
    
    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
    }
    
    public void setCurrentPiece(Tetromino piece) {
        this.currentPiece = piece;
    }
   
    
    public void render() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        
        // Dibujar el tablero
        drawBoard(gc);
               
        // Dibujar la pieza actual
        if (currentPiece != null) {
            drawPiece(gc, currentPiece);
        }
                
        // Dibujar la rejilla
        drawGrid(gc);
        
        // Dibujar mensajes superpuestos según el estado del juego
        if (isGameOver) {
            drawGameOverMessage(gc);
        } else if (isPaused) {
            drawPauseMessage(gc);
        }
        
        
    }
    
private void drawBoard(GraphicsContext gc) {
    // Fondo general del tablero
    gc.setFill(Color.rgb(30, 30, 30));
    gc.fillRect(0, 0, getWidth(), getHeight());
    
    int filledCells = 0; // Contador de celdas llenas
    
    for (int i = 0; i < board.getHeight(); i++) {
        for (int j = 0; j < board.getWidth(); j++) {
            Cell cell = board.getCell(i, j);
            double x = j * CELL_SIZE;
            double y = i * CELL_SIZE;
            
            // Dibujar celda de fondo
            gc.setFill(Color.rgb(50, 50, 50));
            gc.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            
            // Dibujar borde sutil para todas las celdas
            gc.setStroke(Color.rgb(70, 70, 70));
            gc.strokeRect(x, y, CELL_SIZE, CELL_SIZE);
            
            // DEBUG: Verificar estado de la celda
            if (cell.isFilled()) {
                filledCells++;
               
                
                // Dibujar celda llena con efecto de profundidad
                gc.setFill(cell.getColor());
                gc.fillRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
                
                // Efecto de luz (highlight)
                gc.setStroke(cell.getColor().brighter());
                gc.strokeLine(x + 1, y + 1, x + CELL_SIZE - 1, y + 1);
                gc.strokeLine(x + 1, y + 1, x + 1, y + CELL_SIZE - 1);
                
                // Efecto de sombra
                gc.setStroke(cell.getColor().darker());
                gc.strokeLine(x + CELL_SIZE - 1, y + 1, x + CELL_SIZE - 1, y + CELL_SIZE - 1);
                gc.strokeLine(x + 1, y + CELL_SIZE - 1, x + CELL_SIZE - 1, y + CELL_SIZE - 1);
            }
        }
    }
    
    
}


    private void drawPiece(GraphicsContext gc, Tetromino piece) {
        int[][] matrix = piece.getMatrix();
        Color color = piece.getColor();
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    int x = piece.getX() + j;
                    int y = piece.getY() + i;
                    
                    if (y >= 0) {
                        gc.setFill(color);
                        gc.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        
                        gc.setStroke(Color.BLACK);
                        gc.strokeRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
    }
    
    private void drawGrid(GraphicsContext gc) {
    gc.setStroke(Color.rgb(80, 80, 80));
    gc.setLineWidth(0.5);
    
    // Líneas verticales
    for (int i = 0; i <= board.getWidth(); i++) {
        gc.strokeLine(i * CELL_SIZE, 0, i * CELL_SIZE, board.getHeight() * CELL_SIZE);
    }
    
    // Líneas horizontales
    for (int i = 0; i <= board.getHeight(); i++) {
        gc.strokeLine(0, i * CELL_SIZE, board.getWidth() * CELL_SIZE, i * CELL_SIZE);
    }
}
   
    private void drawPauseMessage(GraphicsContext gc) {
        // Fondo semitransparente
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, getWidth(), getHeight());
        
        // Texto de pausa
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("PAUSA", getWidth() / 2, getHeight() / 2 - 30);
        
        // Instrucciones
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        gc.fillText("Presiona P para continuar", getWidth() / 2, getHeight() / 2 + 30);
    }
    
    private void drawGameOverMessage(GraphicsContext gc) {
        // Fondo semitransparente
        gc.setFill(Color.rgb(0, 0, 0, 0.7));
        gc.fillRect(0, 0, getWidth(), getHeight());
        
        // Texto de Game Over
        gc.setFill(Color.RED);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("GAME OVER", getWidth() / 2, getHeight() / 2 - 50);
        
        // Instrucciones
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 24));
        gc.fillText("Presiona ESC para volver al menú", getWidth() / 2, getHeight() / 2 + 20);
    }
    
}

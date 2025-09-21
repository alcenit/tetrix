/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.view;

import com.cenit.tetris1.model.Tetromino;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Usuario
 */
public class NextPieceView extends StackPane {
    
    private static final int CELL_SIZE = 15;
    private static final int PREVIEW_SIZE = 6; // Tamaño de la cuadrícula de preview
    
    private Canvas canvas;
    private Tetromino nextPiece;
    
    public NextPieceView() {
        // Crear un canvas para dibujar la pieza
        canvas = new Canvas(PREVIEW_SIZE * CELL_SIZE, PREVIEW_SIZE * CELL_SIZE);
        
        // Añadir el canvas al StackPane
        getChildren().add(canvas);
        
        // Estilo del panel
        setStyle("-fx-border-color: #666; -fx-border-width: 2; -fx-background-color: #222;");
        
        // Dibujar un fondo inicial
        drawEmptyPreview();
    }
    
    public void setNextPiece(Tetromino piece) {
        this.nextPiece = piece;
        drawNextPiece();
    }
    
    private void drawEmptyPreview() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        // Dibujar fondo de la cuadrícula
        gc.setFill(Color.rgb(40, 40, 40));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        // Dibujar cuadrícula
        gc.setStroke(Color.rgb(80, 80, 80));
        gc.setLineWidth(0.5);
        
        /*
        for (int i = 0; i <= PREVIEW_SIZE; i++) {
            // Líneas verticales
            gc.strokeLine(i * CELL_SIZE, 0, i * CELL_SIZE, PREVIEW_SIZE * CELL_SIZE);
            // Líneas horizontales
            gc.strokeLine(0, i * CELL_SIZE, PREVIEW_SIZE * CELL_SIZE, i * CELL_SIZE);
        }
        */
    }
    
    private void drawNextPiece() {
        drawEmptyPreview(); // Limpiar y dibujar fondo primero
        
        if (nextPiece == null) {
            return;
        }
        
        GraphicsContext gc = canvas.getGraphicsContext2D();
        int[][] matrix = nextPiece.getMatrix();
        Color color = nextPiece.getColor();
        
        // Calcular el desplazamiento para centrar la pieza en el área de preview
        int offsetX = (PREVIEW_SIZE - matrix[0].length) / 2;
        int offsetY = (PREVIEW_SIZE - matrix.length) / 2;
        
        // Dibujar la pieza
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    // Dibujar celda rellena
                    gc.setFill(color);
                    gc.fillRect((offsetX + j) * CELL_SIZE, 
                                (offsetY + i) * CELL_SIZE, 
                                CELL_SIZE, CELL_SIZE);
                    
                    // Dibujar borde de la celda
                    gc.setStroke(Color.BLACK);
                    gc.strokeRect((offsetX + j) * CELL_SIZE, 
                                  (offsetY + i) * CELL_SIZE, 
                                  CELL_SIZE, CELL_SIZE);
                    
                    // Añadir efecto de luz para dar profundidad
                    gc.setStroke(color.brighter());
                    gc.strokeLine((offsetX + j) * CELL_SIZE, 
                                  (offsetY + i) * CELL_SIZE, 
                                  (offsetX + j + 1) * CELL_SIZE, 
                                  (offsetY + i) * CELL_SIZE);
                    gc.strokeLine((offsetX + j) * CELL_SIZE, 
                                  (offsetY + i) * CELL_SIZE, 
                                  (offsetX + j) * CELL_SIZE, 
                                  (offsetY + i + 1) * CELL_SIZE);
                    
                    gc.setStroke(color.darker());
                    gc.strokeLine((offsetX + j) * CELL_SIZE, 
                                  (offsetY + i + 1) * CELL_SIZE, 
                                  (offsetX + j + 1) * CELL_SIZE, 
                                  (offsetY + i + 1) * CELL_SIZE);
                    gc.strokeLine((offsetX + j + 1) * CELL_SIZE, 
                                  (offsetY + i) * CELL_SIZE, 
                                  (offsetX + j + 1) * CELL_SIZE, 
                                  (offsetY + i + 1) * CELL_SIZE);
                }
            }
        }
    }
    
    // Método para obtener el tamaño preferido
    @Override
    protected double computePrefWidth(double height) {
        return PREVIEW_SIZE * CELL_SIZE + 10; // +10 para padding
    }
    
    @Override
    protected double computePrefHeight(double width) {
        return PREVIEW_SIZE * CELL_SIZE + 10; // +10 para padding
    }
    
}

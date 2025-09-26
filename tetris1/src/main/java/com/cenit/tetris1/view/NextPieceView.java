/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.view;

import com.cenit.tetris1.model.Tetromino;
import com.cenit.tetris1.util.GameConstants;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class NextPieceView extends StackPane {
    
    private final int cellSize;
    private final int previewSize;
    
    private Canvas canvas;
    private Tetromino nextPiece;
    
    public NextPieceView() {
        // Usar constantes de GameConstants
        this.cellSize = GameConstants.CELL_SIZE /2; // Más pequeño que el tablero principal
        this.previewSize = GameConstants.NEXT_PIECE_VIEW_SIZE;
        
        // Crear un canvas para dibujar la pieza
        canvas = new Canvas(previewSize * cellSize, previewSize * cellSize);
        
        // Añadir el canvas al StackPane
        getChildren().add(canvas);
        
        // Estilo del panel
        setStyle("-fx-border-color: #666; -fx-border-width: 2; -fx-background-color: #222;");
        
        // Asegurar tamaño consistente
        setMaxSize(previewSize * cellSize + 10, previewSize * cellSize + 10);
        setPrefSize(previewSize * cellSize + 10, previewSize * cellSize + 10);
        
        // Dibujar un fondo inicial
        drawEmptyPreview();
    }
    
    public void setNextPiece(Tetromino piece) {
        this.nextPiece = piece;
        drawNextPiece();
    }
    
    public void clear() {
        this.nextPiece = null;
        drawEmptyPreview();
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
        
        // Líneas verticales
        for (int i = 0; i <= previewSize; i++) {
            gc.strokeLine(i * cellSize, 0, i * cellSize, previewSize * cellSize);
        }
        
        // Líneas horizontales
        for (int i = 0; i <= previewSize; i++) {
            gc.strokeLine(0, i * cellSize, previewSize * cellSize, i * cellSize);
        }
        /*
        // Texto "SIGUIENTE"
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("SIGUIENTE", canvas.getWidth() / 2, 12);

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
        int offsetX = (previewSize - matrix[0].length) / 2;
        int offsetY = (previewSize - matrix.length) / 2 + 1; // +1 para bajar un poco por el texto
        
        // Dibujar la pieza
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    double x = (offsetX + j) * cellSize;
                    double y = (offsetY + i) * cellSize;
                    
                    // Dibujar celda rellena con efectos de profundidad
                    gc.setFill(color);
                    gc.fillRect(x + 1, y + 1, cellSize - 2, cellSize - 2);
                    
                    // Efecto de luz (highlight) - bordes superiores e izquierdos
                    gc.setStroke(color.brighter());
                    gc.strokeLine(x + 1, y + 1, x + cellSize - 1, y + 1); // Superior
                    gc.strokeLine(x + 1, y + 1, x + 1, y + cellSize - 1); // Izquierdo
                    
                    // Efecto de sombra - bordes inferiores y derechos
                    gc.setStroke(color.darker());
                    gc.strokeLine(x + 1, y + cellSize - 1, x + cellSize - 1, y + cellSize - 1); // Inferior
                    gc.strokeLine(x + cellSize - 1, y + 1, x + cellSize - 1, y + cellSize - 1); // Derecho
                    
                    // Borde exterior negro
                    gc.setStroke(Color.BLACK);
                    gc.strokeRect(x + 1, y + 1, cellSize - 2, cellSize - 2);
                }
            }
        }
    }
    
    // Método para obtener el tamaño preferido
    @Override
    protected double computePrefWidth(double height) {
        return previewSize * cellSize + 10; // +10 para padding
    }
    
    @Override
    protected double computePrefHeight(double width) {
        return previewSize * cellSize + 10; // +10 para padding
    }
    
    // Getters útiles
    public int getCellSize() {
        return cellSize;
    }
    
    public int getPreviewSize() {
        return previewSize;
    }
}
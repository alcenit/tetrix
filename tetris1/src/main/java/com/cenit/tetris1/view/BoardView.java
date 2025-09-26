/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.view;



import com.cenit.tetris1.model.Board;
import com.cenit.tetris1.model.Tetromino;
import com.cenit.tetris1.model.Cell;
import com.cenit.tetris1.model.GameState;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class BoardView extends Canvas {
    private Tetromino currentPiece;
    private boolean isPaused;
    private boolean isGameOver;
    
    private final GameState gameState;
    private final Board board;
    private final int cellSize;
    
    // Colores basados en el CSS proporcionado
    private final Color backgroundColor = Color.web("#2b2b2b");
    private final Color cellEmptyColor = Color.web("#333333");
    private final Color cellBorderColor = Color.web("#444444");
    private final Color gridLineColor = Color.web("#808080");
    
    // Colores para los tipos de piezas (basados en CSS)
    private final Color colorI = Color.web("#00BCD4"); // Cyan
    private final Color colorJ = Color.web("#2196F3"); // Blue
    private final Color colorL = Color.web("#FF9800"); // Orange
    private final Color colorO = Color.web("#FFEB3B"); // Yellow
    private final Color colorS = Color.web("#4CAF50"); // Green
    private final Color colorT = Color.web("#9C27B0"); // Purple
    private final Color colorZ = Color.web("#F44336"); // Red
    
    public BoardView(Board board, int cellSize,GameState gameState) {
        this.board = board;
        this.cellSize = cellSize;
        this.gameState = gameState;
        
        setWidth(board.getWidth() * cellSize);
        setHeight(board.getHeight() * cellSize);
        
        this.isPaused = false;
        this.isGameOver = false;
        
        // Aplicar estilo CSS al canvas (para el borde y efectos externos)
        getStyleClass().add("board-panel");
    }
    
    public void setPaused(boolean paused) {
        this.isPaused = paused;
        render();
    }
    
    public void setGameOver(boolean gameOver) {
        this.isGameOver = gameOver;
        render();
    }
    
    public void setCurrentPiece(Tetromino piece) {
        this.currentPiece = piece;
        render();
    }
    
    public void render() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        
        drawBoard(gc);
        
        if (currentPiece != null) {
            drawPiece(gc, currentPiece);
        }
        
        drawGrid(gc);
        
        if (isGameOver) {
            drawGameOverMessage(gc);
        } else if (isPaused) {
            drawPauseMessage(gc);
        }
    }
    
    private void drawBoard(GraphicsContext gc) {
        // Fondo general del tablero (usando color del CSS)
        gc.setFill(backgroundColor);
        gc.fillRect(0, 0, getWidth(), getHeight());
        
        for (int row = 0; row < board.getHeight(); row++) {
            for (int col = 0; col < board.getWidth(); col++) {
                Cell cell = board.getCell(row, col);
                double x = col * cellSize;
                double y = row * cellSize;
                
                // Dibujar celda de fondo
                gc.setFill(cellEmptyColor);
                gc.fillRect(x, y, cellSize, cellSize);
                
                // Dibujar borde sutil para todas las celdas
                gc.setStroke(cellBorderColor);
                gc.strokeRect(x, y, cellSize, cellSize);
                
                if (cell.isFilled()) {
                    drawFilledCell(gc, cell, x, y);
                }
            }
        }
    }
    
    private void drawFilledCell(GraphicsContext gc, Cell cell, double x, double y) {
        Color cellColor = cell.getColor();
        Color cssColor = getCSSColor(cellColor);
        
        // Dibujar celda llena con efecto de profundidad mejorado
        gc.setFill(cssColor);
        gc.fillRect(x + 1, y + 1, cellSize - 2, cellSize - 2);
        
        // Efecto de luz (highlight) más suave
        gc.setStroke(cssColor.deriveColor(0, 1.0, 1.3, 1.0)); // Más brillante
        gc.setLineWidth(1.0);
        gc.strokeLine(x + 1, y + 1, x + cellSize - 1, y + 1); // Superior
        gc.strokeLine(x + 1, y + 1, x + 1, y + cellSize - 1); // Izquierdo
        
        // Efecto de sombra más suave
        gc.setStroke(cssColor.deriveColor(0, 1.0, 0.7, 1.0)); // Más oscuro
        gc.strokeLine(x + cellSize - 1, y + 1, x + cellSize - 1, y + cellSize - 1); // Derecho
        gc.strokeLine(x + 1, y + cellSize - 1, x + cellSize - 1, y + cellSize - 1); // Inferior
        
        // Borde interior negro para definición
        gc.setStroke(Color.rgb(0, 0, 0, 0.3));
        gc.strokeRect(x + 1, y + 1, cellSize - 2, cellSize - 2);
    }
    
    private void drawPiece(GraphicsContext gc, Tetromino piece) {
        int[][] matrix = piece.getMatrix();
        Color pieceColor = piece.getColor();
        Color cssColor = getCSSColor(pieceColor);
        
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    int x = piece.getX() + j;
                    int y = piece.getY() + i;
                    
                    if (y >= 0) { // Solo dibujar si está en área visible
                        drawPieceCell(gc, cssColor, x, y);
                    }
                }
            }
        }
    }
    
    private void drawPieceCell(GraphicsContext gc, Color color, int gridX, int gridY) {
        double x = gridX * cellSize;
        double y = gridY * cellSize;
        
        // Cuerpo de la pieza
        gc.setFill(color);
        gc.fillRect(x + 1, y + 1, cellSize - 2, cellSize - 2);
        
        // Efecto de luz más pronunciado para pieza activa
        gc.setStroke(color.deriveColor(0, 1.0, 1.4, 1.0));
        gc.setLineWidth(1.2);
        gc.strokeLine(x + 1, y + 1, x + cellSize - 1, y + 1);
        gc.strokeLine(x + 1, y + 1, x + 1, y + cellSize - 1);
        
        // Sombra más suave
        gc.setStroke(color.deriveColor(0, 1.0, 0.6, 1.0));
        gc.strokeLine(x + cellSize - 1, y + 1, x + cellSize - 1, y + cellSize - 1);
        gc.strokeLine(x + 1, y + cellSize - 1, x + cellSize - 1, y + cellSize - 1);
        
        // Borde exterior
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.0);
        gc.strokeRect(x + 1, y + 1, cellSize - 2, cellSize - 2);
    }
    
    private void drawGrid(GraphicsContext gc) {
        gc.setStroke(gridLineColor);
        gc.setLineWidth(0.5);
        
        // Líneas verticales
        for (int i = 0; i <= board.getWidth(); i++) {
            double x = i * cellSize;
            gc.strokeLine(x, 0, x, getHeight());
        }
        
        // Líneas horizontales
        for (int i = 0; i <= board.getHeight(); i++) {
            double y = i * cellSize;
            gc.strokeLine(0, y, getWidth(), y);
        }
    }
    
    private void drawPauseMessage(GraphicsContext gc) {
        // Fondo semitransparente con efecto de desenfoque
        gc.setFill(Color.rgb(0, 0, 0, 0.85));
        gc.fillRect(0, 0, getWidth(), getHeight());
        
        // Sombra para el texto
        gc.setFill(Color.rgb(255, 193, 7, 0.3)); // Amarillo oscuro
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 52));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("PAUSA", getWidth() / 2 + 2, getHeight() / 2 - 28);
        
        // Texto principal de pausa
        gc.setFill(Color.web("#FFC107")); // Amarillo del CSS
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.fillText("PAUSA", getWidth() / 2, getHeight() / 2 - 30);
        
        // Instrucciones
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        gc.fillText("Presiona P para continuar", getWidth() / 2, getHeight() / 2 + 30);
    }
    
    private void drawGameOverMessage(GraphicsContext gc) {
        // Fondo semitransparente
        gc.setFill(Color.rgb(0, 0, 0, 0.9));
        gc.fillRect(0, 0, getWidth(), getHeight());
        
        // Sombra para el texto
        gc.setFill(Color.rgb(244, 67, 54, 0.3)); // Rojo oscuro
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 52));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("GAME OVER", getWidth() / 2 + 2, getHeight() / 2 - 48);
        
        // Texto principal de Game Over
        gc.setFill(Color.web("#F44336")); // Rojo del CSS
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        gc.fillText("GAME OVER", getWidth() / 2, getHeight() / 2 - 50);
        
        // Instrucciones
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        gc.fillText("Presiona ESC para volver al menú", getWidth() / 2, getHeight() / 2 + 20);
        
       
            gc.setFill(Color.web("#4CAF50")); // Verde
            gc.setFont(Font.font("Arial", FontWeight.BOLD, 24));
            gc.fillText("Puntuación: " + getScore(gameState), getWidth() / 2, getHeight() / 2 + 60);
        
    }
    
    private Color getCSSColor(Color originalColor) {
        // Mapear colores estándar a los colores del CSS
        if (originalColor.equals(Color.CYAN)) return colorI;
        if (originalColor.equals(Color.BLUE)) return colorJ;
        if (originalColor.equals(Color.ORANGE)) return colorL;
        if (originalColor.equals(Color.YELLOW)) return colorO;
        if (originalColor.equals(Color.GREEN)) return colorS;
        if (originalColor.equals(Color.PURPLE)) return colorT;
        if (originalColor.equals(Color.RED)) return colorZ;
        
        // Si no coincide, usar el color original
        return originalColor;
    }
    /*
    public void updateGameInfo(GameState gameState) {
        scoreLabel.setText("Puntos: " + gameState.getScore());
        levelLabel.setText("Nivel: " + gameState.getLevel());
        linesLabel.setText("Líneas: " + gameState.getLines());
    }*/
    
    private int getScore(GameState gameState) {
        int score =0;
        // Método para obtener la puntuación actual 
                  
           // obtener score de gamState
           score =  gameState.getScore();
         // System.out.println("getScore  "+gameState.getScore());
            
        
       return score; 
    }
    
    public void clear() {
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
    }
    
    public int getCellSize() {
        return cellSize;
    }
    
    public Board getBoard() {
        return board;
    }
    
    // Método para forzar redibujado
    public void refresh() {
        render();
    }
    
    // Método para cambiar el tamaño dinámicamente (si es necesario)
    public void resize(int newCellSize) {
        // No se puede cambiar el cellSize una vez creado, 
        // pero se puede redimensionar el canvas
        setWidth(board.getWidth() * newCellSize);
        setHeight(board.getHeight() * newCellSize);
        render();
    }
}
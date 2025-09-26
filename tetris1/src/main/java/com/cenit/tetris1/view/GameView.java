/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.view;

import com.cenit.tetris1.model.GameState;
import com.cenit.tetris1.model.Tetromino;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author Usuario
 */
public class GameView extends BorderPane {
    
    private BoardView boardView;
    private NextPieceView nextPieceView;
    private Label scoreLabel;
    private Label levelLabel;
    private Label linesLabel;
    
    private Button menuButton;
    
    public GameView(BoardView boardView, NextPieceView nextPieceView) {
        this.boardView = boardView;
        this.nextPieceView = nextPieceView;
        
        initializeUI();
    }
    
    private void initializeUI() {
    // Panel de información
    VBox infoPanel = new VBox(20);
    infoPanel.setPadding(new Insets(20));
    infoPanel.setAlignment(Pos.TOP_CENTER);
    infoPanel.getStyleClass().add("info-panel"); // ← Clase CSS
    
    Label nextLabel = new Label("Siguiente:");
    nextLabel.getStyleClass().add("info-title"); // ← Clase CSS
    
    scoreLabel = new Label("Puntos: 0");
    scoreLabel.getStyleClass().add("info-value"); // ← Clase CSS
    
    levelLabel = new Label("Nivel: 1");
    levelLabel.getStyleClass().add("info-value");
    
    linesLabel = new Label("Líneas: 0");
    linesLabel.getStyleClass().add("info-value");
    
    // Botón para volver al menú
    menuButton = new Button("Menú Principal");
    menuButton.getStyleClass().addAll("menu-button", "secondary-button"); // ← Múltiples clases
    
    infoPanel.getChildren().addAll(nextLabel, nextPieceView, scoreLabel, levelLabel, linesLabel, menuButton);
    
    // Panel principal
    HBox mainPanel = new HBox(20);
    mainPanel.setPadding(new Insets(20));
    mainPanel.setAlignment(Pos.CENTER);
    mainPanel.getStyleClass().add("game-container"); // ← Clase CSS
    mainPanel.getChildren().addAll(boardView, infoPanel);
    
    setCenter(mainPanel);
    
    // En la clase GameView, en el método initializeUI()
    menuButton.setFocusTraversable(false);
    
    // Aplicar estilo al root
    getStyleClass().add("game-root"); // ← Clase CSS para el root
}
    
    public Button getMenuButton() {
        return menuButton;
    }
        
    public void updateGameInfo(GameState gameState) {
        scoreLabel.setText("Puntos: " + gameState.getScore());
        levelLabel.setText("Nivel: " + gameState.getLevel());
        linesLabel.setText("Líneas: " + gameState.getLines());
    }
    
    public void setNextPiece(Tetromino piece) {
        nextPieceView.setNextPiece(piece);
    }
    
}

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
        
        Label nextLabel = new Label("Siguiente:");
        nextLabel.setFont(Font.font(16));
        
        scoreLabel = new Label("Puntos: 0");
        levelLabel = new Label("Nivel: 1");
        linesLabel = new Label("Líneas: 0");
        
        // Botón para volver al menú
        menuButton = new Button("Menú Principal");
        menuButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        
        infoPanel.getChildren().addAll(nextLabel, nextPieceView, scoreLabel, levelLabel, linesLabel, menuButton);
        
        // Panel principal
        HBox mainPanel = new HBox(20);
        mainPanel.setPadding(new Insets(20));
        mainPanel.setAlignment(Pos.CENTER);
        mainPanel.getChildren().addAll(boardView, infoPanel);
        
        setCenter(mainPanel);
        
        // En la clase GameView, en el método initializeUI()
         menuButton.setFocusTraversable(false); // Para que no reciba el foco
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

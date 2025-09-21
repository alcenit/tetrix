/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 *
 * @author Usuario
 */
public class StartMenuView extends VBox{
    private Button startButton;
    private Button exitButton;
    
    public StartMenuView() {
        initializeUI();
    }
    
    private void initializeUI() {
        setAlignment(Pos.CENTER);
        setSpacing(30);
        setPadding(new Insets(50));
        setBackground(new Background(new BackgroundFill(Color.rgb(30, 30, 50), CornerRadii.EMPTY, Insets.EMPTY)));
        
        // Título del juego
        Label titleLabel = new Label("TETRIS");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        titleLabel.setTextFill(Color.WHITE);
        
        // Botón de inicio
        startButton = new Button("Iniciar Juego");
        startButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        startButton.setMinWidth(200);
        startButton.setMinHeight(50);
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        
        // Botón de salida
        exitButton = new Button("Salir");
        exitButton.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        exitButton.setMinWidth(200);
        exitButton.setMinHeight(50);
        exitButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white;");
        
        getChildren().addAll(titleLabel, startButton, exitButton);
    }
    
    public Button getStartButton() {
        return startButton;
    }
    
    public Button getExitButton() {
        return exitButton;
    }
}

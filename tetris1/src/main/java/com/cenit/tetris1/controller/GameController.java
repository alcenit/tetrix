/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.controller;

import com.cenit.tetris1.App;
import com.cenit.tetris1.service.GameEngine;
import com.cenit.tetris1.view.BoardView;
import com.cenit.tetris1.view.GameView;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Usuario
 */
public class GameController {
    
    private GameEngine gameEngine;
    private GameView gameView;
    private BoardView boardView;
    private AnimationTimer gameLoop;
    private long lastUpdate = 0;
    
    public GameController(GameEngine gameEngine, GameView gameView, BoardView boardView) {
        this.gameEngine = gameEngine;
        this.gameView = gameView;
        this.boardView = boardView;
        
        // En el método que maneja el botón de volver al menú
     gameView.getMenuButton().setOnAction(e -> {
        stopGame();
        App.getInstance().showStartMenuFromGame();
      });
            
        
        initializeGameLoop();
    }
    
    
    private void initializeGameLoop() {
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000 / getGameSpeed()) {
                    if (!gameEngine.getGameState().isPaused() && !gameEngine.getGameState().isGameOver()) {
                        if (!gameEngine.moveDown()) {
                            gameEngine.lockPiece();
                            
                        }
                        
                        updateView();
                    }
                    lastUpdate = now;
                }
            }
        };
    }
    
    private int getGameSpeed() {
        // Aumentar la velocidad según el nivel
        return Math.max(1, gameEngine.getGameState().getLevel());
    }
    
    public void handleKeyPress(KeyEvent event) {
    // Solo procesar si el evento no ha sido consumido
    if (event.isConsumed()) {
        return;
    }
    
    System.out.println("Tecla recibida: " + event.getCode());
    
    if (gameEngine.getGameState().isGameOver()) {
        if (event.getCode() == KeyCode.ESCAPE) {
            stopGame();
            App.getInstance().showStartMenuFromGame();
            event.consume();
        }
        return;
    }
    
    KeyCode keyCode = event.getCode();
    boolean needsRender = false;
    
    switch (keyCode) {
        case LEFT:
            needsRender = gameEngine.moveLeft();
            break;
        case RIGHT:
            needsRender = gameEngine.moveRight();
            break;
        case DOWN:
            needsRender = gameEngine.moveDown();
            break;
        case UP:
            needsRender = gameEngine.rotate();
            break;
        case SPACE:
            needsRender = gameEngine.drop();
            break;
        case P:
            gameEngine.togglePause();
            needsRender = true;
            break;
        case ESCAPE:
            stopGame();
            App.getInstance().showStartMenuFromGame();
            break;
    }
    
    if (needsRender) {
        updateView();
    }
    
    // Consumir el evento para evitar procesamiento adicional
    event.consume();
}
    
      
   public void startGame() {
    gameLoop = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if (gameEngine.getGameState().isGameOver()) {
                // Solo actualizamos la vista para que BoardView dibuje el game over
                updateView();
                stopGame();
                return;
            }

            // Si está pausado, renderizamos para mostrar el mensaje de pausa
            if (gameEngine.getGameState().isPaused()) {
                updateView();
                return;
            }

            // Lógica normal del juego
            if (now - lastUpdate >= 1_000_000_000 / getGameSpeed()) {
                if (!gameEngine.moveDown()) {
                    gameEngine.lockPiece();
                }
                updateView();
                lastUpdate = now;
            }
        }
    };
    gameLoop.start();
}
  
// Método para detener el juego
public void stopGame() {
    if (gameLoop != null) {
        gameLoop.stop();
    }
}

// Método para actualizar la vista
private void updateView() {
    // Actualizar todos los estados de la vista
    boardView.setCurrentPiece(gameEngine.getCurrentPiece());
    boardView.setPaused(gameEngine.getGameState().isPaused());
    boardView.setGameOver(gameEngine.getGameState().isGameOver());
    
    // Renderizar la vista
    boardView.render();
    
    // Actualizar otros componentes de la interfaz
    gameView.setNextPiece(gameEngine.getNextPiece());
    gameView.updateGameInfo(gameEngine.getGameState());
}
    
    
   
    public void returnToMainMenu() {
    stopGame();
    // Aquí necesitarías una referencia a la App principal para llamar a showStartMenuFromGame()
    // O implementar un callback/evento
}
    
}

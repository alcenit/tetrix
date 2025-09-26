/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.util;

/**
 *
 * @author Usuario
 */
public class GameConstants {
    // Dimensiones del tablero de juego
    public static final int BOARD_WIDTH = 12;
    public static final int BOARD_HEIGHT = 24;
    public static final int CELL_SIZE = 28;
    
    // Dimensiones de la ventana
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 800;
    
    // Tamaño de la vista de la siguiente pieza
    public static final int NEXT_PIECE_VIEW_SIZE = 4;
    
    // Velocidad del juego (milisegundos)
    public static final int INITIAL_GAME_SPEED = 500;
    
    // Puntuaciones
    public static final int SINGLE_LINE_SCORE = 100;
    public static final int DOUBLE_LINE_SCORE = 300;
    public static final int TRIPLE_LINE_SCORE = 500;
    public static final int TETRIS_SCORE = 1000;
    
    private GameConstants() {
        // Clase de utilidad, no instanciable
    
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.service;

import com.cenit.tetris1.model.Tetromino;
import java.util.Random;

/**
 *
 * @author Usuario
 */
public class TetrominoFactory {
    
     private static final Tetromino.Shape[] SHAPES = Tetromino.Shape.values();
    private static final Random RANDOM = new Random();
    
    public static Tetromino createRandomTetromino() {
        int index = RANDOM.nextInt(SHAPES.length);
        return new Tetromino(SHAPES[index]);
    }
    
}

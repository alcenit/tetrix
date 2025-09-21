/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.model;

import javafx.scene.paint.Color;

/**
 *
 * @author Usuario
 */
public class Tetromino {
    
    public enum Shape {
        I, J, L, O, S, T, Z
    }
    
    private Shape shape;
    private int[][] matrix;
    private Color color;
    private int x;
    private int y;
    
    public Tetromino(Shape shape) {
        this.shape = shape;
        this.matrix = getShapeMatrix(shape);
        this.color = getShapeColor(shape);
        this.x = 0;
        this.y = 0;
    }
    
    private int[][] getShapeMatrix(Shape shape) {
        switch (shape) {
            case I: return new int[][]{{1, 1, 1, 1}};
            case J: return new int[][]{{1, 0, 0}, {1, 1, 1}};
            case L: return new int[][]{{0, 0, 1}, {1, 1, 1}};
            case O: return new int[][]{{1, 1}, {1, 1}};
            case S: return new int[][]{{0, 1, 1}, {1, 1, 0}};
            case T: return new int[][]{{0, 1, 0}, {1, 1, 1}};
            case Z: return new int[][]{{1, 1, 0}, {0, 1, 1}};
            default: return new int[][]{{1}};
        }
    }
    
    private Color getShapeColor(Shape shape) {
        switch (shape) {
            case I: return Color.CYAN;
            case J: return Color.BLUE;
            case L: return Color.ORANGE;
            case O: return Color.YELLOW;
            case S: return Color.GREEN;
            case T: return Color.PURPLE;
            case Z: return Color.RED;
            default: return Color.WHITE;
        }
    }
    
    // Getters y métodos de rotación
    public int[][] getMatrix() { return matrix; }
    public Color getColor() { return color; }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setPosition(int x, int y) { this.x = x; this.y = y; }
    
    public void rotate() {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotated = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = matrix[i][j];
            }
        }
        
        matrix = rotated;
    }
    //arreglo de clone de gameEngine
    public Shape getShape() {
    return shape;
    }
    public Tetromino copy() {
    Tetromino copy = new Tetromino(this.shape);
    copy.matrix = copyMatrix(this.matrix);
    copy.color = this.color;
    copy.x = this.x;
    copy.y = this.y;
    return copy;
}

private int[][] copyMatrix(int[][] original) {
    int[][] copy = new int[original.length][];
    for (int i = 0; i < original.length; i++) {
        copy[i] = original[i].clone();
    }
    return copy;
}

    
}

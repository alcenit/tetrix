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
public class Cell {
    private boolean filled;
    private Color color;
    
    public Cell() {
        this.filled = false;
        this.color = Color.TRANSPARENT;
    }
    
    public Cell(Color color) {
        this.filled = true;
        this.color = color;
    }
    
    public boolean isFilled() { 
        return filled; 
    }
    
    public void setFilled(boolean filled) { 
       // System.out.println("DEBUG - setFilled(" + filled + ") llamado");
        this.filled = filled; 
    }
    
    public Color getColor() { 
        return color; 
    }
    
    public void setColor(Color color) { 
     //   System.out.println("DEBUG - setColor(" + color + ") llamado");
        this.color = color; 
    }
    
    public void clear() {
        this.filled = false;
        this.color = Color.TRANSPARENT;
    }
}

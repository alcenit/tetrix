/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cenit.tetris1.model;

/**
 *
 * @author Usuario
 */
public class GameState {
    
    private int score;
    private int level;
    private int lines;
    private boolean gameOver;
    private boolean paused;
    
    public GameState() {
        this.score = 0;
        this.level = 1;
        this.lines = 0;
        this.gameOver = false;
        this.paused = false;
    }
    
        
    
    
    
    
    // Getters y setters
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    
    public int getLines() { return lines; }
    public void setLines(int lines) { this.lines = lines; }
    
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    
    public boolean isPaused() { return paused; }
    public void setPaused(boolean paused) { this.paused = paused; }
    
    public void addScore(int points) {
        this.score += points;
    }
    
    public void addLines(int lines) {
        this.lines += lines;
        this.level = (this.lines / 10) + 1;
    }
    
       
    public void reset() {
        this.score = 0;
        this.level = 1;
        this.lines = 0;
    }
    
}

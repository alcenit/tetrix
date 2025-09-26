/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.cenit.tetris1.controller;

import com.cenit.tetris1.App;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Usuario
 */
public class StartMenuController implements Initializable {

    @FXML
    private Button startButton;

    @FXML
    private Button exitButton;
    
    @FXML 
    private Line versionLine;
    @FXML 
    private Label versionLabel;
         
    @FXML
     private StackPane mainContainer;
    @FXML
    private Circle lightEffect;
    
    

    private App mainApp;

    // Constructor público sin parámetros (IMPORTANTE para FXML)
    public StartMenuController() {
        // Constructor vacío requerido por FXML
    }

    // Método para inyectar la referencia a la aplicación principal
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Initializes the controller class.
     * Este es el método único que debe inicializar el controlador
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización después de que se hayan inyectado los componentes FXML
        System.out.println("StartMenuController inicializado correctamente");
        
        // Aquí puedes agregar cualquier inicialización adicional que necesites
        if (startButton != null && exitButton != null) {
            System.out.println("Botones inyectados correctamente");
        }
        //llamados de metodos de animaciones
         setupVersionLabelHover();
                  
         setupLineAnimation();
         setupLightEffect();
    }
    private void setupLightEffect() {
        if (lightEffect != null && mainContainer != null) {
            // Mostrar el efecto cuando el mouse entra al contenedor
            mainContainer.setOnMouseEntered(event -> {
                lightEffect.setVisible(true);
            });
            
            // Ocultar el efecto cuando el mouse sale
            mainContainer.setOnMouseExited(event -> {
                lightEffect.setVisible(false);
            });
            
            // Mover el efecto con el mouse
            mainContainer.setOnMouseMoved(event -> {
                lightEffect.setTranslateX(event.getX() - mainContainer.getWidth() / 2);
                lightEffect.setTranslateY(event.getY() - mainContainer.getHeight() / 2);
            });
            
            // También mover cuando se arrastra el mouse
            mainContainer.setOnMouseDragged(event -> {
                lightEffect.setTranslateX(event.getX() - mainContainer.getWidth() / 2);
                lightEffect.setTranslateY(event.getY() - mainContainer.getHeight() / 2);
            });
        }
    }

    private void setupVersionLabelHover() {
        if (versionLabel != null) {
            versionLabel.setOnMouseEntered(event -> {
                double randomAngle = -8 + Math.random() * 16;
                versionLabel.setRotate(randomAngle);
                versionLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-weight: bold;");
            });
            
            versionLabel.setOnMouseExited(event -> {
                versionLabel.setRotate(0);
                versionLabel.setStyle("-fx-text-fill: #666; -fx-font-weight: normal;");
            });
        }
    }
    

    //no esta funcionando la animacion  de linea??
    
    private void setupLineAnimation() {
        if (versionLine != null) {
            // Animación de extensión de la línea
            versionLine.setOnMouseEntered(event -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(300), versionLine);
                st.setFromX(0.1);
                st.setToX(3.0); // Extiende al doble del tamaño
                st.play();
            });
            
            versionLine.setOnMouseExited(event -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(300), versionLine);
                st.setFromX(2.0);
                st.setToX(0.1);
                st.play();
            });
        }
    }
    
    

    @FXML
    private void handleStartButton() {
        System.out.println("Botón Iniciar presionado");
        if (mainApp != null) {
            mainApp.startGame();
        } else {
            System.err.println("Error: mainApp no está inicializada");
        }
    }

    @FXML
    private void handleExitButton() {
        System.out.println("Botón Salir presionado");
        if (mainApp != null && mainApp.getPrimaryStage() != null) {
            mainApp.getPrimaryStage().close();
        } else {
            System.exit(0);
        }
    }

    // Método para obtener los botones (si los necesitas desde fuera)
    public Button getStartButton() {
        return startButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
}
    

    
  



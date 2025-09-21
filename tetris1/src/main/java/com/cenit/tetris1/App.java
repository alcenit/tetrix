package com.cenit.tetris1;

import com.cenit.tetris1.controller.GameController;
import com.cenit.tetris1.model.Board;
import com.cenit.tetris1.service.GameEngine;
import com.cenit.tetris1.view.BoardView;
import com.cenit.tetris1.view.GameView;
import com.cenit.tetris1.view.NextPieceView;
import com.cenit.tetris1.view.StartMenuView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.input.KeyEvent;

/**
 * JavaFX App
 */
public class App extends Application {

    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    
    // Instancia única de la aplicación (patrón Singleton)
    private static App instance;
    
    private Stage primaryStage;
    private Scene gameScene;
    private Scene startMenuScene;
    private GameController gameController;
    
    /**
     * Constructor
     */
    public App() {
        instance = this;
    }
    
    /**
     * Método estático para obtener la instancia única de la aplicación
     * @return La instancia única de App
     */
    public static App getInstance() {
        if (instance == null) {
            throw new IllegalStateException("La aplicación no ha sido inicializada. Llame a launch() primero.");
        }
        return instance;
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        // Crear y mostrar el menú de inicio
        showStartMenu();
        
        primaryStage.setTitle("Tetris");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    private void showStartMenu() {
        StartMenuView startMenuView = new StartMenuView();
        startMenuScene = new Scene(startMenuView, 500, 600);
        
        // Configurar acciones de los botones
        startMenuView.getStartButton().setOnAction(e -> startGame());
        startMenuView.getExitButton().setOnAction(e -> primaryStage.close());
        
        primaryStage.setScene(startMenuScene);
        
        // Asegurar que el StartMenuView tenga el foco
        startMenuView.setFocusTraversable(true);
        startMenuView.requestFocus();
    }
    
    private void startGame() {
        // Inicializar componentes del modelo y servicio
        Board board = new Board();
        GameEngine gameEngine = new GameEngine(board);
        
        // Inicializar componentes de la vista
        BoardView boardView = new BoardView(board, BOARD_WIDTH, BOARD_HEIGHT);
        NextPieceView nextPieceView = new NextPieceView();
        GameView gameView = new GameView(boardView, nextPieceView);
        
        // Inicializar controlador
        gameController = new GameController(gameEngine, gameView, boardView);
        
        // Configurar la escena del juego
        gameScene = new Scene(gameView, 1000, 800);
        
        // Configurar el manejo de eventos de teclado
        setupKeyHandling(gameScene, gameController);
        
        // Cargar estilos CSS si los tienes
        try {
            gameScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        } catch (Exception e) {
            System.out.println("No se pudo cargar el archivo CSS: " + e.getMessage());
        }
        
        primaryStage.setScene(gameScene);
        
        // Asegurar que el GameView tenga el foco
        gameView.setFocusTraversable(true);
        gameView.requestFocus();
        
        gameController.startGame();
    }
    
    private void setupKeyHandling(Scene scene, GameController controller) {
        // Usar ambos métodos para asegurar la captura de eventos
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            System.out.println("EventFilter: Tecla presionada - " + event.getCode());
            controller.handleKeyPress(event);
        });
        
        scene.setOnKeyPressed(event -> {
            System.out.println("OnKeyPressed: Tecla presionada - " + event.getCode());
            controller.handleKeyPress(event);
        });
        
        // Asegurar que la escena pueda recibir foco
        scene.setOnMouseClicked(event -> scene.getRoot().requestFocus());
    }
    
    public void showStartMenuFromGame() {
        // Detener el juego antes de volver al menú
        if (gameController != null) {
            gameController.stopGame();
        }
        primaryStage.setScene(startMenuScene);
        
        // Asegurar que el StartMenuView tenga el foco
        StartMenuView startMenuView = (StartMenuView) startMenuScene.getRoot();
        startMenuView.setFocusTraversable(true);
        startMenuView.requestFocus();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
    
   /* @Override
    public void start(Stage primaryStage) {
        // Crear UNA sola instancia de Board
        Board board = new Board();
        
        // Pasar esta instancia a GameEngine (necesitarás modificar el constructor de GameEngine)
        GameEngine gameEngine = new GameEngine(board);
        
        // Pasar la misma instancia a BoardView
        BoardView boardView = new BoardView(board, BOARD_WIDTH, BOARD_HEIGHT);
        NextPieceView nextPieceView = new NextPieceView();
        GameView gameView = new GameView(boardView, nextPieceView);
        
        // Inicializar controlador
        GameController gameController = new GameController(gameEngine, gameView, boardView);
        
        // Configurar la escena
        Scene scene = new Scene(gameView, 1000, 800);
        scene.addEventFilter(KeyEvent.KEY_PRESSED, gameController::handleKeyPress);
         // Cargar el archivo CSS
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        
        // Configurar la ventana principal
        primaryStage.setTitle("Tetris");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        // Iniciar el juego
        gameController.startGame();
    }
    
    public static void main(String[] args) {
        launch(args);
    } */
   
    
    
    
    
    
  
    
    
    /*  private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    } */


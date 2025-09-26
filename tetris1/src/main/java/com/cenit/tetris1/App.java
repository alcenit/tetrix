package com.cenit.tetris1;

import com.cenit.tetris1.controller.GameController;
import com.cenit.tetris1.controller.StartMenuController;
import com.cenit.tetris1.model.Board;
import com.cenit.tetris1.model.GameState;
import com.cenit.tetris1.service.GameEngine;
import com.cenit.tetris1.util.GameConstants;
import com.cenit.tetris1.view.BoardView;
import com.cenit.tetris1.view.GameView;
import com.cenit.tetris1.view.NextPieceView;
import com.cenit.tetris1.view.StartMenuView;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

/**
 * JavaFX App
 */
public class App extends Application {
    
    private static App instance;
    private Stage primaryStage;
    private Scene gameScene;
    private Scene startMenuScene;
    private GameController gameController;
    
    //private GameState gameState; no estaba correcto se inicializaba null??
    
    public App() {
        instance = this;
    }
    
    public static App getInstance() {
        if (instance == null) {
            throw new IllegalStateException("La aplicación no ha sido inicializada.");
        }
        return instance;
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        
        showStartMenu();
        
        primaryStage.setTitle("Tetris");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    
    
    private void showStartMenu() {
    try {
        System.out.println("=== INICIANDO CARGA FXML ===");
        
        URL fxmlUrl = getClass().getResource("/fxml/start-menu.fxml");
        System.out.println("📁 URL del FXML: " + fxmlUrl);
        
        if (fxmlUrl == null) {
            throw new RuntimeException("No se pudo encontrar: /fxml/start-menu.fxml");
        }
        
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        Parent root = loader.load();
        
        // DIAGNÓSTICO DETALLADO DEL CSS
        System.out.println("🎨 CARGANDO CSS...");
        try {
            URL cssUrl = getClass().getResource("/css/style.css");
            System.out.println("📁 URL del CSS: " + cssUrl);
            
            if (cssUrl != null) {
                String cssExternalForm = cssUrl.toExternalForm();
                System.out.println("🔗 CSS External Form: " + cssExternalForm);
                
                root.getStylesheets().add(cssExternalForm);
                System.out.println("✅ CSS agregado a stylesheets");
                
                // Verificar que se aplicó
                System.out.println("📋 Stylesheets aplicados: " + root.getStylesheets());
            } else {
                System.err.println("❌ NO se encontró el archivo CSS en /css/style.css");
                // Crear CSS básico temporal
                createFallbackCSS(root);
            }
        } catch (Exception cssEx) {
            System.err.println("❌ Error cargando CSS: " + cssEx.getMessage());
            cssEx.printStackTrace();
            createFallbackCSS(root);
        }
        
        StartMenuController controller = loader.getController();
        if (controller == null) {
            System.err.println("❌ ERROR: Controlador es NULL");
            throw new RuntimeException("No se pudo obtener el controlador");
        }
        
        controller.setMainApp(this);
        System.out.println("✅ Controlador configurado correctamente");
        
        startMenuScene = new Scene(root, 500, 600);
        primaryStage.setScene(startMenuScene);
        
        // Forzar aplicación de estilos
        root.applyCss();
        root.layout();
        
        root.setFocusTraversable(true);
        root.requestFocus();
        
        System.out.println("✅ Menú de inicio cargado exitosamente");
        
    } catch (Exception e) {
        System.err.println("❌ Error crítico al cargar FXML: " + e.getMessage());
        e.printStackTrace();
        createFallbackStartMenu();
    }
}

private void createFallbackCSS(Parent root) {
    System.out.println("🔄 Creando CSS de respaldo...");
    // Aplicar estilos básicos directamente
    root.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 40px;");
}

private void createFallbackStartMenu() {
    System.out.println("🔄 Creando menú de respaldo SIN FXML...");
    
    Button startBtn = new Button("Iniciar Juego");
    Button exitBtn = new Button("Salir");
    Label title = new Label("TETRIS");
    Label version = new Label("Versión 1.0 (Modo Respaldo)");
    
    // Estilos inline como fallback
    title.setStyle("-fx-text-fill: white; -fx-font-size: 36px; -fx-font-weight: bold;");
    startBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 150px;");
    exitBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 14px; -fx-pref-width: 150px;");
    version.setStyle("-fx-text-fill: #666; -fx-font-size: 10px;");
    
    startBtn.setOnAction(e -> {
        System.out.println("🎮 Botón Iniciar (respaldo) clickeado!");
        startGame();
    });
    
    exitBtn.setOnAction(e -> {
        System.out.println("🚪 Botón Salir (respaldo) clickeado!");
        primaryStage.close();
    });
    
    VBox vbox = new VBox(20, title, startBtn, exitBtn, version);
    vbox.setAlignment(Pos.CENTER);
    vbox.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 40px;");
    
    startMenuScene = new Scene(vbox, 500, 600);
    primaryStage.setScene(startMenuScene);
}
    
    
    
    
    
   public void startGame() {
    try {
        System.out.println("🔄 Iniciando juego...");
        
        // 1. Crear GameState PRIMERO (como lo tienes bien)
        GameState gameState = new GameState();
        System.out.println("✅ GameState creado - Score: " + gameState.getScore());
        
        // 2. Crear Board
        Board board = new Board();
        System.out.println("✅ Board creado");
        
        // 3. Crear GameEngine y pasarle GameState
        GameEngine gameEngine = new GameEngine(board, gameState); // ← Modificar constructor
        System.out.println("✅ GameEngine creado con GameState");
        
        // 4. Crear vistas
        BoardView boardView = new BoardView(board, GameConstants.CELL_SIZE, gameState);
        NextPieceView nextPieceView = new NextPieceView();
        GameView gameView = new GameView(boardView, nextPieceView);
        
        // 5. Crear controlador y pasarle GameState
        gameController = new GameController(gameEngine, gameView, boardView, gameState); // ← Modificar constructor
        System.out.println("✅ GameController creado con GameState");
        
        // 6. Configurar escena
        gameScene = new Scene(gameView, GameConstants.WINDOW_WIDTH, GameConstants.WINDOW_HEIGHT);
        
        setupKeyHandling(gameScene, gameController);
        
        // 7. Cargar CSS
        try {
            gameScene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
            System.out.println("✅ CSS del juego cargado");
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo cargar CSS: " + e.getMessage());
        }
        
        primaryStage.setScene(gameScene);
        gameView.setFocusTraversable(true);
        gameView.requestFocus();
        
        // 8. Iniciar juego
        gameController.startGame();
        System.out.println("🎮 Juego iniciado correctamente");
        
    } catch (Exception e) {
        System.err.println("❌ Error crítico al iniciar juego: " + e.getMessage());
        e.printStackTrace();
        showStartMenu(); // Volver al menú en caso de error
    }
}
    
    private void setupKeyHandling(Scene scene, GameController controller) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            controller.handleKeyPress(event);
        });
        
        scene.setOnKeyPressed(event -> {
            controller.handleKeyPress(event);
        });
        
        scene.setOnMouseClicked(event -> scene.getRoot().requestFocus());
    }
    
    public void showStartMenuFromGame() {
        if (gameController != null) {
            gameController.stopGame();
        }
        primaryStage.setScene(startMenuScene);
        
        // Request focus para el menú
        startMenuScene.getRoot().requestFocus();
    }
    
    public Stage getPrimaryStage() {
        return primaryStage;
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


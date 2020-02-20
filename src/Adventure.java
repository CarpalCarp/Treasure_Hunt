// Project Prolog
// Name: Matias Galante
// CS3250 Section 001
// Project: Final Project
// Date: 12/6/2018
// Purpose: <Final part of Adventure Game in GUI version 2, in this version the serializing and de-serializing are implemented>

package pkgfinal.project;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import java.io.*;

import static java.lang.Integer.parseInt;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Matias
 */
public class Adventure extends Application {
    // defining height and width for the text area
    private final int TEXT_FIELD_HEIGHT = 300;
    private final int TEXT_FIELD_WIDTH = 300;

    String command;
    String direction;
    String[] listOfInput = new String[2];

    // objects to use
    private GridPane mainPane; // this is the main pane for the map that will hold all nodes
    private GridPane mapPane; // this will be the 5x5 grid for the map display
    private TextArea txtArea; // text area to show character position, commands and other application relevant
                              // information
    private TextField txtField; // used to take input
    private HBox txtAreaHBox;
    private HBox buttonHBox;
    private Button quit;
    private Button open;
    private Button save;
    public static File gameFile; // game file to save and load from
    private String defaultFileName = "AdventureGame.dat";
    public static GameChar player = new GameChar();
    public static Map gameMap;

    // sets up the main grid pane for application
    private void setPanes() {
        mainPane = new GridPane();
        mainPane.setAlignment(Pos.TOP_CENTER);
        mainPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        mainPane.setHgap(5.5);
        mainPane.setVgap(5.5);

        // set grid for map
        mapPane = new GridPane();
        mapPane.setAlignment(Pos.TOP_CENTER);
        mapPane.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        mapPane.setHgap(5.5);
        mapPane.setVgap(5.5);
        mapPane.setStyle("-fx-background-color: green");
    }

    // places nodes on the main pane
    private void placeNodes() {
        mainPane.add(getBtnHBox(), 0, 0);
        mainPane.add(getTxtArea(), 0, 1);
        mainPane.add(getTxtAreaHBox(), 0, 2);
        mainPane.add(mapPane, 1, 1);
    }

    private GridPane getMainPane() {
        return mainPane;
    }

    // processInput takes the user command to move around the map
    private void processInput(String message) {
        message = message.toLowerCase(); // convert to lowercase to avoid case sensitivity
        try {
            listOfInput = message.split(" ", 2);
            try {
                command = listOfInput[0];
                direction = listOfInput[1];
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
            // check the first character to allow for abbreviated commands
            try {
                switch (command.charAt(0)) {
                    case 'g': // abreviated command for "go"
                        goCommand(); // goCommand() executes instructions for go
                        break;
                    case 't': // abbreviated command for "take"
                        player.takeItem(direction, txtArea);
                        break;
                    case 'd': // abbreviated command for "drop"
                        player.dropItem(direction, txtArea);
                        break;
                    default:
                        txtArea.appendText(command + " command is not supported in game.");
                        break;
                }
            } catch (StringIndexOutOfBoundsException ex) {
            }
        } catch (NumberFormatException ex) {
        }
    }

    private void goCommand() { // instructions for command 'go' are given
        player.move(direction, gameMap, txtArea); // move
        player.displayLocation(gameMap, txtArea); // display location on text area
        gameMap.imageMap(mapPane, player); // display minimap
        player.searchForItems(txtArea); // search for items
    }

    // return an HBox that holds a text field for command input
    private HBox getTxtAreaHBox() {
        // create text field
        txtField = new TextField();
        txtField.setPrefWidth(275); // give the text field a width
        // add an action event when the enter key is pressed so that input is read
        txtField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    String message = txtField.getText();
                    txtArea.appendText(message + "\n");
                    processInput(message);
                    txtField.setText("");
                }
            }
        });
        txtAreaHBox = new HBox(15);
        txtAreaHBox.setPadding(new Insets(15, 15, 15, 15));
        txtAreaHBox.setStyle("-fx-background-color: gold");
        txtAreaHBox.getChildren().add(new Label("Command Input: "));
        txtAreaHBox.getChildren().add(txtField);
        return txtAreaHBox;
    }

    // creates an HBox that holds the Open, Save and Quit buttons
    private HBox getBtnHBox() {
        buttonHBox = new HBox(15);
        buttonHBox.setPadding(new Insets(15, 15, 15, 15));
        buttonHBox.setStyle("-fx-background-color: green");
        open = new Button("Open"); // instantiate button for open
        save = new Button("Save"); // instantiate button for save
        quit = new Button("Quit"); // instantiate button for quit
        buttonHBox.getChildren().add(open);
        buttonHBox.getChildren().add(save);
        buttonHBox.getChildren().add(quit);

        return buttonHBox;
    }

    // creates a text area for program output
    private TextArea getTxtArea() {
        txtArea = new TextArea();
        txtArea.setPrefHeight(TEXT_FIELD_HEIGHT);
        txtArea.setPrefWidth(TEXT_FIELD_WIDTH);
        return txtArea;
    }

    // method for serialization or saving of objects
    static void saveGame(Map gameMap, GameChar player, File file) {
        try {
            // Serialize object to a file
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(gameMap);
            out.writeObject(player);
            out.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // method for serialization or saving of objects
    static private void loadGame(File file) {
        gameMap = null;
        player = null;

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fis);
            gameMap = (Map) in.readObject(); // de-serialization for gameMap
            player = (GameChar) in.readObject(); // de-serialization for player
            in.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        } catch (ClassNotFoundException ce) {
            System.out.println(ce.getMessage()); // error - trying to de-seralize type that is not in class path
        }
    }

    @Override
    public void start(Stage primaryStage) {
        setPanes(); // Set up main and map Panes
        final FileChooser fileChooser = new FileChooser(); // declare fileChooser
        placeNodes(); // Place nodes in the pane
        gameMap.imageMap(mapPane, player);

        open.setOnAction(e -> { // set an action event for open
            // user only sees .dat files
            File fileToLoad;
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Game files (*.dat)", "*.dat");
            fileChooser.getExtensionFilters().add(extFilter);
            fileToLoad = fileChooser.showOpenDialog(primaryStage);
            if (fileToLoad == null) { // if user clicks cancel or 'X' then null will be returned
                txtArea.appendText("Game load cancelled\n");
                return; // do nothing
            } else {
                // retrieve file user chose and pass it to loadGame()
                loadGame(fileChooser.showOpenDialog(primaryStage));
                player.displayLocation(gameMap, txtArea); // display loaded location on text area
                gameMap.imageMap(mapPane, player); // display loaded minimap
                txtArea.appendText("Loading Game\n");
                player.searchForItems(txtArea); // search for items again
            }
        });

        save.setOnAction(e -> { // set an action event for save

            File gameFile = new File(defaultFileName);
            if (gameFile.exists()) { // check if file exists, if so quietly override
                saveGame(gameMap, player, new File(defaultFileName));
                txtArea.appendText("Game saved\n");
            } else {// if not then allow user to create file and add

                FileChooser fileToSave = new FileChooser();
                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Game files (*.dat)", "*.dat");

                gameFile = fileToSave.showSaveDialog(primaryStage);
                if (gameFile == null) { // if user clicks cancel or 'X' then null will be returned
                    txtArea.appendText("Game save cancelled\n");
                    return; // do nothing
                } else {
                    defaultFileName = gameFile.toString(); // get the name of file user saved and set as new default
                    saveGame(gameMap, player, gameFile); // save game
                    txtArea.appendText("Game saved\n"); // let user know game was saved
                }
            }
        });

        quit.setOnAction(e -> { // set an action event for quit
            System.exit(0); // terminate the program
        });

        // Create a scene and place it in the stage
        Scene scene = new Scene(getMainPane());
        primaryStage.setTitle("Adventure");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        if (args.length > 1) {
            System.out.println("Too many arguments, only file name is needed.");
            System.exit(0);
        } else if (args.length == 0) {
            System.out.println("Please enter file name to create map.");
            System.exit(0);
        }

        Scanner file = null;
        String txtFile = args[0];
        int outerBoundary = 4; // 4 rows and 4 columns are the outer boundary part of the map that need to be
                               // included
        
        outer: try {
            file = new Scanner(new File(txtFile)); // attempt to open file

            int rows = parseInt(file.next()); // get number of rows from txt file
            int columns = parseInt(file.next()); // get number of columns from txt file
            file.nextLine(); // move the read pointer to the next line

            // create a map object with number of rows and columns provided + the
            // outerBoundary
            gameMap = new Map(rows + outerBoundary, columns + outerBoundary);
            gameMap.initializeMap(); // initailize the game map
            gameMap.fillMap(file); // pass a reference to the file and fill the map

            gameMap.setHeight(parseInt(file.next())); // read map height
            gameMap.setWidth(parseInt(file.next())); // read map width
            file.nextLine(); // move the read pointer to read file name
            gameMap.setFileName(file.nextLine()); // read item file name
            file.useDelimiter(";");
            String val, val2, val3; // temporary variables to get contents of file
            while (file.hasNext()) // finish reading contents of file
            {
                val = file.next();
                val2 = file.next();
                val3 = file.next();
                gameMap.createTerrainObjects(val.charAt(0), val2, val3);
                file.nextLine();
            }
            // gameMap.display();
            file.close();
            file = new Scanner(new File(gameMap.getFileName()));
            file.useDelimiter(";");
            while (file.hasNext()) {
                val = file.next();
                val2 = file.next();
                val3 = file.next();
                player.createItemObjects(val, val2, val3);
                file.nextLine();
            }
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println(txtFile + " failed to open or was not found.");
            System.exit(0); // terminate the program
        } finally {
            if (file != null) { // if file is open then close
                file.close();
            }

        }

        launch(args);
    }
}
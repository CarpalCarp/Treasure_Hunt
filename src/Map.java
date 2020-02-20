package pkgfinal.project;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Matias
 */
public class Map implements Serializable {
    public char[][] map; // double array used to travel through "map" in game
    // create a row and column variables for map array
    private int totalRows;
    private int totalColumns;
    final int MINI_MAP_SIZE = 5;
    private int imageHeight; // holds image height
    private int imageWidth; // holds image width
    private String itemFileName; // holds the name of the text that that tells which items are present on the map
    
    // terrain objects
    Terrain plain, mountain, forest, water, treasure, out, person;

    Map(int row, int column){
        this.totalRows = row;
        this.totalColumns = column;
        this.map = new char[row][column];   // initialize map
    }
    
    public void createTerrainObjects(char terChar, String terName, String terImage){
        
        terName = terName.toLowerCase();
        switch(terName)
        {
            case "mountain":
                mountain = new Terrain(terChar, terName, terImage);
                break;
            case "plains":
                plain = new Terrain(terChar, terName, terImage);
                break;
            case "forest":
                forest = new Terrain(terChar, terName, terImage);
                break;
            case "water":
                water = new Terrain(terChar, terName, terImage);
                break;
            case "goal":
                treasure = new Terrain(terChar, terName, terImage);
                break;
            case "out":
                out = new Terrain('X', terName, terImage);
                break;
            case "person":
                person = new Terrain(terChar, terName, terImage);
                break;
                
        }
    }

    // will initialize around the map with X's
    public void initializeMap(){
        for(int row = 0; row < totalRows; row++){
            for(int column = 0; column < totalColumns; column++){
                if(row == 0 || row == 1 || row == totalRows - 1 || row == totalRows - 2 || column == 1 || column == 0 || column == totalColumns - 1 || column == totalColumns - 2)
                {
                    map[row][column] = 'X'; // get the character used for out-of-bounds and use it for the map boundary
                }
            }
        }
    }

    // fillMap() fills up the map according to the characters from the file
    public void fillMap(Scanner file){
        String line;
        for(int row = 2; row < totalRows - 2; row++){
            line = file.nextLine();  // start reading the lines from the file
            for(int column = 2; column < totalColumns - 2; column++){
                map[row][column] = line.charAt(column - 2); // place each character from file one by one on the map inside the X parameter
            }
        }
    }

    // getMaxRows() gets the maximun number of rows from the map, except for the rows of X's
    public int getMaxRows() {
        int xRow = 2;   // xRow are the rows full of X's
        return totalRows - xRow;
    }
    // getMaxRows() gets the maximun number of columns from the map, except for the column of X's
    public int getMaxColumns(){
        int xColumn = 2; // xColumn are the columns full of X's
        return totalColumns - xColumn;
    }
    
    // sets up the 5x5 grid map with the images
    public void imageMap(GridPane pane, GameChar player){
        int startingRow = player.getRow() - 2; // starting row is location of player - the 2 outer row boundary
        int startingColumn = player.getColumn() - 2; // starting column is location of player - the 2 outer column boundary
        clearMap(pane); // clear the pane before replacing nodes
        int gridRow = 0; // will hold current location on grid row
        int gridCol = 0; // will hold current location on grid column
        String nameOfFile = "File:";
        
        // read through the minimap array and place an image on the grid
        for(int rows = startingRow; rows < startingRow + MINI_MAP_SIZE; rows++) 
        {
            for(int columns = startingColumn; columns < startingColumn + MINI_MAP_SIZE; columns++)
            {
                if(map[rows][columns] == mountain.getTerrainChar()){
                    nameOfFile += mountain.getTerrainImage();
                }else if(map[rows][columns] == out.getTerrainChar()){
                    nameOfFile += out.getTerrainImage();
                }else if(map[rows][columns] == person.getTerrainChar()){
                    nameOfFile += person.getTerrainImage();
                }else if(map[rows][columns] == plain.getTerrainChar()){
                    nameOfFile += plain.getTerrainImage();
                }else if(map[rows][columns] == treasure.getTerrainChar()){
                    nameOfFile += treasure.getTerrainImage();
                }else if(map[rows][columns] == water.getTerrainChar()){
                    nameOfFile += water.getTerrainImage();
                }else if(map[rows][columns] == forest.getTerrainChar()){
                    nameOfFile += forest.getTerrainImage();
                }
                pane.add(new ImageView(new Image(nameOfFile, imageHeight, imageWidth, false, false)), gridCol, gridRow);
                gridCol++;
                nameOfFile = "File:"; // reset file name
            }
            gridCol = 0; // reset gridCol to zero so position is accurate
            nameOfFile = "File:"; // reset file name
            gridRow++;
        }
        //new Image(nameOfFile, imageHeight, imageWidth, false, false)), gridCol, gridRow
        pane.add(new ImageView(new Image("File:" + person.getTerrainImage(), imageHeight, imageWidth, false, false)), 2, 2); // overwrite the player location image with player image
    }
    
    // this method will clear the grid from images
    public void clearMap(GridPane pane){
        if(!pane.getChildren().isEmpty()) // if grid is not empty, proceed with removal of nodes
        {
            pane.getChildren().clear();
        }
        // if grid is empty, then call will be ignored
    }
    
    public void setHeight(int height){ imageHeight = height; }
    public void setWidth(int width){ imageWidth = width; }
    public void setFileName(String name){ itemFileName = name; }
    public String getFileName(){ return itemFileName; }
    
    public void display(){
        for(int row = 0; row < totalRows; row++)
        {
            for(int column = 0; column < totalColumns; column++)
            {
                System.out.print(map[row][column]);
            }
            System.out.println("");
        }
    }
}

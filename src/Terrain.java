/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project;

import java.io.Serializable;
import javafx.scene.image.Image;

/**
 *
 * @author Matias
 */
public class Terrain implements Serializable {
    private char terrainChar;
    private String terrainName;
    private String terrainImage;
    
    public Terrain(char terrainChar_, String terrainName_, String terrainImage_){
        terrainChar = terrainChar_;
        terrainName = terrainName_;
        terrainImage = terrainImage_;
    }
    
    public char getTerrainChar(){ return terrainChar; }
    public String getTerrainName(){ return terrainName; }
    public String getTerrainImage() { return terrainImage; }
}

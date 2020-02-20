/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgfinal.project;

import java.io.Serializable;

/**
 *
 * @author Matias
 */
public class Item implements Serializable {
    private int itemRow; // row location for item
    private int itemCol; // col location for item
    private String itemType; // holds item description
    
    Item(int itemRow_, int itemCol_, String itemType_){
        itemRow = itemRow_;
        itemCol = itemCol_;
        itemType = itemType_;
    }
    
    public int getRow(){ return itemRow; } // returns row location of item
    public int getCol(){ return itemCol; } // returns col location of item
    public String getItemType(){ return itemType;} // returns item type
    public void setRow(int row){ itemRow = row;} // sets the row, used when item is dropped and gets repositioned
    public void setCol(int col){ itemCol = col;} // sets the col, used when item is dropped and gets repositioned
}

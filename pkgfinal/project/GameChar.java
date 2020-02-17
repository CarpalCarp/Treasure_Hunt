package pkgfinal.project;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.scene.control.TextArea;

/**
 *
 * @author Matias
 */
public class GameChar implements Serializable {
    private ArrayList<Item> inventory;                     // character's inventory is stored in an array
    public int row;                                 // current row of where player's at
    public int column;                              // current column of where player's at
    private boolean itemsInLocation;                // will hold true if there are items to take
    private int boundary;                           // used for width or height of boundary around map  
    
    // ArrayList holds item objects
    private ArrayList<Item> mapItems;

    GameChar(){
        //inventory = new String[]{"brass lantern", "rope", "rations", "staff"};
        row = 2;        // we initialize row at 2 because row 0 and 1 is part of the X boundary, same with column
        column = 2;
        mapItems = new ArrayList<>();
        inventory = new ArrayList<>();
        itemsInLocation = false;
        boundary = 2; // boundary takes up two rows and columns, we need to subtract 2 from item location
    }

    /*public void displayInventory()  // displays the player's inventory
    {
        for (String items : inventory) {
            System.out.println(items);
        }
    }*/
    
    // creates the objects for items used in the game
    public void createItemObjects(String row, String col, String name){
        mapItems.add(new Item(Integer.parseInt(row),Integer.parseInt(col),name));
    }
    
    // checks if current player location has any items
    public void searchForItems(TextArea txtArea){
        for(Item item : mapItems){ // on current location check items in arrayList to see if any are located
            if((row - boundary) == item.getRow() && (column - boundary) == item.getCol()) // if row and col match, item is found
            {
                // let user know item was found
                txtArea.appendText("An item was found!: " + item.getItemType() + "\n");
                itemsInLocation = true;
                return;
            }else
                itemsInLocation = false;
        }
    }
    
    // takes item from item array and adds it to the inventory array
    public void takeItem(String itemName, TextArea txtArea){
        if(!itemsInLocation){// if user wants to take and there are no items, let user know
            txtArea.appendText("There are no items here.\n");
            return;
        }
        int index = 0;
        for(Item item : mapItems){ // searth in items ArrayList for item user wants
            if(item.getItemType().equals(itemName)){ // if item is found, get the index
                index = mapItems.indexOf(item);
                break;
            }
        }
        if(index != -1){
            inventory.add(mapItems.get(index)); // remove from items ArrayList and add to inventory
            mapItems.remove(index);
            txtArea.appendText(itemName + " was taken and added to inventory.\n");
            searchForItems(txtArea);// search for items again in case there is another one
        }else
            txtArea.appendText("That item is not here.\n"); // if no item is found, let user know
    }
    
    public void dropItem(String itemName, TextArea txtArea){
        if(inventory.isEmpty()){ // if inventory is empty, there are no items to drop
            txtArea.appendText("There are no items in your inventory");
        }else{
            int index = 0;
            for(Item item : inventory){
                if(item.getItemType().equals(itemName)){ // if item is found, get the index
                    index = inventory.indexOf(item);
                    break;
                }
            }
            if(index != -1){ // -1 is returned by indexOf(n) when object is not in arrayList
                Item item = inventory.get(index); // retrieve item from inventory
                item.setRow(row - boundary); //update new row for item
                item.setCol(column - boundary); // update new col for item
                mapItems.add(item); // remove from inventory and add to mapItems
                inventory.remove(index); // remove duplicate from inventory
                txtArea.appendText(itemName + " is dropped.\n");
                searchForItems(txtArea);// search for items again in case there is another one
            }else{
                txtArea.appendText(itemName + " is not in your inventory.");
            }
            
        }
    }

    // Moves the player throughout array (map)
    // also makes sure the player moves within the map and not out of bounds
    public void move(String command, Map gameMap, TextArea txtArea)//int row, int column, int[][] map, int playerPosition)
    {
        command = command.toLowerCase();                  // convert to lowercase to avoid case sensitive issues
        // boundaries that player shouldn't get past in the map
        int northEdge = 2;
        int eastEdge = gameMap.getMaxColumns() - 1;
        int southEdge = gameMap.getMaxRows() - 1;
        int westEdge = 2;

        switch(command.charAt(0))
        {
            case 'n': //check north
                if(row == northEdge)
                {
                    txtArea.appendText("You cannot go that far north.\n");
                }else{
                    txtArea.appendText("Going north\n");
                    row--;}
                break;
            case 'e': // check east
                if(column == eastEdge)
                {
                    txtArea.appendText("You cannot go that far east.\n");
                }else{
                    txtArea.appendText("Going east\n");
                    column++;}
                break;
            case 's': //check south
                if(row == southEdge)
                {
                    txtArea.appendText("You cannot go that far south.\n");
                }else{
                    txtArea.appendText("Going south\n");
                    row++;}
                break;
            case 'w': //check west
                if(column == westEdge)
                {
                    txtArea.appendText("You cannot go that far west.\n");
                }else{
                    txtArea.appendText("Going west\n");
                    column--;}
                break;
            default: // in case an invalid direction was given
                txtArea.appendText("Invalid direction, try again.\n");
                break;
        }
    }

    public int getRow(){return row;}            // gets the current row of where the player's at
    public int getColumn(){return column;}      // gets the current column of where the player's at

    // Displays the location of the player
    public void displayLocation(Map gameMap, TextArea txtArea)
    {
        // Because of the surrounding X boundary, position (2,2) is where the player starts, we need to display the coordinates
        // by ignoring the x boundary so (0,0) is displayed to the user, we can do this by subtracting 2 from row and
        // column upon display so that we get the start location according to the game not the map
        txtArea.appendText("Location: (" + (row - 2) + "," + (column - 2) + ") in terrain: " + gameMap.map[row][column] + "\n");
    }
}

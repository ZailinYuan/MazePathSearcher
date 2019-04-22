// Name: Zailin Yuan
// USC loginid: zailinyu 7247888150
// CS 455 PA3
// Fall 2017

import java.util.LinkedList;


/**
   Maze class

   Stores information about a maze and can find a path through the maze
   (if there is one).
   
   Assumptions about structure of the maze, as given in mazeData, startLoc, and endLoc
   (parameters to constructor), and the path:
     -- no outer walls given in mazeData -- search assumes there is a virtual 
        border around the maze (i.e., the maze path can't go outside of the maze
        boundaries)
     -- start location for a path is maze coordinate startLoc
     -- exit location is maze coordinate exitLoc
     -- mazeData input is a 2D array of booleans, where true means there is a wall
        at that location, and false means there isn't (see public FREE / WALL 
        constants below) 
     -- in mazeData the first index indicates the row. e.g., mazeData[row][col]
     -- only travel in 4 compass directions (no diagonal paths)
     -- can't travel through walls

 */

public class Maze 
{
   
   private static final boolean FREE = false; // Set a position wall or path
   private static final boolean WALL = true;
   
   private LinkedList<MazeCoord> mazePath; // Store the coordinates of path units
   
   private boolean[][] maze; // The map of the maze
   
   private int numRows; //The rows and the columns of the maze
   private int numCols;
   
   private MazeCoord startLoc; //The entrance and exit coordinates
   private MazeCoord exitLoc;
  

   /**
      Constructs a maze.
      @param mazeData the maze to search.  See general Maze comments above for what
      goes in this array.
      @param startLoc the location in maze to start the search (not necessarily on an edge)
      @param exitLoc the "exit" location of the maze (not necessarily on an edge)
      PRE: 0 <= startLoc.getRow() < mazeData.length and 0 <= startLoc.getCol() < mazeData[0].length
         and 0 <= exitLoc.getRow() < mazeData.length and 0 <= exitLoc.getCol() < mazeData[0].length
         and 0 <= mazePath.size() <= mazeData.length * mazeData[0].length

    */
   public Maze(boolean[][] mazeData, MazeCoord startLoc, MazeCoord exitLoc) 
   {
      // Initialize the invariant variables
      maze = mazeData;
      numRows = maze.length;
      numCols = maze[0].length;
      this.startLoc = startLoc;
      this.exitLoc = exitLoc;
      mazePath = new LinkedList<MazeCoord>();
   }


   /**
      Returns the number of rows in the maze
      @return rows of the maze
   */
   public int numRows() 
   {
      return numRows;
   }

   
   /**
      Returns the number of columns in the maze
      @return columns of the maze
   */   
   public int numCols() 
   {
      return numCols;
   } 
 
   
   /**
      Returns true if there is a wall at this location
      @param loc the location in maze coordinates
   */
   public boolean hasWallAt(MazeCoord loc) 
   {
      return maze[loc.getRow()][loc.getCol()];
   }
   

   /**
      Returns the entry location of this maze.
    */
   public MazeCoord getEntryLoc() 
   {
      return startLoc;
   }
   
   
   /**
     Returns the exit location of this maze.
   */
   public MazeCoord getExitLoc() 
   {
      return exitLoc;
   }

   
   /**
      Returns the path through the maze. First element is start location, and
      last element is exit location.  If there was not path, or if this is called
      before a call to search, returns empty list.
    */
   public LinkedList<MazeCoord> getPath() 
   {
      return mazePath;
   }


   /**
      Find a path from start location to the exit location (see Maze
      constructor parameters, startLoc and exitLoc) if there is one.
      Client can access the path found via getPath method.

      @return whether a path was found.
    */
   public boolean search() 
   {  
      return helper(startLoc.getRow(),startLoc.getCol()); 
   }
   
   
   /**
    * Steps to find path. Each step judge whether the location right
    * now is wall or path. If here is the exit, return true. If here  
    * is neither a wall or exit, search the neighbor units.
    * @param row   the row of present position
    * @param col   the column of present position
    * @return true if there is a path and false if there is a wall.
    */
   private boolean helper(int row, int col)
   {
      if(maze[row][col])
      {
         return false; // Present position is a wall
      }
      else if(row==exitLoc.getRow()&&col==exitLoc.getCol())
      {
         mazePath.addLast(new MazeCoord(row,col));
         return true;  // Present position is exit
      }
      else
      {
         // Present location is neither wall nor exit
         // Set present position as a wall and search the next neighbor
         maze[row][col] = WALL;
         
         if((row+1)>=0&&(row+1)<numRows&&helper(row+1,col))
         {
            mazePath.addLast(new MazeCoord(row,col));
            maze[row][col] = FREE; // Make present position path again.
            return true; // Found path in the following steps.
         }
         
         if((row-1)>=0&&(row-1)<numRows&&helper(row-1,col))
         {
            mazePath.addLast(new MazeCoord(row,col));
            maze[row][col] = FREE; // Make present position path again.
            return true; // Found path in the following steps.
         }

         if((col+1)>=0&&(col+1)<numCols&&helper(row,col+1))
         {
            mazePath.addLast(new MazeCoord(row,col));
            maze[row][col] = FREE; // Make present position path again.
            return true; // Found path in the following steps.
         }
         
         if((col-1)>=0&&(col-1)<numCols&&helper(row,col-1))
         {
            mazePath.addLast(new MazeCoord(row,col));
            maze[row][col] = FREE; // Make present position path again.
            return true; // Found path in the following steps.
         }
         else
         {
            maze[row][col] = FREE; // Make present position path again.
            return false; // Found path in the following steps.
         }
      }
   }

}

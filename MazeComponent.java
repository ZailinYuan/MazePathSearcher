// Name: Zailin Yuan
// USC loginid: zailinyu 7247888150
// CS 455 PA3
// Fall 2017

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JComponent;


/**
   MazeComponent class
   
   A component that displays the maze 
   and path through it if one has been found.
*/
public class MazeComponent extends JComponent
{

   private static final int START_X = 10; // top left of corner of maze in frame
   private static final int START_Y = 10; 
   private static final int BOX_WIDTH = 20;  // width and height of one maze "location"
   private static final int BOX_HEIGHT = 20;
   private static final int INSET = 2;  
                    // how much smaller on each side to make entry/exit inner box
   
   private MazeCoord entryLoc; //Entrance coordinate for drawing entrance box
   private MazeCoord exitLoc;  //Exit coordinate for drawing exit box
   
   private boolean[][] mazeData; //The maze whose walls are true and paths are false
   
   private LinkedList<MazeCoord> mazePath; //The path found after searching, null if
                                           //no path found
   
   /**
      Constructs the component.
      @param maze   the maze to display
      PRE: 0 <= entryLoc.getRow() < mazeData.length and 0 <= entryLoc.getCol() < mazeData[0].length
         and 0 <= exitLoc.getRow() < mazeData.length and 0 <= exitLoc.getCol() < mazeData[0].length
         and 0 <= mazePath.size() <= mazeData.length * mazeData[0].length
   */
   public MazeComponent(Maze maze) 
   {
      //Initialize 2D Array mazeData for drawing maze.
      mazeData = new boolean[maze.numRows()][maze.numCols()];
      for(int i=0;i<maze.numRows();i++)
      {
         for(int j=0;j<maze.numCols();j++)
         {
            MazeCoord mazeCoord = new MazeCoord(i,j);
            mazeData[i][j] = maze.hasWallAt(mazeCoord);
         }
      }
    
      //Initialize maze path
      mazePath = maze.getPath();
      
      //Initialize the location of entrance & exit
      entryLoc = maze.getEntryLoc();
      exitLoc = maze.getExitLoc();
   }

   
   /**
     Draws the current state of maze including the path through it if one has
     been found.
     @param g the graphics context
   */
   public void paintComponent(Graphics g)
   {
      //Use 2D graphics context
      Graphics2D g2 = (Graphics2D)g;
      
      //Draw the background of the maze
      g2.draw(mazeBackground(g2));
     
      //Draw the walls and paths
      walls(g2);
      
      //Draw the entrance & exit
      entranceExitBox(g2);
      
      //Draw the bounder
      g2.draw(bounder(g2));
     
      //Draw the path
      drawPath(g2);
      
      //Reset the mazePath to make sure it satisfies the pre-condition
      mazePath.clear();
   }
   
   
   /**
    * Draw the white background for the maze. 
    * The size is the same as the maze, the color is white.
    * 
    * @param g2 the graphics context
    * @return the drawn rectangle used as background
    */
   private Rectangle mazeBackground(Graphics2D g2)
   {
      //Specify the box
      Rectangle backgroundBox = new Rectangle(START_X,START_Y,
            BOX_WIDTH*mazeData[0].length,BOX_HEIGHT*mazeData.length);
      g2.setColor(Color.WHITE);
      g2.fill(backgroundBox);
      
      return backgroundBox;
   }
   
   
   /**
    * Draw the entrance and exit boxes.
    * The entrance box is yellow and the exit is green.
    * 
    * @param g2 the graphics context
    */
   private void entranceExitBox(Graphics2D g2)
   {
      //Specify the entrance box
      Rectangle entranceBox = new Rectangle(START_X+entryLoc.getCol()*BOX_WIDTH+INSET,
            START_Y+entryLoc.getRow()*BOX_HEIGHT+INSET,BOX_WIDTH-INSET-INSET,
            BOX_HEIGHT-INSET-INSET);
      g2.setColor(Color.YELLOW);
      g2.draw(entranceBox);
      g2.fill(entranceBox);
      
      //Specify the exit box
      Rectangle exitBox = new Rectangle(START_X+exitLoc.getCol()*BOX_WIDTH+INSET,
            START_Y+exitLoc.getRow()*BOX_HEIGHT+INSET,BOX_WIDTH-INSET-INSET,
            BOX_HEIGHT-INSET-INSET);
      g2.setColor(Color.GREEN);
      g2.draw(exitBox);
      g2.fill(exitBox);
   }
   
   
   /**
    * Draw the black bounder that encloses the maze.
    * Be of the same size of the maze.
    * 
    * @param g2 the graphics context
    * @return the bounder rectangle
    */
   private Rectangle bounder(Graphics2D g2)
   {
      //Specify the bounder box
      Rectangle bounder = new Rectangle(START_X,START_Y,
            BOX_WIDTH*mazeData[0].length,BOX_HEIGHT*mazeData.length);
      g2.setColor(Color.BLACK);
      
      return bounder;
   }
   
   
   /**
    * Draw the black wall boxes if there is a wall.
    * The boxes' position is determined by 2D Array mazeData. 
    * 
    * @param g2 the graphics context
    */
   private void walls(Graphics2D g2)
   {
      //Specify and draw the boxes
      for(int i=0;i<mazeData.length;i++)
      {
         for(int j=0;j<mazeData[0].length;j++)
         {
            if(mazeData[i][j])
            {
               Rectangle walls = new Rectangle(START_X+j*BOX_WIDTH,START_Y+i*BOX_HEIGHT,
                     BOX_WIDTH,BOX_HEIGHT);
               g2.setColor(Color.BLACK);
               g2.fill(walls);
            }
         }
      }
   }
   
   
   /**
    * Draw the path found after search.
    * Draw nothing if there is no path for the maze.
    * 
    * @param g2 the graphics context
    */
   private void drawPath(Graphics2D g2)
   {
      //Initial iterator 
      ListIterator<MazeCoord> iter = mazePath.listIterator();
      
      //Specify points on the path and then connect them to draw a path  
      if(mazePath.size()<=1) { }
      else
      {
         iter.next(); // Advance to between the first and the second element
         for(int i=0;iter.hasNext();i++)
         {
            MazeCoord oneEnd = iter.previous();
            iter.next();
            MazeCoord theOtherEnd = iter.next();
            
            // Specify the two neighbor points on the path
            Point2D.Double oneEndPoint = new Point2D.Double
                  (START_X+BOX_WIDTH*oneEnd.getCol()+BOX_WIDTH/2, 
                        START_Y+BOX_HEIGHT*oneEnd.getRow()+BOX_HEIGHT/2);
            Point2D.Double theOtherEndPoint = new Point2D.Double
                  (START_X+BOX_WIDTH*theOtherEnd.getCol()+BOX_WIDTH/2, 
                        START_Y+BOX_HEIGHT*theOtherEnd.getRow()+BOX_HEIGHT/2);
            
            Line2D.Double segment = new Line2D.Double(oneEndPoint,theOtherEndPoint);
            g2.setColor(Color.BLUE);
            g2.draw(segment);
            g2.fill(segment);
         }
      }
   }

}






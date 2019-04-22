// Name: Zailin Yuan
// USC loginid: zailinyu  7247888150
// CS 455 PA3
// Fall 2017


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFrame;


/**
 * MazeViewer class
 * 
 * Program to read in and display a maze and a path through the maze. At user
 * command displays a path through the maze if there is one.
 * 
 * How to call it from the command line:
 * 
 *      java MazeViewer mazeFile
 * 
 * where mazeFile is a text file of the maze. The format is the number of rows
 * and number of columns, followed by one line per row, followed by the start location, 
 * and ending with the exit location. Each maze location is
 * either a wall (1) or free (0). Here is an example of contents of a file for
 * a 3x4 maze, with start location as the top left, and exit location as the bottom right
 * (we count locations from 0, similar to Java arrays):
 * 
 * 3 4 
 * 0111
 * 0000
 * 1110
 * 0 0
 * 2 3
 * 
 */

public class MazeViewer 
{
   
   private static final char WALL_CHAR = '1'; //Used to judge if it's a wall or path
   private static final char FREE_CHAR = '0';

   /**
    * The main method open the file specified in the command line, 
    * produce the frame of the maze and draw the maze. 
    * 
    * @param args the file name specified in the command line
    */
   public static void main(String[] args)
   {
      
      String fileName = "";

      try
      {
         if (args.length < 1) 
         {
            System.out.println("ERROR: missing file name command line argument");
         }
         else
         {
            fileName = args[0];
            
            JFrame frame = readMazeFile(fileName);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setVisible(true);
         }
      }
      catch (FileNotFoundException exc) 
      {
         System.out.println("ERROR: File not found: " + fileName);
      }
      catch (IOException exc)
      {
         exc.printStackTrace();
      }
      
   }

   /**
    readMazeFile reads in maze from the file whose name is given and 
    returns a MazeFrame created from it.
   
   @param fileName
             the name of a file to read from (file format shown in class comments, above)
   @returns a MazeFrame containing the data from the file.
        
   @throws FileNotFoundException
              if there's no such file (subclass of IOException)
   @throws IOException
              (hook given in case you want to do more error-checking --
               that would also involve changing main to catch other exceptions)
   @return Return a new MazeFrame object with the 2D Array of the maze &
           the coordinates of entrance and exit
   */
   private static MazeFrame readMazeFile(String fileName) throws IOException 
   {
      //Define scanner to scan the file
      File mazeFile = new File(fileName);
      Scanner in = new Scanner(mazeFile);
      String aRow = in.nextLine();
      Scanner finder = new Scanner(aRow);
      
      int row = finder.nextInt(); //Rows and columns of the maze
      int col = finder.nextInt();
      int entryRow = 0; //Coordinates of entrance and exit
      int entryCol = 0;
      int exitRow = 0;
      int exitCol = 0;
      
      //Initialize an 2D Array of the same size of the maze
      boolean[][] maze = new boolean[row][col];
      
      //Store the maze in the 2D Array. False means there is a path & 
      //true means there is a wall
      for(int i=0;in.hasNextLine()&&i<row;i++)
      {
         String eachRow = in.nextLine();
         for(int j=0;j<col;j++)
         {
            if(eachRow.charAt(j)==FREE_CHAR)
            {
               maze[i][j] = false;
            }
            if(eachRow.charAt(j)==WALL_CHAR)
            {
               maze[i][j] = true;
            }
            
         }
      }
      
      //Record the coordinates of the entrance of the maze in variable 
      //entryRow and entryCol
      Scanner Coordinates = new Scanner(in.nextLine());
      entryRow = Coordinates.nextInt();
      entryCol = Coordinates.nextInt();
      
      //Record the coordinates of the exit of the maze in variable 
      //exitRow and exitCol
      Coordinates = new Scanner(in.nextLine());
      exitRow = Coordinates.nextInt();
      exitCol = Coordinates.nextInt();
      
      //Close all scanners
      in.close();
      finder.close();
      Coordinates.close();
    
      return new MazeFrame(maze, new MazeCoord(entryRow, entryCol), 
            new MazeCoord(exitRow, exitCol));

   }

}

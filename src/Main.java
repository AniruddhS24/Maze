import java.io.*;
import java.util.*;

public class Main
{
    /*
    FORMAT OF INPUT:
    Line 1: Two integers separated by a space: respectively the number rows(R), and the number of columns(C)
    Lines 2-2+R: The maze, with each cell in a row separated by a space.
        MAZE KEY:
            Use '#' to signify a block.
            Use '.' to signify an open space.
            Use 'S' to signify the start position.
            Use 'F' to signify the finish position.
     */
    /*
    The solution path given will be the shortest path to the finish point in the maze.
    IF MORE THAN ONE SHORTEST PATH EXISTS, THE PROGRAM WILL PRINT ONE OF THE PATHS.
     */
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("***^^^***MAZE SOLVER***^^^***\n" +
                "This program can solve any maze efficiently through a BFS.\n" +
                "The solution path given will be the shortest path to the finish point in the maze.\n" +
                "    IF MORE THAN ONE SHORTEST PATH EXISTS, THE PROGRAM WILL PRINT ONE OF THE PATHS.\n" +
                "Create a TEXT FILE with the contents of the maze: \n" +
                "FORMAT OF INPUT:\n" +
                "    Line 1: Two integers separated by a space: respectively the number rows(R), and the number of columns(C)\n" +
                "    Lines 2-2+R: The maze.\n" +
                "        MAZE KEY:\n" +
                "            Use '#' to signify a block/wall.\n" +
                "            Use '.' to signify an open space.\n" +
                "            Use 'S' to signify the start position.\n" +
                "            Use 'F' to signify the finish position.\n\n\n");
        System.out.println("Enter absolute path of input file:");
        String path = input.nextLine();
        AnalyzeMaze maze = new AnalyzeMaze(new File(path));
        System.out.println("SOLVING MAZE...");
        long st = System.nanoTime();
        maze.solve();
        long fin = System.nanoTime();
        System.out.println((double)((fin-st)*0.000000001) + " s.");
        System.out.println("MAZE SOLVED!\n");

        System.out.println("Follow the 'id' code below to print the original maze, solution,  or step count.");
        while(true)
        {
            System.out.println("\tid ('1')- Original maze");
            System.out.println("\tid ('2')- Solution path");
            System.out.println("\tid ('3')- Steps from start per cell");
            System.out.println("\tid ('min')- Length of shortest path");
            System.out.println("\tid ('end')- Terminate");
            System.out.print("id: ");
            String s = input.next();
            try
            {
                int id = Integer.parseInt(s);
                maze.displayGUI(id);
            }
            catch (Exception e)
            {
                if(s.trim().toUpperCase().equals("END"))
                    System.exit(0);
                else if(s.trim().toUpperCase().equals("MIN"))
                    System.out.println("The shortest path is: " + maze.getShortestPath() + " steps.");
                else
                    System.out.println("Invalid!");
                input.nextLine();
            }
        }

    }
}

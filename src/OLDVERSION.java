import java.util.*;
import java.io.*;

//This approach is cool too: https://www.youtube.com/watch?v=Zwh-QNlsurI
public class OLDVERSION {
	
	static int[][] maze;
	static int[][] cellparents;
	static boolean[][] visited;
	static String[][] optimumpath; //W: part of path, X: wall, O: open space

	static int NUM_ROWS;
	static int NUM_COLS;
	
	static int startx;
	static int starty;
	static int destx;
	static int desty;
	
	public static void main(String[] args) throws IOException {
		Scanner input = new Scanner(new File("mazeinput.txt"));
		NUM_ROWS = input.nextInt();
		NUM_COLS = input.nextInt();
		
		maze = new int[NUM_ROWS+2][NUM_COLS+2];
		cellparents = new int[NUM_ROWS+2][NUM_COLS+2];
		visited = new boolean[NUM_ROWS+2][NUM_COLS+2];
		optimumpath = new String[NUM_ROWS+2][NUM_COLS+2];
		
		for(int i = 0; i < NUM_ROWS+2; i++)
		{
			Arrays.fill(maze[i], 0);
			Arrays.fill(visited[i], false);
			Arrays.fill(optimumpath[i], "X");
		}
		for(int i = 1; i <= NUM_ROWS; i++)
		{
			for(int j = 1; j <= NUM_COLS; j++)
			{
				maze[i][j] = input.nextInt();
				if(maze[i][j] == 1)
					optimumpath[i][j] = ".";
				else
					optimumpath[i][j] = "#";
			}
		}
		
		optimumpath[1][1] = "S";
		optimumpath[optimumpath.length-2][optimumpath[0].length-2] = "F";
		startx = input.nextInt();
		starty = input.nextInt();
		destx = input.nextInt();
		desty = input.nextInt();
		displayPath(optimumpath);
		findPath(startx,starty);
	}

	public static void findPath()
	{

	}
	//dfs and backtrace to find path
	//if we reach a junction:
	//first try one path
	//if that leads us to destination, great! If it leads to a dead end,
	//backtrace (pop back from stack to junction point) 
	//then, try the other paths from that junction point 
	
	//in other words, if going one direction doesn't work, go another
	public static void findPath(int r, int c)
	{
		if(visited[r][c] || maze[r][c] == 0)
			return;
		
		if((r == destx && c == desty))
		{
			optimumpath[r][c] = "X";
			displayPath(optimumpath);
			System.exit(0);
		}
		visited[r][c] = true;
		optimumpath[r][c] = "X";

		findPath(r,c-1);
		findPath(r-1,c);
		findPath(r+1,c);
		findPath(r,c+1);
		optimumpath[r][c] = " ";
	}
	
	public static void displayPath(String[][] arr)
	{
		System.out.println("--------------------------------------------------------------------");
		for(int i = 1; i <= NUM_ROWS; i++)
		{
			for(int j = 1; j <= NUM_COLS; j++)
			{
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("--------------------------------------------------------------------");

	}
}

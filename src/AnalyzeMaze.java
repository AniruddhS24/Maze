import java.io.*;
import java.util.*;

public class AnalyzeMaze
{
	private Cell[][] maze; //Original maze; contains the actual start/finish objects
	private Cell[][] solutionmaze; //Altered solution maze
	private boolean[][] visited; //Visited cells
	private Cell start; //Actual reference to cell in both maze arrays
	private Cell finish; //Actual reference to cell in both maze arrays
	private static final char[] types = {'#','X','.','S','F'}; //Types of cells
	private int NUM_ROWS;
	private int NUM_COLS;
	private final int[] dir = {1,-1,1,-1};
	private boolean nosol;

	/**
	 * A single cell of the maze
	 * Attributes: row, column, type(free space, wall, etc.), dist(distance from 'start' Cell), parent Cell
	 */
	private class Cell
	{
		private int r;
		private int c;
		private char type;
		private int dist;
		private Cell parent;

		private Cell(int r, int c, char type)
		{
			this.r = r;
			this.c = c;
			this.type = type;
			this.dist = 0;
			this.parent = null;
		}
	}

	/**
	 * CONSTRUCTOR
	 * Gets input; initializes instance variables
	 * @param inp Input file
	 */
	public AnalyzeMaze(File inp)
	{
		int tempcts = 0;
		int tempctf = 0;
		//GET INPUT AND INITIALIZE VARIABLES
		try
		{
			Scanner input = new Scanner(inp.getAbsoluteFile());
			NUM_ROWS = input.nextInt();
			NUM_COLS = input.nextInt();
			maze = new Cell[NUM_ROWS][NUM_COLS];
			solutionmaze = new Cell[NUM_ROWS][NUM_COLS];
			visited = new boolean[NUM_ROWS][NUM_COLS];
			nosol = false;

			input.nextLine();
			for(int i = 0; i < NUM_ROWS; i++) {
				String line = input.nextLine();
				line = line.replace(" ","");
				for (int j = 0; j < NUM_COLS; j++) {

					char t = line.charAt(j);
					boolean distid = false;

					for(int k = 0; k < types.length; k++)
						if(t == types[k])
							distid = true;
					if(!distid)
					{
						System.out.println("Oops! The format of the input maze is incorrect.\n" +
								"Please refer to the instructions above.");
						System.exit(0);
					}
					maze[i][j] = new Cell(i, j, t);
					solutionmaze[i][j] = new Cell(i, j, t);
					if (t == types[3])
					{
						start = maze[i][j] = solutionmaze[i][j];
						tempcts++;
					}
					if (t == types[4])
					{
						finish = maze[i][j] = solutionmaze[i][j];
						tempctf++;
					}
				}
			}
		}
		catch(FileNotFoundException e)
		{
			System.out.println("Error! The maze input file couldn't be found!");
			System.exit(0);
		}
		if(start == null || finish == null || tempcts > 1 || tempctf > 1)
		{
			System.out.println("Error! Must specify a start and a finish!");
			System.exit(0);
		}
	}



	/**
	 * Gets the shortest path length
	 * @return Dist from start at finish
	 */
	public int getShortestPath()
	{
		if(nosol)
			return -1;
		return finish.dist;
	}

	/**
	 *MAIN SOLVE METHOD:
	 * USES A BFS (can also be called a 'floodfill' in this case)
	 *	Process:
	 *	1. Enqueue the start cell
	 *  2. Add the OPEN AND UNVISITED adjacent cells of the current cell to the queue. Be sure to update
	 *  	their distance distues to the dist of the current node from the start + 1 (because the adjacent node is
	 *  	one more step away from however many steps the current node is from the start cell).	
	 *	3. On every iteration, poll the head of the queue. This becomes the current cell, or cell that is being visited.
	 *
	 * In other words, the BFS visits all cells that are one step away, two steps away, three steps away... until
	 * all cells have been visited (the queue is empty).
	 * 
	 * Note: There is no need to do Math.min() on a cell's distance distue because the BFS visits ALL cells 1 step away,
	 * 2 steps away, etc. Therefore, the algorithm never visits a cell N steps away before examining all the cells
	 * less than N steps away.	 
	 **/
	public void solve()
	{
		Queue<Cell> q = new LinkedList<Cell>();
		q.add(start);
		Cell cur = null;
		while(!q.isEmpty())
		{
			cur = q.poll();
			visited[cur.r][cur.c] = true;
			if(cur.r-1 >= 0 && (solutionmaze[cur.r-1][cur.c].type == types[2] || solutionmaze[cur.r-1][cur.c].type == types[4]) && !visited[cur.r-1][cur.c] && solutionmaze[cur.r-1][cur.c].dist==0)
			{
				solutionmaze[cur.r-1][cur.c].dist = cur.dist+1;
				solutionmaze[cur.r-1][cur.c].parent = cur;
				q.add(solutionmaze[cur.r-1][cur.c]);
			}
			if(cur.r+1 < NUM_ROWS && (solutionmaze[cur.r+1][cur.c].type == types[2] || solutionmaze[cur.r+1][cur.c].type == types[4]) && !visited[cur.r+1][cur.c]&& solutionmaze[cur.r+1][cur.c].dist==0)
			{
				solutionmaze[cur.r+1][cur.c].dist = cur.dist+1;
				solutionmaze[cur.r+1][cur.c].parent = cur;
				q.add(solutionmaze[cur.r+1][cur.c]);
			}
			if(cur.c-1 >= 0 && (solutionmaze[cur.r][cur.c-1].type == types[2] || solutionmaze[cur.r][cur.c-1].type == types[4]) && !visited[cur.r][cur.c-1] && solutionmaze[cur.r][cur.c-1].dist==0)
			{
				solutionmaze[cur.r][cur.c-1].dist = cur.dist+1;
				solutionmaze[cur.r][cur.c-1].parent = cur;
				q.add(solutionmaze[cur.r][cur.c-1]);
			}
			if(cur.c+1 < NUM_COLS && (solutionmaze[cur.r][cur.c+1].type == types[2] || solutionmaze[cur.r][cur.c+1].type == types[4]) && !visited[cur.r][cur.c+1] && solutionmaze[cur.r][cur.c+1].dist==0)
			{
				solutionmaze[cur.r][cur.c+1].dist = cur.dist+1;
				solutionmaze[cur.r][cur.c+1].parent = cur;
				q.add(solutionmaze[cur.r][cur.c+1]);
			}
		}
		if(finish.dist == 0)
			nosol = true;
		else
			retrace(solutionmaze[finish.r][finish.c]);
	}

	/**
	 * Change the 'types' in solution maze to 'X' if the cell lies in the solution path.
	 * START FROM FINISH AND GO BACKWARDS UNTIL YOU REACH START (which is guaranteed).
	 * If you encounter more than one adjacent tile, pick a random one as both will lead to a shortest path.
	 * @param c Current cell
	 */
	private void retrace(Cell c)
	{
		if(c.type == types[3]) //we stop when we reach the start
			return;
		if(c.type != types[4]) //we don't want to change the finish position to 'X'
			c.type = types[1];
		retrace(c.parent);
	}

	/**
	 * Display method for GUI (uses ViewGUI class)
	 * @param id Display id
	 */
	public void displayGUI(int id) //1: original maze; 2: solution path; 3: maze with steps of each cell;
	{
		if(id != 1 && id != 2 && id != 3)
		{
			System.out.println("Error! Please enter a distid ID for the display method.");
			return;
		}
		if(id != 1 && nosol)
		{
			System.out.println("ERROR");
			for(int i = 0; i < solutionmaze.length*2; i++)
				System.out.print("-");
			System.out.println("\nA solution does not exist!\nIt is impossible to reach the finish line.");
			for(int i = 0; i < solutionmaze.length*2; i++)
				System.out.print("-");
			return;
		}
		String[][] temp = new String[NUM_ROWS][NUM_COLS];
		for(int a = 0; a < NUM_ROWS; a++)
			for(int b = 0; b < NUM_COLS; b++)
				temp[a][b] = String.valueOf(solutionmaze[a][b].type);

		String[][] tempforid3 = new String[NUM_ROWS][NUM_COLS];
		for(int a = 0; a < NUM_ROWS; a++)
			for(int b = 0; b < NUM_COLS; b++)
				if(solutionmaze[a][b].type == '.' || solutionmaze[a][b].type == 'S' || solutionmaze[a][b].type == 'F' || solutionmaze[a][b].type == 'X')
					tempforid3[a][b] = String.valueOf(solutionmaze[a][b].dist);
				else
					tempforid3[a][b] = String.valueOf(solutionmaze[a][b].type);
		switch (id)
		{
			case 1:
				System.out.println("ORIGINAL MAZE");
				ViewGUI.start(temp,1);
				break;
			case 2:
				System.out.println("SOLUTION PATH");
				ViewGUI.start(temp,2);
				break;
			case 3:
				System.out.println("STEPS FROM START PER CELL");
				ViewGUI.start(tempforid3,3);
				break;

		}

	}

	/**
	 * Display method for console output
	 * @param id Display id
	 */
	public void display(int id) //1: original maze; 2: solution path; 3: maze with steps of each cell;
	{
		if(id != 1 && id != 2 && id != 3 && id!=4)
		{
			System.out.println("Error! Please enter a distid ID for the display method.");
			return;
		}
		if(id != 1 && nosol)
		{
			System.out.println("ERROR");
			for(int i = 0; i < solutionmaze.length*2; i++)
				System.out.print("-");
			System.out.println("\nA solution does not exist!\nIt is impossible to reach the finish line.");
			for(int i = 0; i < solutionmaze.length*2; i++)
				System.out.print("-");
			return;
		}
		switch (id)
		{
			case 1:
				System.out.println("ORIGINAL MAZE");
				break;
			case 2:
				System.out.println("SOLUTION PATH");
				break;
			case 3:
				System.out.println("STEPS FROM START PER CELL");
				break;
		}
		for(int i = 0; i < solutionmaze.length*2; i++)
			System.out.print("-");
		System.out.println();
		for(int i = 0; i < NUM_ROWS; i++)
		{
			for(int j = 0; j < NUM_COLS; j++)
			{
				switch(id)
				{
					case 1:
						System.out.print(maze[i][j].type + " ");
						break;
					case 2:
						System.out.print(solutionmaze[i][j].type + " ");
						break;
					case 3:
						if(solutionmaze[i][j].dist == 0)
							System.out.print(solutionmaze[i][j].type + " ");
						else
							System.out.print(solutionmaze[i][j].dist + " ");
						break;
				}
			}
			System.out.println();
		}
		for(int i = 0; i < solutionmaze.length*2; i++)
			System.out.print("-");
		System.out.println();
	}
}

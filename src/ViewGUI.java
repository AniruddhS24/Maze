import oracle.jrockit.jfr.JFR;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewGUI extends JFrame {

    private String[][] maze;
    static int sizefact;
    static int id;

    public ViewGUI(String[][] m, int id) {
        super("Maze Output Display");
        this.id = id;
        maze = new String[m.length][m[0].length];
        for (int i = 0; i < m.length; i++)
            for(int j=0; j < m[i].length; j++)
                maze[i][j] = m[i][j];

        double len = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().getBounds().getHeight();
        int mx = Math.max(maze.length,maze[0].length);
        sizefact = (int) (len*0.8/mx);
        setSize((int)(sizefact*maze[0].length)+50+sizefact,(int)(sizefact*maze.length)+50+sizefact);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.translate(50,50);
        for(int row = 0; row < maze.length; row++) {
            for(int col = 0; col < maze[row].length; col++) {
                Color color = Color.WHITE;
                switch(id)
                {
                    case 1:
                        if(maze[row][col].equals("#"))
                            color = Color.BLACK;
                        break;
                    case 2:
                        if(maze[row][col].equals("#"))
                            color = Color.BLACK;
                        if(maze[row][col].equals("F") || maze[row][col].equals("X") || maze[row][col].equals("S"))
                            color = Color.GREEN;
                        break;
                }
                g.setColor(color);
                g.fillRect(sizefact*col, sizefact*row, sizefact, sizefact);
                g.setColor(Color.BLACK);
                g.drawRect(sizefact*col, sizefact*row, sizefact, sizefact);
                if(maze[row][col].equals("S") || maze[row][col].equals("F"))
                {
                    Font currentFont = g.getFont();
                    float fontsize =  currentFont.getSize() * sizefact/18F;
                    Font newFont = currentFont.deriveFont(currentFont.getSize() * sizefact/18F);
                    g.setFont(newFont);
                    g.drawString(String.valueOf(maze[row][col]),sizefact*col+(int)fontsize/4,sizefact*row+(int)fontsize);
                    g.setFont(currentFont);
                }
                if(id == 3)
                {
                    Font currentFont = g.getFont();
                    float fontsize =  currentFont.getSize() * sizefact/18F;
                    Font newFont = currentFont.deriveFont(currentFont.getSize() * sizefact/18F);
                    g.setFont(newFont);
                    g.drawString(String.valueOf(maze[row][col]),sizefact*col+(int)fontsize/4,sizefact*row+(int)fontsize);
                    g.setFont(currentFont);
                }
            }
        }

    }

    public static void start(String[][] m, int id) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewGUI v = new ViewGUI(m,id);
                v.setVisible(true);
            }
        });
    }

}
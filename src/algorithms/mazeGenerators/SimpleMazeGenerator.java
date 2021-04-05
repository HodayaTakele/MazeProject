package algorithms.mazeGenerators;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns)
    {
        Maze maze = new Maze(rows, columns, 0);
        int r = rows - 1;
        int c = columns - 1;
        int i=0, j=0, s_col=0;
        while( (i < rows-1) || (j < columns -1))
            if (i == r) {
                //go right
                j++;
            }
            else if ( j == c){
                // go down
                down(maze,i,j,s_col);
                s_col = j;
                i++;

            }
            else{
                // go down or right
                Random rand = new Random();
                int r_d = rand.nextInt(2);
                if(r_d == 0)
                {
                    //go right
                    j++;
                }
                else {
                    // go down
                    down(maze,i,j,s_col);
                    s_col = j;
                    i++;
                }
            }
        down(maze,r,c,s_col);
        return maze;
    }
    private void down(Maze maze, int r, int c, int s_col)
    {
        Random rand = new Random();
        for (int i=0; i<s_col; i++)
        {
            if(rand.nextInt(2)==1)
            {
                //build wall
                maze.buildWall(r,i);
            }
        }
        for (int j=c+1; j<maze.getColumns();j++)
        {
            if(rand.nextInt(2)==1)
            {
                //build wall
                maze.buildWall(r,j);
            }
        }
    }
}

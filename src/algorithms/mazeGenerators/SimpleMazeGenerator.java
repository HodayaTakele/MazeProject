package algorithms.mazeGenerators;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        Maze maze = new Maze(rows, columns, 0);
        int r = rows - 1;
        int c = columns - 1;
        int i=0, j=0, s_col=0;
        while( (i < rows-1) || (j < columns -1))
            //go right
            if (i == r) { j++; }
            // go down
            else if ( j == c){
                down(maze,i,j,s_col);
                s_col = j;
                i++;

            }
            // go down or right
            else{
                Random rand = new Random();
                int r_d = rand.nextInt(2);
                //go right
                if(r_d == 0) { j++; }
                // go down
                else {
                    down(maze,i,j,s_col);
                    s_col = j;
                    i++;
                }
            }
        down(maze,r,c,s_col);
        return maze;
    }

    private void down(Maze maze, int r, int c, int s_col) {
        Random rand = new Random();
        //build wall
        for (int i=0; i<s_col; i++)
        {
            if(rand.nextInt(2)==1) { maze.buildWall(r,i); }
        }
        //build wall
        for (int j=c+1; j<maze.getColumns();j++)
        {
            if(rand.nextInt(2)==1) { maze.buildWall(r,j); }
        }
    }

}

import java.util.ArrayList;
import java.util.Scanner;

public class MazePositionState implements State
    {
    private final float DIAG = 1.4142f;
    private String maze;
    private int x;
    private int y;
    private State parent;
    private char[][] board;
    private float gV;
    
    public MazePositionState(String maze, int x, int y)
        {
        this.maze = maze;
        this.x = y;
        this.y = x;
        this.parent = null;
        this.gV = 0;
        
        int rowLength = getMazeRow();
        int colLength = getMazeCol();
        
        this.board = new char[colLength][rowLength];
        
        Scanner scanner = new Scanner(maze);
        int currentLine = 0;
        
        while(scanner.hasNextLine())
            {
            String line = scanner.nextLine();
            line.split("\\r?\\n");
            board[currentLine++] = line.toCharArray();
            }
        
        scanner.close();
        }
    
    public MazePositionState(String maze, int x, int y, State parent, char[][] newBoard, boolean isDiagonal)
        {
        this.maze = maze;
        this.x = x;
        this.y = y;
        this.parent = parent;

        this.board = newBoard;      
        
        if(isDiagonal)
            this.gV += DIAG;
        else
            this.gV += 1;
        
        }
    
    private int getMazeRow()
        {
        int rowLength = 0;
        char skip = '\n';
        while(skip != maze.charAt(rowLength))
            rowLength++;
        return rowLength;
        }
    
    private int getMazeCol()
        {
        int colLength = 0;
        int tracker = 0;
        int counter = 0;
        while(tracker < maze.length())
            {
            if(counter == getMazeRow())
                {
                colLength++;
                tracker++;
                counter = 0;
                }
            else
                {
                tracker++;
                counter++;
                }
            }
        return colLength;
        }
    
    /**
     * Return an array of all of the children of this state.  The returned array 
     * should be just large enough to hold all of the children of this state.
     * @return Array of children of this state
     */
    public State[] getChildren()
        {
        int index = 0;
        State[] children = new State[getNumChildren()];
        
        if(board[x-1][y] == ' ')
            children[index++] = new MazePositionState(this.maze, x-1, y, this, board, false);
        if(board[x+1][y] == ' ')
            children[index++] = new MazePositionState(this.maze, x+1, y, this, board, false);
        if(board[x][y-1] == ' ')
            children[index++] = new MazePositionState(this.maze, x, y-1, this, board, false);
        if(board[x][y+1] == ' ')
            children[index++] = new MazePositionState(this.maze, x, y+1, this, board, false);
        if(board[x-1][y-1] == ' ' && board[x-1][y] == ' ' && board[x][y-1] == ' ')
            children[index++] = new MazePositionState(this.maze, x-1, y-1, this, board, true);
        if(board[x+1][y-1] == ' ' && board[x+1][y] == ' ' && board[x][y-1] == ' ')
            children[index++] = new MazePositionState(this.maze, x+1, y-1, this, board, true);
        if(board[x-1][y+1] == ' ' && board[x-1][y] == ' ' && board[x][y+1] == ' ')
            children[index++] = new MazePositionState(this.maze, x-1, y+1, this, board, true);
        if(board[x+1][y+1] == ' ' && board[x+1][y] == ' ' && board[x][y+1] == ' ')
            children[index++] = new MazePositionState(this.maze, x+1, y+1, this, board, true);
        
        return children;
        }
    
    private int getNumChildren()
        {
        int num = 0;
        
        if(board[x-1][y] == ' ')
            num++;
        if(board[x+1][y] == ' ')
            num++;
        if(board[x][y-1] == ' ')
            num++;
        if(board[x][y+1] == ' ')
            num++;
        if(board[x-1][y-1] == ' ' && board[x-1][y] == ' ' && board[x][y-1] == ' ')
            num++;
        if(board[x+1][y-1] == ' ' && board[x+1][y] == ' ' && board[x][y-1] == ' ')
            num++;
        if(board[x-1][y+1] == ' ' && board[x-1][y] == ' ' && board[x][y+1] == ' ')
            num++;
        if(board[x+1][y+1] == ' ' && board[x+1][y] == ' ' && board[x][y+1] == ' ')
            num++;
        
        return num;
        }
    
    /**
     * Return the parent of this state (that is, the state that generated this
     * state), or null if this is an initial state
     * @return
     */
    public State getParent()
        {
        return this.parent;
        }
    
    /**
     * Returns true if this state is equal to another state, ignoring parents
     * @param other The state to compare to 
     * @return True if the states are equal
     */
    public boolean equals(State other)
        {
        if(x == ((MazePositionState) other).getX() && (y == ((MazePositionState) other).getY()))
            {
            return true;
            }
        return false;
        
        }
    
    /**
     * String representation of the state
     * @return
     */
    public String toString()
        {
        return "(" + this.y + "," + this.x + ")";
        }
    
    /**
     * Return the distance of this state from the initial state.  
     * @return
     */
    public float gValue()
        {
        float g = this.gV;
        State p = this.getParent();
        while(p != null)
            {
            g += ((MazePositionState) p).getG();
            p = p.getParent();
            }
        
        return g;
        }
    
    private float getG()
        {
        return this.gV;
        }
    
    /**
     * Hash code for this state.  States that are equal (via the above equals) need
     * to have the same HashCode
     * @return
     */
    public int hashCode()
        {
        String hash = "";
        hash += x;
        hash += y;
        return Integer.parseInt(hash);
        }
    
    /**
     * Returns a string representation of the solution path.  See concrete states for more information
     * @return String representation of the standard solution path
     */
    public String solutionPath()
        {
        ArrayList<String> pathList = new ArrayList<String>();
        pathList.add(toString());
        String path = "";
        
        State p = getParent();
        while(p != null)
            {
            pathList.add(p.toString());
            p = p.getParent();
            }
        
        for(int i = pathList.size() - 1; i > 0; i--)
            {
            path += pathList.get(i) + ", ";
            }
        
        path += pathList.get(0);
        
        return path;
        }
    
    /**
     * Returns a more verbose representation of the solution path.  See concrete states for more information.
     * @return String representation of the verbose solution path
     */
    public String solutionPathExtended()
        {
        String result = "";
        State p = getParent();
        
        char[][] solution = board;
        board[getX()][getY()] = '.';
        
        while(p != null)
            {
            int r = ((MazePositionState) p).getX();
            int c = ((MazePositionState) p).getY();
            solution[r][c] = '.';
            p = p.getParent();
            }
        
        for(int row = 0; row < solution.length; row++)
            {
            for(int col = 0; col < solution[row].length; col++)
                result += solution[row][col];
            result += "\n";
            }
        
        return result;
        }
        
    /**
     * Returns the (estimated) distance from this state to an arbitrary different state.  
     * The heuristic (h) value is the estimated distance of that state from the goal.
     * @return Estimated distance to the state
     */
    public float distanceToState(State otherState)
        {
        float distance = 0.0f;
        
        distance = (float)(Math.sqrt(Math.pow(((MazePositionState) otherState).getX() - this.x, 2) + 
                                     Math.pow(((MazePositionState) otherState).getY() - this.y, 2)));
        
        return distance;
        }
    
    private int getX()
        {
        return this.x;
        }
    
    private int getY()
        {
        return this.y;
        }
    
    
    }

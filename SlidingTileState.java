import java.util.ArrayList;

public class SlidingTileState implements State
    {
    private int width;
    private int height;
    private int[] tiles1D;
    private int[][] tiles2D;
    private State parent;
    private float gV;
    
    public SlidingTileState(int inputWidth, int inputHeight, int[] inputTiles)
        {
        this.width = inputWidth;
        this.height = inputHeight;
        this.tiles1D = inputTiles;
        this.parent = null;
        this.gV = 0;
        
        this.tiles2D = new int[this.height][this.width];
        
        int index = 0;
        for(int row = 0; row < tiles2D.length; row++)
            {
            for(int col = 0; col < tiles2D[row].length; col++)
                {
                tiles2D[row][col] = inputTiles[index];
                index++;
                }
            }
        
        }
    
    public SlidingTileState(int inputWidth, int inputHeight, int[][] inputTiles, State parent)
        {
        this.width = inputWidth;
        this.height = inputHeight;
        this.parent = parent;
        this.gV += 1;
        
        this.tiles2D = inputTiles;
        
        }
    
    /**
     * Return an array of all of the children of this state.  The returned array 
     * should be just large enough to hold all of the children of this state.
     * @return Array of children of this state
     */
    public State[] getChildren()
        {
        State[] children = new State[getNumChildren()];
        
        int[][] temp1 = new int[height][width];
        for(int row = 0; row < tiles2D.length; row++)
            for(int col = 0; col < tiles2D[row].length; col++)
                temp1[row][col] = tiles2D[row][col];
        
        int[][] temp2 = new int[height][width];
        for(int row = 0; row < tiles2D.length; row++)
            for(int col = 0; col < tiles2D[row].length; col++)
                temp2[row][col] = tiles2D[row][col];
        
        int[][] temp3 = new int[height][width];
        for(int row = 0; row < tiles2D.length; row++)
            for(int col = 0; col < tiles2D[row].length; col++)
                temp3[row][col] = tiles2D[row][col];
        
        int[][] temp4 = new int[height][width];
        for(int row = 0; row < tiles2D.length; row++)
            for(int col = 0; col < tiles2D[row].length; col++)
                temp4[row][col] = tiles2D[row][col];
        
        
        int index = 0;
        for(int row = 0; row < tiles2D.length; row++)
            {
            for(int col = 0; col < tiles2D[row].length; col++)
                {
                if(tiles2D[row][col] == 0)
                    {
                    if(col > 0)
                        {
                        SlidingTileState neighbor2 = new SlidingTileState(this.width, this.height, temp3, this);
                        neighbor2.swap(row, col, row, col - 1, neighbor2.getTiles2D());
                        children[index] = neighbor2;
                        index++;
                        }
                    if(row < tiles2D.length - 1)
                        {
                        SlidingTileState neighbor = new SlidingTileState(this.width, this.height, temp2, this);
                        swap(row, col, row + 1, col, neighbor.getTiles2D());
                        children[index] = neighbor;
                        index++;
                        }
                    
                    if(row > 0)
                        {
                        SlidingTileState neighbor = new SlidingTileState(this.width, this.height, temp1, this);
                        neighbor.swap(row, col, row - 1, col, neighbor.getTiles2D());
                        children[index] = neighbor;
                        index++;
                        }
                    if(col < tiles2D[row].length - 1)
                        {
                        SlidingTileState neighbor = new SlidingTileState(this.width, this.height, temp4, this);
                        swap(row, col, row, col + 1, neighbor.getTiles2D());
                        children[index] = neighbor;
                        index++;
                        }
                    return children;
                    }
                }
            }
        
        return children;
        }
    
    private int getNumChildren()
        {
        int size = 0;
        for(int row = 0; row < tiles2D.length; row++)
            {
            for(int col = 0; col < tiles2D[row].length; col++)
                {
                if(tiles2D[row][col] == 0)
                    {
                    if(row > 0)
                        {
                        size++;
                        }
                    if(row < tiles2D.length - 1)
                        {
                        size++;
                        }
                    if(col > 0)
                        {
                        size++;
                        }
                    if(col < tiles2D[row].length - 1)
                        {
                        size++;
                        }
                    return size;
                    }
                }
            }
        return size;
        }
    
    private int[][] swap(int row, int col, int row2, int col2, int[][] a)
        {
        if(row < 0 || col >= a[row].length || row2 < 0 || col2 >= a[row2].length)
            {
            return a;
            }
        
        int temp = a[row][col];
        a[row][col] = a[row2][col2];
        a[row2][col2] = temp;
        return a;
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
        int[][] a = ((SlidingTileState) other).getTiles2D();
        for(int row = 0; row < tiles2D.length; row++)
            for(int col = 0; col < tiles2D[row].length; col++)
                if (this.tiles2D[row][col] != a[row][col])
                    return false;
        return true;
        }
    
    /**
     * String representation of the state
     * @return
     */
    public String toString()
        {
        String result = "";
        
        for(int row = 0; row < height; row++)
            {
            for(int col = 0; col < width; col++)
                {
                result += String.format("%2s", "|" + tiles2D[row][col]);
                }
            result += "|\n";
            }
        
        return result;
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
            g += ((SlidingTileState) p).getG();
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
        int hash = 0;
        for(int row = 0; row < tiles2D.length; row++)
            for(int col = 0; col < tiles2D[row].length; col++)
                hash = hash*31 + tiles2D[row][col];
        return hash;
        }
    
    /**
     * Returns a string representation of the solution path.  See concrete states for more information
     * @return String representation of the standard solution path
     */
    public String solutionPath()
        {
        ArrayList<String> pathList = new ArrayList<String>();
        String path = "";
        
        State p = getParent();
        State c = this;
        while(p != null)
            {
            int[][] a = ((SlidingTileState) p).getTiles2D();
            int[][] b = ((SlidingTileState) c).getTiles2D();
            
            for(int row = 0; row < height; row++)
                {
                for(int col = 0; col < width; col++)
                    {
                    if(a[row][col] != b[row][col] && a[row][col] == 0)
                        {
                        pathList.add(Integer.toString(b[row][col]));
                        break;
                        }
                    }
                }
            c = p;
            p = p.getParent();
            }
        
        for(int i = pathList.size() - 1; i >= 1; i--)
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
        ArrayList<String> pathList = new ArrayList<String>();
        pathList.add(toString() + "\n");
        String path = "";
        
        State p = getParent();
        while(p != null)
            {
            pathList.add(p.toString() + "\n");
            p = p.getParent();
            }
        
        for(int i = pathList.size() - 1; i >= 0; i--)
            {
            path += pathList.get(i);
            }
        
        return path;
        }
    
    /**
     * Returns the (estimated) distance from this state to an arbitrary different state.  
     * The heuristic (h) value is the estimated distance of that state from the goal.
     * @return Estimated distance to the state
     */
    public float distanceToState(State otherState)
        {
        float distance = 0;
        
        for(int row = 0; row < tiles2D.length; row++)
            {
            for(int col = 0; col < tiles2D[row].length; col++)
                {
                int num = tiles2D[row][col];
                if(num != 0)
                    {
                    distance += Math.abs((row - getRow(otherState, num))) + Math.abs((col - getCol(otherState, num)));
                    }
                }
            }
        return distance;
        }
    
    private int[][] getTiles2D()
        {
        return tiles2D;
        }
    
    private int getRow(State s, int value)
        {
        int[][] arr = ((SlidingTileState) s).getTiles2D();
        for(int row = 0; row < arr.length; row++)
            {
            for(int col = 0; col < arr[row].length; col++)
                {
                if(arr[row][col] == value)
                    return row;
                }
            }
        return -1;
        }
    
    private int getCol(State s, int value)
        {
        int[][] arr = ((SlidingTileState) s).getTiles2D();
        for(int row = 0; row < arr.length; row++)
            {
            for(int col = 0; col < arr[row].length; col++)
                {
                if(arr[row][col] == value)
                    return col;
                }
            }
        return -1;
        }
    

    }




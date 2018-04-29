import java.util.Scanner;
import java.io.File;;

public class TestAstar {


    public static void testSlidingTilePuzzle(State initial, State goal, SearchType[] searches,  boolean showDistance, boolean showExtended)
        {
        for (SearchType st : searches) 
            {

            System.out.println("Search Type: " + st);
            
            State finalState = Search.doSearch(initial, goal, st, true);
            if (finalState != null)
                {               
                System.out.println("Solution cost: " + finalState.gValue() );
                System.out.println(finalState.solutionPath());
                if (showExtended)
                    {
                    System.out.println(finalState.solutionPathExtended());
                    }
                }
            else 
                {
                System.out.println("No Solution");
                }

            }
        }

    public static void testSlidingTile(boolean showExtended)
        {
        SlidingTileState start;  
        SlidingTileState end;  

        start = new SlidingTileState(3, 3, new int[] {1, 4,2,3,5,8,6,7,0});
        end =  new SlidingTileState(3, 3, new int[] {0, 1,2,3,4,5,6,7,8});
        System.out.println("Easy 8-Puzzle");
        testSlidingTilePuzzle(start, end,SearchType.values(), true, showExtended);


        start = new SlidingTileState(3, 3, new int[] {0, 1,2,3,4,5,6,7,8});
        end =  new SlidingTileState(3, 3, new int[] {1, 2,5,4,0,8,3,6,7});
        System.out.println("Easy 8-Puzzle");
        testSlidingTilePuzzle(start, end,SearchType.values(), true, showExtended);

        System.out.println("Hard 8-Puzzle");

        start   = new SlidingTileState(3, 3, new int[] {0, 1,2,3,4,5,6,7,8});
        end   = new SlidingTileState(3, 3, new int[] {8, 7,6,5,4,3,2,1,0});
        testSlidingTilePuzzle(start, end, SearchType.values(),true, showExtended);

        System.out.println("Impossible 8-Puzzle");

        start   = new SlidingTileState(3, 3, new int[] {0, 1,2,3,4,5,6,7,8});
        end   = new SlidingTileState(3, 3, new int[] {0, 1,2,3,4,5,6,8,7});
        testSlidingTilePuzzle(start, end,SearchType.values(), true, showExtended);

        System.out.println("2 x 4 puzzle");

        start   = new SlidingTileState(4, 2, new int[] {7, 6, 5, 4, 3, 2, 1, 0});
        end   = new SlidingTileState(4, 2, new int[] {0, 1,2,3,4,5,6,7});
        testSlidingTilePuzzle(start, end, SearchType.values(), true, showExtended);

        System.out.println("Easy 15-puzzle");

        start   = new SlidingTileState(4, 4, new int[] {1,5,2,3,4,6,10,7,12,9,14,11,13,8,15,0});
        end   = new SlidingTileState(4, 4, new int[] {0, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
        testSlidingTilePuzzle(start, end, SearchType.values(), true, showExtended);

        System.out.println("Hard 15-puzzle  (Greedy search only!)");

        start   = new SlidingTileState(4, 4, new int[] {15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,0});
        end   = new SlidingTileState(4, 4, new int[] {0, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});
        testSlidingTilePuzzle(start, end, new SearchType[] {SearchType.GREEDY}, true, showExtended);
        }


    public static void testMaze(boolean showExtended, String mazeFile, int startX, int startY, int endX, int endY)
        {
        String maze = "";
        try
            {
            StringBuilder sb = new StringBuilder();
            Scanner s = new Scanner(new File(mazeFile));
            while (s.hasNext())
                {
                sb.append(s.nextLine());
                sb.append("\n");
                }
            s.close();
            maze = sb.toString();
            }
        catch (Exception e)
            {
            System.out.println("ERROR! Could not test maze " + mazeFile);
            System.out.println(e.toString());
            }

        for (SearchType st : SearchType.values()) 
            {
            State finalState = Search.doSearch(new MazePositionState(maze, startX,startY), new MazePositionState(maze, endX, endY), st, true);
            System.out.println("Search Type: " + st);

            if (finalState != null)
                {
                System.out.println("Solution cost: " + finalState.gValue() );
                System.out.println(finalState.solutionPath());

                if (showExtended)
                    {
                    System.out.println(finalState.solutionPathExtended());
                    }
                }
            else 
                {
                System.out.println("No Solution");
                }
            }
        }


    public static void testMazes(boolean showExtended)
        {
        testMaze(showExtended, "mazeSmall.txt", 1, 1, 18,17);
        testMaze(showExtended, "maze1.txt", 1, 1, 98, 1);
        testMaze(showExtended, "maze1.txt", 1, 1, 98, 38);
        }

    public static void main(String args[])
        {
        testMazes(true);
        testSlidingTile(true);
        }


    }
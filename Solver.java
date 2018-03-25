import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import java.util.ArrayList;
/**
 *
 * @author Gordon Portzline
 */
public class Solver {
    private Board initialBoard;
    private Integer mvs;
    public Solver(Board b) {
        if(b == null) throw new NullPointerException();
        if(!b.isSolvable()) throw new IllegalArgumentException("Inital board cannot be unsolvable");
        initialBoard = b;
        mvs = null;
    }
    
    public int moves() {
        if(mvs != null) return mvs;
        else throw new NullPointerException("Please execute solution() method before executing moves() method");
    }
    
    public Iterable<Board> solution() {
        // Initialize types and structs needed for solving the board
        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode gameTree = new SearchNode(initialBoard, 0);
        Iterable<Board> neighbors = initialBoard.neighbors();
        gameTree.setChildren(neighbors, 1);
        int m = 0;
        ArrayList<Board> goalPath = new ArrayList<>();
        goalPath.add(initialBoard);
        
        // iteratively evaluate neighbors, build the game tree, and mark the optimal path using MinPQ
        SearchNode p = gameTree;
        while(!p.board.isGoal()) {
            // add children
            for(Board b : neighbors) {
                if(!b.equals(p.board)) { // critical optimization
                    pq.insert(new SearchNode(b, m, p)); // assert parent is not null
                }
            }
            SearchNode lowestPriorityNode = pq.delMin();
            lowestPriorityNode.flagAsLowestPriorityChild();
            goalPath.add(lowestPriorityNode.board);
            neighbors = lowestPriorityNode.board.neighbors();
            lowestPriorityNode.setChildren(neighbors, m);
            p = lowestPriorityNode;
            m++;
        }
        mvs = m;
        return goalPath;
    }
      
    public static void testSolver() {
        Solver s = new Solver(new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, {7, 8, 6} }));
        for(Board b : s.solution()) {
            System.out.printf("%s", b.toString());
        }
        System.out.printf("%nmoves to get to solution was: %d%n", s.moves());
    }
    
    public static void main(String[] args) {
        // create initial board from file
        if(args.length > 0 && !args[0].equals("")) {
            In in = new In(args[0]);
            int N = in.readInt();
            int[][] blocks = new int[N][N];
            for (int i = 0; i < N; i++)
                for (int j = 0; j < N; j++)
                    blocks[i][j] = in.readInt();
            Board initial = new Board(blocks);

            // check if puzzle is solvable; if so, solve it and output solution
            Stopwatch watch = new Stopwatch();
            if (initial.isSolvable()) {
                Solver solver = new Solver(initial);                
                for (Board board : solver.solution())
                    StdOut.println(board);
                StdOut.println("Minimum number of moves = " + solver.moves());
                System.out.printf("calculating solution took: %fms%n", watch.elapsedTime());
            }
            // if not, report unsolvable
            else {
                StdOut.println("Unsolvable puzzle");
            }
        }
        else { testSolver(); }
    }
}

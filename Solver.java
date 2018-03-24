import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
/**
 *
 * @author Gordon Portzline
 */
public class Solver {
    private Board initialBoard;
    private int mvs;
    public Solver(Board b) {
        if(b == null) throw new NullPointerException();
        if(!b.isSolvable()) throw new IllegalArgumentException("Inital board cannot be unsolvable");
        initialBoard = b;
    }
    
    public int moves() { return mvs; }
    
    public Iterable<Board> solution() {
        // I need to loop through similar operations until I have a 'full goal path'
        MinPQ<SearchNode> pq = new MinPQ<>();
        SearchNode gameTree = new SearchNode(initialBoard, 0);
        Iterable<Board> neighbors = initialBoard.neighbors();
        gameTree.setChildren(neighbors, 1);
        int m = 0;
        ArrayList<Board> goalPath = new ArrayList<>();
        goalPath.add(initialBoard);
        
        SearchNode p = gameTree;
        while(!p.board.isGoal()) {
            // add children
            for(Board b : neighbors) {
                pq.insert(new SearchNode(b, m, p)); // assert parent is not null
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
        testSolver();
    }
}

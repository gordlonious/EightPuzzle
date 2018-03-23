import edu.princeton.cs.algs4.MinPQ;
/**
 *
 * @author Gordon Portzline
 */
public class Solver {
    private Board initialBoard;
    public Solver(Board b) {
        if(b == null) throw new NullPointerException();
        if(!b.isSolvable()) throw new IllegalArgumentException("Inital board cannot be unsolvable");
        initialBoard = b;
    }
    
    public int moves() { throw new UnsupportedOperationException(); }
    
    public Iterable<Board> solution() {
        // I need to loop through similar operations until I have a 'full goal path'
        SearchNode sn = new SearchNode(initialBoard, 0);
        Iterable<Board> initNeighbors = initialBoard.neighbors();
        sn.setChildren(initNeighbors, 1, sn);
        return null;
    }
    
    public static void testSolver() {
        Solver s = new Solver(new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, {7, 8, 6} }));
    }
}

import edu.princeton.cs.algs4.MinPQ;
/**
 *
 * @author Gordon Portzline
 */
public class Solver {
    public Solver(Board b) {
        if(b == null) throw new NullPointerException();
        if(!b.isSolvable()) throw new IllegalArgumentException("Inital board cannot be unsolvable");
    }
    
    public int moves() { throw new UnsupportedOperationException(); }
    
    public Iterable<Board> solution() { throw new UnsupportedOperationException(); }
}

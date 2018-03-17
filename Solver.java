
/**
 *
 * @author Gordon Portzline
 */
public class Solver {
    public Solver(Board b) {
        if(b == null) throw new NullPointerException();
        if(!b.isSolvable()) throw new IllegalArgumentException("Inital board cannot be unsolvable");
    }
}

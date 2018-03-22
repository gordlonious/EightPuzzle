/**
 *
 * @author gordl
 */
public class SearchNode implements Comparable<SearchNode> {
    private Integer priority;
    Board board;
    public SearchNode(Board b, int moves) {
        board = b;
        // Do both manhattan and hamming work here?
        priority = board.manhattan() + moves;
    }
    
    @Override
    public int compareTo(SearchNode sn) {
        return priority.compareTo(sn.priority);
    }
}

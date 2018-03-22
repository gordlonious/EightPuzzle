
import java.util.ArrayList;

/**
 *
 * @author gordl
 */
public class SearchNode implements Comparable<SearchNode> {
    private Integer priority;
    private Board board;
    private SearchNode parent;
    private ArrayList<SearchNode> children;
    private boolean onGoalPath = false;
    
    // Board b is assumed to be the initial-board/root node
    public SearchNode(Board b, int moves) {
        children = new ArrayList<>();
        board = b;
        parent = null;
        priority = board.manhattan() + moves;
    }
    
    // constructor for non-root search nodes
    public SearchNode(Board b, int moves, SearchNode p) {
        children = new ArrayList<>();
        board = b;
        parent = p;
    }
    
    public void setChildren(ArrayList<Board> c, int moves, SearchNode p) {
        for(Board b : c) {
            SearchNode sn = new SearchNode(b, moves, p);
            children.add(sn);
        }
    }
    
    public void flagAsLowestPriorityChild() {
        onGoalPath = true;
    }
    
    @Override
    public int compareTo(SearchNode sn) {
        return priority.compareTo(sn.priority);
    }
}

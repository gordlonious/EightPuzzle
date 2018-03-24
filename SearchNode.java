
import java.util.ArrayList;

/**
 *
 * @author gordl
 */
public class SearchNode implements Comparable<SearchNode> {
    private final Integer priority;
    public Board board;
    private SearchNode parent;
    private final ArrayList<SearchNode> children;
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
        priority = board.manhattan() + moves;
    }
    
    public void setChildren(Iterable<Board> c, int moves) {
        for(Board b : c) {
            SearchNode sn = new SearchNode(b, moves, this);
            children.add(sn);
        }
        parent = this;
    }
    
    public void flagAsLowestPriorityChild() {
        onGoalPath = true;
    }
    
    @Override
    public int compareTo(SearchNode sn) {
        return priority.compareTo(sn.priority);
    }
    
    @Override
    public String toString() {
        return board.toString();
    }
    
    public static void main(String[] args) {
        Board ib = new Board(new int[][] { { 0, 1, 3 }, { 4, 2, 5 }, {7, 8, 6} });
        SearchNode sn = new SearchNode(ib, 0);
        Iterable<Board> initNeighbors = ib.neighbors();
        sn.setChildren(initNeighbors, 1);
        System.out.println("Children are...");
        for(SearchNode s : sn.children) {
            System.out.printf("%s", s.toString());
        }
    }
}

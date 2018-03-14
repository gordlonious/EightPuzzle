
/**
 *
 * @author Gordon Portzline
 */
public class Board {
    private int[][] board;
    private int n;
    public Board(int[][] blocks) {
        this.board = blocks;
        this.n = blocks[0].length;
    }
    public int size() {
        return this.n;
    }
    public int manhattan() {
        int manhattanSum = 0;
        for(int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
                if(board[i][j] != 0) {
                    manhattanSum += getManhattanValue(board[i][j], i, j);
                }
            }
        }
        return manhattanSum;  // still need to add how many moves leading to this board
    }
    
    // method only works for 3 x 3 board
    public int hamming() {
        int h = 0;
        int pad;
        for(int i = 0; i < this.n; i++) {
            switch(i) {
                case 0:
                    pad = 1;
                    break;
                case 1:
                    pad = 4;
                    break;
                case 2:
                    pad = 7;
                    break;
                default:
                    throw new IllegalArgumentException("something went wrong in hamming()");
            }
            for(int j = 0; j < this.n; j++) {
                if(board[i][j] == 0) continue;
                if(board[i][j] != (j+pad)) h++;
            }
        }
        return h; // still need to add how many moves it took to get here..
    }
    
    // val: tile value of the given NPuzzle board
    private int getxgoal(int val) {
        if(val % this.n == 0) return this.n - 1;
        else return (val % this.n) - 1;
    }
    
    // method only works for 3 x 3 board
    private int getygoal(int val) {
        int col;
        if(val == 0) { // blank tile
            col = 2;
        }
        else if(val <= this.n) { // top row
            col = 0;
        }
        else if(val <= this.n*2 && val > this.n) { // second row
            col = 1;
        }
        else if(val <= this.n*3 && val > this.n*2) { // third row
            col = 2;
        }
        else throw new IllegalArgumentException("getygoal() failed");
        
        return col;
    }
    
    private int getxman(int xgoal, int col) {
        return Math.abs(xgoal - col);
    }
    
    private int getyman (int ygoal, int row) {
        return Math.abs(ygoal - row);
    }
    
    private int getManhattanValue(int val, int x, int y) {
        return getxman(getxgoal(val), y) + getyman(getygoal(val), x);
    }
    
    public static void main(String[] args) {
        Board goalBoard = new Board( new int[][] { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} } );
        System.out.printf("size should equal 3, size is actually %d%n", goalBoard.size());
        System.out.printf("hamming should be zero for the goal board, hamming is actually %d%n", goalBoard.hamming());
        System.out.printf("Manhattan should be zero for the goal board, manhattan is actually %d%n", goalBoard.manhattan());
        
        System.out.printf("If value is 4 then xgoal should be 0, actual is %d%n", goalBoard.getxgoal(4));
        System.out.printf("If value is 5 then xgoal should be 1, actual is %d%n", goalBoard.getxgoal(5));
        System.out.printf("If value is 6 then xgoal should be 2, actual is %d%n", goalBoard.getxgoal(6));
        System.out.printf("If value is 1 then xgoal should be 0, actual is %d%n", goalBoard.getxgoal(1));
        System.out.printf("If value is 8 then xgoal should be 1, actual is %d%n", goalBoard.getxgoal(8));
        System.out.printf("If value is 0 then xgoal should be 2, actual is %d%n", goalBoard.getxgoal(0));
        
        System.out.printf("If value is 1 then ygoal should be 0, actual is %d%n", goalBoard.getygoal(1));
        System.out.printf("If value is 2 then ygoal should be 0, actual is %d%n", goalBoard.getygoal(2));
        System.out.printf("If value is 7 then ygoal should be 2, actual is %d%n", goalBoard.getygoal(7));
        System.out.printf("If value is 6 then ygoal should be 1, actual is %d%n", goalBoard.getygoal(6));
        System.out.printf("If value is 0 then ygoal should be 2, actual is %d%n", goalBoard.getygoal(0));
        
        System.out.printf("If tile 8 is in row 3 and column 2 then its xmanhattan should be 0, its actual is %d%n", goalBoard.getxman(goalBoard.getxgoal(8), 1));
        System.out.printf("If tile 8 is in row 1 and column 1 then its xmanhattan should be 1, its actual is %d%n", goalBoard.getxman(goalBoard.getxgoal(8), 0));
        System.out.printf("If tile 3 is in row 1 and column 1 then its xmanhattan should be 2, its actual is %d%n", goalBoard.getxman(goalBoard.getxgoal(3), 0));
        
        System.out.printf("if tile 4 is at (0, 0) then its ymanhattan should be 1, its actual is %d%n", goalBoard.getyman(goalBoard.getygoal(4), 0));
        System.out.printf("if tile 2 is at (1, 2) then its ymanhattan should be 2, its actual is %d%n", goalBoard.getyman(goalBoard.getygoal(2), 1));
        System.out.printf("if tile 1 is at (2, 2) then its ymanhattan should be 2, its actual is %d%n", goalBoard.getyman(goalBoard.getygoal(1), 2));
        
        System.out.printf("If value 8 is at (0, 0) then its manhattan value should be 3, its actual is %d%n", 
                goalBoard.getManhattanValue(8, 0, 0));
        System.out.printf("If value 3 is at (2, 0) then its manhattan value should be 4, its actual is %d%n", 
                goalBoard.getManhattanValue(3, 2, 0));
        
        Board offByOneBoard = new Board(new int[][] { {2, 1, 3}, {4, 5, 6}, {7, 8, 0} } );
        System.out.printf("Hamming for offByOneBoard should be 2, actual is %d%n", offByOneBoard.hamming());
        System.out.printf("Manhattan for offByOneBoard should be 2, actual is %d%n", offByOneBoard.manhattan());
        
        Board exampleBoard = new Board(new int[][] { {8, 1, 3}, {4, 0, 2}, {7, 6, 5} });
        System.out.printf("Hamming for exampleBoard should be 5, actual is %d%n", exampleBoard.hamming());
        System.out.printf("Manhattan for exampleBoard should be 10, actual is %d%n", exampleBoard.manhattan());
    }
}

import edu.princeton.cs.algs4.Inversions;
import java.util.ArrayList;
/**
 *
 * @author Gordon Portzline
 */
public class Board {
    private final int[][] board;
    private final int n;
    public Board(int[][] blocks) {
        this.board = blocks;
        this.n = blocks[0].length;
    }
    public int size() {
        return this.n;
    }
    
    public boolean isGoal() {
// // Can I get better performance by using a series of checks to detect the goal board?
//        for(int i = 0; i < board.length; i++) {
//            for(int j = 0; j < board[i].length; j++) {
//                // check for tile out of position in row
//                // check for blank tile that is not in the last position
//                if ((i != (board.length - 1)) && j != (board[(this.n-1)].length - 1) && board[i][j] == 0) return false;
//            }
//        }
//        return true;
        return (int) Inversions.count(Board.flattenArray(board)) == 0;
    }
    
    public boolean isSolvable() {
        boolean s = false;
        int numOfInversions = (int) Inversions.count(Board.flattenArray(board));
        int rowOfBlank = -1;
        outerLoop:
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    rowOfBlank = i;
                    break outerLoop;
                }
            }
        }
        if(rowOfBlank == -1) throw new InvalidBoardState("the blank tile was never found, isSolvable() has failed");
        if(this.n % 2 != 0 && numOfInversions % 2 != 0) {
            return false;
        } else if (this.n % 2 == 0 && (numOfInversions+rowOfBlank) % 2 == 0) {
            return false;
        } else {
            return true;
        }
    }
    
    public Iterable<Board> neighbors() {
        boolean right, left, up, down;
        int[][] leftcopy = boardCopy();
        int[][] rightcopy = boardCopy();
        int[][] upcopy = boardCopy();
        int[][] downcopy = boardCopy();
        
        Board rightBoard = null;
        Board leftBoard = null;
        Board upBoard = null;
        Board downBoard = null;
        for (int i = 0; i < this.n; i++) {
            for(int j = 0; j < this.n; j++) {
                if(board[i][j] == 0) {
                    // find legal moves
                    if(j < (this.n-1)) {
                        // right = true
                        rightcopy[i][j] = board[i][(j+1)];
                        rightBoard = new Board(leftcopy);
                    }
                    if(j > 0) {
                        // left = true
                        leftcopy[i][j] = board[i][(j-1)];
                        leftBoard = new Board(board);
                    }
                    if(i < (this.n-1)) {
                        // down = true
                        downcopy[i][j] = board[(i+1)][j];
                        downBoard = new Board(board);
                    }
                    if(i > 0) {
                        // up = true
                        upcopy[i][j] = board[(i-1)][j];
                        upBoard = new Board(board);
                    }
                }
            }
        }
//        int neighborCount = 0;
//        if(rightBoard != null) neighborCount++;
//        if(leftBoard != null) neighborCount++;
//        if(upBoard != null) neighborCount++;
//        if(downBoard != null) neighborCount++;
        ArrayList<Board> a = new ArrayList();
        if(rightBoard != null) a.add(rightBoard);
        if(leftBoard != null) a.add(leftBoard);
        if(upBoard != null) a.add(upBoard);
        if(downBoard != null) a.add(downBoard);
        return a;
    }
    
    @Override
    public String toString() {
        String s = "";
        for(int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.n; j++) {
//                if(j == 0)  System.out.printf("%n%d ", board[i][j]);
//                else System.out.printf("%d ", board[i][j]);
                  String sf = String.format("\n%d ", board[i][j]);
                  if(j == 0) s = s.concat(String.format("\n%d ", board[i][j]));
                  else s = s.concat(String.format("%d ", board[i][j]));
            }
        }
        return s;
    }
    
    private int[][] boardCopy() {
        int[][] boardcopy = new int[this.n][this.n];
        for(int i = 0; i < this.n; i++) {
            for(int j = 0; j < this.n; j++) {
                boardcopy[i][j] = board[i][j];
            }
        }
        return boardcopy;
    }
    
//    private static tileSwap(int[][] board, int row, int col, int row2, int col2) {
//        int temp = board[row][col];
//        board[]
//    }
    
    // returns a 1d array given a 2d array and removes the blank tile (0)
    private static int[] flattenArray(int[][] a) {
        int flatLength = -1;
        for(int i = 0; i < a.length; i++) {
            flatLength += a[i].length;
        }
        int[] flatArray = new int[flatLength];
        
        int flatIndex = 0;
        for(int i = 0; i < a.length; i++) { // copy
            for (int j = 0; j < a[i].length; j++) {
                if(a[i][j] != 0)  flatArray[flatIndex] = a[i][j];
                if(a[i][j] != 0) flatIndex++;
            }
        }
        return flatArray;
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
        else throw new IllegalArgumentException("getygoal() failed, this method isn't finished and you should probably fix it");
        
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
    
      private class InvalidBoardState extends Error {
      public InvalidBoardState(String message){
         super(message);
      }
    }
    
//    private boolean isNeighborTest() {
//        boolean isNeightbor = false;
//        for (int i = 0; i < this.n; i++) {
//            for (int j = 0; j < this.n; j++) {
//                if(board[i][j] == board[(j+1)])
//            }
//        }
//    }
    
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
        
        int[][] t1a = new int[][] { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} };
        int[] f1a = Board.flattenArray(t1a);
        for(int f : f1a ) System.out.printf("%d, ", f);
        
        final Board unsolvable1 = new Board(new int[][] { {1, 2, 3}, {4, 5, 6}, {8, 7, 0} });
        System.out.printf("%nthe board should not be solvable, isSolvable actually is: %b%n", unsolvable1.isSolvable());
        
        System.out.printf("An unsolvable board is not a goal board, isGoal returns %b%n", unsolvable1.isGoal());
        System.out.printf("A goal board is a goal board, isGoal returns %b%n", goalBoard.isGoal());
        
        System.out.printf("goal board looks like..%n");
        System.out.println(goalBoard.toString());
        System.out.printf("insolvable1 looks like.. %s", unsolvable1.toString());
    }
}

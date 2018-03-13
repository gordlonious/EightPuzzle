
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
                    pad = -100;
                    System.out.println("Something went wrong with hamming()");
            }
            for(int j = 0; j < this.n; j++) {
                if(j == (this.n - 1) && i == (this.n - 1)) break;
                if(board[i][j] == 0) continue;
                if(board[i][j] != (j+pad)) h++;
            }
        }
        return h;
    }
    
    public static void main(String[] args) {
        Board goalBoard = new Board( new int[][] { {1, 2, 3}, {4, 5, 6}, {7, 8, 0} } );
        System.out.printf("size should equal 3, size is actually %d%n", goalBoard.size());
        System.out.printf("hamming should be zero for the goal board, hamming is actually %d for the goal board%n", goalBoard.hamming());
    }
}

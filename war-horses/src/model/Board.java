package model;
/**
 *
 * @author Fabian Valencia
 */
public class Board {
    // 0: empty, 1:red horse, 2: green horse, 3: bonus.
    private int [][] board;
    private int redHorse;
    private int greenHorse;
    private int bonus;
    
    public Board() {
        board = new int[8][8];
        createBoard();
    }
    
    private void createBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = (int) (Math.random() * 4);
            }
        }      
    }
    
    private int getRandomsValues(){
        int num = 0;
           
        
        return num;
    }


    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }
    
     
}

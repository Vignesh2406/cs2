import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Game {
    int[][] board;
    char[] moves;

    public Game(int i, String str) {
        board = new int[i][i];
        readMoves(str);
    }

    public void readMoves(String source){
        File file = new File(source);
        try {
            Scanner scan = new Scanner(file);
            moves = new char[(int)file.length()];
            int index = 0;
            while(scan.hasNext()){
                moves[index] = (char) scan.nextByte();
                index++;
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int play(int player){
        int turn = 1;
        while(board[board.length-1][board[0].length-1] != -2){
            if b move down 1. board[row+1][columns]
                if r move right 1. board[row][columns+1]
                if d move right and down 1. board[row+1][columns+1]
        }
        return turn;
    }

    public static void main(String[] args) {
        Game game = new Game(8, "player2Moves.txt");
    }
}

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

        int currPlayer = 1;
        int currPosRow = 0;
        int currPosCol = 0;
        int currIndex2 = 0;
        while(board[board.length-1][board[0].length-1] != -2){
            if(currPlayer == 1){ //Player 1's turn
                if(player == 1){ //Play to win
                    if(currPosRow%2 == 0){
                        if(currPosCol > currPosRow){
                            //Move diagonally
                            currPosCol++;
                            currPosRow++;
                        }else if(currPosRow < board.length){
                            currPosRow++;
                        }else{
                            currPosCol++;
                        }
                    }else if(currPosCol%2 == 0){
                        if(currPosRow > currPosCol){
                            //Move diagonally
                            currPosCol++;
                            currPosRow++;
                        }else if(currPosRow < board.length){
                            currPosRow++;
                        }else{
                            currPosCol++;
                        }
                    }else{
                        if(currPosRow < board.length){
                            currPosRow++;
                        }else{
                            currPosCol++;
                        }
                    }
                }else{ //Play to lose
                    if(currPosRow%2 == 0){

                    }else if(currPosCol%2 == 0){

                    }
                }
                currPlayer = 2;
            }else{ //Player two's turn
                if(player == 2){ //Play to win

                }else{ //Play to lose

                }
                currPlayer = 1;
                currIndex2 ++;
            }
            board[currPosRow][currPosCol] = -2;
        }
        return currPlayer;
    }

    public static void main(String[] args) {
        Game game = new Game(8, "player2Moves.txt");
    }
}

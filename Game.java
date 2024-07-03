import java.io.File;
import java.util.Scanner;

public class Game {

    int[][] board;
    char[] moves;

    Game(int size, String file_name) {
        for (int i = 0; i < size; i++) {
            for (int f = 0; f < size; f++) {
                board[i][f] = 0;
            }
        }

        board[0][0] = 1;

        readMoves(file_name);
        
    }

    public void readMoves(String file_name) {
        File file = new File(file_name);
        Scanner s = new Scanner(file);
        
        String input = "";
        while(s.hasNextLine()) {
            String data = s.nextLine();
            input = input + "\n" + data;
        }

        moves = input.toCharArray();
        s.close();
    }

    public int play(int winner) {
        // if winner = 1 then player 1 wins
        // if winner = 2 then player 2 wins

        // player 1 always goes first
        // we know what player 2 is doing

        int player = 1;
        int move_num2 = 0;

        while (true) {
            char move = 'r';
            
            if (player == 1) {
                move = 'r';
            } else if (player == 2) {
                move = moves[move_num2];
                move_num2++;
            }
            

            // start with finding current location
            int x = 0;
            int y = 0;

            for (int i = 0; i < board.length; i++) {
                for (int f = 0; f < board[0].length; f++) {
                    if (board[i][f] == 1) {
                        x = i;
                        y = f;
                    }
                }
            }

            if (move == 'r') {
                board[x+1][y] = 1;
                board[x][y] = 0;
                x++;
            }
            else if (move == 'b') {
                board[x][y+1] = 1;
                board[x][y] = 0;
                y++;
            }
            else if (move == 'd') {
                board[x+1][y+1] = 1;
                board[x][y] = 0;
                x++;
                y++;
            }

            if (x == board.length - 1 && y == board.length - 1) {
                return -1; // somebody won
            }

        }
    }

}

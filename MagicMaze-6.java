import java.io.*;
import java.util.*;

public class MagicMaze {

    private char[][] maze;
    private String mazeNumber;
    private int numRows;
    private int numColumns;

    public MagicMaze(String mazeNumber, int numRows, int numColumns) {
        this.mazeNumber = mazeNumber;
        this.numRows = numRows;
        this.numColumns = numColumns;
        maze = new char[numRows][numColumns];
    }

    public void readMazeFromFile() {
        try {
            Scanner scanner = new Scanner(new File(mazeNumber + ".txt"));
            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                for (int col = 0; col < numColumns; col++) {
                    maze[row][col] = line.charAt(col);
                }
                row++;
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean solveMagicMaze() {
        readMazeFromFile();
        return solveMagicMaze(0, numColumns-1);
    }

    private boolean solveMagicMaze(int row, int col) {
        // check if we reached the exit
        if (maze[row][col] == 'X') {
            return true;
        }
        // check if we hit an obstacle or visited cell
        if (maze[row][col] != '*') {
            return false;
        }
        // mark current cell as visited
        maze[row][col] = 'V';
        // check if there's a teleportation pad
        if (Character.isDigit(maze[row][col])) {
            int teleportNum = maze[row][col] - '0';
            // look for other teleportation pad with the same number
            for (int r = 0; r < numRows; r++) {
                for (int c = 0; c < numColumns; c++) {
                    if (maze[r][c] == maze[row][col] && (r != row || c != col)) {
                        // teleport to the other pad
                        if (solveMagicMaze(r, c)) {
                            return true;
                        }
                    }
                }
            }
        }
        // try moving in all directions
        if (row > 0 && solveMagicMaze(row - 1, col)) {
            return true;
        }
        if (col > 0 && solveMagicMaze(row, col - 1)) {
            return true;
        }
        if (row < numRows - 1 && solveMagicMaze(row + 1, col)) {
            return true;
        }
        if (col < numColumns - 1 && solveMagicMaze(row, col + 1)) {
            return true;
        }
        // mark current cell as unvisited (backtracking)
        maze[row][col] = '*';
        return false;
    }
}

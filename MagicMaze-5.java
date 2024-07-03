import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MagicMaze {

    private char[][] maze;
    private String mazeNumber;
    private int rows;
    private int columns;

    public MagicMaze(String mazeNumber, int rows, int columns) {
        this.mazeNumber = mazeNumber;
        this.rows = rows;
        this.columns = columns;
        this.maze = new char[rows][columns];
    }

    public void readMazeFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(mazeNumber + ".txt"));
            String line = null;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                for (int column = 0; column < line.length(); column++) {
                    maze[row][column] = line.charAt(column);
                }
                row++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean solveMagicMaze() {
        readMazeFile(); // Read the maze file
        int startRow = rows - 1;
        int startColumn = 0;
        return solveMagicMaze(startRow, startColumn);
    }

    private boolean solveMagicMaze(int row, int column) {
        // Check if we reached the exit
        if (maze[row][column] == 'X') {
            return true;
        }
        // Check if the current cell is invalid or has already been visited
        if (maze[row][column] == '@' || maze[row][column] == '.') {
            return false;
        }
        // Mark the current cell as visited
        maze[row][column] = '.';
        // Try moving in all four directions
        if (row > 0 && solveMagicMaze(row - 1, column)) { // Move up
            return true;
        }
        if (column < columns - 1 && solveMagicMaze(row, column + 1)) { // Move right
            return true;
        }
        if (row < rows - 1 && solveMagicMaze(row + 1, column)) { // Move down
            return true;
        }
        if (column > 0 && solveMagicMaze(row, column - 1)) { // Move left
            return true;
        }
        // If none of the directions led to the exit, mark the current cell as unvisited and backtrack
        maze[row][column] = '*';
        return false;
    }
}
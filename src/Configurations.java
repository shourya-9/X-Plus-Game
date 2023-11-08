class Configurations
{
    // Instance variables
    private char[][] board; // Game board
    private int board_size; // Size of game board
    private int lengthToWin; // Number of tiles required to win
    private int max_levels; // Depth

    // Constructor
    public Configurations(int board_size, int lengthToWin, int max_levels)
    {
        this.board_size=board_size;
        this.lengthToWin=lengthToWin;
        this.max_levels=max_levels;
        board = new char[board_size][board_size];
        // Initializing board with empty spaces
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                board[i][j] = ' ';
            }
        }
    }

    // Method to create a hash dictionary
    public HashDictionary createDictionary()
    {
        int hashTableSize = 9967; // Size of hash table
        return new HashDictionary(hashTableSize);
    }

    // Method to check whether the String representing the board is in hashTable
    public int repeatedConfiguration(HashDictionary hashTable){
        String st = boardtoString();
        return hashTable.get(st);
    }

    // Method to insert this String and score in the hashTable
    public void addConfiguration(HashDictionary hashDictionary, int score){
        hashDictionary.put(new Data(boardtoString(),score));
    }

    // Method to store symbol in board[row][col]
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    // Method to check if square is empty or not
    public boolean squareIsEmpty(int row, int col) {
        return board[row][col] == ' ';
    }

    // Method to check for X or + shape with k length
    public boolean wins(char symbol) {
        // Check for +
        for (int i = 1; i < board_size - 1; i++) {
            for (int j = 1; j < board_size - 1; j++) { // Iterating through all board squares
                if (board[i][j] == symbol) {
                    if (checkPlus(i, j, symbol)) { // Checking if initial plus is formed
                        if(checkPlusOther(i,j,symbol)) // Checking if other directions have same symbol
                            return true;
                    }
                }
            }
        }
        // Check for X
        for (int i = 1; i < board_size - 1; i++) {
            for (int j = 1; j < board_size - 1; j++) { // Iterating through all board squares
                if (board[i][j] == symbol) {
                    if (checkX(i, j, symbol)) { // Checking if initial X is formed
                        if(checkXOther(i,j,symbol)) // Checking if other diagonals have same symbol
                            return true;
                    }
                }
            }
        }
        return false;  // No X or + pattern found
    }

    // Helper method to check for basic X shape
    private boolean checkX(int i, int j, char symbol){
        // variables to store corner values for X
        char ltc = board[i-1][j-1];
        char rtc = board[i-1][j+1];
        char lbc = board[i+1][j-1];
        char rbc = board[i+1][j+1];
        // Checking if the symbol is the same as the centre symbol
        return (ltc == symbol && rtc == symbol && lbc == symbol && rbc ==symbol);
    }

    // Helper method to check for same symbol on the diagonals of initial X till length k
    private boolean checkXOther(int i, int j, char symbol){
        // Top left diagonal
        int k=i-2;
        int l=j-2;
        int count = 5;
        while(0<=k && k<board_size && 0<=l && l<board_size){
            if (board[k--][l--] == symbol)
                count++;
            else
                break;
        }
        // Top right diagonal
        k=i-2;
        l=j+2;
        while(0<=k && k<board_size && 0<=l && l<board_size)
        {
            if (board[k--][l++] == symbol)
                count++;
            else
                break;
        }
        // Bottom left diagonal
        k=i+2;
        l=j-2;
        while(0<=k && k<board_size && 0<=l && l<board_size){
            if(board[k++][l--]==symbol)
                count++;
            else
                break;
        }
        // Bottom right diagonal
        k=i+2;
        l=j+2;
        while(0<=k && k<board_size && 0<=l && l<board_size){
            if(board[k++][l++]==symbol)
                count++;
            else
                break;
        }
        // Checking if counter has reached the value of k
        if(count >= lengthToWin)
            return true;
        else
            return false;
    }

    // Helper method to check for basic + shape
    private boolean checkPlus(int i, int j, char symbol) {
        // Variables to store symbols in 4 directions for + shape
        char up = board[i - 1][j];
        char down = board[i + 1][j];
        char left = board[i][j - 1];
        char right = board[i][j + 1];
        // Checking if the symbol is the same as the centre symbol
        return (up == symbol && down == symbol && left == symbol && right == symbol);
    }

    // Helper method to check for same symbol on all four directions till length k
    private boolean checkPlusOther(int i, int j, char symbol){
        // Vertical upwards
        int k=i-2;
        int count = 5;
        while(k>=0){
            if (board[k--][j] == symbol)
                count++;
            else
                break;
        }
        // Vertical downwards
        k=i+2;
        while(k<board_size)
        {
            if (board[k++][j] == symbol)
                count++;
            else
                break;
        }
        // Horizontal leftwards
        k=j-2;
        while(k>=0){
            if(board[i][k--]==symbol)
                count++;
            else
                break;
        }
        // Horizontal rightwards
        k=j+2;
        while(k<board_size){
            if(board[i][k++]==symbol)
                count++;
            else
                break;
        }
        // Checking if counter has reached the value of k
        if(count >= lengthToWin)
            return true;
        else
            return false;
    }

    // Method to check if no empty positions are left and no player has won the game
    public boolean isDraw() {
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                if (board[i][j] == ' ') {
                    return false; // There's an empty position, game is not a draw
                }
            }
        }
        return true; // All positions are filled, game is a draw
    }

    // Return a score based on the given rules
    public int evalBoard() {
        if (wins('O')) {
            return 3; // Computer wins
        } else if (wins('X')) {
            return 0; // Human wins
        } else if (isDraw()) {
            return 2; // Draw
        } else {
            return 1; // Undecided
        }
    }

    // Helper method to convert game board configuration to a string
    private String boardtoString(){
        String str = "";
        for (int i = 0; i < board_size; i++) {
            for (int j = 0; j < board_size; j++) {
                str += (board[i][j]);
            }
        }
        return str;
    }
}
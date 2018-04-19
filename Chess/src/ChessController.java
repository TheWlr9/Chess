//package logic;

public interface ChessController {
	final static int MAX_DIFFICULTY= 3;
	
	final public static char DEBUG= 'd';
	
	final public static int ROWS= 8;
	final public static int COLS= 8;
	
	final public static byte WHITE= 90; //End of whites ('Z')
	final public static char WHITE_PAWN= 'P';
	final public static char WHITE_ROOK= 'R';
	final public static char WHITE_KNIGHT= 'N';
	final public static char WHITE_BISHOP= 'B';
	final public static char WHITE_QUEEN= 'Q';
	final public static char WHITE_KING= 'K';
	
	final public static char UNOCCUPIED= ' ';
	
	final public static byte BLACK= 97; //Start of blacks ('a')
	final public static char BLACK_PAWN= 'p';
	final public static char BLACK_ROOK= 'r';
	final public static char BLACK_KNIGHT= 'n';
	final public static char BLACK_BISHOP= 'b';
	final public static char BLACK_QUEEN= 'q';
	final public static char BLACK_KING= 'k';
	
	public boolean movePiece(int prevRow, int prevCol, int curRow, int curCol);
	
	public void secretMovePiece(int prevRow, int prevCol, int curRow, int curCol, ChessPlayer ai);
	
	public char[][] getBoard();
	
	public int getDifficulty();
	
	public void reset();
	
	public void debugReset();
	
	public boolean continueGame();
}

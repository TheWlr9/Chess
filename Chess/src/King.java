
public class King extends GamePiece {
	final static private int VALUE= 100;

	public King(int row, int col, boolean isWhite) {
		super(row, col, isWhite, VALUE);
	}

	public boolean validateMove(int newRow, int newCol, char[][] board) {
		if(newRow>=0 && newRow<ChessController.ROWS && newCol>=0 && newCol<ChessController.COLS) {
			if(Math.abs(newRow-getRow())<=1 && Math.abs(newCol-getCol())<=1 && (newRow!=getRow() || newCol!=getCol())) {
				if(board[newRow][newCol]==ChessController.UNOCCUPIED)
					return true;
				else if(getIsWhite()) {
					if(board[newRow][newCol]>=ChessController.BLACK)
						return true;
					else
						return false;
				}
				else {
					if(board[newRow][newCol]<=ChessController.WHITE) 
						return true;
					else
						return false;
				}
			}
			else
				return false;
		}
		else 
			return false;
	}

}

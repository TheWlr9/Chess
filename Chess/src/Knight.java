
public class Knight extends GamePiece {
	final static private int VALUE= 5;

	public Knight(int row, int col, boolean isWhite) {
		super(row, col, isWhite, VALUE);
	}

	public boolean validateMove(int newRow, int newCol, char[][] board) {
		if(newRow>=0 && newRow<ChessController.ROWS && newCol>=0 && newCol<ChessController.COLS) {
			if(Math.abs(newRow-getRow())>0 && Math.abs(newRow-getRow())<=2 && Math.abs(newCol-getCol())>0 && Math.abs(newCol-getCol())<=2 && Math.abs(newRow-getRow())!=Math.abs(newCol-getCol())) {
				
				
				
				/*
				 * Do some checking to inplement REAL chess rules...
				 * Sorry, I couldn't help myself...
				 * You can just comment this part out if you want it the way you
				 * guys want it, where the knights can travel
				 * through ANY pieces...
				 *
				if(getIsWhite()) {
					if(newRow<getRow() && newCol<getCol()) {
						if(board[getRow()-1][getCol()]>=ChessController.BLACK && board[getRow()][getCol()-1]>=ChessController.BLACK)
							return false;
					}
					else if(newRow<getRow() && newCol>getCol()) {
						if(board[getRow()-1][getCol()]>=ChessController.BLACK && board[getRow()][getCol()+1]>=ChessController.BLACK)
							return false;
					}
					else if(newRow>getRow() && newCol<getCol()) {
						if(board[getRow()+1][getCol()]>=ChessController.BLACK && board[getRow()][getCol()-1]>=ChessController.BLACK)
							return false;
					}
					else if(newRow>getRow() && newCol>getCol()) {
						if(board[getRow()+1][getCol()]>=ChessController.BLACK && board[getRow()][getCol()+1]>=ChessController.BLACK)
							return false;
					}
				}
				else {
					if(newRow<getRow() && newCol<getCol()) {
						if(board[getRow()-1][getCol()]<=ChessController.WHITE && board[getRow()-1][getCol()]>ChessController.UNOCCUPIED && 
								board[getRow()][getCol()-1]<=ChessController.WHITE && board[getRow()][getCol()-1]>ChessController.UNOCCUPIED)
							return false;
					}
					else if(newRow<getRow() && newCol>getCol()) {
						if(board[getRow()-1][getCol()]<=ChessController.WHITE && board[getRow()-1][getCol()]>ChessController.UNOCCUPIED && 
								board[getRow()][getCol()+1]<=ChessController.WHITE && board[getRow()][getCol()+1]>ChessController.UNOCCUPIED)
							return false;
					}
					else if(newRow>getRow() && newCol<getCol()) {
						if(board[getRow()+1][getCol()]<=ChessController.WHITE && board[getRow()+1][getCol()]>ChessController.UNOCCUPIED && 
								board[getRow()][getCol()-1]<=ChessController.WHITE && board[getRow()][getCol()-1]>ChessController.UNOCCUPIED)
							return false;
					}
					else if(newRow>getRow() && newCol>getCol()) {
						if(board[getRow()+1][getCol()]<=ChessController.WHITE && board[getRow()+1][getCol()]>ChessController.UNOCCUPIED && 
								board[getRow()][getCol()+1]<=ChessController.WHITE && board[getRow()][getCol()+1]>ChessController.UNOCCUPIED)
							return false;
					}
				}*/
				
				
				
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

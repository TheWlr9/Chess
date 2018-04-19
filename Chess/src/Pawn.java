
public class Pawn extends GamePiece {
	final static private int VALUE= 1;
	
	public Pawn(int row, int col, boolean white) {
		super(row,col, white, VALUE);
	}

	public boolean validateMove(int newRow, int newCol, char[][] board) {
		if(getIsWhite()) {
			if(getRow()==ChessController.ROWS-2) { //For double move at beginning of pawn
				if(getRow()-newRow==2 && getCol()-newCol==0 && board[getRow()-1][getCol()]==ChessController.UNOCCUPIED &&
						board[getRow()-2][getCol()]==ChessController.UNOCCUPIED)
					return true;
			}
			if(getRow()-newRow==1) {
				if(getCol()-newCol==0) {
					if(board[newRow][newCol]==ChessController.UNOCCUPIED)
						return true;
					return false; //else
				}
				else if(Math.abs(getCol()-newCol)==1) {
					if(board[newRow][newCol]>ChessController.BLACK)
						return true;
					return false; //else
				}
				return false; //else
			}
			return false; //else
		}
		else {
			if(getRow()==1) { //For double move at beginning of pawn
				if(getRow()-newRow==-2 && getCol()-newCol==0 && board[getRow()+1][getCol()]==ChessController.UNOCCUPIED &&
						board[getRow()+2][getCol()]==ChessController.UNOCCUPIED)
					return true;
			}
			if(getRow()-newRow==-1) {
				if(getCol()-newCol==0) {
					if(board[newRow][newCol]==ChessController.UNOCCUPIED)
						return true;
					return false; //else
				}
				else if(Math.abs(getCol()-newCol)==1) {
					if(board[newRow][newCol]<=ChessController.WHITE && 
							board[newRow][newCol]>ChessController.UNOCCUPIED)
						return true;
					return false; //else
				}
				return false; //else
			}
			return false; //else
		}
	}

}

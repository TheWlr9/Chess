
public class Rook extends GamePiece {
	final static private int VALUE= 4;

	public Rook(int row, int col, boolean isWhite) {
		super(row, col, isWhite, VALUE);
	}

	public boolean validateMove(int newRow, int newCol, char[][] board) {
		int selectedRow= getRow();
		int selectedCol= getCol();
		
		if(getIsWhite()) {
			if(newRow<selectedRow && newCol==selectedCol) {
				//Up
				
				do
					selectedRow--;
				while(selectedRow>=0 && board[selectedRow][selectedCol]==ChessController.UNOCCUPIED && selectedRow!=newRow);
				
				if(selectedRow==newRow && selectedCol==newCol) {
					if(board[selectedRow][selectedCol]== ChessController.UNOCCUPIED || board[selectedRow][selectedCol]>=ChessController.BLACK)
						return true;
					else
						return false;
				}
				else
					return false;
			}
			else if(newRow==selectedRow && newCol<selectedCol) {
				// Left
				
				do
					selectedCol--;
				while(selectedCol>=0 && board[selectedRow][selectedCol]==ChessController.UNOCCUPIED && selectedCol!=newCol);
				
				if(selectedRow==newRow && selectedCol==newCol) {
					if(board[selectedRow][selectedCol]== ChessController.UNOCCUPIED || board[selectedRow][selectedCol]>=ChessController.BLACK)
						return true;
					else
						return false;
				}
				else
					return false;
			}
			else if(newRow==selectedRow && newCol>selectedCol) {
				//Right
				
				do
					selectedCol++;
				while(selectedCol<ChessController.COLS && board[selectedRow][selectedCol]==ChessController.UNOCCUPIED && selectedCol!=newCol);
				
				if(selectedRow==newRow && selectedCol==newCol) {
					if(board[selectedRow][selectedCol]== ChessController.UNOCCUPIED || board[selectedRow][selectedCol]>=ChessController.BLACK)
						return true;
					else
						return false;
				}
				else
					return false;
			}
			else if(newRow>selectedRow && newCol==selectedCol) {
				//Down
				
				do
					selectedRow++;
				while(selectedRow<ChessController.ROWS && board[selectedRow][selectedCol]==ChessController.UNOCCUPIED && selectedRow!=newRow);
				
				if(selectedRow==newRow && selectedCol==newCol) {
					if(board[selectedRow][selectedCol]== ChessController.UNOCCUPIED || board[selectedRow][selectedCol]>=ChessController.BLACK)
						return true;
					else
						return false;
				}
				else
					return false;
			}
		}
		else {//Is black
			/*
			 *We can omit the check to see if the space chosen is unoccupied because in ASCII, ' '<=WHITE
			 *and the rook's attack method and movement method are identical, 
			 *so we can combine the two checks into just one check to see whether the selected point is 
			 *less than WHITE.
			 */
			if(newRow<selectedRow && newCol==selectedCol) {
				//Up
				
				do
					selectedRow--;
				while(selectedRow>=0 && board[selectedRow][selectedCol]==ChessController.UNOCCUPIED && selectedRow!=newRow);
				
				if(selectedRow==newRow && selectedCol==newCol) {
					if(board[selectedRow][selectedCol]<=ChessController.WHITE)
						return true;
					else
						return false;
				}
				else
					return false;
			}
			else if(newRow==selectedRow && newCol<selectedCol) {
				// Left
				
				do
					selectedCol--;
				while(selectedCol>=0 && board[selectedRow][selectedCol]==ChessController.UNOCCUPIED && selectedCol!=newCol);
				
				if(selectedRow==newRow && selectedCol==newCol) {
					if(board[selectedRow][selectedCol]<=ChessController.WHITE)
						return true;
					else
						return false;
				}
				else
					return false;
			}
			else if(newRow==selectedRow && newCol>selectedCol) {
				//Right
				
				do
					selectedCol++;
				while(selectedCol<ChessController.COLS && board[selectedRow][selectedCol]==ChessController.UNOCCUPIED && selectedCol!=newCol);
				
				if(selectedRow==newRow && selectedCol==newCol) {
					if(board[selectedRow][selectedCol]<=ChessController.WHITE)
						return true;
					else
						return false;
				}
				else
					return false;
			}
			else if(newRow>selectedRow && newCol==selectedCol) {
				//Down
				
				do
					selectedRow++;
				while(selectedRow<ChessController.ROWS && board[selectedRow][selectedCol]==ChessController.UNOCCUPIED && selectedRow!=newRow);
				
				if(selectedRow==newRow && selectedCol==newCol) {
					if(board[selectedRow][selectedCol]<=ChessController.WHITE)
						return true;
					else
						return false;
				}
				else
					return false;
			}
		}
		
		return false; //newRow==selectedRow && newCol==selectedCol
	}

}

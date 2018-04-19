
public abstract class GamePiece {
	private int row, col;
	private boolean isWhite;
	private int value;
	
	GamePiece(int row, int col, boolean isWhite){
		this.row= row;
		this.col= col;
		this.isWhite= isWhite;
	}
	public GamePiece(int row, int col, boolean isWhite, int value) {
		this(row,col,isWhite);
		this.value= value;
	}
	
	//Accessor methods
	final public int getRow() {
		return row;
	}
	final public int getCol() {
		return col;
	}
	final public boolean getIsWhite() {
		return isWhite;
	}
	final public int getValue() {
		return value;
	}

	public abstract boolean validateMove(int newRow, int newCol, char[][] board);
	
	/**
	 * 
	 * @param token
	 * @param board
	 * @return the MoveData if the piece is in danger, or null otherwise.
	 */
	public static MoveData endangered(GamePiece token, char[][] board) {
		GamePiece attacker;
		if(token.isWhite) {
			for(int r= 0; r< board.length; r++) {
				for(int c= 0; c< board[r].length; c++) {
					if(board[r][c]>ChessController.BLACK) { //Checks to see if you found an enemy piece
						attacker= getPiece(r,c,board);
						
						if(attacker.validateMove(token.row, token.col, board))
							return new MoveData(attacker.row, attacker.col, token.row, token.col, token.value);
					}
				}
			}
			return null;
		}
		else {
			for(int r= 0; r< board.length; r++) {
				for(int c= 0; c< board[r].length; c++) {
					if(board[r][c]>ChessController.UNOCCUPIED && board[r][c]<=ChessController.WHITE) { //Checks to see if you found an enemy piece
						attacker= getPiece(r,c,board);
						
						if(attacker.validateMove(token.row, token.col, board))
							return new MoveData(attacker.row, attacker.col, token.row, token.col, token.value);
					}
				}
			}
			return null;
		}
	}
	
	private static GamePiece getPiece(int row, int col, char[][] theBoard) {
		if(theBoard[row][col]==ChessController.UNOCCUPIED)
			return null;
		else if(theBoard[row][col]==ChessController.WHITE_PAWN)
			return new Pawn(row,col,true);
		else if(theBoard[row][col]==ChessController.BLACK_PAWN)
			return new Pawn(row,col,false);
		else if(theBoard[row][col]==ChessController.WHITE_QUEEN)
			return new Queen(row,col,true);
		else if(theBoard[row][col]==ChessController.BLACK_QUEEN)
			return new Queen(row,col,false);
		else if(theBoard[row][col]==ChessController.WHITE_KING)
			return new King(row,col,true);
		else if(theBoard[row][col]==ChessController.BLACK_KING)
			return new King(row,col,false);
		else if(theBoard[row][col]==ChessController.WHITE_BISHOP)
			return new Bishop(row,col,true);
		else if(theBoard[row][col]==ChessController.BLACK_BISHOP)
			return new Bishop(row,col,false);
		else if(theBoard[row][col]==ChessController.WHITE_KNIGHT)
			return new Knight(row,col,true);
		else if(theBoard[row][col]==ChessController.BLACK_KNIGHT)
			return new Knight(row,col,false);
		else if(theBoard[row][col]==ChessController.WHITE_ROOK)
			return new Rook(row,col,true);
		else if(theBoard[row][col]==ChessController.BLACK_ROOK)
			return new Rook(row,col,false);
		else {
			System.err.println("Error in accessing character in point: ("+row+","+col+")");
			return null;
		}
	}

}

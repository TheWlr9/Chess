//package graphics;
import java.io.*;

class ChessTextGraphics implements GameDisplay{
	private char[][] savedBoard;
	private BufferedReader bufInput;
	
	public ChessTextGraphics() {
		savedBoard= new char[ChessController.ROWS][ChessController.COLS];
		bufInput= new BufferedReader(new InputStreamReader(System.in));
	}
	
	private static String nameOf(char character) {
		if(character== 'r' || character== 'R')
			return "Rook";
		if(character== 'n' || character== 'N')
			return "Knight";
		if(character== 'b' || character== 'B')
			return "Bishop";
		if(character== 'k' || character== 'K')
			return "King";
		if(character== 'q' || character== 'Q')
			return "Queen";
		if(character== 'p' || character== 'P')
			return "Pawn";
		return "INVALID CHESS PIECE";
	}

	public int promptOpponentForDifficulty(int maxDifficulty) {
		System.out.println("Enter an integer between 1 and "+maxDifficulty+" to set difficulty.");
		
		try {
			int difficultySelected= Integer.parseInt(bufInput.readLine());
			
			if(difficultySelected>=1 && difficultySelected<=maxDifficulty)
				return difficultySelected;
			else {
				System.out.println("Invalid difficulty, try again.");
				
				return promptOpponentForDifficulty(maxDifficulty); //Recursively try again until a valid difficulty is received
			}
		} catch (Exception ioe) {
			System.err.println("Error in reading user input");
			
			ioe.printStackTrace();
			
			return 0; //Invalid return
		}
	}

	public void displayBoard(char[][] info) {
		System.out.print(" ");
		for(int row= 1; row<=ChessController.ROWS; row++) {
			System.out.print(" "+row);
		}
		System.out.println(); //New line
		System.out.print(" ");
		for(int sep= 1; sep<=ChessController.ROWS*2+2-1; sep++) {
			System.out.print("-");
		}
		System.out.println(); //New line
		
		for(int row= 1; row<=ChessController.ROWS*2; row++) {
			if(row%2!=0) {
				for(int col= 0; col<ChessController.COLS*2+2; col++) {
					if(col==0)
						System.out.print(row/2+1); //CHECK
					else if(col%2!=0)
						System.out.print("|");
					else
						System.out.print(info[row/2][(col-2)/2]);
				}
			}
			else {
				System.out.print(" ");
				for(int col= 1; col<ChessController.COLS*2+2; col++)
					System.out.print("-");
			}
			System.out.println(); //New line
		}
		System.out.println(); //New line
	}

	public void summarizeMove(char[][] latestBoard) {
		char saved1 = 0, saved2= 0, latest1= 0, latest2= 0;
		int sr1 = 0, sc1 = 0, sr2 = 0, sc2 = 0;
		
		for(int r= 0; r<ChessController.ROWS; r++)
			for(int c= 0; c<ChessController.COLS; c++) {
				if(latestBoard[r][c]!=savedBoard[r][c]) {
					if(latest1!=0) {
						saved2= savedBoard[r][c];
						sr2= r;
						sc2= c;
						
						latest2= latestBoard[r][c];
					}
					else {
						saved1= savedBoard[r][c];
						sr1= r;
						sc1= c;
						
						latest1= latestBoard[r][c];
					}
				}
			}
		
		if(latest1==ChessController.UNOCCUPIED && saved2==ChessController.UNOCCUPIED) {
			//Top moved down to unoccupied (late1= unoccupied && save2== unoccupied)
			System.out.println(nameOf(saved1)+" moved down from ("+(sr1+1)+","+(sc1+1)+") to ("+(sr2+1)+","+(sc2+1)+"). No capture.");
		}
		else if(latest1==ChessController.UNOCCUPIED && saved2!=ChessController.UNOCCUPIED) {
			//Top took down (late1= unoccupied && save2!= unoccupied)
			System.out.println(nameOf(saved1)+" moved down from ("+(sr1+1)+","+(sc1+1)+") to ("+(sr2+1)+","+(sc2+1)+"). Captured: "+nameOf(saved2));
		}
		else if(latest2==ChessController.UNOCCUPIED && saved1==ChessController.UNOCCUPIED) {
			//Down moved top (late2== unoccupied && save1== unoccupied)
			System.out.println(nameOf(saved2)+" moved up from ("+(sr2+1)+","+(sc2+1)+") to ("+(sr1+1)+","+(sc1+1)+"). No capture.");
		}
		else if(latest2==ChessController.UNOCCUPIED && saved1!=ChessController.UNOCCUPIED) {
			//Down took top (late2== unoccupied && save1!= unoccupied)
			System.out.println(nameOf(saved2)+" moved up from ("+(sr2+1)+","+(sc2+1)+") to ("+(sr1+1)+","+(sc1+1)+"). Captured: "+nameOf(saved1));
		}
		
		updateSavedBoard(latestBoard); //Update the board
	}

	/**
	 * @param winner is the byte code for either WHITE or BLACK
	 * 
	 * @return true if player wants to play again, false if not
	 */
	public boolean gameOver(int winner) {
		if(winner==ChessController.WHITE) 
			System.out.println("Game over. Congratulations! You win!");
		else
			System.out.println("Game over. You lose!");
		
		System.out.println("Play again? (Enter \"yes\" if yes)");
		try {
			if(bufInput.readLine().equals("yes"))
				return true;
			else
				return false;
		} catch (IOException e) {
			System.out.println("Invalid answer");
			gameOver(winner);
		}
		return false;
	}
	
	public String promptForMove() {
		System.out.println("It's your turn! Type the row and column number of the piece you want to move separated by a comma,"
				+ " and then specify the point you want to move it to, by separating the two coordinated by a space!\n"
				+ "Example: I want to move my pawn located at (7,1), to (6,1). I'll type: \n7,1 6,1\nPress Q to quit");
		try {
			String returner= bufInput.readLine();
			System.out.println(); //New line
			return returner;
		} catch (IOException e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public char promptForPromotion() {
		System.out.println("Your pawn has reached the other side! Choose what it promotes into!\n"
				+ "Queen: \'Q\', Rook: \'R\', Knight: \'N\', or Bishop: \'B\'");
		
		String chosen= "";
		try {
			chosen= bufInput.readLine();
			if(chosen.charAt(0)== ChessController.WHITE_BISHOP || 
					chosen.charAt(0)== ChessController.WHITE_QUEEN || chosen.charAt(0)== ChessController.WHITE_KNIGHT || 
					chosen.charAt(0)== ChessController.WHITE_ROOK)
				return chosen.charAt(0);
			else {
				System.out.println("Invalid piece.");
				return promptForPromotion();
			}
		} catch (IOException e) {
			e.printStackTrace();
			
			return 0;
		}
	}
	
	public void sendMessage(String message) {
		System.out.println(message);
	}
	
	public void setup(char[][] board) {
		updateSavedBoard(board);
	}
	
	private void updateSavedBoard(char[][] newBoard) {
		for(int r= 0; r<ChessController.ROWS; r++) {
			for(int c= 0; c<ChessController.COLS; c++) {
				savedBoard[r][c]= newBoard[r][c];
			}
		}
	}
}

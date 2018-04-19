
final public class WindowTest {

	public static void main(String[] args) {
		WindowedGraphics frame1= new WindowedGraphics();
		
		frame1.setXscale(0, 100);
		frame1.setYscale(0, 100);
		frame1.circle(50, 50, 10);
		
		frame1.show();
		
		int i= frame1.promptOpponentForDifficulty(2);
		
		frame1.show();
		char[][] boardd= new char[8][8];
		boardd[0][0]= 'r';
		boardd[0][1]= 'n';
		boardd[0][2]= 'b';
		boardd[0][3]= 'q';
		boardd[0][4]= 'k';
		boardd[0][5]= 'b';
		boardd[0][6]= 'n';
		boardd[0][7]= 'r';
		for(int a= 0; a<8; a++)
			boardd[1][a]= 'p';
		for(int a= 2; a<8; a++) {
			for(int b= 0; b<8; b++) {
				boardd[a][b]= ChessController.UNOCCUPIED;
			}
		}
		frame1.setup(boardd);
		frame1.displayBoard(boardd);
		
		//String move= frame1.promptForMove();
		//System.out.println(move);
		
		//char chosen= frame1.promptForPromotion();
		//System.out.println(chosen);
		
		boardd[1][0]= ChessController.UNOCCUPIED;
		boardd[2][0]= ChessController.BLACK_PAWN;
		
		frame1.summarizeMove(boardd);
		
		boolean newGame= frame1.gameOver(ChessController.WHITE);
		System.out.println(newGame);
		
		while(frame1.exists()) {
			frame1.show();
		}
		System.out.println("Difficulty: "+i);
	}

}

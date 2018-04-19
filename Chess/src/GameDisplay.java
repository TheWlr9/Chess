//package graphics;

public interface GameDisplay {
	public int promptOpponentForDifficulty(int maxDifficulty);
	
	public String promptForMove();
	
	public char promptForPromotion();
	
	public void setup(char[][] board);
	
	public void displayBoard(char[][] info);
	
	public void summarizeMove(char[][] latestBoard);
	
	public boolean gameOver(int winner);

	public void sendMessage(String string);
}

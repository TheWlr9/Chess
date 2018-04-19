
public interface ChessPlayer {
	public MoveData makeMove(String lastPlayerMove, char[][] theboard);
	
	public char promotionPrompt();
}

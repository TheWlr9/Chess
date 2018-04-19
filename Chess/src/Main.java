/**
 * Title: Chess
 * @author William Ritchie
 * @version 1.0.0
 * 
 * Course: COMP 2150
 * Assignment: A3
 * Section: A02
 * Professor: Heather Matheson
 * 
 * Description: This program is the game of chess. It requires objects that implement 
 * ChessController, GameDisplay, or ChessPlayer interfaces. It does not notify the player 
 * when he is in check. Promotion is implemented. Castling is not implemented. En passant is not implemented.
 * No timing.
 * Draw checking is not implemented.
 * 
 *
 */
public final class Main {
 private static ChessController mainGame;
 private static GameDisplay ui;
 private static ChessPlayer machine;
 
 private static String lastPlayerMoveMade;
 
 //final public static char[][] getMainBoard(){
  //return mainGame.getBoard();
 //}
 
 public static void main(String[] args) {
  ui= new VisualChessGraphics();//ChessTextGraphics(); //WindowedGraphics();
  mainGame= new Game(ui);
  machine= new AI(mainGame.getDifficulty());
  
  playGame();
 }
 
 private static void playerTurn() {
  int startingRow= 0;
  int startingCol= 0;
  int endingRow= 0;
  int endingCol= 0;
  String[] points= {};
  try {
   ui.displayBoard(mainGame.getBoard());
   String nextMove= ui.promptForMove();
   lastPlayerMoveMade= nextMove;
   
   points= nextMove.split(" ");
   
   String[] elements= points[0].split(",");
   startingRow= Integer.parseInt(elements[0]);
   startingCol= Integer.parseInt(elements[1]);
   
   elements= points[1].split(",");
   endingRow= Integer.parseInt(elements[0]);
   endingCol= Integer.parseInt(elements[1]);
  }
  catch(Exception e) {
   if(points[0].equals("q") || points[0].equals("Q"))
    System.exit(0);
   ui.sendMessage("Invalid coordinates");
   playerTurn();
   
   return;
  }
  
  if(!mainGame.movePiece(startingRow-1, startingCol-1, endingRow-1, endingCol-1)) { //Minus 1 because of the offset)
   ui.sendMessage("Invalid move, pick another one!");
   playerTurn();
  }
 }
 
 private static void AITurn() {
  MoveData nextAIMove= machine.makeMove(lastPlayerMoveMade, mainGame.getBoard());
  mainGame.secretMovePiece(nextAIMove.getFromRow(), nextAIMove.getFromCol(), nextAIMove.getToRow(), nextAIMove.getToCol(), machine);
 }
 
 private static void playGame() {
  while(mainGame.continueGame()) {
   playerTurn();
   AITurn();
  }
  ui.sendMessage("Thanks for playing!");
 }

}

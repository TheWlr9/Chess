
public final class Game implements ChessController {
 private char[][] theBoard;
 
 private GameDisplay gui;
 
 private int difficulty; //The selected difficulty of the game
 private boolean sessionContinue;
 
 public Game(GameDisplay gui){
  theBoard= new char[ROWS][COLS];
  
  sessionContinue= true;
  
  reset();
  
  gui.setup(theBoard);
  this.gui= gui;
  
  difficulty= gui.promptOpponentForDifficulty(MAX_DIFFICULTY);
  
  if(difficulty==(int)DEBUG) { //Debug mode
   difficulty= 1;
   
   debugReset();
   
   this.gui.setup(theBoard); //Re-setup the board in debug mode
  }
 }
 
 public int getDifficulty() {
  return difficulty;
 }
 
 private static GamePiece getPiece(int row, int col, char[][] theBoard) {
  if(theBoard[row][col]==UNOCCUPIED)
   return null;
  else if(theBoard[row][col]==WHITE_PAWN)
   return new Pawn(row,col,true);
  else if(theBoard[row][col]==BLACK_PAWN)
   return new Pawn(row,col,false);
  else if(theBoard[row][col]==WHITE_QUEEN)
   return new Queen(row,col,true);
  else if(theBoard[row][col]==BLACK_QUEEN)
   return new Queen(row,col,false);
  else if(theBoard[row][col]==WHITE_KING)
   return new King(row,col,true);
  else if(theBoard[row][col]==BLACK_KING)
   return new King(row,col,false);
  else if(theBoard[row][col]==WHITE_BISHOP)
   return new Bishop(row,col,true);
  else if(theBoard[row][col]==BLACK_BISHOP)
   return new Bishop(row,col,false);
  else if(theBoard[row][col]==WHITE_KNIGHT)
   return new Knight(row,col,true);
  else if(theBoard[row][col]==BLACK_KNIGHT)
   return new Knight(row,col,false);
  else if(theBoard[row][col]==WHITE_ROOK)
   return new Rook(row,col,true);
  else if(theBoard[row][col]==BLACK_ROOK)
   return new Rook(row,col,false);
  else {
   System.err.println("Error in accessing character in point: ("+row+","+col+")");
   return null;
  }
 }
 
 /*
  * This moves a player piece on the board
  */
 public boolean movePiece(int prevRow, int prevCol, int curRow, int curCol) {
  //byte winner= isGameOver();
  //System.out.println("WIINIER: "+winner);
  
  /*if(winner<WHITE_KING+BLACK_KING && winner>=0) {
   gui.displayBoard(theBoard);
   sessionContinue= gui.gameOver(winner);
   if(sessionContinue) {
    reset();
    
    gui.promptOpponentForDifficulty(MAX_DIFFICULTY);
   }
  }*/

  GamePiece moving= getPiece(prevRow,prevCol, theBoard);
  
  if(moving!=null) {
   if(moving.validateMove(curRow, curCol, theBoard)) {
    if(theBoard[prevRow][prevCol]==WHITE_PAWN && curRow==0) {
     theBoard[curRow][curCol]= gui.promptForPromotion();
     theBoard[prevRow][prevCol]= UNOCCUPIED;
    }
    else {
     theBoard[curRow][curCol]= theBoard[prevRow][prevCol];
     theBoard[prevRow][prevCol]= UNOCCUPIED;
    }
     
    gui.summarizeMove(theBoard);
    
    /*winner= isGameOver();
    if(winner<WHITE_KING+BLACK_KING && winner>=0) {
     gui.displayBoard(theBoard);
     sessionContinue= gui.gameOver(winner);
     if(sessionContinue) {
      reset();
      
      gui.promptOpponentForDifficulty(MAX_DIFFICULTY);
     }
    }*/
    
    return true;
   }
   else
    return false;
  }
  else
   return false;
 }
 
 /**
  * This method is called by the ChessPlayer (AI) interface (class) so that is calls tailored methods
  */
 public void secretMovePiece(int prevRow, int prevCol, int curRow, int curCol, ChessPlayer ai) {
  byte winner= isGameOver();
  
  if(winner<BLACK_KING+WHITE_KING && winner>=0) {
   gui.displayBoard(theBoard);
   sessionContinue= gui.gameOver(winner);
   if(sessionContinue) {
    reset();
    
    gui.promptOpponentForDifficulty(MAX_DIFFICULTY);
   }
  }
  else {
   if(theBoard[prevRow][curCol]==BLACK_PAWN && curRow==ROWS-1) {
    theBoard[curRow][curCol]= ai.promotionPrompt(); //Required for ChessPlayer promotion prompt, therefore need a ChessPlayer parameter
    theBoard[prevRow][prevCol]= UNOCCUPIED;
   }
   else {
    theBoard[curRow][curCol]= theBoard[prevRow][prevCol];
    theBoard[prevRow][prevCol]= UNOCCUPIED;
   }
   
   gui.summarizeMove(theBoard);
  }
  
  winner= isGameOver();
  if(winner<=BLACK_KING+WHITE_KING && winner>=0) {
   gui.displayBoard(theBoard);
   sessionContinue= gui.gameOver(winner);
   if(sessionContinue) {
    reset();
    
    gui.promptOpponentForDifficulty(MAX_DIFFICULTY);
   }
  }
 }

 public void reset() {
  int row= 0;
  
  theBoard[row][0]= BLACK_ROOK;
  theBoard[row][1]= BLACK_KNIGHT;
  theBoard[row][2]= BLACK_BISHOP;
  theBoard[row][3]= BLACK_KING;
  theBoard[row][4]= BLACK_QUEEN;
  theBoard[row][5]= BLACK_BISHOP;
  theBoard[row][6]= BLACK_KNIGHT;
  theBoard[row][7]= BLACK_ROOK;
  row++;
  for(int col= 0; col<COLS; col++)
   theBoard[row][col]= BLACK_PAWN;
  row++;
  
  while(row<ROWS-2) {
   for(int col= 0; col<COLS; col++)
    theBoard[row][col]= UNOCCUPIED;
   row++;
  }
  
  for(int col= 0; col<COLS; col++)
   theBoard[row][col]= WHITE_PAWN;
  row++;
  theBoard[row][7]= WHITE_ROOK;
  theBoard[row][6]= WHITE_KNIGHT;
  theBoard[row][5]= WHITE_BISHOP;
  theBoard[row][4]= WHITE_QUEEN;
  theBoard[row][3]= WHITE_KING;
  theBoard[row][2]= WHITE_BISHOP;
  theBoard[row][1]= WHITE_KNIGHT;
  theBoard[row][0]= WHITE_ROOK;
 }
 public void debugReset() {
  for(int row= 0; row<ROWS; row++) {
   for(int col= 0; col<COLS; col++){
    theBoard[row][col]= UNOCCUPIED;
   }
  }
  
  theBoard[0][0]= BLACK_KING;
  theBoard[7][0]= WHITE_ROOK;
  theBoard[1][1]= WHITE_KING;
  theBoard[0][7]= BLACK_PAWN;
 }
 
 public char[][] getBoard(){
  return theBoard;
 }
 
 private byte isGameOver() {
  byte whiteKing= 0;
  byte blackKing= 0;
  
  for(int r= 0; r<ROWS; r++)
   for(int c= 0; c<COLS; c++) {
    if(theBoard[r][c]==WHITE_KING)
     whiteKing= WHITE;
    else if(theBoard[r][c]==BLACK_KING)
     blackKing= BLACK;
   }
  
  return (byte)(whiteKing+blackKing);
 }
 
 public boolean continueGame() {
  return sessionContinue;
 }
}

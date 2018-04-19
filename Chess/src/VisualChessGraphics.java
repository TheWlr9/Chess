public class VisualChessGraphics implements GameDisplay{
  public static final String BLACK_ROOK_FILENAME= "BlackRook.png";
  public static final String WHITE_ROOK_FILENAME= "WhiteRook.png";
  public static final String BLACK_KNIGHT_FILENAME= "BlackKnight.png";
  public static final String WHITE_KNIGHT_FILENAME= "WhiteKnight.png";
  public static final String BLACK_BISHOP_FILENAME= "BlackBishop.png";
  public static final String WHITE_BISHOP_FILENAME= "WhiteBishop.png";
  public static final String BLACK_KING_FILENAME= "BlackKing.png";
  public static final String WHITE_KING_FILENAME= "WhiteKing.png";
  public static final String BLACK_QUEEN_FILENAME= "BlackQueen.png";
  public static final String WHITE_QUEEN_FILENAME= "WhiteQueen.png";
  public static final String BLACK_PAWN_FILENAME= "BlackPawn.png";
  public static final String WHITE_PAWN_FILENAME= "WhitePawn.png";
  
  private static final int BLACK_ROOK_SIZE= 60;
  private static final int WHITE_ROOK_SIZE= 60;
  private static final int BLACK_KNIGHT_SIZE= 115;
  private static final int WHITE_KNIGHT_SIZE= 75;
  private static final int BLACK_BISHOP_SIZE= 80;
  private static final int WHITE_BISHOP_SIZE= 80;
  private static final int BLACK_KING_SIZE= 110;
  private static final int WHITE_KING_SIZE= 90;
  private static final int BLACK_QUEEN_SIZE= 110;
  private static final int WHITE_QUEEN_SIZE= 75;
  private static final int BLACK_PAWN_SIZE= 105;
  private static final int WHITE_PAWN_SIZE= 90;
  
  private static final int SIZE= 800;
  private static final int PADDING= 10;
  private static final int BOTTOM_PADDING= 10;
  private static final int LEFT_PADDING= 20;
  private static final int RIGHT_PADDING= 20;
  private static final int TOP_PADDING= 30;
  private static final int BOTTOM_OF_MESSAGE_PANE= 30;
  private static final int BOTTOM_TEXT_Y= 10;
  private static final int LEFT_TEXT_X= 15;
  
  private char[][] savedBoard;
  
  int boardWidth;
  int boardHeight;
  int spaceWidth;
  int spaceHeight;
  
  private final double SQUARE_SIZE= (SIZE-(SIZE/10.0))/8.0;
  
  private WindowedGraphics window;
  
  public VisualChessGraphics(){    
    window= new WindowedGraphics(SIZE,SIZE);
    
    boardWidth= SIZE-(LEFT_PADDING+RIGHT_PADDING)*3;
    boardHeight= SIZE-(TOP_PADDING+BOTTOM_PADDING)*3;
    spaceWidth= boardWidth/8;
    spaceHeight= boardHeight/8;
    
    window.setPenColour(WindowedGraphics.BLACK);
    window.setTitle("Chess");
    
    savedBoard= new char[ChessController.ROWS][ChessController.COLS];
    
    
  }
  
  private void clearBoard() {
    window.setPenColour(WindowedGraphics.WHITE);
    window.filledRectangle(SIZE/2.0,SIZE/2.0,(SIZE-(LEFT_PADDING+RIGHT_PADDING)*3)/2.0,(SIZE-(TOP_PADDING+BOTTOM_PADDING)*3)/2.0);
    
    window.show();
  }
  
  public int promptOpponentForDifficulty(int maxDifficulty) {
    window.setPenColour(WindowedGraphics.BLACK);
    window.setPenRadius();
    window.clear(WindowedGraphics.WHITE);
    
    window.text(SIZE/2,SIZE/2,"Enter difficulty from 1 - "+maxDifficulty);
    
    window.show();
    
    while(!window.hasNextKeyTyped()); //Wait
    
    char typedChar= window.nextKeyTyped();
    
    window.clear();
    
    if(typedChar==ChessController.DEBUG)
      return ChessController.DEBUG;
    
    return typedChar-48; //48 is '0'
  }
  
  public String promptForMove() {
    /*while(!window.isMousePressed()); //Wait
     * 
     int r;
     int c;
     for(r= 1; r<= ChessController.ROWS; r++) {
     if(Math.abs(window.(r*spaceWidth)/2-window.mouseX())<=spaceWidth/2) {
     break;
     }
     }
     for(c= 1; c<= ChessController.COLS; c++) {
     if(Math.abs(window.(c*spaceHeight)/2-window.mouseY())<=spaceHeight/2) {
     break;
     }
     }
     System.out.println((window.(r*spaceWidth))+" "+(window.(c*spaceHeight)));
     
     window.setPenColour(WindowedGraphics.BLUE);
     window.square(window.(r*spaceWidth),window.(c*spaceHeight),window.(spaceWidth)/2);
     
     System.out.println(window.(r*SQUARE_SIZE)+" "+window.(c*SQUARE_SIZE));
     
     window.show();
     
     while(true);*/
    
    //return "7,1 6,1";
    
    window.setPenColour(WindowedGraphics.WHITE);
    window.filledRectangle(SIZE/2.0,BOTTOM_PADDING*3,SIZE/2.0,(BOTTOM_PADDING+10)/2.0); //Alert: Should be TEXT_SIZE
    window.setPenColour(WindowedGraphics.BLACK);
    String message= "Your turn! Coordinates like: 7,1 6,1 or Q.";
    String result= "";
    window.text(SIZE/2,BOTTOM_PADDING*3,message+" "+result);
    
    window.show();
    
    char lastCharTyped= 0;
    
    while(lastCharTyped!='\n' && lastCharTyped!=13) { //13 is carriage return on some devices
      if(window.hasNextKeyTyped()) {
        window.setPenColour(WindowedGraphics.WHITE);
        window.filledRectangle(SIZE/2,BOTTOM_PADDING*3,SIZE/2,(BOTTOM_PADDING+10)/2.0); //Alert: Should be TEXT_SIZE
        lastCharTyped= window.nextKeyTyped();
        if(lastCharTyped==8) //Backspace
        {
          char[] letters= result.toCharArray();
          if(letters.length-1>=0) {
            letters[letters.length-1]= 0;
            result= "";
            for(int i= 0; i<letters.length-1; i++) {
              result+=letters[i];
            }
            result= result.trim();
          }
        }
        else
          result+= lastCharTyped;
        
        window.setPenColour(WindowedGraphics.BLACK);
        window.text((SIZE/2),BOTTOM_PADDING*3,message+" "+result);
        window.show();
      }
    }
    return result.trim();
  }
  
  public char promptForPromotion() {
    window.setPenColour(WindowedGraphics.BLACK);
    window.text((SIZE/2),(SIZE/2),"Your pawn is ready for a promotion!");
    window.text((SIZE/2),BOTTOM_PADDING*3,"Choose a piece to promote into!");
    window.text((SIZE/2),(SIZE/2+20),"Enter "+ChessController.WHITE_QUEEN+", "+ChessController.WHITE_ROOK+", "
           +ChessController.WHITE_KNIGHT+", or "+ChessController.WHITE_BISHOP);
    
    window.show();
    
    while(!window.hasNextKeyTyped()); //Wait
    
    char chosen= window.nextKeyTyped();
    
    if(chosen== ChessController.WHITE_BISHOP || 
       chosen== ChessController.WHITE_QUEEN || chosen== ChessController.WHITE_KNIGHT || 
       chosen== ChessController.WHITE_ROOK)
      return chosen;
    else {
      return promptForPromotion();
    }
  }
  
  public void setup(char[][] board) {
    updateSavedBoard(board);
  }
  
  public void displayBoard(char[][] info) {
    clearBoard();
    
    window.setPenColour(WindowedGraphics.LIGHT_GRAY);
    
    for(int w= 1; w<=8; w++) {
      window.setPenColour(WindowedGraphics.BLACK);
      window.text((LEFT_PADDING/4)*3,boardHeight-(w*spaceHeight*2)/2.0+spaceHeight,w+"");
      window.setPenColour(WindowedGraphics.LIGHT_GRAY);
      for (int h= 1; h<=8; h++){
        double newX= (w*spaceWidth);
        double newY= boardHeight-(h*spaceHeight*2)/2.0+spaceHeight;
        char space= info[h-1][w-1];
        
        if((h+w)%2==0) {
          window.filledSquare(newX,newY,(spaceWidth)/2);
          
          if(space!=ChessController.UNOCCUPIED) {
            if(space==ChessController.BLACK_ROOK) {
              window.picture(newX,newY,BLACK_ROOK_FILENAME,BLACK_ROOK_SIZE,BLACK_ROOK_SIZE);
            }
            else if(space==ChessController.WHITE_ROOK) {
              window.picture(newX,newY,WHITE_ROOK_FILENAME,WHITE_ROOK_SIZE,WHITE_ROOK_SIZE);
            }
            else if(space==ChessController.BLACK_KNIGHT) {
              window.picture(newX,newY,BLACK_KNIGHT_FILENAME,BLACK_KNIGHT_SIZE,BLACK_KNIGHT_SIZE);
            }
            else if(space==ChessController.WHITE_KNIGHT) {
              window.picture(newX,newY,WHITE_KNIGHT_FILENAME,WHITE_KNIGHT_SIZE,WHITE_KNIGHT_SIZE);
            }
            else if(space==ChessController.BLACK_BISHOP) {
              window.picture(newX,newY,BLACK_BISHOP_FILENAME,BLACK_BISHOP_SIZE,BLACK_BISHOP_SIZE);
            }
            else if(space==ChessController.WHITE_BISHOP) {
              window.picture(newX,newY,WHITE_BISHOP_FILENAME,WHITE_BISHOP_SIZE,WHITE_BISHOP_SIZE);
            }
            else if(space==ChessController.BLACK_KING) {
              window.picture(newX,newY,BLACK_KING_FILENAME,BLACK_KING_SIZE,BLACK_KING_SIZE);
            }
            else if(space==ChessController.WHITE_KING) {
              window.picture(newX,newY,WHITE_KING_FILENAME,WHITE_KING_SIZE,WHITE_KING_SIZE);
            }
            else if(space==ChessController.BLACK_QUEEN) {
              window.picture(newX,newY,BLACK_QUEEN_FILENAME,BLACK_QUEEN_SIZE,BLACK_QUEEN_SIZE);
            }
            else if(space==ChessController.WHITE_QUEEN) {
              window.picture(newX,newY,WHITE_QUEEN_FILENAME,WHITE_QUEEN_SIZE,WHITE_QUEEN_SIZE);
            }
            else if(space==ChessController.BLACK_PAWN) {
              window.picture(newX,newY,BLACK_PAWN_FILENAME,BLACK_PAWN_SIZE,BLACK_PAWN_SIZE);
            }
            else if(space==ChessController.WHITE_PAWN) {
              window.picture(newX,newY,WHITE_PAWN_FILENAME,WHITE_PAWN_SIZE,WHITE_PAWN_SIZE);
            }
            else
              System.err.println("ERROR IN VisualChessGraphics.displayBoard(char[][] info): Unrecognized game piece at ("+(h-1)+","+(w-1)+")");
          }
        }
        else {
          window.square((w*spaceWidth),(h*spaceHeight),(spaceWidth)/2);
          
          if(space!=ChessController.UNOCCUPIED) {
            if(space==ChessController.BLACK_ROOK) {
              window.picture(newX,newY,BLACK_ROOK_FILENAME,BLACK_ROOK_SIZE,BLACK_ROOK_SIZE);
            }
            else if(space==ChessController.WHITE_ROOK) {
              window.picture(newX,newY,WHITE_ROOK_FILENAME,WHITE_ROOK_SIZE,WHITE_ROOK_SIZE);
            }
            else if(space==ChessController.BLACK_KNIGHT) {
              window.picture(newX,newY,BLACK_KNIGHT_FILENAME,BLACK_KNIGHT_SIZE,BLACK_KNIGHT_SIZE);
            }
            else if(space==ChessController.WHITE_KNIGHT) {
              window.picture(newX,newY,WHITE_KNIGHT_FILENAME,WHITE_KNIGHT_SIZE,WHITE_KNIGHT_SIZE);
            }
            else if(space==ChessController.BLACK_BISHOP) {
              window.picture(newX,newY,BLACK_BISHOP_FILENAME,BLACK_BISHOP_SIZE,BLACK_BISHOP_SIZE);
            }
            else if(space==ChessController.WHITE_BISHOP) {
              window.picture(newX,newY,WHITE_BISHOP_FILENAME,WHITE_BISHOP_SIZE,WHITE_BISHOP_SIZE);
            }
            else if(space==ChessController.BLACK_KING) {
              window.picture(newX,newY,BLACK_KING_FILENAME,BLACK_KING_SIZE,BLACK_KING_SIZE);
            }
            else if(space==ChessController.WHITE_KING) {
              window.picture(newX,newY,WHITE_KING_FILENAME,WHITE_KING_SIZE,WHITE_KING_SIZE);
            }
            else if(space==ChessController.BLACK_QUEEN) {
              window.picture(newX,newY,BLACK_QUEEN_FILENAME,BLACK_QUEEN_SIZE,BLACK_QUEEN_SIZE);
            }
            else if(space==ChessController.WHITE_QUEEN) {
              window.picture(newX,newY,WHITE_QUEEN_FILENAME,WHITE_QUEEN_SIZE,WHITE_QUEEN_SIZE);
            }
            else if(space==ChessController.BLACK_PAWN) {
              window.picture(newX,newY,BLACK_PAWN_FILENAME,BLACK_PAWN_SIZE,BLACK_PAWN_SIZE);
            }
            else if(space==ChessController.WHITE_PAWN) {
              window.picture(newX,newY,WHITE_PAWN_FILENAME,WHITE_PAWN_SIZE,WHITE_PAWN_SIZE);
            }
            else
              System.err.println("ERROR IN VisualChessGraphics.displayBoard(char[][] info): Unrecognized game piece at ("+(h-1)+","+(w-1)+")");
          }
        }
      }
    }
    
    
    
    window.show();
    
  }
  
  private void updateSavedBoard(char[][] newBoard) {
    for(int r= 0; r<ChessController.ROWS; r++) {
      for(int c= 0; c<ChessController.COLS; c++) {
        savedBoard[r][c]= newBoard[r][c];
      }
    }
  }
  
  private void clearMessage() {
    window.setPenColour(WindowedGraphics.WHITE);
    window.filledRectangle(SIZE/2.0, SIZE-(TOP_PADDING*2)/2.0, SIZE/2.0, (TOP_PADDING)/2.0);
    
    window.show();
  }
  
  public void summarizeMove(char[][] latestBoard) {
    clearMessage();
    
    window.setPenColour(WindowedGraphics.BLACK);
    
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
      updateMessage(nameOf(saved1)+" moved down from ("+(sr1+1)+","+(sc1+1)+") to ("+(sr2+1)+","+(sc2+1)+"). No capture.");
    }
    else if(latest1==ChessController.UNOCCUPIED && saved2!=ChessController.UNOCCUPIED) {
      //Top took down (late1= unoccupied && save2!= unoccupied)
      updateMessage(nameOf(saved1)+" moved down from ("+(sr1+1)+","+(sc1+1)+") to ("+(sr2+1)+","+(sc2+1)+"). Captured: "+nameOf(saved2));
    }
    else if(latest2==ChessController.UNOCCUPIED && saved1==ChessController.UNOCCUPIED) {
      //Down moved top (late2== unoccupied && save1== unoccupied)
      updateMessage(nameOf(saved2)+" moved up from ("+(sr2+1)+","+(sc2+1)+") to ("+(sr1+1)+","+(sc1+1)+"). No capture.");
    }
    else if(latest2==ChessController.UNOCCUPIED && saved1!=ChessController.UNOCCUPIED) {
      //Down took top (late2== unoccupied && save1!= unoccupied)
      updateMessage(nameOf(saved2)+" moved up from ("+(sr2+1)+","+(sc2+1)+") to ("+(sr1+1)+","+(sc1+1)+"). Captured: "+nameOf(saved1));
    }
    
    window.show();
    
    updateSavedBoard(latestBoard); //Update the board
    
  }
  
  public boolean gameOver(int winner) {
    window.setPenColour(WindowedGraphics.BLACK);
    
    if(winner==ChessController.WHITE)
      sendMessage("Game over. Congratulations, you win! Play again?");
    else
      sendMessage("Game over. You lose! Play again?");
        
    char lastCharTyped= 0;
    String result= "";
    
    while(lastCharTyped!='\n' && lastCharTyped!=13) { //13 is carriage return on some devices
      if(window.hasNextKeyTyped()) {
        window.setPenColour(WindowedGraphics.WHITE);
        window.filledRectangle((SIZE/2),(SIZE/2-TOP_PADDING),SIZE/2,(BOTTOM_PADDING+10)/2.0); //Alert right here, it should be TEXT_SIZE
        lastCharTyped= window.nextKeyTyped();
        if(lastCharTyped==8) //Backspace
        {
          char[] letters= result.toCharArray();
          if(letters.length-1>=0) {
            letters[letters.length-1]= 0;
            result= "";
            for(int i= 0; i<letters.length-1; i++) {
              result+=letters[i];
            }
            result= result.trim();
          }
        }
        else
          result+= lastCharTyped;
        
        window.setPenColour(WindowedGraphics.BLACK);
        window.text((SIZE/2),(SIZE/2-TOP_PADDING),result);
        window.show();
      }
    }
    if(result.trim().equals("Yes") || result.trim().equals("yes"))
      return true;
    else
      return false;
  }
  
  private static String nameOf(char character) {
    if(character== ChessController.BLACK_ROOK || character== ChessController.WHITE_ROOK)
      return "Rook";
    if(character== ChessController.BLACK_KNIGHT || character== ChessController.WHITE_KNIGHT)
      return "Knight";
    if(character== ChessController.BLACK_BISHOP || character== ChessController.WHITE_BISHOP)
      return "Bishop";
    if(character== ChessController.BLACK_KING || character== ChessController.WHITE_KING)
      return "King";
    if(character== ChessController.BLACK_QUEEN || character== ChessController.WHITE_QUEEN)
      return "Queen";
    if(character== ChessController.BLACK_PAWN || character== ChessController.WHITE_PAWN)
      return "Pawn";
    return "INVALID CHESS PIECE";
  }
  
  private void updateMessage(String message) {
    clearMessage();
    
    window.setPenColour(WindowedGraphics.BLACK);
    
    window.text((SIZE/2),SIZE-(TOP_PADDING*2)/2.0,message);
  }
  
  public void sendMessage(String message) {
    updateMessage(message);
    
    window.show();
  }
}

public class AI implements ChessPlayer {
	private int difficulty;
	private MoveData best, secondBest;
	
	public AI() {
		this(1);
	}
	public AI(int difficulty) {
		this.difficulty= difficulty;
		best= null;
		secondBest= null;
	}
	
	/**
	 * 
	 * @param theBoard
	 * @return The move to make, or null otherwise
	 */
	private static MoveData worthCapturing(char[][] theBoard) {
		MoveData deathMove= null;
		MoveData attackMove= null;
		
		MoveData theOne= null; //Reset best
		
		for(int r= 0; r<theBoard.length; r++) {
			for(int c= 0; c<theBoard[r].length; c++) {
				if(theBoard[r][c]<=ChessController.WHITE && theBoard[r][c]>ChessController.UNOCCUPIED) {
					attackMove= GamePiece.endangered(getPiece(r,c,theBoard), theBoard);
					
					if(attackMove!= null) {
						char[][] fakeBoard = new char[theBoard.length][theBoard[0].length];
						
						//Deep copy
						for(int row= 0; row<theBoard.length; row++)
							for(int col= 0; col<theBoard[row].length; col++)
								fakeBoard[row][col]= theBoard[row][col];
						
						//Make the potential move
						fakeBoard[attackMove.getToRow()][attackMove.getToCol()]= fakeBoard[attackMove.getFromRow()][attackMove.getFromCol()];
						fakeBoard[attackMove.getFromRow()][attackMove.getFromCol()]= ChessController.UNOCCUPIED;
						
						//Now determine whether the AI's piece is endangered
						deathMove= GamePiece.endangered(getPiece(attackMove.getToRow(), attackMove.getToCol(), fakeBoard),fakeBoard);
						
						if(deathMove==null) {
							if(theOne==null || attackMove.getValue()>=theOne.getValue())
								theOne= new MoveData(attackMove);
						}
						else if((theOne==null || attackMove.getValue()-deathMove.getValue()>=theOne.getValue()) && attackMove.getValue()>=deathMove.getValue())
							theOne= new MoveData(attackMove,attackMove.getValue()-deathMove.getValue());
					}
				}
			}
		}
		return theOne;
	}
	
	/**
	 * 
	 * @param theBoard
	 * @return The move to make, or null otherwise
	 */
	private static MoveData bait(char[][] theBoard) {
		MoveData theOne= null;
		
		char[][] fakeBoard= new char[theBoard.length][theBoard[0].length];
		
		//Find check to see if any of it's pieces are in danger
		for(int pieceR= 0; pieceR<theBoard.length; pieceR++) {
			for(int pieceC= 0; pieceC<theBoard[pieceR].length; pieceC++) {
				if(theBoard[pieceR][pieceC]>=ChessController.BLACK) {
					GamePiece token= getPiece(pieceR,pieceC,theBoard);
					
					//For all possible moves
					for(int moveR= 0; moveR<theBoard.length; moveR++) {
						for(int moveC= 0; moveC<theBoard[moveR].length; moveC++) {
							if(token.validateMove(moveR, moveC, theBoard)) {
								MoveData initialMove= new MoveData(pieceR,pieceC,moveR,moveC,0);
								
								//Deep copy
								for(int row= 0; row<theBoard.length; row++)
									for(int col= 0; col<theBoard[row].length; col++)
										fakeBoard[row][col]= theBoard[row][col];
								
								fakeBoard[moveR][moveC]= fakeBoard[pieceR][pieceC];
								fakeBoard[pieceR][pieceC]= ChessController.UNOCCUPIED;
								
								MoveData fallenBait= GamePiece.endangered(getPiece(moveR,moveC,fakeBoard), fakeBoard);
								
								if(fallenBait!=null) {
									fakeBoard[fallenBait.getToRow()][fallenBait.getToCol()]= fakeBoard[fallenBait.getFromRow()][fallenBait.getFromCol()];
									fakeBoard[fallenBait.getFromRow()][fallenBait.getFromCol()]= ChessController.UNOCCUPIED;
									
									MoveData revenge= GamePiece.endangered(getPiece(fallenBait.getToRow(),fallenBait.getToCol(),fakeBoard), fakeBoard);
									
									if(theOne==null) {
										if(revenge!=null && fallenBait.getValue()<revenge.getValue())
											theOne= new MoveData(initialMove,revenge.getValue()-fallenBait.getValue());
									}
									else if(revenge!=null && revenge.getValue()-fallenBait.getValue()>=theOne.getValue())//&& fallenBait.getValue()<revenge.getValue() 
										theOne= new MoveData(initialMove,revenge.getValue()-fallenBait.getValue());
								}
							}
						}
					}
				}
			}
		}
		return theOne;
	}
	
	private static MoveData defendKing(char[][] theBoard) {
		MoveData theOne= null;
		
		char[][] fakeBoard= new char[theBoard.length][theBoard[0].length];

		//Find the black king
		for(int row= 0; row<theBoard.length; row++) {
			for(int col= 0; col<theBoard[row].length; col++) {
				if(theBoard[row][col]==ChessController.BLACK_KING) {
					GamePiece theKing= getPiece(row,col,theBoard);
					MoveData assassin= GamePiece.endangered(theKing, theBoard);
					
					if(assassin!=null) {
						//The king is in danger!
						
						//Retreat king!!
						for(int i= -1; i<=1; i++) {
							for(int j= -1; j<=1; j++){
								if(theKing.validateMove(row+i, col+j, theBoard)) {
									//Deep copy
									for(int r= 0; r<theBoard.length; r++)
										for(int c= 0; c<theBoard[r].length; c++)
											fakeBoard[r][c]= theBoard[r][c];
									
									fakeBoard[row+i][col+j]= fakeBoard[row][col];
									fakeBoard[row][col]= ChessController.UNOCCUPIED;
									
									assassin= GamePiece.endangered(getPiece(row+i,col+j,fakeBoard), fakeBoard);
									if(assassin==null)
										return new MoveData(row,col,row+i,col+j, 0);
								}
							}
						}
						
						
						//Kill the assassin!!
						assassin= GamePiece.endangered(theKing, theBoard);
						
						for(int pieceR= 0; pieceR<theBoard.length; pieceR++) {
							for(int pieceC= 0; pieceC<theBoard[pieceR].length; pieceC++) {
								if(theBoard[pieceR][pieceC]>=ChessController.BLACK) {
									GamePiece token= getPiece(pieceR,pieceC,theBoard);
									
									if(token.validateMove(assassin.getFromRow(), assassin.getFromCol(), theBoard))
										return new MoveData(pieceR,pieceC,assassin.getFromRow(),assassin.getFromCol(),10);
								}
							}
						}
						
						//Sacrifice!!
						for(int pieceR= 0; pieceR<theBoard.length; pieceR++) {
							for(int pieceC= 0; pieceC<theBoard[pieceR].length; pieceC++) {
								if(theBoard[pieceR][pieceC]>=ChessController.BLACK) {
									GamePiece token= getPiece(pieceR,pieceC,theBoard);
									for(int moveR= 0; moveR<theBoard.length; moveR++) {
										for(int moveC= 0; moveC<theBoard[moveR].length; moveC++) {
											if(token.validateMove(moveR, moveC, theBoard)) {
												//Deep copy
												for(int r= 0; r<theBoard.length; r++)
													for(int c= 0; c<theBoard[r].length; c++)
														fakeBoard[r][c]= theBoard[r][c];
												
												MoveData initialMove;
												if(theBoard[moveR][moveC]<=ChessController.WHITE && theBoard[moveR][moveC]>ChessController.UNOCCUPIED)
													initialMove= new MoveData(pieceR,pieceC,moveR,moveC,getPiece(moveR,moveC,theBoard).getValue());
												else
													initialMove= new MoveData(pieceR,pieceC,moveR,moveC,0);
												
												fakeBoard[moveR][moveC]= fakeBoard[pieceR][pieceC];
												fakeBoard[pieceR][pieceC]= ChessController.UNOCCUPIED;
												
												MoveData assassination= GamePiece.endangered(theKing, fakeBoard);
												
												if(assassination==null && initialMove.getValue()==0) {
													if(theOne==null)
														theOne= new MoveData(initialMove,-getPiece(pieceR,pieceC,theBoard).getValue());
													else if(-getPiece(pieceR,pieceC,theBoard).getValue()>theOne.getValue())
														theOne= new MoveData(initialMove,-getPiece(pieceR,pieceC,theBoard).getValue());
												}
												else if(assassination==null)
													if(theOne==null || initialMove.getValue()>theOne.getValue())
														theOne= new MoveData(initialMove);
											}
										}
									}
								}
							}
						}
					}
					return null;
				}
			}
		}
		
		return theOne;
	}
	
	private static MoveData defend(char[][] theBoard) {
		MoveData theOne= null;
		
		char[][] fakeBoard= new char[theBoard.length][theBoard[0].length];
		
		for(int pieceR= 0; pieceR<theBoard.length; pieceR++) {
			for(int pieceC= 0; pieceC<theBoard[pieceR].length; pieceC++) {
				if(theBoard[pieceR][pieceC]>=ChessController.BLACK) {
					GamePiece token= getPiece(pieceR,pieceC,theBoard);
					MoveData attack= GamePiece.endangered(token, theBoard);
					
					if(attack!=null) { //There is an attacker!
						GamePiece attacker= getPiece(attack.getFromRow(),attack.getFromCol(), theBoard);
						
						
						//Deep copy
						for(int r= 0; r<theBoard.length; r++)
							for(int c= 0; c<theBoard[r].length; c++)
								fakeBoard[r][c]= theBoard[r][c];
						
						fakeBoard[pieceR][pieceC]= fakeBoard[attack.getFromRow()][attack.getFromCol()];
						fakeBoard[attack.getFromRow()][attack.getFromCol()]= ChessController.UNOCCUPIED;
						
						MoveData revenge= GamePiece.endangered(getPiece(pieceR,pieceC,fakeBoard), fakeBoard);
						
						for(int i= 0; i<theBoard.length; i++) {
							for(int j= 0; j<theBoard[i].length; j++) {
								if(theBoard[i][j]==ChessController.BLACK_KING) {
									
									//Deep copy
									for(int r= 0; r<theBoard.length; r++)
										for(int c= 0; c<theBoard[r].length; c++)
											fakeBoard[r][c]= theBoard[r][c];
									fakeBoard[pieceR][pieceC]= ChessController.UNOCCUPIED;
									
									if(null!=GamePiece.endangered(getPiece(i,j,theBoard), fakeBoard))
										return null;
								}
							}
						}
						
						boolean defend= true;
						if(revenge!=null) {
							if(attacker.getValue()>token.getValue())
								defend= false;
						}
						if(defend) {
							for(int row= 0; row<theBoard.length; row++) {
								for(int col= 0; col<theBoard[row].length; col++) {
									if(token.validateMove(row, col, theBoard)) {
										MoveData initialMove= new MoveData(pieceR,pieceC,row,col,0);
										
										//Deep copy
										for(int r= 0; r<theBoard.length; r++)
											for(int c= 0; c<theBoard[r].length; c++)
												fakeBoard[r][c]= theBoard[r][c];
										
										fakeBoard[row][col]= fakeBoard[pieceR][pieceC];
										fakeBoard[pieceR][pieceC]= ChessController.UNOCCUPIED;
										
										attack= GamePiece.endangered(getPiece(row,col,fakeBoard), fakeBoard);
										
										if(attack==null) {
											if(theOne==null) 
												theOne= new MoveData(initialMove,token.getValue());
											else if(theOne.getValue()<initialMove.getValue())
												theOne= new MoveData(initialMove,token.getValue());
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return theOne;
	}

	public MoveData makeMove(String lastPlayerMove, char[][] theBoard) {
		String lastMove= lastPlayerMove; //Unused
		
		best= null; //reset best
		secondBest= null; //reset secondBest
		
		if(difficulty==1) { //Easy
			for(int r= 0; r< theBoard.length; r++) {
				for(int c= 0; c< theBoard[r].length; c++) {
					if(theBoard[r][c]>ChessController.UNOCCUPIED && theBoard[r][c]<=ChessController.WHITE) { //Checks to see if you found an enemy piece
						secondBest= GamePiece.endangered(getPiece(r,c,theBoard), theBoard);
						if(secondBest!=null) {
							if(best==null)
								best= secondBest;
							else if(secondBest.getValue()>=best.getValue())
								best= secondBest;
						}
					}
				}
			}
			
			if(best!=null)
				return best;
			else {
				MoveData theMove= giveMeAMove(theBoard,difficulty);
				
				return theMove;
			}
		}
		else if(difficulty==2) {
			//Protect king
			best= defendKing(theBoard);
			
			if(best==null)
				//Smart attack
				best= worthCapturing(theBoard);
			
			if(best==null)
				//Smart defend
				best= defend(theBoard);
			
			if(best==null) 
				//Safe move
				best= giveMeAMove(theBoard, difficulty);
			
			
			return best;
		}
		else if(difficulty==3) {
			//Protect king
			best= defendKing(theBoard);
			
			if(best==null)
				//Smart attack
				best= worthCapturing(theBoard);
			
			if(best==null)
				//Smart defend
				best= defend(theBoard);
			
			if(best==null) 
				//Bait
				best= bait(theBoard);
			
			if(best==null) 
				//Safe move
				best= giveMeAMove(theBoard, difficulty);
			
			
			return best;
		}
		
		return null;
	}
	
	public char promotionPrompt() {
		if(difficulty==1)
			return ChessController.BLACK_QUEEN;
		else if(difficulty==2) {
			if(Math.random()>0.5)
				return ChessController.BLACK_QUEEN;
			else
				return ChessController.BLACK_KNIGHT;
		}
		else
			return ChessController.BLACK_QUEEN; //Unreachable
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
	
	/**
	 * 
	 * @param theBoard is the game board
	 * @return a valid move no matter what
	 */
	private static MoveData giveMeAMove(char[][] theBoard, int level) {
		if(level==1) {
			int row= (int) (Math.random()*8);
			int col= (int) (Math.random()*8);
			
			while(theBoard[row][col]<ChessController.BLACK) {
				row= (int) (Math.random()*8);
				col= (int) (Math.random()*8);
			}
			
			GamePiece mover= getPiece(row,col,theBoard);
			int r;
			int c;
			
			for(r= 0; r<ChessController.ROWS; r++) {
				for(c= 0; c<ChessController.COLS; c++) {
					if(mover.validateMove(r, c, theBoard)) {
						if(getPiece(r,c,theBoard)!=null)
							return new MoveData(row,col,r,c,getPiece(r,c,theBoard).getValue());
						else
							return new MoveData(row,col,r,c,0);
					}
				}
			}
			return giveMeAMove(theBoard,level);
		}
		else if(level>=2) {
			char[][] fakeBoard= new char[theBoard.length][theBoard[0].length];
			
			//Find a black piece
			for(int pieceR= 0; pieceR<theBoard.length; pieceR++) {
				for(int pieceC= 0; pieceC<theBoard[pieceR].length; pieceC++){
					if(theBoard[pieceR][pieceC]>=ChessController.BLACK) {
						GamePiece token= getPiece(pieceR,pieceC,theBoard);
						for(int moveR= 0; moveR<theBoard.length; moveR++) {
							for(int moveC= 0; moveC<theBoard[moveR].length; moveC++) {
								if(token.validateMove(moveR, moveC, theBoard)) {
									//Deep copy
									for(int row= 0; row<theBoard.length; row++)
										for(int col= 0; col<theBoard[row].length; col++)
											fakeBoard[row][col]= theBoard[row][col];
									
									fakeBoard[moveR][moveC]= fakeBoard[pieceR][pieceC];
									fakeBoard[pieceR][pieceC]= ChessController.UNOCCUPIED;
									
									MoveData toMake= GamePiece.endangered(getPiece(moveR,moveC,fakeBoard), fakeBoard);
									
									if(toMake==null)
										return new MoveData(pieceR,pieceC,moveR,moveC,0);
								}
							}
						}
					}
				}
			}
			
			return giveMeAMove(theBoard, 1);
		}
		
		return null; //Unreachable
	}

}


public final class MoveData {
	private int r1, c1, r2, c2, value;
	
	public MoveData() {
		r1= 0;
		c1= 0;
		r2= 0;
		c2= 0;
		value= 0;
	}
	public MoveData(int startingRow, int startingCol, int finishingRow, int finishingCol, int value) {
		r1= startingRow;
		c1= startingCol;
		r2= finishingRow;
		c2= finishingCol;
		this.value= value;
	}
	public MoveData(MoveData from) { //Copy constructor
		r1= from.r1;
		c1= from.c1;
		r2= from.r2;
		c2= from.c2;
		value= from.value;
	}
	public MoveData(MoveData from, int value) {
		this(from);
		this.value= value;
	}
	
	public int getFromRow() {
		return r1;
	}
	public int getFromCol() {
		return c1;
	}
	public int getToRow() {
		return r2;
	}
	public int getToCol() {
		return c2;
	}
	public int getValue() {
		return value;
	}

}

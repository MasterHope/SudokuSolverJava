package satsudoku;

public class SudokuVariable extends Variable{
	private int r;
	private int c;
	private int v;

	public SudokuVariable(String var) {
		super(var);
		getPosition(var);
	}

	private void getPosition(String var) {
		String appo = var.substring(1, var.length() - 1);
		try {
			String[] splitted = appo.split(",");
			r = Integer.parseInt(splitted[0]);
			c = Integer.parseInt(splitted[1]);
			v = Integer.parseInt(splitted[2]);
		}catch(NumberFormatException exp){
			throw new NumberFormatException("Variable of Sudoku is expressed by the string(row,column,value)");
		}
	}

	public int getR() {
		return r;
	}

	public int getC() {
		return c;
	}

	public int getV() {
		return v;
	}

	@Override
	public SudokuVariable clone() throws CloneNotSupportedException {
		return new SudokuVariable(this.getVarRapresentation());
	}

}

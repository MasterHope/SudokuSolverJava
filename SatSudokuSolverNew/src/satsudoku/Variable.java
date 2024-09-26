package satsudoku;

public class Variable implements Cloneable {

	private String varRapresentation;

	public Variable(String var) {
		this.varRapresentation = var;
	}

	public String getVarRapresentation() {
		return varRapresentation;
	}

	public void setVarRapresentation(String var) {
		this.varRapresentation = var;
	}

	@Override
	public String toString() {
		return "Variable [var=" + varRapresentation + "]";
	}

	@Override
	public Variable clone() throws CloneNotSupportedException {
		return new Variable(varRapresentation);
	}

}

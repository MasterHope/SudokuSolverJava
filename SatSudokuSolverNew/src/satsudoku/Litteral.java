package satsudoku;

public class Litteral implements Cloneable{

	private Variable var;
	private boolean isNegated;

	public Litteral(Variable var, boolean isNegated) {
		this.var = var;
		this.isNegated = isNegated;
	}

	public Litteral(Variable var) {
		this.var = var;
		this.isNegated = false;
	}

	public Variable getVar() {
		return var;
	}

	public void setVar(Variable var) {
		this.var = var;
	}

	public boolean isNegated() {
		return isNegated;
	}

	public void setNegated(boolean isNegated) {
		this.isNegated = isNegated;
	}

	@Override
	public Litteral clone() throws CloneNotSupportedException {
		return new Litteral(var.clone(),isNegated);
	}

	@Override
	public String toString() {
		return "Litteral [var=" + var + ", isNegated=" + isNegated + "]";
	}

}

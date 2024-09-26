package satsudoku;

import java.util.ArrayList;
import java.util.List;

public class CNFExpression implements Cloneable {

	private ArrayList<Clause> clauses;

	public CNFExpression() {
		clauses = new ArrayList<>();
	}

	public CNFExpression(ArrayList<Clause> clauses) {
		this.clauses = clauses;
	}

	public CNFExpression concatClauses(Clause... clauses) {
		this.clauses.addAll(List.of(clauses));
		return this;
	}
	
	public CNFExpression concatClauses(ArrayList<Clause> clauses) {
		this.clauses.addAll(clauses);
		return this;
	}

	public ArrayList<Clause> getClauses() {
		return clauses;
	}
	
	@Override
	public CNFExpression clone() throws CloneNotSupportedException {
		ArrayList<Clause> newClauses = new ArrayList<>();
		for(Clause clause : clauses) {
			newClauses.add(clause.clone());
		}
		return new CNFExpression(newClauses);
	}

}

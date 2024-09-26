package satsudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Clause implements Cloneable{

	private ArrayList<Litteral> litterals;

	public Clause() {
		litterals = new ArrayList<>();
	}

	public Clause(ArrayList<Litteral> litterals) {
		this.litterals = litterals;
	}

	public ArrayList<Litteral> getLitterals() {
		return litterals;
	}

	public Clause concatLitterals(Litteral... litterals) {
		this.litterals.addAll(List.of(litterals));
		return this;
	}

	@Override
	public Clause clone() throws CloneNotSupportedException {
		ArrayList<Litteral> newLitterals = new ArrayList<>();
		for(Litteral litt : litterals) {
			newLitterals.add(litt.clone());
		}
		return new Clause(newLitterals);
	}

	@Override
	public String toString() {
		return "Clause [litterals=" + Arrays.toString(litterals.toArray(Litteral[]::new)) + "]";
	}
	

}

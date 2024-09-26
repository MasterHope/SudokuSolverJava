package sudoku_nxn;

import java.util.ArrayList;

public class Cell implements Cloneable {

	private ArrayList<Integer> candidates;
	private int r;
	private int c;
	private int finalNumber;
	private int n;

	public Cell(int n) {
		candidates = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			candidates.add(i + 1);
		}
		finalNumber = 0;
		this.n = n;
	}

	public Cell(int finalNumber, int n) {
		candidates = new ArrayList<>(n);
		this.finalNumber = finalNumber;
		this.n = n;
	}

	private Cell(ArrayList<Integer> candidates, int r, int c, int finalNumber, int n) {
		this.candidates = candidates;
		this.r = r;
		this.c = c;
		this.finalNumber = finalNumber;
		this.n = n;
	}

	public ArrayList<Integer> getCandidates() {
		return candidates;
	}

	public void removeCandidates(Integer... numbers) {
		for (int numb : numbers) {
			if (candidates.contains(numb)) {
				candidates.remove(Integer.valueOf(numb));
			}
		}

	}

	public void addCandidates(Integer... numbers) {
		for (int numb : numbers) {
			candidates.add(numb);
		}

	}

	public int getFinalNumber() {
		return finalNumber;
	}

	public void setFinalNumber(int finalNumber) {
		this.finalNumber = finalNumber;
		this.candidates.removeAll(SudokuUtilities.getAllCandidates(n));
	}

	public void setFinalNumberWithoutRemovingCandidates(int finalNumber) {
		this.finalNumber = finalNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((candidates == null) ? 0 : candidates.hashCode());
		result = prime * result + finalNumber;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (candidates == null) {
			if (other.candidates != null)
				return false;
		} else if (!candidates.equals(other.candidates))
			return false;
		if (finalNumber != other.finalNumber)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cell [candidates=" + candidates + ", r=" + r + ", c=" + c + ", finalNumber=" + finalNumber + "]";
	}

	public void removeCandidatesIf(ArrayList<Integer> cand2) {
		ArrayList<Integer> arrApp = new ArrayList<>(candidates);
		for (int numb : cand2) {
			if (candidates.contains(numb)) {
				arrApp.removeAll(cand2);
				if (!arrApp.isEmpty()) {
					candidates.remove(Integer.valueOf(numb));
				} else {
					arrApp.addAll(cand2);
				}

			}
		}
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	@Override
	public Cell clone() throws CloneNotSupportedException {
		ArrayList<Integer> cands = new ArrayList<>();
		for (Integer integ : candidates) {
			cands.add(Integer.valueOf(integ.intValue()));
		}
		return new Cell(cands, r, c, finalNumber, n);
	}

	public boolean onlyOneCandidateIsPresent() {
		return getCandidates().size() == 1;
	}

	public boolean isNotAlreadyOccupied() {
		return getFinalNumber() == 0;
	}

	public int getN() {
		return n;
	}
}

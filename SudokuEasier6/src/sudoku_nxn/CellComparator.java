package sudoku_nxn;

import java.util.Comparator;

public class CellComparator implements Comparator<Cell> {

	@Override
	public int compare(Cell o1, Cell o2) {
		return o1.getCandidates().size() - o2.getCandidates().size();
	}

}

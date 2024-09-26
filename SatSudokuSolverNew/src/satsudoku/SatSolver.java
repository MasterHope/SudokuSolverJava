package satsudoku;

import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import satsudoku.exception.NotClonableExpressionException;

public class SatSolver {

	private String result;
	private Map<Integer, String> variablesMap;
	private CNFExpression cnfAppo;

	public SatSolver(ArrayList<? extends Variable> vars) {
		variablesMap = IntStream.range(0, vars.size()).boxed()
				.collect(Collectors.toMap(i -> i, i -> vars.get(i).getVarRapresentation()));
	}

	public boolean findAssignment(CNFExpression cnf) {
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < variablesMap.size(); i++) {
			str.append('.');
		}
		return DPLL(cnf, str.toString());
	}

	private boolean DPLL(CNFExpression cnf2, String assignment) {
		if (cnf2.getClauses().size() == 0) {
			result = new String(assignment);
			return true;
		}
		if (isCNFContainingEmptyClause(cnf2)) {
			return false;
		}
		ArrayList<Litteral> litterals = getLitteralsFromUnitClause(cnf2);
		ArrayList<Character> elementsToBeAssigned = new ArrayList<>();
		ArrayList<Variable> variabs = new ArrayList<>();
		StringBuilder stringAssign = new StringBuilder(assignment);
		if (!litterals.isEmpty()) {
			for (Litteral litt : litterals) {
				char elementToBeAssigned = '1';
				if (litt.isNegated()) {
					elementToBeAssigned = '0';
				}
				int pos = getPos(litt);
				if (stringAssign.toString().charAt(pos) != '.') {
					if (stringAssign.toString().charAt(pos) != elementToBeAssigned) {
						return false;
					}
				}
				stringAssign.setCharAt(pos, elementToBeAssigned);
				variabs.add(litt.getVar());
				elementsToBeAssigned.add(elementToBeAssigned);
			}
			return DPLL(reduction(cnf2, variabs, elementsToBeAssigned), stringAssign.toString());
		}
		stringAssign = new StringBuilder(assignment);
		int pos = 0;
		Variable var = chooseCNFVariable(assignment);
		pos = getPos(var);
		stringAssign.setCharAt(pos, '1');
		ArrayList<Variable> wrapVar = wrap(var);
		ArrayList<Character> wrapChar1 = wrap('1');
		ArrayList<Character> wrapChar0 = wrap('0');

		StringBuilder stringAssign2 = new StringBuilder(assignment);
		stringAssign2.setCharAt(pos, '0');
		return DPLL(reduction(cnf2, wrapVar, wrapChar1), stringAssign.toString())
				|| DPLL(reduction(cnf2, wrapVar, wrapChar0), stringAssign2.toString());
	}

	private <T extends Object> ArrayList<T> wrap(T element) {
		ArrayList<T> arr = new ArrayList<>();
		arr.add(element);
		return arr;
	}

	private int getPos(Variable var) {
		int pos = -1;
		Set<Entry<Integer, String>> entrySet = variablesMap.entrySet();
		for (Entry<Integer, String> entry : entrySet) {
			if (entry.getValue().equals(var.getVarRapresentation())) {
				pos = entry.getKey();
				break;
			}
		}
		return pos;
	}

	private int getPos(Litteral litt) {
		return getPos(litt.getVar());
	}

	protected CNFExpression reduction(CNFExpression cnf2, ArrayList<Variable> vars, ArrayList<Character> values) {
		try {
			cnfAppo = cnf2.clone();
		} catch (CloneNotSupportedException e) {
			throw new NotClonableExpressionException(cnfAppo.getClass().getSimpleName() + " must implement Cloneable");
		}
		for (int i = 0; i < vars.size(); i++) {
			Variable var = vars.get(i);
			Character value = values.get(i);
			Iterator<Clause> iter = cnfAppo.getClauses().iterator();
			while (iter.hasNext()) {
				Clause clause = iter.next();
				Iterator<Litteral> iterLitterals = clause.getLitterals().iterator();
				while (iterLitterals.hasNext()) {
					Litteral litt = iterLitterals.next();
					if (variableEqualsRapresentation(var, litt)) {
						iterLitterals.remove();
						if (litt.isNegated() && value == '0') {
							iter.remove();
							break;
						}
						if (!litt.isNegated() && value == '1') {
							iter.remove();
							break;
						}
					}
				}
			}
		}
		return cnfAppo;
	}

	private boolean variableEqualsRapresentation(Variable var, Litteral litt) {
		return litt.getVar().getVarRapresentation().equals(var.getVarRapresentation());
	}

	protected Variable chooseCNFVariable(String assignment) {
		for (int i = 0; i < assignment.length(); i++) {
			if (assignment.charAt(i) == '.') {
				String str = variablesMap.get(i);
				return new Variable(str);
			}
		}
		return null;
	}

	private ArrayList<Litteral> getLitteralsFromUnitClause(CNFExpression cnf2) {
		ArrayList<Litteral> litterals = new ArrayList<>();
		for (Clause clause : cnf2.getClauses()) {
			if (clause.getLitterals().size() == 1) {
				litterals.add(clause.getLitterals().get(0));
			}
		}
		return litterals;
	}

	private boolean isCNFContainingEmptyClause(CNFExpression cnf2) {
		for (Clause clause : cnf2.getClauses()) {
			if (clause.getLitterals().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public String getResult() {
		return result;
	}

	public Map<Integer, String> getVariablesMap() {
		return variablesMap;
	}

}

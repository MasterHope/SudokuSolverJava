package satsudoku;

import java.util.ArrayList;

import java.util.List;

public class SudokuExpressionBuilder {

	private static List<SudokuVariable> variables = getVariables(9);

	public static List<SudokuVariable> getVariables() {
		return variables;
	}

	public static CNFExpression getGeneralSudokuCNFExpression() {
		return getSudokuExpression();
	}

	public static SudokuVariable getVariable(String str) {
		for (SudokuVariable var : variables) {
			if (str.equals(var.getVarRapresentation())) {
				return var;
			}
		}
		return null;
	}

	private static ArrayList<SudokuVariable> getVariables(int n) {
		ArrayList<SudokuVariable> vars = new ArrayList<>();
		for (int i = 0; i <= n - 1; i++) {
			for (int j = 0; j <= n - 1; j++) {
				for (int v = 1; v <= n; v++) {
					SudokuVariable var = new SudokuVariable("(" + i + "," + j + "," + v + ")");
					vars.add(var);
				}
			}
		}
		return vars;
	}

	private static CNFExpression getDefinessCellExpression() {
		CNFExpression exp = new CNFExpression();
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				ArrayList<Litteral> clauseArr = new ArrayList<>();
				for (int n = 1; n <= 9; n++) {
					String var = "(" + i + "," + j + "," + n + ")";
					clauseArr.add(new Litteral(getVariable(var)));
				}
				Clause clause = new Clause(clauseArr);
				exp.concatClauses(clause);
			}

		}
		return exp;
	}

	private static CNFExpression getDefinessRowExpression() {
		CNFExpression exp = new CNFExpression();
		for (int i = 0; i < 9; i++) {
			for (int n = 1; n <= 9; n++) {
				ArrayList<Litteral> clauseArr = new ArrayList<>();
				for (int j = 0; j < 9; j++) {
					String var = "(" + i + "," + j + "," + n + ")";
					clauseArr.add(new Litteral(getVariable(var)));
				}
				Clause clause = new Clause(clauseArr);
				exp.concatClauses(clause);
			}

		}
		return exp;
	}

	private static CNFExpression getDefinessColumnExpression() {
		CNFExpression exp = new CNFExpression();
		for (int j = 0; j < 9; j++) {
			for (int n = 1; n <= 9; n++) {
				ArrayList<Litteral> clauseArr = new ArrayList<>();
				for (int i = 0; i < 9; i++) {
					String var = "(" + i + "," + j + "," + n + ")";
					clauseArr.add(new Litteral(getVariable(var)));
				}
				Clause clause = new Clause(clauseArr);
				exp.concatClauses(clause);
			}

		}
		return exp;
	}

	private static CNFExpression getDefinessBoxExpression() {
		CNFExpression exp = new CNFExpression();
		for (int z = 1; z <= 9; z++) {
			Clause clause = new Clause();
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					for (int r = 0; r < 3; r++) {
						for (int c = 0; c < 3; c++) {
							int r1 = 3*i+r;
							int c1 = 3*j+c;
							String var = "(" + r1 + "," + c1 + "," + z + ")";
							clause.concatLitterals(new Litteral(getVariable(var)));
						}
					}
				}
			}
			exp.concatClauses(clause);
		}
		return exp;
	}

	private static CNFExpression getCellUniqueness() {
		CNFExpression exp = new CNFExpression();
		for(int r=0; r<9; r++) {
			for(int c=0; c < 9; c++) {
				for(int numb = 1; numb <= 9-1; numb++) {
					for(int numb2 = numb+1; numb2 <= 9; numb2++) {
						Clause clause = new Clause();
						String var1 = "(" + r + "," + c + "," + numb + ")";
						String var2 = "(" + r + "," + c + "," + numb2 + ")";
						Litteral litt1 = new Litteral(getVariable(var1),true);
						Litteral litt2 = new Litteral(getVariable(var2),true);
						clause.concatLitterals(litt1,litt2);
						exp.concatClauses(clause);
					}
				}
			}
		}
		return exp;
	}

	private static CNFExpression getUniquenessBoxExpression() {
		CNFExpression exp = new CNFExpression();
		for (int z = 1; z <= 9; z++) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					for (int r = 0; r < 3; r++) {
						for (int c = 0; c < 3; c++) {
							for (int k = c + 1; k < 3; k++) {
								int r1 = 3 * i + r;
								int c1 = 3 * j + c;
								int c2 = 3 * j + k;
								String var1str = "(" + r1 + "," + c1 + "," + z + ")";
								String var2str = "(" + r1 + "," + c2 + "," + z + ")";
								Variable var1 = getVariable(var1str);
								Variable var2 = getVariable(var2str);
								ArrayList<Litteral> arr = new ArrayList<>();
								arr.add(new Litteral(var1, true));
								arr.add(new Litteral(var2, true));
								Clause clause = new Clause(arr);
								exp.concatClauses(clause);
							}
						}
					}
				}
			}
		}
		for (int z = 1; z <= 9; z++) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					for (int r = 0; r < 3; r++) {
						for (int c = 0; c < 3; c++) {
							for (int k = r + 1; k < 3; k++) {
								for (int l = 0; l < 3; l++) {
									int r1 = 3 * i + r;
									int c1 = 3 * j + c;
									int r2 = 3 * i + k;
									int c2 = 3 * j + l;
									String var1str = "(" + r1 + "," + c1 + "," + z + ")";
									String var2str = "(" + r2 + "," + c2 + "," + z + ")";
									Variable var1 = getVariable(var1str);
									Variable var2 = getVariable(var2str);
									ArrayList<Litteral> arr = new ArrayList<>();
									arr.add(new Litteral(var1, true));
									arr.add(new Litteral(var2, true));
									Clause clause = new Clause(arr);
									exp.concatClauses(clause);
								}

							}
						}
					}
				}
			}
		}
		return exp;
	}

	private static CNFExpression getUniquenessRowExpression() {
		CNFExpression exp = new CNFExpression();
		for (int r = 0; r < 9; r++) {
			for (int n = 1; n <= 9; n++) {
				for (int ci = 0; ci < 9 - 1; ci++) {
					for (int cj = ci + 1; cj < 9; cj++) {
						String var1str = "(" + r + "," + ci + "," + n + ")";
						String var2str = "(" + r + "," + cj + "," + n + ")";
						Variable var1 = getVariable(var1str);
						Variable var2 = getVariable(var2str);
						ArrayList<Litteral> arr = new ArrayList<>();
						arr.add(new Litteral(var1, true));
						arr.add(new Litteral(var2, true));
						Clause clause = new Clause(arr);
						exp.concatClauses(clause);

					}
				}
			}
		}
		return exp;
	}

	private static CNFExpression getUniquenessColumnExpression() {
		CNFExpression exp = new CNFExpression();
		for (int c = 0; c < 9; c++) {
			for (int n = 1; n <= 9; n++) {
				for (int ri = 0; ri < 9 - 1; ri++) {
					for (int rj = ri + 1; rj < 9; rj++) {
						String var1str = "(" + ri + "," + c + "," + n + ")";
						String var2str = "(" + rj + "," + c + "," + n + ")";
						Variable var1 = getVariable(var1str);
						Variable var2 = getVariable(var2str);
						ArrayList<Litteral> arr = new ArrayList<>();
						arr.add(new Litteral(var1, true));
						arr.add(new Litteral(var2, true));
						Clause clause = new Clause(arr);
						exp.concatClauses(clause);
					}
				}
			}
		}
		return exp;
	}

	private static CNFExpression getSudokuExpression() {
		return new CNFExpression().concatClauses(columnUniquenessExpression())
				.concatClauses(rowUniquenessExpression())
				.concatClauses(getUniquenessBoxExpression().getClauses())
				.concatClauses(defineCellExpression())
				.concatClauses(getDefinessRowExpression().getClauses())
				.concatClauses(getDefinessColumnExpression().getClauses())
				.concatClauses(getDefinessBoxExpression().getClauses())
				.concatClauses(getCellUniqueness().getClauses());
				
	}

	private static ArrayList<Clause> rowUniquenessExpression() {
		return getUniquenessRowExpression().getClauses();
	}

	private static ArrayList<Clause> columnUniquenessExpression() {
		return getUniquenessColumnExpression().getClauses();
	}

	private static ArrayList<Clause> defineCellExpression() {
		return getDefinessCellExpression().getClauses();
	}
}

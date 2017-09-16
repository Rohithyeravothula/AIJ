import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Validator {

	private static final String INPUT_FILE_TEXT = "output.txt";
	public Integer[][] solution;

	public void readInput() {
		Stream<String> stream = null;
		try {
			String pathToFile = Validator.getWorkingDirPath() + File.separator + INPUT_FILE_TEXT;
			//System.out.println("path to file " + pathToFile);
			List<String> inputLines = new ArrayList<>();
			stream = Files.lines(Paths.get(pathToFile));
			inputLines = stream.collect(Collectors.toList());
			processData(inputLines);

		} catch (IOException e) {
			System.out.println("IO exception");
			e.printStackTrace();
		} finally {
			stream.close();
		}
	}

	private void printValues() {
		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution.length; j++) {
				System.out.print(solution[i][j]);
			}
			System.out.println();
		}
	}

	private void processData(List<String> inputLines) {

		if (inputLines.size() > 1) {
			inputLines.remove(0);
			solution = new Integer[inputLines.size()][inputLines.size()];
		} else {
			return;
		}

		for (int i = 0; i < inputLines.size(); i++) {
			String val = inputLines.get(i);
			for (int j = 0; j < inputLines.size(); j++) {
				solution[i][j] = Integer.parseInt(String.valueOf(val.charAt(j)));
			}
		}
		//printValues();
	}

	List<Integer> convert(String x) {
		String[] val = x.split(" ");
		List<Integer> machineList = new ArrayList<>();
		for (int i = 0; i < val.length; i++) {
			machineList.add(Integer.parseInt(val[i]));
		}
		return machineList;
	}

	private static String getWorkingDirPath() {
		Path currentRelativePath = Paths.get("");
		String absPath = currentRelativePath.toAbsolutePath().toString();
		return absPath;
	}

	public void validate() {

		for (int i = 0; i < solution.length; i++) {
			for (int j = 0; j < solution.length; j++) {

				if (solution[i][j] == 1) {
					if (checkrow(i, j)) {
						System.out.println("FAIL");
						return;
					}

					else if (checkcolumn(i, j)) {
						System.out.println("FAIL");
						return;
					}

					else if (checkSlope(i, j)) {
						System.out.println("FAIL");
						return;
					}
				}
			}
		}
		System.out.println("PASSED");
	}

	private boolean checkSlope(int i, int j) {

		if (checktopRight(i, j)) {
			return true;
		} else if (checkTopLeft(i, j)) {
			return true;
		} else if (checkBottomRight(i, j)) {
			return true;
		} else if (checkBottomLeft(i, j)) {
			return true;
		}
		return false;
	}

	private boolean checkBottomLeft(int i, int j) {
		boolean isTreeAvail = false;
		int row = i, column = j;
		while (row != solution.length - 1 && column != 0) {
			row = row + 1;
			column = column - 1;
			if (solution[row][column] == 2) {
				isTreeAvail = true;
			} else if (!isTreeAvail && solution[row][column] == 1) {
				return true;
			}
		}

		return false;
	}

	private boolean checkBottomRight(int i, int j) {
		boolean isTreeAvail = false;
		int row = i, column = j;

		while (row != solution.length - 1 && column != solution.length - 1) {
			row = row + 1;
			column = column + 1;
			if (solution[row][column] == 2) {
				isTreeAvail = true;
			} else if (!isTreeAvail && solution[row][column] == 1) {
				return true;
			}
		}

		return false;
	}

	private boolean checkTopLeft(int i, int j) {
		boolean isTreeAvail = false;
		int row = i, column = j;

		while (row != 0 && column != 0) {
			row = row - 1;
			column = column - 1;
			if (solution[row][column] == 2) {
				isTreeAvail = true;
			} else if (!isTreeAvail && solution[row][column] == 1) {
				return true;
			}
		}

		return false;
	}

	private boolean checktopRight(int i, int j) {

		boolean isTreeAvail = false;
		int row = i, column = j;

		while (row != 0 && column != solution.length - 1) {

			row = row - 1;
			column = column + 1;

			if (solution[row][column] == 2) {
				isTreeAvail = true;
			} else if (!isTreeAvail && solution[row][column] == 1) {
				return true;
			}

		}

		return false;
	}

	private boolean checkcolumn(int i, int j) {
		boolean isTreeAvailable = false;
		for (int k = i + 1; k < solution.length; k++) {
			if (solution[k][j] == 2) {
				isTreeAvailable = true;
			} else if (!isTreeAvailable && solution[k][j] == 1) {
				return true;
			}
		}
		return false;
	}

	private boolean checkrow(int i, int j) {

		boolean isTreeAvailable = false;
		for (int k = j + 1; k < solution.length; k++) {
			if (solution[i][k] == 2) {
				isTreeAvailable = true;
			} else if (!isTreeAvailable && solution[i][k] == 1) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {

		Validator validator = new Validator();
		validator.readInput();
		validator.validate();
	}
}

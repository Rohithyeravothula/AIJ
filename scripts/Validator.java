//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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

	public Validator() {
	}

	public void readInput() {
		Stream var1 = null;

		try {
			String var2 = getWorkingDirPath() + File.separator + "output.txt";
			new ArrayList();
			var1 = Files.lines(Paths.get(var2));
			List var3 = (List)var1.collect(Collectors.toList());
			System.out.println(var3.size());
			this.processData(var3);
		} catch (IOException var7) {
			System.out.println("IO exception");
			var7.printStackTrace();
		} finally {
			var1.close();
		}

	}

	private void printValues() {
		for(int var1 = 0; var1 < this.solution.length; ++var1) {
			for(int var2 = 0; var2 < this.solution.length; ++var2) {
				System.out.print(this.solution[var1][var2]);
			}

			System.out.println();
		}

	}

	private void processData(List<String> var1) {
		if (var1.size() > 1) {
			var1.remove(0);
			this.solution = new Integer[var1.size()][var1.size()];

			for(int var2 = 0; var2 < var1.size(); ++var2) {
				String var3 = (String)var1.get(var2);

				for(int var4 = 0; var4 < var1.size(); ++var4) {
					this.solution[var2][var4] = Integer.parseInt(String.valueOf(var3.charAt(var4)));
				}
			}

		}
	}

	List<Integer> convert(String var1) {
		String[] var2 = var1.split(" ");
		ArrayList var3 = new ArrayList();

		for(int var4 = 0; var4 < var2.length; ++var4) {
			var3.add(Integer.parseInt(var2[var4]));
		}

		return var3;
	}

	private static String getWorkingDirPath() {
		Path var0 = Paths.get("");
		String var1 = var0.toAbsolutePath().toString();
		return var1;
	}

	public void validate() {
		if(this.solution != null){
			for(int var1 = 0; var1 < this.solution.length; ++var1) {
				for(int var2 = 0; var2 < this.solution.length; ++var2) {
					if (this.solution[var1][var2].intValue() == 1) {
						if (this.checkrow(var1, var2)) {
							System.out.println("FAIL");
							return;
						}

						if (this.checkcolumn(var1, var2)) {
							System.out.println("FAIL");
							return;
						}

						if (this.checkSlope(var1, var2)) {
							System.out.println("FAIL");
							return;
						}
					}
				}
			}
		}
		System.out.println("PASSED");
	}

	private boolean checkSlope(int var1, int var2) {
		if (this.checktopRight(var1, var2)) {
			return true;
		} else if (this.checkTopLeft(var1, var2)) {
			return true;
		} else if (this.checkBottomRight(var1, var2)) {
			return true;
		} else {
			return this.checkBottomLeft(var1, var2);
		}
	}

	private boolean checkBottomLeft(int var1, int var2) {
		boolean var3 = false;
		int var4 = var1;
		int var5 = var2;

		while(var4 != this.solution.length - 1 && var5 != 0) {
			++var4;
			--var5;
			if (this.solution[var4][var5].intValue() == 2) {
				var3 = true;
			} else if (!var3 && this.solution[var4][var5].intValue() == 1) {
				return true;
			}
		}

		return false;
	}

	private boolean checkBottomRight(int var1, int var2) {
		boolean var3 = false;
		int var4 = var1;
		int var5 = var2;

		while(var4 != this.solution.length - 1 && var5 != this.solution.length - 1) {
			++var4;
			++var5;
			if (this.solution[var4][var5].intValue() == 2) {
				var3 = true;
			} else if (!var3 && this.solution[var4][var5].intValue() == 1) {
				return true;
			}
		}

		return false;
	}

	private boolean checkTopLeft(int var1, int var2) {
		boolean var3 = false;
		int var4 = var1;
		int var5 = var2;

		while(var4 != 0 && var5 != 0) {
			--var4;
			--var5;
			if (this.solution[var4][var5].intValue() == 2) {
				var3 = true;
			} else if (!var3 && this.solution[var4][var5].intValue() == 1) {
				return true;
			}
		}

		return false;
	}

	private boolean checktopRight(int var1, int var2) {
		boolean var3 = false;
		int var4 = var1;
		int var5 = var2;

		while(var4 != 0 && var5 != this.solution.length - 1) {
			--var4;
			++var5;
			if (this.solution[var4][var5].intValue() == 2) {
				var3 = true;
			} else if (!var3 && this.solution[var4][var5].intValue() == 1) {
				return true;
			}
		}

		return false;
	}

	private boolean checkcolumn(int var1, int var2) {
		boolean var3 = false;

		for(int var4 = var1 + 1; var4 < this.solution.length; ++var4) {
			if (this.solution[var4][var2].intValue() == 2) {
				var3 = true;
			} else if (!var3 && this.solution[var4][var2].intValue() == 1) {
				return true;
			}
		}

		return false;
	}

	private boolean checkrow(int var1, int var2) {
		boolean var3 = false;

		for(int var4 = var2 + 1; var4 < this.solution.length; ++var4) {
			if (this.solution[var1][var4].intValue() == 2) {
				var3 = true;
			} else if (!var3 && this.solution[var1][var4].intValue() == 1) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] var0) {
		Validator var1 = new Validator();
		var1.readInput();
		var1.validate();
	}
}

import java.io.File;

public class Main {
	public static void main(String[] args) {
		if (args.length == 3) {
			File file = new File(args[2]);
			if (args[0].equals("--name")) {
				FileSearcher fileSearcher = new FileSearcher();
				fileSearcher.findDirectory(file, args[1]);
				return;
			} else if (args[0].equals("--data")) {
				try {
					StringSearcher stringSearcher = new StringSearcher(args[1]);
					stringSearcher.findString(file);
					return;
				} catch (ArrayIndexOutOfBoundsException ex) {
					System.out.println("Your data string " + args[1] + " contains non ASCII symbols. Execution stopped.");
					return;
				}
			}
		} else if (args.length == 1 && args[0].equals("-h")) {
			printHelp();
			return;
		}
		printHelp();
	}

	private static void printHelp() {
		System.out.println("This is program for recursive search of file in directory" +
				" or for searching substring in files.\n" +
				"Program arguments should be either\n" +
				"--name <fileName> <folder> \n" +
				"or\n" +
				"--data \"<text>\" <folder>|<file>");
	}
}

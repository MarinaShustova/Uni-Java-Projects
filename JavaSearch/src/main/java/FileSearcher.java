import java.io.File;

class FileSearcher {
	void findDirectory(File parentDirectory, String stringToFind) {
		File[] files = parentDirectory.listFiles();
		assert files != null;
		for (File file : files) {
			if (file.isFile() && file.getName().contains(stringToFind)) {
				System.out.println(file.getPath());
				continue;
			}
			if (file.isDirectory()) {
				findDirectory(file, stringToFind);
			}
		}
	}
}

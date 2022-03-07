import java.io.*;

class StringSearcher {
	private static short[] shifts;
	private static String data;
	private static long filepos;
	private static boolean found;
	private static String filePath;
	private final int BUFFER_SIZE = 1024 * 5;

	StringSearcher(String string) {
		shifts = new short[256];
		data = string;
		fillShifts();
	}

	private void fillShifts() throws ArrayIndexOutOfBoundsException {
		short size = (short) data.length();
		for (short i = 0; i < 256; ++i) {
			shifts[i] = size;
		}
		char[] dataBytes = data.toCharArray();
		short lastIndex = (short) (size - 1);
		for (short i = 0; i < lastIndex; ++i) {
			shifts[(short) dataBytes[i]] = (short) (lastIndex - i);
		}
	}

	void findString(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			assert files != null;
			for (File fileInDir : files) {
				if (fileInDir.isFile()) {
					findInFile(fileInDir);
				}
				if (fileInDir.isDirectory()) {
					findString(fileInDir);
				}
			}
		} else if (file.isFile()) {
			findInFile(file);
		} else {
			System.out.println("No file or directory with such name found. Please try again.");
		}
	}

	private void findInFile(File file) {
		char[] buffer = new char[BUFFER_SIZE];
		filepos = 0;
		found = false;
		filePath = file.getAbsolutePath();
		try {
			FileReader in = new FileReader(file);
			int readBytes = in.read(buffer, 0, buffer.length);
			if (readBytes != -1) {
				BoyerMoorHorspool(String.valueOf(buffer));
			}
			filepos += readBytes - (data.length() - 1);
			while (readBytes == buffer.length || readBytes == buffer.length - (data.length() - 1)) {
				copySubstring(buffer);
				readBytes = in.read(buffer, data.length() - 1, buffer.length - (data.length() - 1));
				if (readBytes != -1) {
					BoyerMoorHorspool(String.valueOf(buffer));
					filepos += readBytes;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException ex) {
			System.out.println("Current file " + file.getAbsolutePath() + " contains non ASCII symbols. " +
					"Search in it will be stopped.");
		}
	}

	private void copySubstring(char[] source) {
		short substringSize = (short) (data.length() - 1);
		int sourceLen = source.length;
		if (substringSize >= 0)
			System.arraycopy(source, sourceLen - substringSize, source, 0, substringSize);
	}

	private void BoyerMoorHorspool(String chunk) throws ArrayIndexOutOfBoundsException {
		int sourceLen = chunk.length();
		short dataLen = (short) data.length();
		short i = (short) (data.length() - 1);
		short j;
		short k;
		int offset = 0;
		while (i <= sourceLen - 1) {
			j = (short) (dataLen - 1);
			k = i;
			while (j >= 0 && chunk.charAt(k) == data.charAt(j)) {
				k--;
				j--;
				if (j == -1) {
					if (!found) {
						System.out.println(filePath + ":");
						found = true;
					}
					System.out.println(filepos + offset + " : " + data);
				}
			}
			offset += shifts[chunk.charAt(i)];
			i += shifts[chunk.charAt(i)];
		}

	}
}

package caesar;

import com.sun.deploy.util.StringUtils;
import enums.CypherOptions;

import java.io.*;
import java.util.ArrayList;

public class Caesar {
	private ArrayList<String> alphabetLow;
	private ArrayList<String> alphabetUp;

	public Caesar(CypherOptions option, int key, String fileName) {
		makeAlphabet();
		if (option == CypherOptions.ENCRYPT) {
			CaesarEncryptor encryptor = new CaesarEncryptor(key, alphabetLow, alphabetUp, getMessage(fileName));
		} else if (option == CypherOptions.DECRYPT) {
			CaesarDecryptor decryptor = new CaesarDecryptor(key, alphabetLow, alphabetUp, getMessage(fileName));
		}
	}

	private String getMessage(String filename) {
		File file = new File(filename);
		BufferedReader br;
		StringBuilder res = new StringBuilder();
		char[] buf = new char[64];
		try {
			br = new BufferedReader(new FileReader(file));
			while (br.read(buf) != -1) {
				res.append(String.valueOf(buf));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res.toString();
	}

	private void makeAlphabet() {
		alphabetLow = new ArrayList<>();
		alphabetUp = new ArrayList<>();
		for (char c = 'a'; c <= 'z'; ++c) {
			alphabetLow.add(Character.toString(c));
		}
		for (char c = 'A'; c <= 'Z'; ++c) {
			alphabetUp.add(Character.toString(c));
		}
	}
}

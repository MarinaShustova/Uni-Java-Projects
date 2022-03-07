package caesar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class CaesarEncryptor {

	private ArrayList<String> alphabetLow;
	private ArrayList<String> alphabetUp;

	CaesarEncryptor(int key, ArrayList<String> alphabetLow, ArrayList<String> alphabetUp, String message) {
		this.alphabetLow = alphabetLow;
		this.alphabetUp = alphabetUp;
		System.out.println(encrypt(message, key));
		File file = new File("caesarEncrypted.txt");
		try (FileWriter fr = new FileWriter(file)){
			fr.write(encrypt(message, key));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String encrypt(String message, int key) {
		String eMessage = "";
		for (int i = 0; i < message.length(); ++i) {
			eMessage = eMessage.concat(Character.toString(eChar(message.charAt(i), key)));
		}
		return eMessage;
	}

	private char eChar(char aChar, int key) {
		char c = aChar;
		int size = alphabetLow.size();
		if (aChar >= 'a' && aChar <= 'z') {
			c = alphabetLow.get((alphabetLow.indexOf(Character.toString(aChar)) + key) % size).charAt(0);
		} else if (aChar >= 'A' && aChar <= 'Z') {
			c = alphabetUp.get((alphabetUp.indexOf(Character.toString(aChar)) + key) % size).charAt(0);
		}
		return c;
	}
}

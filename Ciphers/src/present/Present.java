package present;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import enums.CypherOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Present {
	public Present(CypherOptions option, String keyFile, String filename) {
		ByteArray key = getKey(keyFile);
		KeyGenerator keyGenerator = new KeyGenerator();
		if (key != null) {
			if (option == CypherOptions.ENCRYPT) {
				PresentEncryptor presentEncryptor = new PresentEncryptor(filename, key.length());
				presentEncryptor.roundKeys = keyGenerator.getKeysForKey(key);
				presentEncryptor.writeEncrypted();
			} else if (option == CypherOptions.DECRYPT) {
				PresentDecryptor presentDecryptor = new PresentDecryptor(filename);
				presentDecryptor.roundKeys = keyGenerator.getKeysForKey(key);
				presentDecryptor.writeDecrypted();
			}
		}
	}

	private ByteArray getKey(String filename) {
		File file = new File(filename);
		BufferedReader br;
		ByteArray bytes = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String key = br.readLine();
			bytes = new ByteArray(key.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
}

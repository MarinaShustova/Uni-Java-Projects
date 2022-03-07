package present;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

class PresentEncryptor {
	ArrayList<ByteArray> roundKeys;
	private ByteArray hash;
	private File source;
	private boolean makeHash;
	final int[] SBOX = {
			0x0C, 0x05, 0x06, 0x0B, 0x09, 0x00, 0x0A, 0x0D, 0x03, 0x0E, 0x0F, 0x08, 0x04, 0x07, 0x01, 0x02
	};
	private final int[] PERMUTATION = {
			0, 16, 32, 48, 1, 17, 33, 49, 2, 18, 34, 50, 3, 19, 35, 51,
			4, 20, 36, 52, 5, 21, 37, 53, 6, 22, 38, 54, 7, 23, 39, 55,
			8, 24, 40, 56, 9, 25, 41, 57, 10, 26, 42, 58, 11, 27, 43, 59,
			12, 28, 44, 60, 13, 29, 45, 61, 14, 30, 46, 62, 15, 31, 47, 63
	};

	PresentEncryptor(String filename, int keyLen) {
		source = new File(filename);
		makeHash = false;
		if (keyLen == 16) {
			makeHash = true;
			hash = new ByteArray(new byte[]{1,0,1,1,1,1,0,1,1,1,
											1,1,0,1,1,1});
		}
	}

	public void writeEncrypted() {
		ByteArray encrypted = encrypt(roundKeys);
		File file = new File("presentEncrypted.txt");
		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			fileOutputStream.write(toByteArray(encrypted));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (makeHash){
			File hashfile = new File("presentHash.txt");
			try (FileOutputStream fileOutputStream = new FileOutputStream(hashfile)) {
				fileOutputStream.write(toByteArray(hash));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private byte[] toByteArray(ByteArray source) {
		byte[] res = new byte[source.length()];
		for (int i = 0; i < source.length(); ++i) {
			res[i] = (byte) source.get(i);
		}
		return res;
	}

	public ByteArray encrypt(ArrayList<ByteArray> rk) {
		ByteArray result = null;
		try {
			byte[] fileContent = Files.readAllBytes(source.toPath());
			int diff = fileContent.length % 8;
			if (diff != 0) {
				result = new ByteArray(fileContent.length + 8 - diff);
			} else {
				result = new ByteArray(fileContent.length);
			}
			ByteArray content = new ByteArray(fileContent);
			int size = fileContent.length;
			int pos = 0;
			while (pos < size) {
				ByteArray current;
				if (size - pos >= 8) {
					current = new ByteArray(8);
					current.copyBytes(content, pos, 0, 8);
					result.copyBytes(encrypt(current, rk), 0, pos, 8);
					if (makeHash) {
						computeHash(current);
					}
				} else {
					current = new ByteArray(8);
					current.copyBytes(content, pos, 0, size - pos);
					result.copyBytes(encrypt(current, rk), 0, pos, 8);
					if (makeHash) {
						computeHash(current);
					}
				}
				pos += 8;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private ByteArray encrypt(ByteArray block, ArrayList<ByteArray> rk) {
		ByteArray encrypted = block.clone();
		for (int i = 0; i < 31; ++i) {
			encrypted = encrypted.xor(rk.get(i), 0, 8);
			encrypted = subBytes(encrypted);
			encrypted = permute(encrypted);
		}
		encrypted.xor(rk.get(31), 0, 8);
		return encrypted;
	}

	private ByteArray permute(ByteArray state) {
		ByteArray newState = state.clone();

		for (int i = 0; i < Long.SIZE; i++) {
			newState.setBit(i, state.getBit(PERMUTATION[i]));
		}

		return newState;
	}

	private ByteArray subBytes(ByteArray state) {
		int value, msbNibble, lsbNibble;

		for (int i = 0; i < 8; i++) {
			value = state.get(i);
			lsbNibble = SBOX[value & 0x0F];
			msbNibble = SBOX[(value & 0xF0) >> 4] << 4;
			state.set(i, msbNibble | lsbNibble);
		}

		return state;
	}

	private void computeHash(ByteArray dataPiece) {
		ByteArray key = new ByteArray(16);
		key.copyBytes(hash, 0, 0, 8);
		key.copyBytes(dataPiece, 0, 8, 8);
		KeyGenerator keyGenerator = new KeyGenerator();
		ArrayList<ByteArray> keys = keyGenerator.getKeysForKey(key);
		ByteArray encrypted = encrypt(hash, keys);
		hash = encrypted.xor(hash, 0, 8);
	}
}

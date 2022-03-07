package present;

import java.util.ArrayList;

public class KeyGenerator {
	public final int[] SBOX = {
			0x0C, 0x05, 0x06, 0x0B, 0x09, 0x00, 0x0A, 0x0D, 0x03, 0x0E, 0x0F, 0x08, 0x04, 0x07, 0x01, 0x02
	};

	public ArrayList<ByteArray> getKeysForKey(ByteArray key){
		return generateKeys(key);
	}

	private ArrayList<ByteArray> generateKeys(ByteArray key) {
		ArrayList<ByteArray> keys = new ArrayList<>();
		ByteArray tempKey;
		if (key.length() == 10) {
			int v1, v2;
			for (int i = 1; i <= 32; ++i) {
				ByteArray roundkey = new ByteArray(8);
				roundkey.copyBytes(key, 0, 0, 8);
				keys.add(roundkey);
				tempKey = new ByteArray(10);
				for (int j = 0; j < 10; ++j) {
					v1 = (key.get((j + 7) % 10) & 0x7) << 5;
					v2 = (key.get((j + 8) % 10) & 0xF8) >> 3;
					tempKey.set(j, v1 | v2);
				}
				for (int j = 0; j < key.length(); ++j) {
					key.set(j, tempKey.get(j));
				}
				v1 = key.get(0);
				v1 = (SBOX[(v1 >> 4) & 0xF] << 4) | (v1 & 0xF);
				key.set(0, v1);
				v1 = key.get(7);
				v1 ^= (i >> 1) & 0xF;
				key.set(7, v1);

				v2 = key.get(8);
				v2 ^= (i & 1) << 7;
				key.set(8, v2);
			}
		} else if (key.length() == 16) {
			long[] k = key.readLongs(); // k.length = 2
			long[] temp = new long[2];
			long value;
			long mask60 = 0x0FFFFFFFFFFFFFFFL;
			long mask56 = 0xF0FFFFFFFFFFFFFFL;
			for (int i = 1; i <= 32; ++i) {
				ByteArray roundkey = new ByteArray(8);
				roundkey.writeLong(0, k[0]);
				keys.add(roundkey);
				temp[0] = (k[0] << 61) | (k[1] >>> 3);
				temp[1] = (k[1] << 61) | (k[0] >>> 3);
				k[0] = temp[0];
				k[1] = temp[1];
				value = (k[0] >>> 60) & 0xF;
				value = SBOX[(int) value];
				k[0] &= mask60;
				k[0] |= value << 60;
				value = (k[0] >>> 56) & 0xF;
				value = SBOX[(int) value];
				k[0] &= mask56;
				k[0] |= value << 56;
				value = (long) i;
				k[0] ^= (value & 0x1c) >>> 2;
				k[1] ^= (value & 0x3) << 62;
			}
		}
		return keys;
	}
}

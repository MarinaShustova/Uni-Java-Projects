package present;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

public class ByteArray implements Externalizable, Cloneable {

	short[] array = new short[0];

	public ByteArray() {

	}

	ByteArray(int length) {
		this.array = new short[length];
	}


	ByteArray(byte[] input) {
		array = new short[input.length];

		for (int i = 0; i < input.length; i++) {
			array[i] = input[i];
		}
	}

	public ByteArray clone() {
		ByteArray copy = new ByteArray(length());

		for (int i = 0; i < array.length; i++) {
			copy.set(i, get(i));
		}

		return copy;
	}

	void copyBytes(ByteArray source, int sourceFrom, int destinationIndex, int numBytes) {
		if (source.length() < sourceFrom + numBytes) {
			throw new ArrayIndexOutOfBoundsException();
		} else if (length() < destinationIndex + numBytes) {
			throw new ArrayIndexOutOfBoundsException();
		} else if (sourceFrom < 0) {
			throw new ArrayIndexOutOfBoundsException();
		} else if (destinationIndex < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}

		if (numBytes >= 0) System.arraycopy(source.array, sourceFrom, array, destinationIndex, numBytes);
	}

	private boolean equals(ByteArray other) {
		if (array.length != other.array.length) {
			return false;
		}

		return Arrays.equals(array, other.array);
	}

	public boolean equals(Object object) {
		try {
			ByteArray other = (ByteArray) object;

			if (other == null) {
				return false;
			} else {
				return equals(other);
			}
		} catch (Exception e) {
			return false;
		}
	}

	public int hashCode() {
		assert false : "hashCode not implemented";
		return -1;
	}

	short get(int position) {
		return array[position];
	}


	boolean getBit(int position) {
		int bitIndex = position % 8;
		int mask = getByteMaskWithOneActiveBit(bitIndex);
		return (array[position / 8] & mask) != 0;
	}

	int length() {
		return array.length;
	}

	public ByteArray or(ByteArray other, int from, int to) {
		for (int i = from; i < to; i++) {
			array[i] |= other.array[i];
		}

		return this;
	}

	private long readLong(int position) {
		long word = 0;

		for (int i = 0; i < 8; i++) {
			word |= ((long) (array[position + 7 - i] & 0xFF)) << ((i) * 8);
		}

		return word;
	}

	long[] readLongs() {
		return readLongs(0);
	}

	private long[] readLongs(int position) {
		int numToRead = (length() - position) / 8;
		return readLongs(position, numToRead);
	}

	private long[] readLongs(int position, int numToRead) {
		long[] results = new long[numToRead];
		long word = 0;

		for (int i = 0; i < numToRead; i++) {
			word = readLong(position + i * 8);
			results[i] = word;
		}

		return results;
	}


	void set(int position, byte value) {
		array[position] = (byte) (value & 0xFF);
	}

	void set(int position, int value) {
		array[position] = (byte) (value & 0xFF);
	}

	void setBit(int position, boolean value) {
		int mask = 0;
		int bitIndex = position % 8;

		if (value) {
			mask = getByteMaskWithOneActiveBit(bitIndex);
			array[position / 8] |= mask;
		} else {
			mask = getByteMaskWithOneNonActiveBit(bitIndex);
			array[position / 8] &= mask;
		}
	}


	void writeLong(int position, long word) {
		for (int j = 7; j >= 0; j--) {
			array[position + j] = (byte) (word & 0xff);
			word >>= 8;
		}
	}

	ByteArray xor(ByteArray other, int from, int to) {
		for (int i = from; i < to; i++) {
			array[i] ^= other.array[i];
		}

		return this;
	}

	private int getByteMaskWithOneActiveBit(int bitIndex) {
		return 1 << (7 - bitIndex);
	}

	private int getByteMaskWithOneNonActiveBit(int bitIndex) {
		return 0xff ^ getByteMaskWithOneActiveBit(bitIndex);
	}


	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		final int length = input.readInt();
		array = new short[length];

		for (int i = 0; i < length; i++) {
			array[i] = input.readByte();
		}
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		if (array == null) {
			output.writeInt(0);
		} else {
			output.writeInt(array.length);

			for (short b : array) {
				output.writeShort(b);
			}
		}
	}

}

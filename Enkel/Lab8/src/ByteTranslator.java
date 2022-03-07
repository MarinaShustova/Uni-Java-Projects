import java.util.Arrays;

class ByteTranslator {
	void print1sInLong(long num){
		int count = 0;
		for (int i = 0; i < 64; ++i) {
			if (((num >>> i) & 1) == 1) {
				++count;
			}
		}
		System.out.println(count);
	}

	byte[] toByteArray(long[] arr){
		byte[] bytes = new byte[arr.length*8];
		int size = arr.length;
		for (int i = 0; i < size; ++i){
			bytes[i*8] = (byte) ((arr[i] >> 56) & 0xFF);
			bytes[1+i*8] = (byte) ((arr[i] >> 48) & 0xFF);
			bytes[2+i*8] = (byte) ((arr[i] >> 40) & 0xFF);
			bytes[3+i*8] = (byte) ((arr[i] >> 32) & 0xFF);
			bytes[4+i*8] = (byte) ((arr[i] >> 24) & 0xFF);
			bytes[5+i*8] = (byte) ((arr[i] >> 16) & 0xFF);
			bytes[6+i*8] = (byte) ((arr[i] >>  8) & 0xFF);
			bytes[7+i*8] = (byte) (arr[i] & 0xFF);
//			bytes[i*8] = (byte) ((arr[i] >> 56) );
//			bytes[1+i*8] = (byte) ((arr[i] >> 48) );
//			bytes[2+i*8] = (byte) ((arr[i] >> 40));
//			bytes[3+i*8] = (byte) ((arr[i] >> 32) );
//			bytes[4+i*8] = (byte) ((arr[i] >> 24) );
//			bytes[5+i*8] = (byte) ((arr[i] >> 16) );
//			bytes[6+i*8] = (byte) ((arr[i] >>  8) );
//			bytes[7+i*8] = (byte) (arr[i] );
		}
		System.out.println("Byte array is: " + Arrays.toString(bytes));
		return bytes;
	}

	long[] toLongArray(byte[] arr){
		long[] longs = new long[arr.length/8];
		int size = arr.length;
		for (int i = 0; i < size; i++) {
			long current = longs[i/8];
			current =  ((current << 8) + (arr[i] & 0xff));
			longs[i/8] = current;
		}
		System.out.println(Arrays.toString(longs));
		return longs;
	}

	boolean assertEquals(long[] params, long[] computed){
		return Arrays.equals(params, computed);
	}
}

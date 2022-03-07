public class Main {
	public static void main(String[] args) {
		ByteTranslator b = new ByteTranslator();
		if (args.length > 0) {
			if (args[0].equals("-b")) {
				b.print1sInLong(Integer.parseInt(args[1]));
				return;
			} else if (args[0].equals("-a")) {
				int size = args.length -1;
				long[] array = new long[size];
				for (int i = 1; i < size+1; ++i) {
					array[i-1] = Long.parseLong(args[i]);
				}
				System.out.println(b.assertEquals(array, b.toLongArray(b.toByteArray(array))));
				return;
			} else {
				System.out.println("Unknown key");
				return;
			}
		}
		System.out.println("You didn't specify any parameters: -b|-a long_value1[,...]");
	}
}

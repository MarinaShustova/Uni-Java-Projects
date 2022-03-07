public class Main {
	public static void main(String[] args) {
		System.out.println(new Main().getCpuInfo());
	}
	static {
		System.loadLibrary("CpuInfo");
	}
	private native String getCpuInfo();
}

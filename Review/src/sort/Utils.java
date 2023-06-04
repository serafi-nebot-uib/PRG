public class Utils {
	public static int randomInt(int min, int max) {
		return (int) (Math.random() * (max - min + 1)) + min;
	}

	public static int[] generateRandomArray(int size, int min, int max) {
		int[] arr = new int[size];
		for (int i = 0; i < arr.length; i++)
			arr[i] = randomInt(min, max);
		return arr;
	}

	public static void dumpArray(int[] arr) {
		for (int i = 0; i < arr.length; i++)
			System.out.printf("%d ", arr[i]);
		System.out.println();
	}
}

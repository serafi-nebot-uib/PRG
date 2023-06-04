public class SortBubble {
	public static void main(String[] args) {
		int[] src = Utils.generateRandomArray(10000, 0, 1000000);
		Utils.dumpArray(src);
		System.out.println();

		long st = System.nanoTime();
		boolean swapped = true;
		for (int i = 0; i < src.length - 1 && swapped; i++) {
			swapped = false;
			for (int j = 0; j < src.length - 1; j++) {
				if (src[j + 1] < src[j]) {
					int tmp = src[j];
					src[j] = src[j + 1];
					src[j + 1] = tmp;
					swapped = true;
				}
			}
			if (!swapped) break;
			// Utils.dumpArray(src);
		}
		long et = System.nanoTime();

		System.out.println();
		Utils.dumpArray(src);
		System.out.printf("duration: %d\n", et - st);
	}
}

public class SortDirectSelection {
	public static void main(String[] args) {
		int[] src = Utils.generateRandomArray(10, 0, 100);

		System.out.print("src: ");
		for (int i = 0; i < src.length; i++)
			System.out.printf("%d ", src[i]);
		System.out.println();

		for (int i = 0; i < src.length; i++) {
			int idx = i;
			for (int j = i + 1; j < src.length; j++)
				if (src[j] < src[idx]) idx = j;
			int tmp = src[i];
			src[i] = src[idx];
			src[idx] = tmp;
		}

		System.out.print("src: ");
		for (int i = 0; i < src.length; i++)
			System.out.printf("%d ", src[i]);
		System.out.println();
	}
}

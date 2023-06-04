import java.lang.Math;

public class SearchBinary {
	private static int search(int[] src, int val) {
		int start = 0;
		int end = src.length - 1;
		while (end != start) {
			int idx = (int) Math.floor(start + ((end - start) / 2.0));
			if (val < src[idx]) {
				end = idx;
			} else if (val > src[idx]) {
				start = idx + 1;
			} else {
				start = idx;
				end = idx;
			}
		}
		return src[start] == val ? src[start] : -1;
	}

	public static void main(String[] args) {
		int[] src = Utils.generateRandomArray(100, 0, 100);
		int val = src[Utils.randomInt(0, src.length - 1)];
		System.out.printf("searching for value: %d\n", val);
		int match = search(src, val);
		if (match > 0) {
			System.out.printf("found value: %d\n", match);
		} else {
			System.out.println("value not found");
		}
	}
}

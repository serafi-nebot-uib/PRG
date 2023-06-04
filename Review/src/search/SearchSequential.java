import java.lang.Math;

public class SearchSequential {
	private static final int RANDOM_MIN = 0;
	private static final int RANDOM_MAX = 100;

	public static void main(String[] args) {
		int[] src = Utils.generateRandomArray(10, RANDOM_MIN, RANDOM_MAX);
		System.out.print("src: ");
		for (int i = 0; i < src.length; i++)
			System.out.printf("%d ", src[i]);
		System.out.println();

		int val = src[Utils.randomInt(0, src.length - 1)];
		System.out.printf("val: %d\n", val);

		Integer c = null;
		for (int i = 0; i < src.length && c == null; i++)
			c = src[i] == val ? src[i] : null;

		if (c == null)
			System.out.printf("%d not found in list\n", val);
		else
			System.out.printf("%d found!\n", c);
	}
}

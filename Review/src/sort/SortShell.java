import java.lang.Math;

public class SortShell {
	public static void main(String[] args) {
		int[] src = Utils.generateRandomArray(10000, 0, 1000000);
		Utils.dumpArray(src);
		System.out.println();

		long st = System.nanoTime();
		for (int i = (int) Math.floor(src.length / 2.0); i > 0; i = (int) Math.floor(i / 2.0)) {
			for (int j = 0; j < i; j++) {
				boolean swap = false;
				for (int k = j; k < src.length; k += i) {
					int limit = Math.min((src.length - src.length % i) + 1, src.length);
					int l = j;
					for (; l < limit && src[k] > src[l]; l += i);
					if (l == k) continue;
					int tmp = src[k];
					int inc = k > l ? -i : i;
					for (int m = k; (k > l && m > l) || m < l; m += inc) src[m] = src[m + inc];
					src[l] = tmp;
					swap = true;
				}
				if (!swap) break;
			}
		}
		long et = System.nanoTime();
		Utils.dumpArray(src);
		System.out.printf("duration: %d\n", et - st);
	}
}

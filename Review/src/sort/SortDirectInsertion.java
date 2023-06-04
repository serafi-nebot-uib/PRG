public class SortDirectInsertion {
	public static void main(String[] args) {
		int[] src = Utils.generateRandomArray(20, 0, 100);
		Utils.dumpArray(src);
		for (int i = 0; i < src.length; i++) {
			int j = 0;
			for (; j < src.length - 1 && src[i] > src[j]; j++);
			int tmp = src[i];
			int inc = i > j ? -1 : 1;
			for (int k = i; (i > j && k > j) || k < j; k += inc) src[k] = src[k + inc];
			src[j] = tmp;
		}
		Utils.dumpArray(src);
	}
}

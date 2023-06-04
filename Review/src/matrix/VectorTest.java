public class VectorTest {
	public static void main(String[] args) {
		Matrix m1 = new Matrix(
				new Vector(1, 2, 3),
				new Vector(4, 5, 6));
		Matrix m2 = new Matrix(
				new Vector(10, 11),
				new Vector(20, 21),
				new Vector(30, 31));
		System.out.printf("m1: \n%s\n", m1);
		System.out.printf("m2: \n%s\n", m2);
		System.out.printf("mul: \n%s\n", m1.mult(m2));
	}
}

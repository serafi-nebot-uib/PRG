import java.lang.Math;

public class Vector {
	private double[] vec = null;

	public Vector(int size) {
		this.vec = new double[Math.max(0, size)];
	}

	public Vector(double ... values) {
		this.vec = values;
	}

	public int dimension() {
		return this.vec.length;
	}

	public double modulus() {
		double sum = 0;
		for (double val : this.vec) sum += Math.pow(val, 2);
		return Math.sqrt(sum);
	}
	
	public double get(int index) {
		if (index >= dimension())
			throw new IndexOutOfBoundsException("vector index out of bounds");
		return this.vec[index];
	}

	public double[] values() {
		return this.vec;
	}

	public Vector sum(Vector vec) {
		if (vec.dimension() != dimension())
			throw new IllegalArgumentException(String.format("vectors must have the same dimension: %d != %d", vec.dimension(), dimension()));
		double[] sum = new double[dimension()];
		for (int i = 0; i < dimension(); i++) sum[i] = this.vec[i] + vec.get(i);
		return new Vector(sum);
	}

	public double dot(Vector vec) {
		if (vec.dimension() != dimension())
			throw new IllegalArgumentException(String.format("vectors must have the same dimension: %d != %d", vec.dimension(), dimension()));
		double res = 0;
		for (int i = 0; i < dimension(); i++) res += this.vec[i] * vec.get(i);
		return res;
	}

	public double sumComponents() {
		double sum = 0;
		for (double val : this.vec) sum += val;
		return sum;
	}

	@Override
	public String toString() {
		String[] values = new String[this.vec.length];
		for (int i = 0; i < values.length; i++) values[i] = Double.toString(this.vec[i]);
		return "[" + String.join(", ", values) + "]";
	}
}

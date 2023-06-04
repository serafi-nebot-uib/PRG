import java.lang.Math;

public class Matrix {
	public double[][] mat = null;

	public Matrix(int rows, int cols) {
		this.mat = new double[Math.max(0, rows)][Math.max(0, cols)];
	}

	public Matrix(Vector ... vectors) {
		int vdim = vectors.length == 0 ? 0 : vectors[0].dimension();
		this.mat = new double[vectors.length][vdim];
		for (int i = 0; i < this.mat.length; i++) this.mat[i] = vectors[i].values();
	}

	public int rows() {
		return this.mat.length;
	}

	public int cols() {
		return this.mat[0].length;
	}

	public double[] get(int row) {
		if (row >= rows())
			throw new IndexOutOfBoundsException("row index out of bounds");
		return this.mat[row];
	}

	public Vector getv(int row) {
		return new Vector(get(row));
	}

	public double get(int row, int col) {
		if (row >= rows() || col >= cols())
			throw new IndexOutOfBoundsException("row or col index out of bounds");
		return this.mat[row][col];
	}

	public void set(int row, int col, double val) {
		if (this.mat == null || this.mat[0] == null)
			throw new NullPointerException("matrix not initialized");
		if (row < 0 || col < 0 || row > rows() - 1 || col > cols() - 1)
			throw new IndexOutOfBoundsException(String.format("invalid index %dx%d for matrix %dx%d", row, col, rows(), cols()));
		this.mat[row][col] = val;
	}

	public void set(int row, Vector vec) {
		if (vec == null)
			throw new NullPointerException("vector cannot be null");
		if (this.mat == null || this.mat[0] == null)
			throw new NullPointerException("matrix not initialized");
		if (row < 0 || row > rows() - 1)
			throw new IndexOutOfBoundsException(String.format("invalid index %d for matrix %dx%d", row, rows(), cols()));
		if (vec.dimension() != cols())
			throw new IllegalArgumentException(String.format("vector dimension must be same size as matrix (%d != %d)",vec.dimension(), rows()));
		this.mat[row] = vec.values();
	}

	public Vector[] vectors() {
		Vector[] vecs = new Vector[rows()];
		for (int i = 0; i < vecs.length; i++) vecs[i] = new Vector(this.mat[i]);
		return vecs;
	}

	public Matrix transpose() {
		Matrix mat = new Matrix(cols(), rows());
		for (int i = 0; i < mat.rows(); i++)
			for (int j = 0; j < mat.cols(); j++)
				mat.set(i, j, this.mat[j][i]);
		return mat;
	}

	public Matrix mult(Matrix mat) {
		Matrix res = new Matrix(rows(), mat.cols());
		mat = mat.transpose();
		for (int i = 0; i < res.rows(); i++)
			for (int j = 0; j < res.cols(); j++)
				res.set(i, j, getv(i).dot(mat.getv(j)));
		return res;
	}

	@Override
	public String toString() {
		Vector[] vecs = vectors();
		String[] str = new String[vecs.length];
		for (int i = 0; i < str.length; i++) str[i] = vecs[i].toString();
		return "[\n" + String.join("\n", str) + "\n]";
	}
}

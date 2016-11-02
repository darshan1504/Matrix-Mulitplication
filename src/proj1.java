import java.util.*;

public class proj1 {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int size = 0;
		long startTime, endTime;
		long classicTime, divideandconqTime, strassenTime;
		int[][] temp;

		System.out.print("Enter 'n * n' size matrix: ");
		size = in.nextInt();
		matrix m = new matrix(size);
		m.initialize();
		// System.out.println("1st Random Generated Matrix: "); m.print(m.m1);
		// System.out.println("2nd Random Generated Matrix: "); m.print(m.m2);

		startTime = System.nanoTime();
		temp = m.classic_mult(m.m1, m.m2);
		endTime = System.nanoTime();
		// m.print(temp);
		classicTime = (endTime - startTime) / m.count_classic;

		// System.out.println("\nDivide and Conquer Multiplication Matrix:");
		startTime = System.nanoTime();
		temp = m.divideandconq(m.m1, m.m2);
		endTime = System.nanoTime();
		// m.print(temp);
		divideandconqTime = (endTime - startTime) / m.count_divideandconq;

		// System.out.println("\nStrassen Multiplication Matrix:");
		startTime = System.nanoTime();
		temp = m.strassen(m.m1, m.m2);
		endTime = System.nanoTime();
		// m.print(temp);
		strassenTime = (endTime - startTime) / m.count_strassen;

		System.out.println("Matrix Size: " + size);
		System.out.println("Classic Total Time: " + classicTime);
		System.out.println("Divide & Conquer Total Time: " + divideandconqTime);
		System.out.println("Strassen Total Time: " + strassenTime);
	}// end main

	public static class matrix {
		int count_classic = 0;
		int count_divideandconq = 0;
		int count_strassen = 0;
		int size;
		int m1[][];
		int m2[][];

		public matrix(int size) {
			this.size = size;
			m1 = new int[size][size];
			m2 = new int[size][size];
		}

		public void initialize() {
			Random r1 = new Random();
			for (int a = 0; a < size; a++) {
				for (int b = 0; b < size; b++) {
					m1[a][b] = r1.nextInt(9);
					m2[a][b] = r1.nextInt(9);
				}
				// System.out.println();
			}
		}

		public void initialize(int[][] copyFrom, int[][] copyTo, int startRow,
				int startCol) {
			for (int a = 0, a2 = startRow; a < copyTo.length; a++, a2++) { // row
				for (int b = 0, b2 = startCol; b < copyTo.length; b++, b2++) { // col
					copyTo[a][b] = copyFrom[a2][b2];
				}
			}
		}

		public int[][] divideandconq(int[][] a, int[][] b) {
			count_divideandconq++;
			int n = a.length;
			int[][] result = new int[n][n];

			if (n == 1) {
				result[0][0] = a[0][0] * b[0][0];
			} else {
				int[][] a11 = new int[n / 2][n / 2];
				int[][] a12 = new int[n / 2][n / 2];
				int[][] a21 = new int[n / 2][n / 2];
				int[][] a22 = new int[n / 2][n / 2];
				int[][] b11 = new int[n / 2][n / 2];
				int[][] b12 = new int[n / 2][n / 2];
				int[][] b21 = new int[n / 2][n / 2];
				int[][] b22 = new int[n / 2][n / 2];

				initialize(a, a11, 0, 0);
				initialize(a, a12, 0, n / 2);
				initialize(a, a21, n / 2, 0);
				initialize(a, a22, n / 2, n / 2);
				initialize(b, b11, 0, 0);
				initialize(b, b12, 0, n / 2);
				initialize(b, b21, n / 2, 0);
				initialize(b, b22, n / 2, n / 2);

				int[][] c11 = add(divideandconq(a11, b11),
						divideandconq(a12, b21));
				int[][] c12 = add(divideandconq(a11, b12),
						divideandconq(a12, b22));
				int[][] c21 = add(divideandconq(a21, b11),
						divideandconq(a22, b21));
				int[][] c22 = add(divideandconq(a21, b12),
						divideandconq(a22, b22));

				putTogether(c11, result, 0, 0);
				putTogether(c12, result, 0, n / 2);
				putTogether(c21, result, n / 2, 0);
				putTogether(c22, result, n / 2, n / 2);
			}
			return result;
		}

		public int[][] strassen(int[][] a, int[][] b) {
			count_strassen++;
			int n = a.length;
			int[][] result = new int[n][n];

			if (n == 1) {
				result[0][0] = a[0][0] * b[0][0];
			} else {
				int[][] a11 = new int[n / 2][n / 2];
				int[][] a12 = new int[n / 2][n / 2];
				int[][] a21 = new int[n / 2][n / 2];
				int[][] a22 = new int[n / 2][n / 2];
				int[][] b11 = new int[n / 2][n / 2];
				int[][] b12 = new int[n / 2][n / 2];
				int[][] b21 = new int[n / 2][n / 2];
				int[][] b22 = new int[n / 2][n / 2];

				initialize(a, a11, 0, 0);
				initialize(a, a12, 0, n / 2);
				initialize(a, a21, n / 2, 0);
				initialize(a, a22, n / 2, n / 2);
				initialize(b, b11, 0, 0);
				initialize(b, b12, 0, n / 2);
				initialize(b, b21, n / 2, 0);
				initialize(b, b22, n / 2, n / 2);

				int[][] _m1 = strassen(add(a11, a22), add(b11, b22));
				int[][] _m2 = strassen(add(a21, a22), b11);
				int[][] _m3 = strassen(a11, minus(b12, b22));
				int[][] _m4 = strassen(a22, minus(b21, b11));
				int[][] _m5 = strassen(add(a11, a12), b22);
				int[][] _m6 = strassen(minus(a21, a11), add(b11, b12));
				int[][] _m7 = strassen(minus(a12, a22), add(b21, b22));

				int[][] c11 = add(minus(add(_m1, _m4), _m5), _m7);
				int[][] c12 = add(_m3, _m5);
				int[][] c21 = add(_m2, _m4);
				int[][] c22 = add(minus(add(_m1, _m3), _m2), _m6);

				putTogether(c11, result, 0, 0);
				putTogether(c12, result, 0, n / 2);
				putTogether(c21, result, n / 2, 0);
				putTogether(c22, result, n / 2, n / 2);

			}
			return result;
		}

		public void putTogether(int[][] child, int[][] parent, int startRow,
				int startCol) {
			for (int a = 0, a2 = startRow; a < child.length; a++, a2++) {
				for (int b = 0, b2 = startCol; b < child.length; b++, b2++) {
					parent[a2][b2] = child[a][b];
				}
			}
		}

		public int[][] add(int[][] m1, int[][] m2) {
			int result[][] = new int[m1.length][m1.length];
			for (int a = 0; a < m1.length; a++) {
				for (int b = 0; b < m1.length; b++) {
					result[a][b] = m1[a][b] + m2[a][b];
				}
			}
			return result;
		}

		public int[][] minus(int[][] m1, int[][] m2) {
			int result[][] = new int[m1.length][m1.length];
			for (int a = 0; a < m1.length; a++) {
				for (int b = 0; b < m1.length; b++) {
					result[a][b] = m1[a][b] - m2[a][b];
				}
			}
			return result;
		}

		public int[][] classic_mult(int[][] m1, int[][] m2) {
			count_classic++;
			int result[][] = new int[m1.length][m1.length];
			for (int i = 0; i < m1.length; i++)
				for (int j = 0; j < m1.length; j++) {
					result[i][j] = 0;
					for (int k = 0; k < m1.length; k++)
						result[i][j] = result[i][j] + m1[i][k] * m2[k][j];
				}
			return result;
		}

		public void print(int matrix[][]) {
			for (int a = 0; a < matrix.length; a++) {
				for (int b = 0; b < matrix.length; b++) {
					System.out.print(matrix[a][b] + "  ");
				}
				System.out.println();
			}
		}
	}// end class matrix
}// end class proj1
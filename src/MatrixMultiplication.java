import java.util.*;
import java.util.Scanner;

public class MatrixMultiplication {
	public static void main(String[] args) {
		Scanner matrixSize = new Scanner(System.in);
		int size = 0;
		long startTime, endTime;
		long classicTime, divideandconqTime, strassenTime;
		int[][] temp;

		long testavg, classicavg, dncavg, stracavg;
		classicavg = 0;
		dncavg = 0;
		stracavg = 0;

		System.out.print("Enter matrix size: ");
		size = matrixSize.nextInt();
		matrix m = new matrix(size);

		// Testing
		for (int testing = 1; testing <= 10; testing++) {
			m.initializeMatrix();
			System.out.println("Initial testing:/n" + testing);
			// Calculating time for Classic Matrix Multiplication
			startTime = System.nanoTime();
			temp = m.classicMethod(m.m1, m.m2);
			endTime = System.nanoTime();
			classicTime = (endTime - startTime) / m.countClassic;
			// counting average
			classicavg += classicTime;

			// Calculating time for Divide and Conquer
			startTime = System.nanoTime();
			temp = m.divideAndConqMethod(m.m1, m.m2);
			endTime = System.nanoTime();
			divideandconqTime = (endTime - startTime) / m.countDivideAndConq;
			System.out.println("divideandconqTime:" + divideandconqTime);

			// counting average
			dncavg += divideandconqTime;

			// Calculating time for Strassen Multiplication
			startTime = System.nanoTime();
			temp = m.strassenMethod(m.m1, m.m2);
			endTime = System.nanoTime();
			strassenTime = (endTime - startTime) / m.countStrassen;
			// counting average
			stracavg += strassenTime;

			System.out.println("Matrix Size is: " + size);
			System.out.println("Classic Total Execution Time is: "
					+ classicTime);
			System.out.println("Divide & Conquer Total Execution Time is: "
					+ divideandconqTime);
			System.out.println("Strassen Total Execution Time is: "
					+ strassenTime);

			if (testing == 10) {
				classicavg = classicavg / testing;
				dncavg = dncavg / testing;
				stracavg = stracavg / testing;
				System.out.println("Total Classic average:" + classicavg);
				System.out.println("Total Divide & Conquer average:" + dncavg);
				System.out.println("Total Strassens average:" + stracavg);

			}

		}

	}

	public static class matrix {
		long countClassic = 0;
		long countDivideAndConq = 0;
		long countStrassen = 0;
		int size;
		int m1[][];
		int m2[][];

		public matrix(int size) {
			this.size = size;
			m1 = new int[size][size];
			m2 = new int[size][size];
		}

		public void initializeMatrix() {
			Random r1 = new Random();
			for (int a = 0; a < size; a++) {
				for (int b = 0; b < size; b++) {
					m1[a][b] = r1.nextInt(3);
					m2[a][b] = r1.nextInt(3);
				}
			}
		}

		public void initializeToFrom(int[][] copyFromMatrix,
				int[][] copyToMatrix, int startRow, int startCol) {
			for (int a = 0, a2 = startRow; a < copyToMatrix.length; a++, a2++) { // row
				for (int b = 0, b2 = startCol; b < copyToMatrix.length; b++, b2++) { // col
					copyToMatrix[a][b] = copyFromMatrix[a2][b2];
				}
			}
		}

		public int[][] classicMethod(int[][] m1, int[][] m2) {
			countClassic++;
			int result[][] = new int[m1.length][m1.length];
			for (int i = 0; i < m1.length; i++)
				for (int j = 0; j < m1.length; j++) {
					result[i][j] = 0;
					for (int k = 0; k < m1.length; k++)
						result[i][j] = result[i][j] + m1[i][k] * m2[k][j];
				}
			return result;
		}

		public int[][] divideAndConqMethod(int[][] a, int[][] b) {

			countDivideAndConq++;
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

				initializeToFrom(a, a11, 0, 0);
				initializeToFrom(a, a12, 0, n / 2);
				initializeToFrom(a, a21, n / 2, 0);
				initializeToFrom(a, a22, n / 2, n / 2);
				initializeToFrom(b, b11, 0, 0);
				initializeToFrom(b, b12, 0, n / 2);
				initializeToFrom(b, b21, n / 2, 0);
				initializeToFrom(b, b22, n / 2, n / 2);

				int[][] c11 = add(divideAndConqMethod(a11, b11),
						divideAndConqMethod(a12, b21));
				int[][] c12 = add(divideAndConqMethod(a11, b12),
						divideAndConqMethod(a12, b22));
				int[][] c21 = add(divideAndConqMethod(a21, b11),
						divideAndConqMethod(a22, b21));
				int[][] c22 = add(divideAndConqMethod(a21, b12),
						divideAndConqMethod(a22, b22));

				JointMatrix(c11, result, 0, 0);
				JointMatrix(c12, result, 0, n / 2);
				JointMatrix(c21, result, n / 2, 0);
				JointMatrix(c22, result, n / 2, n / 2);
			}
			return result;
		}

		public int[][] strassenMethod(int[][] a, int[][] b) {
			countStrassen++;
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

				initializeToFrom(a, a11, 0, 0);
				initializeToFrom(a, a12, 0, n / 2);
				initializeToFrom(a, a21, n / 2, 0);
				initializeToFrom(a, a22, n / 2, n / 2);
				initializeToFrom(b, b11, 0, 0);
				initializeToFrom(b, b12, 0, n / 2);
				initializeToFrom(b, b21, n / 2, 0);
				initializeToFrom(b, b22, n / 2, n / 2);

				int[][] p = strassenMethod(add(a11, a22), add(b11, b22));
				int[][] q = strassenMethod(add(a21, a22), b11);
				int[][] r = strassenMethod(a11, minus(b12, b22));
				int[][] s = strassenMethod(a22, minus(b21, b11));
				int[][] t = strassenMethod(add(a11, a12), b22);
				int[][] u = strassenMethod(minus(a21, a11), add(b11, b12));
				int[][] v = strassenMethod(minus(a12, a22), add(b21, b22));

				int[][] c11 = add(minus(add(p, s), t), v);
				int[][] c12 = add(r, t);
				int[][] c21 = add(q, s);
				int[][] c22 = add(minus(add(p, r), q), u);

				JointMatrix(c11, result, 0, 0);
				JointMatrix(c12, result, 0, n / 2);
				JointMatrix(c21, result, n / 2, 0);
				JointMatrix(c22, result, n / 2, n / 2);

			}
			return result;
		}

		public void JointMatrix(int[][] childMatrix, int[][] parentMatrix,
				int startRow, int startCol) {
			for (int a = 0, a2 = startRow; a < childMatrix.length; a++, a2++) {
				for (int b = 0, b2 = startCol; b < childMatrix.length; b++, b2++) {
					parentMatrix[a2][b2] = childMatrix[a][b];
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

		public void print(int matrix[][]) {
			for (int a = 0; a < matrix.length; a++) {
				for (int b = 0; b < matrix.length; b++) {
					System.out.print(matrix[a][b] + "  ");
				}
				//System.out.println();
			}
		}
	}
}
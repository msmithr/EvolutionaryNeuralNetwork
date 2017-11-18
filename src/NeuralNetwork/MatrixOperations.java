

package NeuralNetwork;

/**
 * Static class containing simple matrix operations
 */
public class MatrixOperations {
	
	/**
	 * Add two matrices together
	 * @param matrixA Matrix A
	 * @param matrixB Matrix B
	 * @return Sum of matrix A and matrix B
	 */
	public static double[][] add(double[][] matrixA, double[][] matrixB) {		
		if (matrixA.length != matrixB.length || matrixA[0].length != matrixB[0].length) {
			System.out.println("attempting to add matrices of incompatible dimensions");
			return null;
		}
		
		double[][] result = new double[matrixA.length][matrixA[0].length];
		
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result.length; j++) {
				result[i][j] = matrixA[i][j] + matrixB[i][j];
			}
		}
		
		return result;
	} // end add()
	
	/**
	 * Get the negative of a matrix
	 * @param matrix Matrix A
	 * @return Negative of Matrix A
	 */
	public static double[][] neg(double[][] matrix) {
		double[][] result = new double[matrix.length][matrix[0].length];
		
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result.length; j++) {
				result[i][j] = -matrix[i][j];
			}
		}
		
		return result;
	} // end neg()
	
	/**
	 * Multiply two matrices together
	 * @param matrixA Matrix A
	 * @param matrixB Matrix B
	 * @return Product of matrix A and matrix B
	 */
	public static double[][] multiply(double[][] matrixA, double[][] matrixB) {
		if (matrixA[0].length != matrixB.length) {
			System.out.println("trying to multiply matrices of incompatible dimensions");
			return null;
		}
		
		double[][] result = new double[matrixA.length][matrixB[0].length];
		
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[0].length; j++) {
				int sum = 0;
				for (int k = 0; k < result.length; k++) {
					sum += (matrixA[i][k] * matrixB[k][j]);
				}
				result[i][j] = sum;
			}
		}
		
		return result;
	} // end multiply()
	
} // end class

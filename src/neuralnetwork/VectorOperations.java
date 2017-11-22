package neuralnetwork;

/**
 * Static class containing simple vector operations
 */
public class VectorOperations {
	
	/**
	 * Multiply a row vector by a matrix
	 * @param vector Vector v
	 * @param matrix Matrix M
	 * @return Product v*M
	 */
	public static double[] multiply(double[] vector, double[][] matrix) {
		if (vector.length != matrix.length) {
			System.out.println("Trying to multiply a vector by a matrix of incompatible dimensions");
			return null;
		}
		
		double[] result = new double[matrix[0].length];
		for (int i = 0; i < result.length; i++) {
			double sum = 0;
			for (int j = 0; j < matrix.length; j++) {
				sum += vector[j]*matrix[j][i];
			}
			result[i] = sum;
		}
		return result;
	} // end multiply()
	
	/**
	 * Get the negative of a vector
	 * @param vector Vector v
	 * @return Vector -v
	 */
	public static double[] neg(double[] vector) {
		double[] result = new double[vector.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = -vector[i];
		}
		
		return result;
	} // end neg()
	
	/**
	 * Sum two vectors together
	 * @param vectorA Vector A
	 * @param vectorB Vector B
	 * @return Sum vector A+B
	 */
	public static double[] sum(double[] vectorA, double[] vectorB) {
		if (vectorA.length != vectorB.length) {
			System.out.println("Trying to add two vectors of incompatible dimension");
			return null;
		}
		
		double[] result = new double[vectorA.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = vectorA[i] + vectorB[i];
		}
		
		return result;
	} // end sum()
	
	public static double[] sigmoid(double[] vector) {
		double[] result = new double[vector.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = sigmoid(vector[i]);
		}
		
		return result;
	} // end sigmoid()
	
	private static double sigmoid(double x) {
		//return 1/(1+Math.exp(-x));
		if (x < 0) return 0;
		else return 1;
		//return Math.tanh(x);
	} // end sigmoid()
	
	public static double[] abs(double[] vector) {
		double[] result = new double[vector.length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = Math.abs(vector[i]);
		}
		
		return result;
	}
	
	/**
	 * Return a string representing a matrix
	 * @param matrix Given matrix
	 * @return String representing given matrix
	 */
	public static String toString(double[][] matrix) {
		String result = "";
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				result += matrix[i][j] + " ";
			}
			result += '\n';
		}
		
		return result;
	} // end toString()
	
	/**
	 * Return a string representing a vector
	 * @param vector Given vector
	 * @return String representing given vector
	 */
	public static String toString(double[] vector) {
		String result = "";
		
		for (int i = 0; i < vector.length; i++) {
			result += vector[i] + " ";
		}
		result += '\n';
		
		return result;
	} // end toString()
	
} // end class

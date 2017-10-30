
public class MathLib {
	public static double[] vectorSum(double[] a, double[] b) {
		if (a.length != b.length) {
			System.out.println("Attempting to add arrays of incompatible dimension");
			return null;
		}
		
		for (int i = 0; i < a.length; i++) {
			a[i] += b[i];
		}
		
		return a;
	}
	
	public static void printVector(double[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.print("\n");
	}
	
	// sigmoid activation function
	public static double sigmoid(double x) {
		return 1/(1+Math.exp(-x));
	}
	
	// hyperbolic tangent activation function
	public static double tanh(double x) {
		return Math.tanh(x);
	}
	
	// hyperbolic tangent derivative
	public static double Dtanh(double x) {
		return 1 - x*x;
	}
}

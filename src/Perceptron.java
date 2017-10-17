import java.util.Random;

public class Perceptron {
	private double alpha;
	private double theta;
	private double[] weights;

	Random entropy = new Random();
	
	// constructor
	public Perceptron(double alpha, int numInputs) {
		this.alpha = alpha;
		this.theta = (entropy.nextDouble());
		
		this.weights = new double[numInputs];
		for (int i = 0; i < numInputs; i++) {
			weights[i] = (entropy.nextDouble() - 0.5);
		}
	}
	
	// evaluate given inputs
	public double activate(double[] inputs) {
		double result = 0;
		for (int i = 0; i < weights.length; i++) {
			result += (inputs[i]*weights[i]);
		}
		return step(result - theta);
	}
	
	// activation function
	private double step(double x) {
		return x >= 0 ? 1 : 0;
	}
	
	public void learn(double[] inputs, double expected) {
		double result = activate(inputs);
		double error = expected - result;
		for (int i = 0; i < weights.length; i++) {
			weights[i] += (alpha * inputs[i] * error);
		}
	}
	
	public String toString() {
		String result = "";
		
		result += "[ ";
		for (int i = 0; i < weights.length; i++) {
			result += weights[i] + " ";
		}
		result += "] ";
		result += "; Theta = " + theta;
		
		return result;
	}
}

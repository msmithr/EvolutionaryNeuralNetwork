import java.util.Random;

public class Neuron {
	private double alpha;
	private double theta;
	private double[] weights;

	Random entropy = new Random();
	
	// constructor
	public Neuron(double alpha, int numInputs) {
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
		return sigmoid(result - theta);
	}
	
	// activation function
	private double sigmoid(double x) {
		return 1/(1+Math.exp(-x));
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

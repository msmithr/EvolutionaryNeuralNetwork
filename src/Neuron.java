import java.util.Random;

public class Neuron {
	private double alpha;
	private double theta;
	private double[] weights;
	private int numOutputs;

	Random entropy = new Random();
	
	// constructor
	public Neuron(double alpha, int numOutputs) {
		this.alpha = alpha;
		this.theta = (entropy.nextDouble());
		this.numOutputs = numOutputs;
		
		this.weights = new double[numOutputs];
		for (int i = 0; i < numOutputs; i++) {
			weights[i] = (entropy.nextDouble() - 0.5);
		}
	}
	
	// evaluate given inputs
	public double[] activate(double[] inputs) {
		double sum = 0;
		for (int i = 0; i < inputs.length; i++) {
			sum += inputs[i];
		}
		
		double[] result = new double[numOutputs];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = MathLib.sigmoid(sum*weights[i] - theta);
		}
		return result;
	} // end activate
	
	public int getNumOutputs() {
		return this.numOutputs;
	}

	public String toString() {
		String result = "";
		
		result += "[ ";
		for (int i = 0; i < weights.length; i++) {
			result += weights[i] + " ";
		}
		result += "] ";
		result += "; Theta = " + theta;
		result += "; NUMOUTPUTS: " + numOutputs;
		
		return result;
	}
}

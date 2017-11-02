package NeuralNetwork;

public class Neuron {
	private double output;
	private double[] weights;
	private int index;
	
	/**
	 * Neuron class for the neural network
	 * @param index The location of the neuron within it's layer
	 * @param weights The array of doubles representing this neurons weights
	 */
	public Neuron(int index, double[] weights) {
		this.index = index;
		this.weights = weights;
	}
	
	/**
	 * Setter function for output
	 * @param output Value for output
	 */
	public void setOutput(double output) {
		this.output = output;
	}
	
	/**
	 * Getter function for output
	 * @return The current output value
	 */
	public double getOutput() {
		return output;
	}
	
	/**
	 * Getter function for a specific weight
	 * @param index The index of the weight to get
	 * @return The weight of that connection
	 */
	public double getWeight(int index) {
		return weights[index];
	}
	
	/**
	 * Feed forward given the previous layer's outputs
	 * @param prevLayer An array of neurons representing the previous, already activated, layer
	 */
	public void feedForward(Neuron[] prevLayer) {
		double sum = 0;
		for (int i = 0; i < prevLayer.length; i++) {
			sum += prevLayer[i].getOutput() * prevLayer[i].getWeight(index);
		}
		output = Math.tanh(sum);
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; i < weights.length; i++) {
			result += weights[i] + " ";
		}
		result += "\n";
		return result;
	}
}

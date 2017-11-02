

public class Neuron {
	private double output;
	private double[] weights;
	private int index;
	
	public Neuron(int index, double[] weights) {
		this.index = index;
		this.weights = weights;
	}
	
	public void setOutput(double output) {
		this.output = output;
	}
	
	public double getOutput() {
		return output;
	}
	
	public double getWeight(int index) {
		return weights[index];
	}
	
	public void feedForward(Neuron[] prevLayer) {
		double sum = 0;
		for (int i = 0; i < prevLayer.length; i++) {
			sum += prevLayer[i].getOutput() * prevLayer[i].getWeight(index);
		}
		output = MathLib.tanh(sum);
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

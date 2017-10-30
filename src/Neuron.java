

import java.util.Random;

public class Neuron {
	private double output;
	private double[] weights;
	private double[] Dweights;
	private int index;
	private double gradient;
	static private double alpha = 0.2;
	static private double beta = 0;
	
	public Neuron(int nOutput, int index) {
		Random r = new Random();
		weights = new double[nOutput];
		Dweights = new double[nOutput];
		
		this.index = index;

		for (int i = 0; i < nOutput; i++) {
			weights[i] = r.nextDouble();
		}
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
	
	public void calcOutputGradients(double target) {
		double error = target - this.output;
		this.gradient = error * MathLib.Dtanh(this.output);
	}
	
	public void calcHiddenGradients(Neuron[] nextLayer) {
		double sum = 0;
		for (int i = 0; i < nextLayer.length; i++) {
			sum += weights[i] * nextLayer[i].gradient;
		}
		this.gradient = sum * MathLib.Dtanh(this.output);
	}
	
	// adjusts weights of previous layer
	public void adjustWeights(Neuron[] prevLayer) {
		double oldDW;
		double newDW;
	
		for (int i = 0; i < prevLayer.length; i++) {
			Neuron neuron = prevLayer[i];
			oldDW = neuron.Dweights[i];
			newDW = alpha * neuron.getOutput() * this.gradient + beta * oldDW;
			neuron.Dweights[i] = newDW;
			neuron.weights[i] += newDW;
		}
	}
	
	public String toString() {
		return Double.toString(output);
	}
}

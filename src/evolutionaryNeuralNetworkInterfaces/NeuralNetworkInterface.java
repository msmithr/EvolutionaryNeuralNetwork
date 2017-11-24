package evolutionaryNeuralNetworkInterfaces;

import evolutionaryNeuralNetwork.VectorOperations;

public interface NeuralNetworkInterface {
	/**
	 * Feed forward; get the corresponding output for an input
	 * @param inputs Array of doubles representing the input
	 * @return Array of doubles representing the output
	 */
	public double[] feedForward(double[] inputs);
}

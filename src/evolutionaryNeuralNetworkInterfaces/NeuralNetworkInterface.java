package evolutionaryNeuralNetworkInterfaces;

import evolutionaryNeuralNetwork.DataSet;
import evolutionaryNeuralNetwork.VectorOperations;

public interface NeuralNetworkInterface {
	/**
	 * Feed forward; get the corresponding output for an input
	 * @param inputs Array of doubles representing the input
	 * @return Array of doubles representing the output
	 */
	public double[] feedForward(double[] inputs);
	
	/**
	 * Overloaded feed forward, including DataSet parameter for normalizing data before evaluation
	 * @param inputs Input vector into neural network
	 * @param learningData Normalized Dataset containing max and min vectors
	 * @return Output vector out of neural network
	 */
	public double[] feedForward(double[] inputs, DataSet learningData);
}

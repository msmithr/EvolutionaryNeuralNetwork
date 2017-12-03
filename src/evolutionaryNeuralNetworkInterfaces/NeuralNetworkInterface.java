package evolutionaryNeuralNetworkInterfaces;

import java.io.IOException;

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
	 * Save the neural network parameters out to a file to be loaded in the future 
	 * @param filename Filename to save to
	 */
	public void save(String filename) throws IOException;
}

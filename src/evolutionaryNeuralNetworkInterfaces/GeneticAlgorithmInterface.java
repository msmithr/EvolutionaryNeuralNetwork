package evolutionaryNeuralNetworkInterfaces;

import evolutionaryNeuralNetwork.NeuralNetwork;

public interface GeneticAlgorithmInterface {

	/**
	 * Run the genetic algorithm for a given number of iterations
	 * @param iterations Number of iterations to run the GA
	 * @return The best neural network in the final population
	 */
	public NeuralNetwork optimize(int iterations);
	
	/**
	 * Run until error reaches some threshold
	 * @param error Error to stop at
	 * @return The best neural network at stop time
	 */
	public NeuralNetwork optimizeUntil(double error);
}

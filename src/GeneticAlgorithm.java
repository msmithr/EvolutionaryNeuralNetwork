import java.util.Random;
import NeuralNetwork.NeuralNetwork;

public class GeneticAlgorithm {
	private int chromosomeLength;
	private int popSize;
	private int nInputs;
	private int nOutputs;
	private int nNeurons;
	private int nLayers;
	private Random r;
	private double[][] population;
	private NeuralNetwork[] networks;
	
	/**
	 * Genetic algorithm for neural network optimization
	 * @param nInputs Number of inputs
	 * @param nOutputs Number of outputs
	 * @param nLayers Number of hidden layers for the neural networks
	 * @param nNeurons Number of neurons per hidden layer for the neural network
	 * @param popSize The population size 
	 */
	public GeneticAlgorithm(int nInputs, int nOutputs, int nLayers, int nNeurons, int popSize) {
		this.chromosomeLength = nNeurons*(nInputs + nNeurons*(nLayers-1) + nOutputs);
		this.r = new Random();
		this.popSize = popSize;
		networks = new NeuralNetwork[popSize];
		
		// initialize population
		this.population = new double[popSize][];
		for (int i = 0; i < popSize; i++) {
			population[i] = randomChromosome();
		}
		
		networks = generateNetworks(population);
		
	} // end constructor

	/**
	 * Generates a random chromosome
	 * @return Randomly generated chromosome with elements between -0.5 and 0.5
	 */
	public double[] randomChromosome() {
		double[] newChromosome = new double[chromosomeLength];
		for (int i = 0; i < chromosomeLength; i++) {
			newChromosome[i] = r.nextDouble() - 0.5;
		}
		return newChromosome;
	}
	
	/**
	 * Generates a set of neural networks from a population of chromosomes
	 * @param population 
	 */
	public NeuralNetwork[] generateNetworks(double[][] population) {
		NeuralNetwork[] networks = new NeuralNetwork[popSize];
		for (int i = 0; i < popSize; i++) {
			networks[i] = new NeuralNetwork(population[i], nInputs, nOutputs, nLayers, nNeurons);
		}
		return networks;
	}
	
	/**
	 * Crossover genetic operator, simple single point crossover
	 * @param parent1 First parent
	 * @param parent2 Second parent
	 * @return Array of two child chromosomes resulting from the crossover
	 */
	public double[][] crossover(double[] parent1, double[] parent2) {
		int point = r.nextInt(chromosomeLength);
		
		double[] child1 = new double[chromosomeLength];
		double[] child2 = new double[chromosomeLength];
		
		for (int i = 0; i < point; i++) {
			child1[i] = parent1[i];
			child2[i] = parent2[i];
		}
		
		for (int i = point; i < chromosomeLength; i++) {
			child1[i] = parent2[i];
			child2[i] = parent1[i];
		}
		
		return new double[][] {child1, child2};
	} // end crossover()
	
	/**
	 * Mutation genetic operator, simple Gaussian mutation
	 * @param chromosome Chromosome to be mutated
	 * @return A mutated version of the chromosome
	 */
	public double[] mutation(double[] chromosome) {
		double[] child = chromosome.clone();
		int point = r.nextInt(chromosomeLength);
		child[point] += r.nextGaussian();
		return child;
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; i < population.length; i++) {
			for (int j = 0; j < population[i].length; j++) {
				result += population[i][j] + " ";
			}
			result += "\n";
		}
		
		return result;
	}
}

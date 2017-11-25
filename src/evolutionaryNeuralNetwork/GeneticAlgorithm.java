package evolutionaryNeuralNetwork;
import java.util.Random;

import evolutionaryNeuralNetworkInterfaces.GeneticAlgorithmInterface;

public class GeneticAlgorithm implements GeneticAlgorithmInterface{
	private int chromosomeLength; // number of doubles in the chromosomes
	private int popSize;
	private int nInputs;
	private int nOutputs;
	private int nNeurons;
	private int nLayers;
	private int nGenes; // number of 'genes' in the chromosomes
	private double crossoverProbability;
	private double mutationProbability;
	private Random r;
	private double[][] population;
	private DataSet learningData;
	
	/**
	 * Genetic algorithm for neural network optimization
	 * @param nInputs Number of inputs
	 * @param nOutputs Number of outputs
	 * @param nLayers Number of hidden layers for the neural networks
	 * @param nNeurons Number of neurons per hidden layer for the neural network
	 * @param popSize The population size 
	 * @param crossoverProbability probability for crossover operator
	 * @param mutationProbability probability for mutation operator
	 * @param learningData DataSet containing data to learn from
	 */
	public GeneticAlgorithm(int nInputs, int nOutputs, int nLayers, int nNeurons, 
			int popSize, double crossoverProbability, double mutationProbability, DataSet learningData) {
		
		this.chromosomeLength = nNeurons*(nInputs + nNeurons*(nLayers-1) + nOutputs + nLayers) + nOutputs;
		this.nGenes = (nNeurons * nLayers) + nOutputs;
		
		this.r = new Random();
		this.popSize = popSize;
		this.learningData = learningData;
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		this.nLayers = nLayers;
		this.nNeurons = nNeurons;
		this.crossoverProbability = crossoverProbability;
		this.mutationProbability = mutationProbability;
		
		// initialize population
		this.population = new double[popSize][];
		for (int i = 0; i < popSize; i++) {
			population[i] = randomChromosome();
		}
		
	} // end constructor
	
	public NeuralNetwork optimize(int iterations) {
		for (int i = 0; i < iterations; i++) {
			iterate();
		}
		NeuralNetwork[] results = generateNetworks(population);
		double[] fitness = this.fitnessPop(results, learningData);
		
		// find the best network
		double max = 0;
		int maxIndex = 0;
		for (int i = 0; i < fitness.length; i++) {
			if (fitness[i] > max) {
				maxIndex = i;
				max = fitness[i];
			}
		}
		
		return results[maxIndex];
		
	}
	
	/**
	 * Transition the population to it's next iteration
	 * @param population 
	 */
	private void iterate() {
		int[] parentIndices;
		int parent1Index;
		int parent2Index;
		double[][] newPopulation = new double[population.length][chromosomeLength];
		double[] child;
		
		int index = 0;
		while (index < popSize) {
			// selection
			parentIndices = selection(population, learningData);
			parent1Index = parentIndices[0];
			parent2Index = parentIndices[1];
			
		
			// crossover
			if (r.nextDouble() < crossoverProbability) {
				child = crossover(population[parent1Index], population[parent2Index]);
			} else {
				child = population[parent1Index].clone();
			}
			
			// mutation
			if (r.nextDouble() < mutationProbability) {
				child = mutation(child);
			}
			
			// add new child to population
			newPopulation[index++] = child;
		}
		
		this.population = newPopulation;
	} // end iterate()

	/**
	 * Generates a random chromosome
	 * @return Randomly generated chromosome with elements between -0.5 and 0.5
	 */
	private double[] randomChromosome() {
		double[] newChromosome = new double[chromosomeLength];
		for (int i = 0; i < chromosomeLength; i++) {
			newChromosome[i] = r.nextDouble() - 0.5;
		}
		return newChromosome;
	} // end randomChromosome()
	
	/**
	 * Generates a set of neural networks from a population of chromosomes
	 * @param population 
	 */
	private NeuralNetwork[] generateNetworks(double[][] population) {
		NeuralNetwork[] networks = new NeuralNetwork[popSize];
		for (int i = 0; i < popSize; i++) {
			networks[i] = new NeuralNetwork(population[i], nInputs, nOutputs, nLayers, nNeurons);
		}
		return networks;
	} // end generateNetworks()
	
	/**
	 * Calculate the fitness of a given neural network given learning data
	 * @param network Neural network to calculate fitness of 
	 * @param learningData Data to learn from
	 * @return fitness of the network
	 */
	private double fitness(NeuralNetwork network, DataSet learningData) {
		double[] activationResult;
		double[] inputs;
		double[] expectedOutputs;
		double error = 0;
		double sumSquaredErrors = 0;
		
		for (int i = 0; i < learningData.getSize(); i++) {
			inputs = learningData.getInputs(i);
			expectedOutputs = learningData.getOutputs(i);
			activationResult = network.feedForward(inputs);
			
			for (int j = 0; j < expectedOutputs.length; j++) {
				error += expectedOutputs[j] - activationResult[j];
				sumSquaredErrors += (error * error);
			}
		}
		if (sumSquaredErrors == 0) {
			return 1000;
		}
		return sumSquaredErrors;
		//return fitness;
	} // end fitness()
	
	/**
	 * Calculate the fitness for a population of networks
	 * @param networks Array of networks to calculate fitness of
	 * @param learningData Data for networks to learn from
	 * @return Double array of fitness for each network
	 */
	private double[] fitnessPop(NeuralNetwork[] networks, DataSet learningData) {
		double[] result = new double[popSize];
		for (int i = 0; i < popSize; i++) {
			result[i] = fitness(networks[i], learningData);
		}
		return result;
	} // end fitnessPop()
	
	/**
	 * Selection genetic operator
	 * @param population Current population
	 * @return Indices of two chromosomes selected randomly based on fitness
	 */
	private int[] selection(double[][] population, DataSet learningData) {
		// construct 'roulette ranges'
		double sumFitness = 0;
		double previous = 0;
		double parent1Selection;
		double parent2Selection;
		int parent1Index;
		int parent2Index;
	
		NeuralNetwork[] nns = generateNetworks(population);
		double[] fitness = fitnessPop(nns, learningData);
		
		for (int i = 0; i < fitness.length; i++) {
			sumFitness += fitness[i];
		}
		
		System.out.println(sumFitness);

		// normalize fitness vector
		for (int i = 0; i < fitness.length; i++) {
			fitness[i] = (fitness[i] / sumFitness) + previous;
			previous = fitness[i];
		}
		
		parent1Selection = r.nextDouble();
		parent2Selection = r.nextDouble();
		parent1Index = 0;
		parent2Index = 0;

		while (fitness[parent1Index] < parent1Selection && parent1Index < fitness.length-1) {
			parent1Index++;
		}
		while (fitness[parent2Index] < parent2Selection && parent2Index < fitness.length-1) {
			parent2Index++;
		}
		
		return new int[] {parent1Index, parent2Index};
	} // end selection()
	
	/**
	 * Crossover genetic operator
	 * @param parent1 First parent
	 * @param parent2 Second parent
	 * @return child
	 */
	private double[] crossover(double[] parent1, double[] parent2) {

		int curInputs = nInputs;
		int index = 0;
		double[] child = new double[chromosomeLength];

		int iteration = 0;
		for (int i = 0; i < nGenes; i++) {
			if (r.nextDouble() > 0.5) {
				for (int j = index; j < index+curInputs+1; j++) {
					child[j] = parent1[j];
				}
			} else {
				for (int j = index; j < index+curInputs+1; j++) {
					child[j] = parent2[j];
				}
			}
			index += curInputs + 1;
			if (iteration == nNeurons-1) {
				curInputs = nNeurons;
			}
			iteration++;
		}
		
		return child;
	} // end crossover()
	
	/**
	 * Mutation genetic operator
	 * @param chromosome Chromosome to be mutated
	 * @return A mutated version of the chromosome
	 */
	private double[] mutation(double[] chromosome) {
		int gene = r.nextInt(nGenes); // randomly select a gene

		// find indices surrounding gene
		int index = 0;
		int curInputs = nInputs;
		int iteration = 0;
		for (int i = 0; i < gene; i++) {
			index += curInputs + 1;
			if (iteration == nNeurons-1) {
				curInputs = nNeurons;
			}
			iteration++;
		}
		
		double[] child = chromosome.clone();
		for (int i = index; i < index+curInputs; i++) {
			child[index] += (r.nextDouble() - 0.5)*2;
		}
		return child;
	} // end mutation()
	
	public String toString() {
		String result = "";
		for (int i = 0; i < population.length; i++) {
			for (int j = 0; j < population[i].length; j++) {
				result += population[i][j] + " ";
			}
			result += "\n";
		}
		
		return result;
	} // end toString()
}

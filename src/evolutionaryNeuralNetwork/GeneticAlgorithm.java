package evolutionaryNeuralNetwork;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import evolutionaryNeuralNetworkInterfaces.GeneticAlgorithmInterface;

public class GeneticAlgorithm implements GeneticAlgorithmInterface{
	private int chromosomeLength; // number of doubles in the chromosomes
	private int popSize;
	private int nInputs;
	private int nOutputs;
	private int nNeurons;
	private int nLayers;
	private Random r;
	private DataSet learningData;
	private ExecutorService executor;
	private ArrayList<WorkerTask> tasks;
	private ActivationFunction af;
	
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
			int popSize, double crossoverProbability, double mutationProbability, double migrationProbability, 
			 int tournSize, DataSet learningData, ActivationFunction af) {
		
		this.chromosomeLength = nNeurons*(nInputs + nNeurons*(nLayers-1) + nOutputs + nLayers) + nOutputs;
		
		this.r = new Random();
		this.popSize = popSize;
		this.learningData = learningData;
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		this.nLayers = nLayers;
		this.nNeurons = nNeurons;
		this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.af = af;
		
		// initialize the tasks
		this.tasks = new ArrayList<WorkerTask>();
		for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
			tasks.add(new WorkerTask(nInputs, nOutputs, nNeurons, 
					nLayers, popSize, crossoverProbability, mutationProbability, migrationProbability,
					tournSize, learningData, af));
		}
		
		WorkerTask.setIslands(tasks);
		
	} // end constructor
	
	public NeuralNetwork optimize(int iterations) {
		for (int i = 0; i < iterations; i++) {
			iterate();
		}
		executor.shutdown();
		return null;
	}
	
	/**
	 * Run until error reaches some threshold
	 * @param error Error to stop at
	 * @return The best neural network at stop time
	 */
	public NeuralNetwork optimizeUntil(double error) {
		while (iterate() > error) ;
		executor.shutdown();
		return null;
	}

	/**
	 * Transition the population to it's next iteration
	 * @return Minimum error of the new population
	 */
	private double iterate() {
		double minFitness = Double.MAX_VALUE;
		double fitness;
		List<Future<Double>> result = null;
		
		try {
			result = executor.invokeAll(tasks);
		} catch (InterruptedException e) {
			e.printStackTrace();
			return -1;
		}
		
		try {
			for (Future<Double> f : result) {
				fitness = f.get();
				if (fitness < minFitness) {
					minFitness = fitness;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

		System.out.println(minFitness);
		return minFitness;
	} // end iterate()
	
	/**
	 * Generates a set of neural networks from a population of chromosomes
	 * @param population Population of chromosomes
	 * @return An array of neural networks represented by the population
	 */
	private NeuralNetwork[] generateNetworks(double[][] population) {
		NeuralNetwork[] networks = new NeuralNetwork[popSize];
		for (int i = 0; i < popSize; i++) {
			networks[i] = new NeuralNetwork(population[i], nInputs, nOutputs, nLayers, nNeurons, af);
		}
		return networks;
	} // end generateNetworks()
	
	/**
	 * Calculate the fitness of a given neural network given learning data
	 * @param network Neural network to calculate fitness of 
	 * @param learningData Data to learn from
	 * @return fitness of the network
	 */
	public double fitness(NeuralNetwork network, DataSet learningData) {
		double[] activationResult;
		double[] inputs;
		double[] expectedOutputs;
		double error = 0;
		double sumSquaredErrors = 0;

		// fitness: sum of squared errors
		for (int i = 0; i < learningData.getSize(); i++) {
			inputs = learningData.getInputs(i);
			expectedOutputs = learningData.getOutputs(i);
			activationResult = network.feedForward(inputs);

			for (int j = 0; j < expectedOutputs.length; j++) {
				error = expectedOutputs[j] - activationResult[j];
				sumSquaredErrors += (error * error);
			}
		}

		return sumSquaredErrors;
		//return fitness;
	} // end fitness()
	
	/**
	 * Calculate the fitness of a given chromosome given learning data
	 * @param chromosome Chromosome of network to calculate fitness of
	 * @param learningData Data to learn from
	 * @return Fitness of the chromosome
	 */
	public double fitness(double[] chromosome, DataSet learningData) {
		NeuralNetwork nn = new NeuralNetwork(chromosome, nInputs, nOutputs, nLayers, nNeurons, af);
		return fitness(nn, learningData);
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
	 * Find the best neural network out of an array of neural networks, given
	 * the learning data
	 * @param networks Array of learning networks
	 * @param learningData Data for networks to learn and evaluate from
	 * @return The best neural network in the given array
	 */
	private NeuralNetwork bestNN(NeuralNetwork[] networks, DataSet learningData) {
		// find the best network
		double[] fitness = this.fitnessPop(networks, learningData);
		double min = Double.MAX_VALUE;
		int minIndex = 0;
		for (int i = 0; i < fitness.length; i++) {
			if (fitness[i] < min) {
				minIndex = i;
				min = fitness[i];
			}
		}
		
		return networks[minIndex];
	}
}

package evolutionaryNeuralNetwork;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import evolutionaryNeuralNetworkInterfaces.GeneticAlgorithmInterface;

public class GeneticAlgorithm extends DriverUI implements GeneticAlgorithmInterface{
	private int chromosomeLength; // number of doubles in the chromosomes
	private int popSize;
	private int nInputs;
	private int nOutputs;
	private int nNeurons;
	private int nLayers;
	private Random r;
	private double[][] population;
	private DataSet learningData;
	private ExecutorService executor;
	private ArrayList<WorkerThread> threads;
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
			int popSize, double crossoverProbability, double mutationProbability, 
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
		
		this.threads = new ArrayList<WorkerThread>();
		for (int i = 0; i < popSize; i++) {
			threads.add(new WorkerThread(nInputs, nOutputs, nNeurons, 
					nLayers, crossoverProbability, mutationProbability,
					tournSize, learningData, af));
		}
		
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
		executor.shutdown();
		NeuralNetwork[] results = generateNetworks(population);
		double[] fitness = this.fitnessPop(results, learningData);
		
		// find the best network
		double min = Double.MAX_VALUE;
		int minIndex = 0;
		for (int i = 0; i < fitness.length; i++) {
			if (fitness[i] < min) {
				minIndex = i;
				min = fitness[i];
			}
		}
		
		return results[minIndex];
		
	}
	
	/**
	 * Transition the population to it's next iteration
	 * @param population 
	 */
	private void iterate() {
		double[][] newPopulation = new double[population.length][chromosomeLength];
		WorkerThread.setPopulation(population);
		List<Future<Double[]>> result = null;
		int index = 0;
		double[] child;
		
		while (index < popSize) {
			try {
				result = executor.invokeAll(threads);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			for (Future<Double[]> i : result) {
				try {
					child = new double[chromosomeLength];
					Double[] temp = i.get();
					for (int j = 0; j < temp.length; j++) {
						child[j] = temp[j];
					}
					newPopulation[index++] = child;
					//System.out.println(VectorOperations.toString(child));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		this.population = newPopulation;
		
		double minFitness = Double.MAX_VALUE;
		for (int i = 0; i < popSize; i++) {
			if (fitness(newPopulation[i], learningData) < minFitness) {
				minFitness = fitness(newPopulation[i], learningData);
			}
		}
		System.out.println(minFitness);
		
		

	} // end iterate()
	
	private double fitness(double[] chromosome, DataSet learningData) {
		NeuralNetwork nn = new NeuralNetwork(chromosome, nInputs, nOutputs, nLayers, nNeurons, af);
		return fitness(nn, learningData);
	}
	
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
				error = expectedOutputs[j] - activationResult[j];
				sumSquaredErrors += (error * error);
			}
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

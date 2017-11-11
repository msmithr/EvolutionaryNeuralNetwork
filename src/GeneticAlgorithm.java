import java.util.Random;
import NeuralNetwork.NeuralNetwork;

public class GeneticAlgorithm {
	private int chromosomeLength;
	private int popSize;
	private Random r;
	private int nInputs;
	private int nOutputs;
	private int nNeurons;
	private int nLayers;
	private double[][] population;
	private NeuralNetwork[] networks;
	
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
		
		generateNetworks(population);
		
		
	} // end constructor

	// randomly generate a new chromosome
	public double[] randomChromosome() {
		double[] newChromosome = new double[chromosomeLength];
		for (int i = 0; i < chromosomeLength; i++) {
			newChromosome[i] = r.nextDouble() - 0.5;
		}
		return newChromosome;
	}
	
	// generate a set of neural networks from a population
	public void generateNetworks(double[][] population) {
		for (int i = 0; i < population.length; i++) {
			networks[i] = new NeuralNetwork(population[i], nInputs, nOutputs, nLayers, nNeurons);
		}
	}
	
	// crossover operation between two parent chromosomes
	// returns two child chromosomes
	// single point crossover
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
	
	// gaussian mutation function
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

package evolutionaryNeuralNetwork;

import java.util.Random;
import java.util.concurrent.*;

public class WorkerThread implements Callable<Double[]> {

	private double crossoverProbability;
	private double mutationProbability;
	private int tournSize;
	private static double[][] population;
	private DataSet learningData;
	private Random r;
	private int nInputs;
	private int nOutputs;
	private int nNeurons;
	private int nLayers;
	private int nGenes;
	
	public WorkerThread(int nInputs, int nOutputs, int nNeurons, int nLayers, 
			double crossoverProbability, double mutationProbability, int tournSize, DataSet learningData) 
	{
		this.crossoverProbability = crossoverProbability;
		this.mutationProbability = mutationProbability;
		this.tournSize = tournSize;
		this.learningData = learningData;
		this.nGenes = (nNeurons * nLayers) + nOutputs;
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		this.nNeurons = nNeurons;
		this.nLayers = nLayers;
		r = new Random();
	}
	
	public static void setPopulation(double[][] pop) {
		population = pop;
	}
	
	@Override
	public Double[] call() throws Exception {
		double[] child;
		double[] parent1;
		double[] parent2;
		
		parent1 = tournamentSelection(population);
		parent2 = tournamentSelection(population);
		
		// crossover
		if (r.nextDouble() < crossoverProbability) {
			child = crossover(parent1, parent2);
		} else {
			child = parent1.clone();
		}
		
		// mutation
		if (r.nextDouble() < mutationProbability) {
			child = mutation(child);
		}
		Double[] result = new Double[child.length];
		for (int i = 0; i < child.length; i++) {
			result[i] = child[i];
		}
		
		return result;
	}
	
	private double[] tournamentSelection(double[][] population) {
		double[] selection = null;
		double[] individual;
		for (int i = 0; i < tournSize; i++) {
			individual = population[r.nextInt(population.length)];
			if (selection == null || fitness(individual, learningData) < fitness(selection, learningData)) {
				selection = individual;
			}
		}
		
		return selection;
	}
	
	/**
	 * Crossover genetic operator
	 * @param parent1 First parent
	 * @param parent2 Second parent
	 * @return child
	 */
	private double[] crossover(double[] parent1, double[] parent2) {

		int curInputs = nInputs;
		int index = 0;
		double[] child = new double[parent1.length];

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
	
	private double fitness(double[] chromosome, DataSet learningData) {
		NeuralNetwork nn = new NeuralNetwork(chromosome, nInputs, nOutputs, nLayers, nNeurons);
		return fitness(nn, learningData);
	}
	

}

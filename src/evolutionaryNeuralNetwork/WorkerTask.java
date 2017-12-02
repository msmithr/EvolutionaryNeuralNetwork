package evolutionaryNeuralNetwork;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;

public class WorkerTask implements Callable<Double> {

	private double crossoverProbability;
	private double mutationProbability;
	private double migrationProbability;
	private double migrationSize;
	private int tournSize;
	private double[][] population;
	private DataSet learningData;
	private Random r;
	private int nInputs;
	private int nOutputs;
	private int nNeurons;
	private int nLayers;
	private int nGenes;
	private int chromosomeLength;
	private int popSize;
	private ActivationFunction af;
	private ConcurrentLinkedDeque<Double[]> migrationQueue;
	private static ArrayList<WorkerTask> islands;
	
	public WorkerTask(int nInputs, int nOutputs, int nNeurons, int nLayers, int popSize, 
			double crossoverProbability, double mutationProbability, double migrationProbability,
			int tournSize, DataSet learningData, ActivationFunction af) 
	{
		this.chromosomeLength = nNeurons*(nInputs + nNeurons*(nLayers-1) + nOutputs + nLayers) + nOutputs;
		this.crossoverProbability = crossoverProbability;
		this.mutationProbability = mutationProbability;
		this.migrationProbability = migrationProbability;
		this.migrationSize = migrationSize;
		this.tournSize = tournSize;
		this.learningData = learningData;
		this.nGenes = (nNeurons * nLayers) + nOutputs;
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		this.nNeurons = nNeurons;
		this.nLayers = nLayers;
		this.af = af;
		this.popSize = popSize;
		r = new Random();
		this.migrationQueue = new ConcurrentLinkedDeque<Double[]>();
		
		
		population = new double[popSize][];
		for (int i = 0; i < popSize; i++) {
			population[i] = randomChromosome();
		}
	}
	
	public static void setIslands(ArrayList<WorkerTask> iArray) {
		islands = iArray;
	}

	@Override
	public Double call() throws Exception {
		double[] child;
		double[] parent1;
		double[] parent2;
		int index = 0;
		double[][] newPopulation = new double[popSize][];
		
		while (index < popSize) {
			if (!migrationQueue.isEmpty()) {
				child = new double[chromosomeLength];
				Double[] child_object = migrationQueue.getFirst();
				for (int i = 0; i < chromosomeLength; i++) {
					child[i] = child_object[i];
				}
				newPopulation[index++] = child;
				continue;
			}
			
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
			
			if (r.nextDouble() < migrationProbability ) {
				int selection = r.nextInt(islands.size());
				if (islands.get(selection) == this) {
					selection = (selection + 1) % islands.size();
				}
				
			}
			
			newPopulation[index++] = child;
		}
		
		population = newPopulation;
		
		// find the min fitness and return it
		Double minFitness = Double.MAX_VALUE;
		for (int i = 0; i < popSize; i++) {
			if (fitness(newPopulation[i], learningData) < minFitness) {
				minFitness = fitness(newPopulation[i], learningData);
			}
		}
		
		learningData.shuffle(); // shuffle the data for better learnings
		return minFitness;
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
	
	public void migrate(double[] child) {
		Double[] toAdd = new Double[child.length];
		for (int i = 0; i < child.length; i++) {
			toAdd[i] = child[i];
		}
		migrationQueue.addLast(toAdd);
	}
	
	
	
	private double fitness(double[] chromosome, DataSet learningData) {
		NeuralNetwork nn = new NeuralNetwork(chromosome, nInputs, nOutputs, nLayers, nNeurons, af);
		return fitness(nn, learningData);
	} // end fitness()
	
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
	

}

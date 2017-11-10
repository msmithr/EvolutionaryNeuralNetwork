import java.util.Random;

public class GeneticAlgorithm {
	private int chromosomeLength;
	private Random r;
	
	public GeneticAlgorithm(int nInputs, int nOutputs, int nLayers, int nNeurons) {
		this.chromosomeLength = nNeurons*(nInputs + nNeurons*(nLayers-1) + nOutputs);
		this.r = new Random();
	} // end constructor

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
		return Integer.toString(this.chromosomeLength);
	}
}

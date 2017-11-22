import neuralnetwork.VectorOperations;
import GeneticAlgorithm.DataSet;
import GeneticAlgorithm.GeneticAlgorithm;
import neuralnetwork.NeuralNetwork;
import GeneticAlgorithm.OldGA;
import neuralnetwork.NeuralNetwork;
//import OldNN.NeuralNetwork;

public class Driver {
	public static void main(String[] args) {
		
		//GeneticAlgorithm GA = new GeneticAlgorithm(1, 4, 2, 2, 100, 0.7, 0.1, null);
		
		//double[] chromosome1 = {1,0.5,2,0.5,1,3,0.5,2,4,0.5,-1,-1,0.5,0,0,0.5,1,1,0.5,2,2,0.5};
		//double[] chromosome2 = {7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7,7};
		
		//System.out.println(VectorOperations.toString(GA.crossover(chromosome1, chromosome2)));
		

		DataSet learningData = new DataSet(4, 1);
		learningData.addData(new double[] {0, 0, 0, 0}, new double[] {0});
		learningData.addData(new double[] {0, 0, 0, 1}, new double[] {1});
		learningData.addData(new double[] {0, 0, 1, 0}, new double[] {0});
		learningData.addData(new double[] {0, 1, 0, 0}, new double[] {1});
		learningData.addData(new double[] {1, 0, 0, 0}, new double[] {0});
		GeneticAlgorithm GA = new GeneticAlgorithm(4, 1, 2, 4, 100, 0.75, 0.10, learningData);

		//System.out.println(GA);

		NeuralNetwork nn = GA.optimize();
		
		System.out.println(nn);
		
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 0, 0, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 0, 0, 1})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 0, 1, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 1, 0, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1, 0, 0, 0})));

		//System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1})));
		//System.out.println(nn);


	}
	
}
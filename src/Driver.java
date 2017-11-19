import neuralnetwork.VectorOperations;
import GeneticAlgorithm.DataSet;
import GeneticAlgorithm.GeneticAlgorithm;
import neuralnetwork.NeuralNetwork;

public class Driver {
	public static void main(String[] args) {
		/*
		DataSet learningData = new DataSet(2, 1);
		learningData.addData(new double[] {0, 0}, new double[] {0});
		learningData.addData(new double[] {1, 0}, new double[] {0});
		learningData.addData(new double[] {0, 1}, new double[] {0});
		learningData.addData(new double[] {1, 1}, new double[] {0});
		GeneticAlgorithm GA = new GeneticAlgorithm(2, 1, 3, 5, 100, 0.7, 0.1, learningData);
		
		NeuralNetwork nn = GA.optimize();
		
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 1})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1, 1})));
		*/
		double[] chromosome = {1,2,0.5,0.5,1,2,3,4,0.5,0.5,-1,0,1,2,-1,0,1,2,0.5,0.5,0.5,0.5};
		NeuralNetwork nn = new NeuralNetwork(chromosome, 1, 4, 2, 2);
		
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1})));
	}
	
}
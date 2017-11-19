import neuralnetwork.VectorOperations;
import GeneticAlgorithm.DataSet;
import GeneticAlgorithm.GeneticAlgorithm;
import neuralnetwork.NeuralNetwork;

public class Driver {
	public static void main(String[] args) {
		
		DataSet learningData = new DataSet(2, 1);
		learningData.addData(new double[] {0, 0}, new double[] {0});
		learningData.addData(new double[] {1, 0}, new double[] {0});
		learningData.addData(new double[] {0, 1}, new double[] {0});
		learningData.addData(new double[] {1, 1}, new double[] {1});
		GeneticAlgorithm GA = new GeneticAlgorithm(2, 1, 1, 2, 100, 0.75, 0.1, learningData);
		
		//System.out.println(GA);

		NeuralNetwork nn = GA.optimize();
		
		System.out.println(nn);
		
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 1})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1, 1})));

		//System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1})));
		//System.out.println(nn);

	}
	
}
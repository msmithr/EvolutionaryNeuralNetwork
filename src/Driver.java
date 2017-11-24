import evolutionaryNeuralNetwork.DataSet;
import evolutionaryNeuralNetwork.GeneticAlgorithm;
import evolutionaryNeuralNetwork.NeuralNetwork;
import evolutionaryNeuralNetwork.VectorOperations;

public class Driver {
	public static void main(String[] args) {

		DataSet learningData = new DataSet(4, 1);
		learningData.addData(new double[] {0, 0, 0, 0}, new double[] {0});
		learningData.addData(new double[] {0, 0, 0, 1}, new double[] {1});
		learningData.addData(new double[] {0, 0, 1, 0}, new double[] {0});
		learningData.addData(new double[] {0, 1, 0, 0}, new double[] {1});
		learningData.addData(new double[] {1, 0, 0, 0}, new double[] {0});
		GeneticAlgorithm GA = new GeneticAlgorithm(4, 1, 2, 4, 30, 0.75, 0.3, learningData);

		NeuralNetwork nn = GA.optimize(100);
		
		System.out.println(nn);
		
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 0, 0, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 0, 0, 1})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 0, 1, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 1, 0, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1, 0, 0, 0})));


	}
	
}
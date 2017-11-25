import evolutionaryNeuralNetwork.DataSet;
import evolutionaryNeuralNetwork.GeneticAlgorithm;
import evolutionaryNeuralNetwork.NeuralNetwork;
import evolutionaryNeuralNetwork.VectorOperations;

public class Driver {
	public static void main(String[] args) {

		DataSet learningData = new DataSet(2, 1);
		learningData.addData(new double[] {0, 0}, new double[] {0});
		learningData.addData(new double[] {0, 1}, new double[] {1});
		learningData.addData(new double[] {1, 0}, new double[] {1});
		learningData.addData(new double[] {1, 1}, new double[] {0});
		GeneticAlgorithm GA = new GeneticAlgorithm(2, 1, 2, 2, 300, 0.75, 0.4, learningData);

		NeuralNetwork nn = GA.optimize(1000);
		
		System.out.println(nn);
		
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0, 1})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1, 0})));
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {1, 1})));


	}
	
}
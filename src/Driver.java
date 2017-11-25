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
		
		NeuralNetwork nn = new NeuralNetwork(2, 1, 1, 2, learningData);
		
		nn.backProp(new double[] {1, 0},new double[] {1});
		
		System.out.println("Neural Network: ");
		System.out.println(nn);

		System.out.println("Output: ");
		System.out.println(VectorOperations.toString(nn.feedForward(new double[] {0,0})));
	
		
		/*
		double[] chromosome = new double[] {0.074414, 0.92288, 0.59106, 
				0.976635, 0.9076044, 0.5827188, 0.9660535, 0.750396, 0.96719};
		
		NeuralNetwork nn = new NeuralNetwork(chromosome, 2, 1, 1, 2);
		
		nn.backProp(new double[] {1,0}, new double[] {1});
		*/
		
		//System.out.println("FeedForward: " + VectorOperations.toString(nn.feedForward(new double[] {1, 0})));
	}
	
}
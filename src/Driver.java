import evolutionaryNeuralNetwork.DataSet;
import evolutionaryNeuralNetwork.GeneticAlgorithm;
import evolutionaryNeuralNetwork.NeuralNetwork;
import evolutionaryNeuralNetwork.VectorOperations;

public class Driver {
	public static void main(String[] args) {

		DataSet learningData = new DataSet(2, 1);
		learningData.addData(new double[] {0,0}, new double[] {0});
		learningData.addData(new double[] {0,1}, new double[] {0});
		learningData.addData(new double[] {1,0}, new double[] {0});
		learningData.addData(new double[] {1,1}, new double[] {1});
		
		
        GeneticAlgorithm GA = new GeneticAlgorithm(2, 1, 2, 100, 10, 0.7, 0.3, 2, learningData);
        NeuralNetwork result = GA.optimize(10000);
        

		
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0,0})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0,1})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {1,0})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {1,1})));
        
        /*
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0.1})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0.2})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0.3})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0.4})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0.5})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0.6})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0.7})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0.8})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {0.9})));
        System.out.println(VectorOperations.toString(result.feedForward(new double[] {1})));
        */
	}
	
	private static void addData(DataSet learningData) {
		learningData.addData(new double[] {0.1}, new double[] {0});
		learningData.addData(new double[] {0.3}, new double[] {0});
		learningData.addData(new double[] {0.5}, new double[] {0});
		learningData.addData(new double[] {0.7}, new double[] {1});
		learningData.addData(new double[] {0.9}, new double[] {1});
		learningData.addData(new double[] {1}, new double[] {1});
	}
            
}

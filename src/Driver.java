import NeuralNetwork.NeuralNetwork;

public class Driver {
	public static void main(String[] args) {
		//NeuralNetwork n = new NeuralNetwork(new double[] {1,2,3,4,5,6,7,8,9,10,11,12}, 3, 1, 2, 2);
		//System.out.println(n);

		//System.out.println(n.feedForward(new double[] {1,1})[0]);
		double[] a = {1,2,3,4,5,6,7,8,9,10,11,12};
		double[] b = {5,4,3,2,1,6,5,4,3,2,1,2};
		double[][] result;
		GeneticAlgorithm GA = new GeneticAlgorithm(3,1,2,2);
		result = GA.crossover(a,b);
		
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				System.out.print(result[i][j] + " ");
			}
			System.out.print("\n");
		}
		
		b = GA.mutation(a);
		for (int i = 0; i < b.length; i++) {
			System.out.println(b[i]);
		}
	}
	
}



public class Driver {
	public static void main(String[] args) {
		NeuralNetwork n = new NeuralNetwork(new int[] {3,3}, 2, 1);
		
		
		for (int i = 0; i < 1000; i++) {
			n.feedForward(new double[] {0,0});
			n.backPropagate(new double[] {0});
			n.feedForward(new double[] {1,0});
			n.backPropagate(new double[] {1});
			n.feedForward(new double[] {0,1});
			n.backPropagate(new double[] {0});
			n.feedForward(new double[] {1,1});
			n.backPropagate(new double[] {0});
		}
		

		n.feedForward(new double[] {0,0});
		System.out.println(n.getResults()[0]);
		n.feedForward(new double[] {1,0});
		System.out.println(n.getResults()[0]);
		n.feedForward(new double[] {0,1});
		System.out.println(n.getResults()[0]);
		n.feedForward(new double[] {1,1});
		System.out.println(n.getResults()[0]);
	}
}



public class Driver {
	public static void main(String[] args) {
		NeuralNetwork n = new NeuralNetwork(new double[] {1,2,3,4,5,6,7,8,9,10,11,12}, 3, 1, 2, 2);
		System.out.println(n);

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

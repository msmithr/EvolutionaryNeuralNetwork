
public class Driver {
	public static void main(String[] args) {
		NeuralNetwork n = new NeuralNetwork(2,3,2,2);
		System.out.println(n);
		MathLib.printVector(n.activate(new double[] {1,2}));
	}
}

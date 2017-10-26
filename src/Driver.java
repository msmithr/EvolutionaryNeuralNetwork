
public class Driver {
	public static void main(String[] args) {
		NeuralNetwork n = new NeuralNetwork(3,3,1,2);
		System.out.println(n);
		MathLib.printVector(n.activate(new double[] {1,2}));
	}
}

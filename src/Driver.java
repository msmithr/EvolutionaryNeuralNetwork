
public class Driver {
	public static void main(String[] args) {
		Perceptron p = new Perceptron(0.1, 2);
		
		// AND
		for (int i = 0; i < 100; i++) {
			p.learn(new double[] {0, 0}, 0);
			p.learn(new double[] {0, 1}, 0);
			p.learn(new double[] {1, 0}, 0);
			p.learn(new double[] {1, 1}, 1);
		}
		
		System.out.println("0, 0: " + p.activate(new double[] {0,0}));
		System.out.println("0, 1: " + p.activate(new double[] {0,1}));
		System.out.println("1, 0: " + p.activate(new double[] {1,0}));
		System.out.println("1, 1: " + p.activate(new double[] {1,1}));
		
		System.out.println("\n" + p);
	}
}

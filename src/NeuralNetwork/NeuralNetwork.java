package NeuralNetwork;

public class NeuralNetwork {
	private double[][][] weightMatrices;
	private double[][] threshholdVectors;
	
	public NeuralNetwork(double[] chromosome, int nInputs, int nOutputs, int nLayers, int nNeurons) {
		weightMatrices = new double[nLayers + 1][][];
		threshholdVectors = new double[nLayers + 1][];
		
	}
	
}

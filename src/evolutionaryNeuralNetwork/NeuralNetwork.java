package evolutionaryNeuralNetwork;

import evolutionaryNeuralNetworkInterfaces.NeuralNetworkInterface;

public class NeuralNetwork implements NeuralNetworkInterface{
	private double[][][] weightMatrices;
	private double[][] thresholdVectors;
	private ActivationFunction af;
	
	/**
	 * Neural Network 
	 * @param chromosome Chromosome encoding the network
	 * @param nInputs Number of inputs
	 * @param nOutputs Number of outputs
	 * @param nLayers Number of hidden layers
	 * @param nNeurons Number of neurons per hidden layer
	 */
	public NeuralNetwork(double[] chromosome, int nInputs, int nOutputs, int nLayers, int nNeurons, ActivationFunction af) {
		weightMatrices = new double[nLayers + 1][][];
		thresholdVectors = new double[nLayers + 1][];
		this.af = af;
		
		int chromosomeIndex = 0; // current index in the chromosome
		int curInputs = nInputs;
		int curOutputs = nNeurons;
		
		// decode the chromosome
		for (int i = 0; i < nLayers + 1; i++) {
			double[][] newMatrix = new double[curInputs][curOutputs];
			double[] newVector = new double[curOutputs];
			
			for (int j = 0; j < curOutputs; j++) {
				for (int k = 0; k < curInputs; k++) {
					newMatrix[k][j] = chromosome[chromosomeIndex++];
				}
				newVector[j] = chromosome[chromosomeIndex++];
			}
			weightMatrices[i] = newMatrix;
			thresholdVectors[i] = newVector;
			
			curInputs = curOutputs;
			curOutputs = i == nLayers-1 ? nOutputs : nNeurons;
		} // end for
		
	} // end constructor
	
	public double[] feedForward(double[] inputs) {
		double[] state = inputs;
		
		for (int i = 0; i < weightMatrices.length; i++) {
			state = VectorOperations.multiply(state, weightMatrices[i]);
			state = VectorOperations.sum(state, VectorOperations.neg(thresholdVectors[i]));
			
			switch (af) {
			case STEP:
				state = VectorOperations.step(state);
				break;
			case SIGMOID:
				state = VectorOperations.sigmoid(state);
				break;
			case TANH:
				state = VectorOperations.tanh(state);
				break;
			case SIGMOID_STEP:
				if (i != weightMatrices.length-1) {
					state = VectorOperations.sigmoid(state);
				} else {
					state = VectorOperations.step(state);
				}		
			} // end switch/case
		}
		return state;
	} // end feedForward()
	
	public String toString() {
		String result = "";
		for (int i = 0; i < weightMatrices.length; i++) {
			result += VectorOperations.toString(weightMatrices[i]);
			result += "\n";
			result += VectorOperations.toString(new double[][] {thresholdVectors[i]});
			result += "\n";
		}
		return result;
	} // end toString()
	
} // end class

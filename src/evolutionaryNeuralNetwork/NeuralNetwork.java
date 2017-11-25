package evolutionaryNeuralNetwork;

import java.util.Random;

import evolutionaryNeuralNetworkInterfaces.NeuralNetworkInterface;

public class NeuralNetwork implements NeuralNetworkInterface{
	private double[][][] weightMatrices;
	private double[][] thresholdVectors;
	private Random r;
	private int nLayers;
	private int nNeurons;
	private int nOutputs;
	private int nInputs;
	private double alpha = 0.1;
	
	/**
	 * Neural Network 
	 * @param chromosome Chromosome encoding the network
	 * @param nInputs Number of inputs
	 * @param nOutputs Number of outputs
	 * @param nLayers Number of hidden layers
	 * @param nNeurons Number of neurons per hidden layer
	 */
	public NeuralNetwork(double[] chromosome, int nInputs, int nOutputs, int nLayers, int nNeurons) {
		weightMatrices = new double[nLayers + 1][][];
		thresholdVectors = new double[nLayers + 1][];
		
		int chromosomeIndex = 0; // current index in the chromosome
		int curInputs = nInputs;
		int curOutputs = nNeurons;
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		this.nLayers = nLayers;
		this.nNeurons = nNeurons;
		
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
	
	// alternative constructor for backprop things
	public NeuralNetwork(int nInputs, int nOutputs, int nLayers, int nNeurons, DataSet learningData) {
		weightMatrices = new double[nLayers + 1][][];
		thresholdVectors = new double[nLayers + 1][];
		
		int curInputs = nInputs;
		int curOutputs = nNeurons;
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		this.nLayers = nLayers;
		this.nNeurons = nNeurons;
		
		r = new Random();
		
		for (int i = 0; i < nLayers + 1; i++) {
			double[][] newMatrix = new double[curInputs][curOutputs];
			double[] newVector = new double[curOutputs];
			
			for (int j = 0; j < curOutputs; j++) {
				for (int k = 0; k < curInputs; k++) {
					newMatrix[k][j] = r.nextDouble();
				}
				newVector[j] = r.nextDouble();
			}
			weightMatrices[i] = newMatrix;
			thresholdVectors[i] = newVector;
			
			curInputs = curOutputs;
			curOutputs = i == nLayers-1 ? nOutputs : nNeurons;
		}
	}
	
	public double[] feedForward(double[] inputs) {
		double[] state = inputs;
		
		for (int i = 0; i < weightMatrices.length; i++) {
			state = VectorOperations.multiply(state, weightMatrices[i]);
			state = VectorOperations.sum(state, VectorOperations.neg(thresholdVectors[i]));
			state = VectorOperations.sigmoid(state);
		}
		return state;
	} // end feedForward()
	
	public void backProp(double[] inputs, double[] expected) {
		double[] state = inputs;
		int index = 0;
		double[] neuronOutputs = new double[(nNeurons * nLayers) + nOutputs];
		double[] gradients = new double[(nNeurons * nLayers) + nOutputs];
		double[] error = new double[nOutputs];
		
		for (int i = 0; i < weightMatrices.length; i++) {
			state = VectorOperations.multiply(state, weightMatrices[i]);
			state = VectorOperations.sum(state, VectorOperations.neg(thresholdVectors[i]));
			state = VectorOperations.sigmoid(state);
			for (int j = 0; j < state.length; j++) {
				neuronOutputs[index++] = state[j];
			}
		}
		
		error = VectorOperations.reverse(VectorOperations.sum(expected, VectorOperations.neg(state)));
		double[] formattedOutputs = neuronOutputs.clone();
		
		double tempvalue;
		for (int layer = 0; layer < nLayers; layer++) {
			for (int i = layer*nNeurons; i < layer*nNeurons+(nNeurons/2); i++) {
				tempvalue = formattedOutputs[i];
				formattedOutputs[i] = formattedOutputs[layer*nNeurons+nNeurons - i - 1];
				formattedOutputs[layer*nNeurons+nNeurons - i - 1] = tempvalue;
			}
		}
		formattedOutputs = VectorOperations.reverse(neuronOutputs);
		
		System.out.println("OUTPUTS: " + VectorOperations.toString(neuronOutputs));
		
		double[][] temp;
		double[][][] rWeights = weightMatrices.clone();
		for (int i = 0; i < rWeights.length; i++) {
			temp = rWeights[rWeights.length - i - 1];
			rWeights[i] = rWeights[rWeights.length - i - 1];
			rWeights[rWeights.length - i - 1] = temp;
		}

		// calculate the output gradient
		index = 0;
		for (int i = 0; i < nOutputs; i++) {
			gradients[index++] = error[i]*formattedOutputs[i]*(1-formattedOutputs[i]);
		}
		
		// calculate the rest of the gradients
		int curInputs = nInputs;
		for (int layer = 0; layer < nLayers; layer++) {
			for (int neuron = 0; neuron < nNeurons; neuron++) {
				double sum = 0;
				for (int input = 1; input < curInputs; input++) {
					sum += gradients[index - input]*rWeights[layer][neuron][input-1];
					System.out.println("sum += " + gradients[index - input] + " * " + rWeights[layer][neuron][input-1]);
				}
				tempvalue = formattedOutputs[nNeurons*layer + neuron + 1];
				gradients[nNeurons*layer + neuron + 1] = sum*tempvalue*(1-tempvalue);
				
				System.out.println("Weights::::");
				for (int input = 1; input < curInputs; input++) {
					System.out.println(rWeights[layer][neuron][input-1]);
				}
				curInputs = nNeurons;
			}
		}
		
		curInputs = nInputs;
		int curOutputs = nNeurons;
		for (int layer = 0; layer < nLayers-1; layer++) {
			double[][] matrix = new double[curInputs][curOutputs];
		}
		
		//gradients = VectorOperations.reverse(gradients);
		
		System.out.println(VectorOperations.toString(gradients));
		
	}
	
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

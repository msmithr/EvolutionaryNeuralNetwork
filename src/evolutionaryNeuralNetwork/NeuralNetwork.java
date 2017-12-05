package evolutionaryNeuralNetwork;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import evolutionaryNeuralNetworkInterfaces.NeuralNetworkInterface;


public class NeuralNetwork implements NeuralNetworkInterface {
	private double[][][] weightMatrices;
	private double[][] thresholdVectors;
	private double[] chromosome;
	private int nInputs;
	private int nOutputs;
	private int nLayers;
	private int nNeurons;
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
		initialize(chromosome, nInputs, nOutputs, nLayers, nNeurons, af);
	} // end constructor
	
	/**
	 * Alternative constructor, loading NN parameters from a given file
	 * @param filename Name of the file to load from
	 * @throws IOException
	 */
	public NeuralNetwork(String filename) throws IOException {
		FileReader filereader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(filereader);
		
		String nInputsString = bufferedReader.readLine();
		String nOutputsString = bufferedReader.readLine();
		String nLayersString = bufferedReader.readLine();
		String nNeuronsString = bufferedReader.readLine();
		String afString = bufferedReader.readLine();
		String chromosomeString = bufferedReader.readLine();
		
		bufferedReader.close();
		
		int nInputs = Integer.parseInt(nInputsString);
		int nOutputs = Integer.parseInt(nOutputsString);
		int nLayers = Integer.parseInt(nLayersString);
		int nNeurons = Integer.parseInt(nNeuronsString);
		ActivationFunction af = ActivationFunction.valueOf(afString);
		String[] chromosomeArray = chromosomeString.split(" ");
		
		double[] chromosome = new double[nNeurons*(nInputs + nNeurons*(nLayers-1) + nOutputs + nLayers) + nOutputs];
		
		for (int i = 0; i < chromosome.length; i++) {
			chromosome[i] = Double.parseDouble(chromosomeArray[i]);
		}
		
		initialize(chromosome, nInputs, nOutputs, nLayers, nNeurons, af);
	}
	
	private void initialize(double[] chromosome, int nInputs, int nOutputs, int nLayers, int nNeurons, ActivationFunction af) {
		this.weightMatrices = new double[nLayers + 1][][];
		this.thresholdVectors = new double[nLayers + 1][];
		this.chromosome = chromosome;
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		this.nLayers = nLayers;
		this.nNeurons = nNeurons;
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
	}
	
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
	
	public void save(String filename) throws IOException {
		FileWriter filewriter = new FileWriter(filename);
		BufferedWriter bufferedWriter = new BufferedWriter(filewriter);
		String toWrite = "";
		
		toWrite += Integer.toString(nInputs) + '\n';
		toWrite += Integer.toString(nOutputs) + '\n';
		toWrite += Integer.toString(nLayers) + '\n';
		toWrite += Integer.toString(nNeurons) + '\n';
		toWrite += af;
		
		toWrite += '\n';
		
		for (int i = 0; i < chromosome.length; i++) {
			toWrite += chromosome[i] + " ";
		}
		
		toWrite += '\n';
		
		bufferedWriter.write(toWrite);
		bufferedWriter.close();
	}
	

}// end class

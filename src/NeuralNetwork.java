

public class NeuralNetwork {
	private Neuron[][] nArray; // jagged array of neurons
	private int nNeurons;
	private int nLayers;
	private int nInputs;
	private int nOutputs;
	
	// chromosome structure: just the weights
	public NeuralNetwork(double[] chromosome, int nInputs, int nOutputs, int nLayers, int nNeurons) {
		this.nNeurons = nNeurons;
		this.nLayers = nLayers;
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		
		int index = 0; // current index in chromosome
		double[] weights;
		
		// initialize jagged array
		nArray = new Neuron[nLayers+2][];
		
		// initialize input neurons
		nArray[0] = new Neuron[nInputs];
		for (int i = 0; i < nInputs; i++) {
			weights = new double[nNeurons];
			for (int j = 0; j < nNeurons; j++) {
				weights[j] = chromosome[index++];
			}
			nArray[0][i] = new Neuron(i, weights);
		}
		
		// initialize hidden neurons ((all but last layer)
		for (int i = 1; i < nArray.length-2; i++) {
			nArray[i] = new Neuron[nNeurons];
			for (int j = 0; j < nNeurons; j++) {
				weights = new double[nNeurons];
				for (int k = 0; k < nNeurons; k++) {
					weights[k] = chromosome[index++];
				}
				nArray[i][j] = new Neuron(j, weights);
			}
		}
		
		// initialize last layer of hidden neurons
		nArray[nArray.length-2] = new Neuron[nNeurons];
		for (int i = 0; i < nNeurons; i++) {
			weights = new double[nOutputs];
			for (int j = 0; j < nOutputs; j++) {
				weights[j] = chromosome[index++];
			}
			nArray[nArray.length-2][i] = new Neuron(i, weights);
		}

		// initialize output neurons
		nArray[nLayers+1] = new Neuron[nOutputs];
		for (int i = 0; i < nOutputs; i++) {
			nArray[nLayers+1][i] = new Neuron(i, new double[0]);
		}

	} // end constructor
	
	public void feedForward(double[] inputs) {
		Neuron[] prevLayer;
		
		// initialize input neurons
		for (int i = 0; i < inputs.length; i++) {
			nArray[0][i].setOutput(inputs[i]);
		}
		
		// propagate
		for (int i = 1; i < nArray.length; i++) {
			prevLayer = nArray[i-1];
			for (int j = 0; j < nArray[i].length; j++) {
				nArray[i][j].feedForward(prevLayer);
			}
		}

	} // end feedForward
	
	public double[] getResults() {
		double[] result = new double[nOutputs];

		for (int i = 0; i < nOutputs; i++) {
			result[i] = nArray[nArray.length-1][i].getOutput();
		}
		
		return result;
	}
	
	public String toString() {
		String result = "";
		for (int i = 0; i < nArray.length; i++) {
			for (int j = 0; j < nArray[i].length; j++) {
				result += nArray[i][j].toString() + " ";
			}
			result += "\n";
		}
		return result;
	}
}

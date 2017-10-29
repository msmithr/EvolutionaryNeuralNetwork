

public class NeuralNetwork {
	private Neuron[][] nArray; // jagged array of neurons
	private int nNeurons;
	private int nLayers;
	private int nInputs;
	private int nOutputs;
	private double error;
	
	public NeuralNetwork(int[] chromosome, int nInputs, int nOutputs) {
		int nLayers = chromosome[0];
		int nNeurons = chromosome[1];
		
		this.nNeurons = nNeurons;
		this.nLayers = nLayers;
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		
		// initialize jagged array
		nArray = new Neuron[nLayers+2][];
		
		// initialize input neurons
		nArray[0] = new Neuron[nInputs];
		for (int i = 0; i < nInputs; i++) {
			nArray[0][i] = new Neuron(nNeurons, i);
		}
		
		// initialize output neurons
		nArray[nLayers+1] = new Neuron[nOutputs];
		for (int i = 0; i < nOutputs; i++) {
			nArray[nLayers+1][i] = new Neuron(0, i);
		}
		
		// initialize hidden neurons
		for (int i = 1; i < nArray.length-1; i++) {
			nArray[i] = new Neuron[nNeurons];
			for (int j = 0; j < nNeurons; j++) {
				nArray[i][j] = new Neuron(nNeurons, j);
			}
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
	
	public void backPropagate(double[] targets) {
		double error = 0;
		double temp;
		for (int i = 0; i < nOutputs; i++) {
			temp = targets[i] - nArray[nArray.length-1][i].getOutput(); 
			error += temp*temp;
		}
		error /= nOutputs;
		this.error = Math.sqrt(error);

		// output layer gradients
		for (int i = 0; i < nOutputs; i++) {
			nArray[nArray.length-1][i].calcOutputGradients(targets[i]);
		}

		// hidden layer gradients
		for (int i = nLayers; i > 0; i--) {
			Neuron[] hiddenLayer = nArray[i];
			Neuron[] nextLayer = nArray[i+1];
			
			for (int j = 0; j < hiddenLayer.length; j++) {
				hiddenLayer[j].calcHiddenGradients(nextLayer);
			}
		}
		
		// update weights
		for (int i = nLayers; i > 0; i--) {
			Neuron[] layer = nArray[i];
			Neuron[] prevLayer = nArray[i-1];
			
			for (int j = 0; j < layer.length; j++) {
				layer[j].adjustWeights(prevLayer);
			}
		}
	} // end backPropagtion
	
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

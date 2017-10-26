
public class NeuralNetwork {
	Neuron[][] nArray;
	
	public NeuralNetwork(int numInputs, int numOutputs, int numLayers, int nodesPerLayer) {
		// initialize jagged array
		nArray = new Neuron[numLayers + 1][];
		nArray[nArray.length-2] = new Neuron[numOutputs];
		nArray[nArray.length-1] = new Neuron[numOutputs];
		
		
		nArray = new Neuron[numLayers + 1][];
		for (int i = 0; i < nArray.length-1; i++) {
			nArray[i] = new Neuron[numLayers];
		}
		nArray[nArray.length-1] = new Neuron[numOutputs];
		
		for (int i = 0; i < nArray.length-2; i++) {
			for (int j = 0; j < numLayers; j++) {
				nArray[i][j] = new Neuron(0.2, numLayers);
			}
		}
		
		for (int i = 0; i < numLayers; i++) {
			nArray[nArray.length-2][i] = new Neuron(0.2, numOutputs);
		}
		
		for (int i = 0; i < numOutputs; i++) {
			nArray[nArray.length-1][i] = new Neuron(0.2, 1);
		}
	} // end constructor
	
	public double[] activate(double[] inputs) {
		double[] result = null;

		for (int i = 0; i < nArray.length; i++) { // for each layer
			result = new double[nArray[i][0].getNumOutputs()]; // set result to an array of the correct size
			for (int j = 0; j < nArray[i].length; j++) { // for each node in the current layer
				result = MathLib.vectorSum(result, nArray[i][j].activate(inputs)); // sum the results
			}
			inputs = result;
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
	} // end toString()

} // end class

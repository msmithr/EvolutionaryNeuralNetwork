package evolutionaryNeuralNetworkInterfaces;

public interface DataSetInterface {
	/**
	 * Add some data to the data set
	 * @param inputs Array of doubles representing an input
	 * @param outputs Array of doubles representing expected outputs
	 */
	public void addData(double[] inputs, double[] outputs);
	
	/**
	 * Get a specific input array
	 * @param index Index of input array to get
	 * @return Input array at the given index
	 */
	public double[] getInputs(int index);
	
	/**
	 * Get a specific output array
	 * @param index Index of output array to get
	 * @return Output array at the given index
	 */
	public double[] getOutputs(int index);
	
	/**
	 * Get current size of dataset
	 * @return size of data set
	 */
	public int getSize();
}

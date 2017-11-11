package GeneticAlgorithm;

import java.util.ArrayList;

public class DataSet {
	private ArrayList<dataElement> data;
	
	public DataSet(int nInputs, int nOutputs) {
		data = new ArrayList<dataElement>();
	}
	
	/**
	 * Add some data to the data set
	 * @param inputs Array of doubles representing an input
	 * @param outputs Array of doubles representing expected outputs
	 */
	public void addData(double[] inputs, double[] outputs) {
		data.add(new dataElement(inputs, outputs));
	}
	
	/**
	 * Get a specific input array
	 * @param index Index of input array to get
	 * @return Input array at the given index
	 */
	public double[] getInputs(int index) {
		return data.get(index).getInputs();
	}
	
	/**
	 * Get a specific output array
	 * @param index Index of output array to get
	 * @return Output array at the given index
	 */
	public double[] getOutputs(int index) {
		return data.get(index).getOutputs();
	}
	
	/**
	 * Get current size of dataset
	 * @return size of data set
	 */
	public int getSize() {
		return data.size();
	}
	
	private class dataElement {
		private double[] inputs;
		private double[] outputs;
		
		public dataElement(double[] inputs, double[] outputs) {
			this.inputs = inputs;
			this.outputs = outputs;
		} // end constructor
		
		public double[] getOutputs() {
			return this.outputs;
		}
		
		public double[] getInputs() {
			return this.inputs;
		}
		
	} // end class
} // end class

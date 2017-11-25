package evolutionaryNeuralNetwork;

import java.util.ArrayList;

import evolutionaryNeuralNetworkInterfaces.DataSetInterface;

public class DataSet implements DataSetInterface{
	private ArrayList<dataElement> data;
	private int nInputs;
	private int nOutputs;
	
	public DataSet(int nInputs, int nOutputs) {
		data = new ArrayList<dataElement>();
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
	}
	
	public void addData(double[] inputs, double[] outputs) {
		data.add(new dataElement(inputs, outputs));
	}
	
	public double[] getInputs(int index) {
		return data.get(index).getInputs();
	}
	
	public double[] getOutputs(int index) {
		return data.get(index).getOutputs();
	}
	
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

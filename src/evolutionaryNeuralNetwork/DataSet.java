package evolutionaryNeuralNetwork;

import java.util.ArrayList;
import java.util.Collections;

import evolutionaryNeuralNetworkInterfaces.DataSetInterface;

public class DataSet implements DataSetInterface{
	private ArrayList<dataElement> data;
	private int nInputs;
	private int nOutputs;
	private double[] max;
	private double[] min;
	
	public DataSet(int nInputs, int nOutputs) {
		data = new ArrayList<dataElement>();
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
	}
	
	public void addData(double[] inputs, double[] outputs) {
		if (inputs.length != nInputs || outputs.length != nOutputs) {
			System.out.println("Invalid dimensions for DataSet");
		} else {
			data.add(new dataElement(inputs, outputs));
		}
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
	
	public double[] getMaximums() {
		return this.max;
	}
	
	public double[] getMinimums() {
		return this.min;
	}
	
	public void shuffle() {
		Collections.shuffle(data);
	}
	
	public void normalize() {
		this.max = new double[nInputs];
		this.min = new double[nInputs];
		
		for (int i = 0; i < nInputs; i++) {
			// find min and max values for the variable
			double min = Double.MAX_VALUE;
			double max = Double.MIN_VALUE;
			for (int j = 0; j < data.size(); j++) {
				if (data.get(j).getInputs()[i] > max) {
					max = data.get(j).getInputs()[i];
				} 
				if (data.get(i).getInputs()[i] < min) {
					min = data.get(j).getInputs()[i];
				}
			}
			
			this.max[i] = max;
			this.min[i] = min;
			
			// normalize the variable in each of the data elements
			for (int j = 0; j < data.size(); j++) {
				data.get(j).normalize(i, min, max);
			}
		}
		
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
		
		public void normalize(int index, double min, double max) {
			inputs[index] = (inputs[index] - min) / (max - min);
		}
		
	} // end class
} // end class

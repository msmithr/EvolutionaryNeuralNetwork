package evolutionaryNeuralNetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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
	
	public DataSet(String filename, int nInputs, int nOutputs) throws IOException {
		FileReader filereader = new FileReader(filename);
		BufferedReader bufferedReader = new BufferedReader(filereader);
		data = new ArrayList<dataElement>();
		String line;
		double[] temp1 = new double[nInputs];
		double[] temp2 = new double[nOutputs];
		String[] split;
		this.nInputs = nInputs;
		this.nOutputs = nOutputs;
		
		int iteration = 0;
		while ((line = bufferedReader.readLine()) != null) {
			split = line.split(" ");
			if (iteration % 2 == 0) {
				temp1 = new double[nInputs];
				for (int i = 0; i < split.length; i++) {
					temp1[i] = Double.parseDouble(split[i]);
				}
			} else {
				temp2 = new double[nOutputs];
				for (int i = 0; i < split.length; i++) {
					temp2[i] = Double.parseDouble(split[i]);
				}
				addData(temp1, temp2);
			}
			iteration++;
		}
		
		bufferedReader.close();
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

package evolutionaryNeuralNetwork;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class DriverUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldOutput;
	private JTextField textFieldNInputs;
	private JTextField textFieldNOutputs;
	private JTextField textFieldLayers;
	private JTextField textFieldNeuronsLayer;
	private JTextField textFieldPopSize;
	private JTextField textFieldCrossover;
	private JTextField textFieldMutation;
	private JTextField textFieldTournamentSize;
	private JTextField textFieldInput;
	
	public NeuralNetwork result;
	public GeneticAlgorithm moon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DriverUI frame = new DriverUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public DriverUI() {
		setTitle("Evolutionary Neural Network");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 490, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDataEntry = new JLabel("Data Entry");
		lblDataEntry.setBounds(221, 84, 52, 14);
		contentPane.add(lblDataEntry);
		
		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(239, 11, 46, 14);
		contentPane.add(lblOutput);
		
		textFieldOutput = new JTextField();
		textFieldOutput.setEditable(false);
		textFieldOutput.setBounds(278, 8, 112, 20);
		contentPane.add(textFieldOutput);
		textFieldOutput.setColumns(10);
		
		JComboBox<String> comboBoxAF = new JComboBox<String>();
		comboBoxAF.setModel(new DefaultComboBoxModel<String>(new String[] {"sigmoid", "step", "tanh", "sigmoid-step"}));
		comboBoxAF.setBounds(122, 303, 86, 20);
		contentPane.add(comboBoxAF);
		
		JButton btnTrain = new JButton("Train");
		btnTrain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				textFieldNInputs.setEditable(false);
				textFieldNOutputs.setEditable(false);
				textFieldLayers.setEditable(false);
				textFieldNeuronsLayer.setEditable(false);
				textFieldPopSize.setEditable(false);
				textFieldCrossover.setEditable(false);
				textFieldMutation.setEditable(false);
				textFieldTournamentSize.setEditable(false);
				comboBoxAF.setEditable(false);
				
				if (!validateInputs()) {
					textFieldNInputs.setEditable(true);
					textFieldNOutputs.setEditable(true);
					textFieldLayers.setEditable(true);
					textFieldNeuronsLayer.setEditable(true);
					textFieldPopSize.setEditable(true);
					textFieldCrossover.setEditable(true);
					textFieldMutation.setEditable(true);
					textFieldTournamentSize.setEditable(true);
					comboBoxAF.setEditable(true);
					JOptionPane.showMessageDialog(null, "Invalid Inputs");
					return;
				}
				
				String inputTemp = textFieldNInputs.getText();
				int nInputs = Integer.parseInt(inputTemp);
				
				String outputTemp = textFieldNOutputs.getText();
				int nOutputs = Integer.parseInt(outputTemp);
				
				String layerTemp = textFieldLayers.getText();
				int nLayers = Integer.parseInt(layerTemp);
				
				String nLayerTemp = textFieldNeuronsLayer.getText();
				int nNeurons = Integer.parseInt(nLayerTemp);
				
				String popTemp = textFieldPopSize.getText();
				int popSize = Integer.parseInt(popTemp);
				
				String crossTemp = textFieldCrossover.getText();
				double crossoverPropability = Double.parseDouble(crossTemp);
				
				String mutantTemp = textFieldMutation.getText();
				double mutationPropability = Double.parseDouble(mutantTemp);
				
				String tournamentTemp = textFieldTournamentSize.getText();
				int tournSize = Integer.parseInt(tournamentTemp);
				
				String afTemp = (String) comboBoxAF.getSelectedItem();
				ActivationFunction af;
				if (afTemp == "sigmoid"){
					af = ActivationFunction.SIGMOID;
				} else if (afTemp == "step") {
					af = ActivationFunction.STEP;
				} else if (afTemp == "tanh") {
					af = ActivationFunction.TANH;
				} else {
					af = ActivationFunction.SIGMOID_STEP;
				}
				
				DataSet learningData;
				try {
					learningData = new DataSet("wineData", nInputs, nOutputs);
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				
				GeneticAlgorithm moon = new GeneticAlgorithm(nInputs, nOutputs, nLayers, nNeurons, 
						popSize, crossoverPropability, mutationPropability, 
						tournSize, learningData, af);
				
				result = moon.optimize(50000);
				
				textFieldNInputs.setEditable(true);
				textFieldNOutputs.setEditable(true);
				textFieldLayers.setEditable(true);
				textFieldNeuronsLayer.setEditable(true);
				textFieldPopSize.setEditable(true);
				textFieldCrossover.setEditable(true);
				textFieldMutation.setEditable(true);
				textFieldTournamentSize.setEditable(true);
				comboBoxAF.setEditable(true);
				
			}
		});
		btnTrain.setBounds(265, 302, 89, 23);
		contentPane.add(btnTrain);
		
		JLabel lblNewLabel = new JLabel("Layers");
		lblNewLabel.setBounds(10, 156, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNeuronsPerLayer = new JLabel("Neurons per Layer");
		lblNeuronsPerLayer.setBounds(10, 181, 89, 14);
		contentPane.add(lblNeuronsPerLayer);
		
		JLabel lblPopulationSize = new JLabel("Population Size");
		lblPopulationSize.setBounds(10, 206, 72, 14);
		contentPane.add(lblPopulationSize);
		
		JLabel lblCrossoverPropability = new JLabel("Crossover Propability");
		lblCrossoverPropability.setBounds(10, 231, 102, 14);
		contentPane.add(lblCrossoverPropability);
		
		JLabel lblMutationPropability = new JLabel("Mutation Propability");
		lblMutationPropability.setBounds(10, 256, 95, 14);
		contentPane.add(lblMutationPropability);
		
		JLabel lblNumberOfInputs = new JLabel("Number of Inputs");
		lblNumberOfInputs.setBounds(10, 84, 84, 14);
		contentPane.add(lblNumberOfInputs);
		
		JLabel lblNumberOfOutputs = new JLabel("Number of Outputs");
		lblNumberOfOutputs.setBounds(10, 109, 95, 14);
		contentPane.add(lblNumberOfOutputs);
		
		textFieldNInputs = new JTextField();
		textFieldNInputs.setBounds(107, 81, 86, 20);
		contentPane.add(textFieldNInputs);
		textFieldNInputs.setColumns(10);
		
		textFieldNOutputs = new JTextField();
		textFieldNOutputs.setBounds(107, 106, 86, 20);
		contentPane.add(textFieldNOutputs);
		textFieldNOutputs.setColumns(10);
		
		textFieldLayers = new JTextField();
		textFieldLayers.setText("1");
		textFieldLayers.setBounds(122, 153, 86, 20);
		contentPane.add(textFieldLayers);
		textFieldLayers.setColumns(10);
		
		textFieldNeuronsLayer = new JTextField();
		textFieldNeuronsLayer.setText("1");
		textFieldNeuronsLayer.setBounds(122, 178, 86, 20);
		contentPane.add(textFieldNeuronsLayer);
		textFieldNeuronsLayer.setColumns(10);
		
		textFieldPopSize = new JTextField();
		textFieldPopSize.setText("100");
		textFieldPopSize.setBounds(122, 203, 86, 20);
		contentPane.add(textFieldPopSize);
		textFieldPopSize.setColumns(10);
		
		textFieldCrossover = new JTextField();
		textFieldCrossover.setText(".75");
		textFieldCrossover.setBounds(122, 228, 86, 20);
		contentPane.add(textFieldCrossover);
		textFieldCrossover.setColumns(10);
		
		textFieldMutation = new JTextField();
		textFieldMutation.setText(".2");
		textFieldMutation.setBounds(122, 253, 86, 20);
		contentPane.add(textFieldMutation);
		textFieldMutation.setColumns(10);
		
		JLabel lblTournamentSize = new JLabel("Tournament Size");
		lblTournamentSize.setBounds(10, 281, 84, 14);
		contentPane.add(lblTournamentSize);
		
		JLabel lblActivation = new JLabel("Activation Function");
		lblActivation.setBounds(10, 306, 95, 14);
		contentPane.add(lblActivation);
		
		textFieldTournamentSize = new JTextField();
		textFieldTournamentSize.setText("2");
		textFieldTournamentSize.setBounds(122, 278, 86, 20);
		contentPane.add(textFieldTournamentSize);
		textFieldTournamentSize.setColumns(10);
		
		JLabel lblInput = new JLabel("Input");
		lblInput.setBounds(10, 11, 46, 14);
		contentPane.add(lblInput);
		
		textFieldInput = new JTextField();
		textFieldInput.setBounds(45, 8, 102, 20);
		contentPane.add(textFieldInput);
		textFieldInput.setColumns(10);
		
		JButton btnQuery = new JButton("Query");
		btnQuery.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(result);
			}
		});
		btnQuery.setBounds(10, 36, 89, 23);
		contentPane.add(btnQuery);
	}
	
	private boolean validateInputs() {
		String inputTemp = textFieldNInputs.getText();
		int nInputs = Integer.parseInt(inputTemp);
		
		String outputTemp = textFieldNOutputs.getText();
		int nOutputs = Integer.parseInt(outputTemp);
		
		String layerTemp = textFieldLayers.getText();
		int nLayers = Integer.parseInt(layerTemp);
		
		String nLayerTemp = textFieldNeuronsLayer.getText();
		int nNeurons = Integer.parseInt(nLayerTemp);
		
		String popTemp = textFieldPopSize.getText();
		int popSize = Integer.parseInt(popTemp);
		
		String crossTemp = textFieldCrossover.getText();
		double crossoverPropability = Double.parseDouble(crossTemp);
		
		String mutantTemp = textFieldMutation.getText();
		double mutationPropability = Double.parseDouble(mutantTemp);
		
		String tournamentTemp = textFieldTournamentSize.getText();
		int tournSize = Integer.parseInt(tournamentTemp);
		
		if (
			nInputs <= 0 ||
			nOutputs <= 0 ||
			nLayers <= 0 ||
			nNeurons <= 0 ||
			popSize <= 0 ||
			crossoverPropability < 0 ||
			crossoverPropability > 1 ||
			mutationPropability < 0 ||
			mutationPropability > 1 ||
			tournSize <= 0
			) 
		{
			return false;
		}
		return true;	
	}
	
	private static void addData(DataSet learningData) {
		learningData.addData(new double[] {0.1}, new double[] {0});
		learningData.addData(new double[] {0.3}, new double[] {0});
		learningData.addData(new double[] {0.5}, new double[] {0});
		learningData.addData(new double[] {0.7}, new double[] {1});
		learningData.addData(new double[] {0.9}, new double[] {1});
		learningData.addData(new double[] {1}, new double[] {1});
	}
}

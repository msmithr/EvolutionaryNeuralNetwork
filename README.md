# Island Model Evolutionary Neural Network
Neural networks trained via island model genetic algorithms.

# Data File
The "Load Data" button is used to load in an input data file. This data file requires a specific form. For example,

0 0

0

0 1

1

1 0

1

1 1

0

Would be a proper data file for logical XOR. The number of inputs and outputs must be pre-entered before loading the file.

# Parameters:
Number of Inputs: Number of inputs in each data item
Number of Outouts: Number of outputs in each data item
Layers: Number of hidden layers in the networks
Neurons per Layer: Number of neurons in each of the hidden layers
Population Size: The size of the population (Per island)
Crossover Probability: Probability for crossover to happen to each child
Mutation Probability: Probabilty for mutation to happen to each child
Tournament Size: The size of the tournament for tournament selection
Activation Function: The activation function used in the neural networks
  (Sigmoid-step: Use sigmoid for every layer except for the last one, which step is used)
Migration Probability: Probability for a child to migrate to another "island"

Once the parameters are filled, the use can either choose the number of iterations to run, or the goal sum of squared errors. Once that is filled, the buttons "Train for Iterations" or "Train until Error" begin the training process. "Stop" stops the training process, and returns the current best neural network. During the training process, the output in the big "Error" box shows the current minimum sum of squared errors for each iteration.

# Querying the network
Once the network is trained, it can be queried freely by the user. The user can put an input in the "input" box, click "Query", and get the corresponding output in the "Output" box.

# Saving and Loading
Using the "Save NN" button, the user can save a trained Neural Network, to avoid having to train again in the future. This writes all of the information needed to construct the network out to a file. Using "Load NN", the user can reload one of these files to re-obtain the network without training.

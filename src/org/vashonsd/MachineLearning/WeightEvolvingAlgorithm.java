package org.vashonsd.MachineLearning;

import org.vashonsd.FitnessTest.BaseFitnessTest;
import org.vashonsd.FitnessTest.OnePlayerFitnessTest;
import org.vashonsd.FitnessTest.TwoPlayerFitnessTest;
import org.vashonsd.MachineLearning.Nodes.ConnectedNode;
import org.vashonsd.MachineLearning.Nodes.SimpleNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WeightEvolvingAlgorithm {

    // Using a standard Genetic Algorithm with Uniform Crossover

    List<NeuralNetwork> neuralNetworks = new ArrayList<>();
    int numLayers;
    int numInputs;
    int numOutputs;
    int batchSize;
    BaseFitnessTest fitnessTest;
    double mutationChance;

    // For a fixed topology network with one hidden layer
    public WeightEvolvingAlgorithm(int batchSize, int numInputs, int numOutputs, BaseFitnessTest fitnessTest) {
        this.batchSize = batchSize;
        for (int i = batchSize; i > 0; i--) {
            neuralNetworks.add(new NeuralNetwork(3, numInputs, numOutputs));
        }
        this.numLayers = 3;
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.fitnessTest = fitnessTest;
        mutationChance = 0.05;
    }

    public WeightEvolvingAlgorithm(int batchSize, int numLayers, int numInputs, int numOutputs, BaseFitnessTest fitnessTest) {
        this.batchSize = batchSize;
        for (int i = batchSize; i > 0; i--) {
            neuralNetworks.add(new NeuralNetwork(numLayers, numInputs, numOutputs));
        }
        this.numLayers = numLayers;
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.fitnessTest = fitnessTest;
        mutationChance = 0.05;
    }

    public WeightEvolvingAlgorithm(int batchSize, int numInputs, int numOutputs, BaseFitnessTest fitnessTest, double mutationChance) {
        this.batchSize = batchSize;
        for (int i = batchSize; i > 0; i--) {
            neuralNetworks.add(new NeuralNetwork(3, numInputs, numOutputs));
        }
        this.numLayers = 3;
        this.numInputs = numInputs;
        this.numOutputs = numOutputs;
        this.fitnessTest = fitnessTest;
        this.mutationChance = mutationChance;
    }

    public List<NeuralNetwork> getNeuralNetworks() {
        return neuralNetworks;
    }

    public void run(int numGenerations) {
        for (int i = 0; i < numGenerations; i++) {
            runFitnessTest(); // How stronk are u?
            snap(); // Only the strong survive
            repopulate(); // Regenerate for a stronger population than last time... Hopefully
        }
    }

    public void repopulate() {
        // Do I replace all ten? Or just replace the missing half? Gonna replace half, all was too unstable
        // Do I select each parent randomly? Or proportionally based on their fitness? Gonna go proportional for now cause I hate myself
        // Is the mutation chance per node? or per network? Gonna go per node for now and suffer.
        ArrayList<NeuralNetwork> nextGeneration = new ArrayList<>();

        for (int x = 0; x < batchSize / 2; x++) {
            double totalFitness = 0;
            NeuralNetwork parentOne = neuralNetworks.get(0);
            NeuralNetwork parentTwo = neuralNetworks.get(1);


            for (NeuralNetwork n: neuralNetworks) {
                totalFitness += n.getFitness();
            }
            double selectedNetwork = (Math.random() * totalFitness);

            for (NeuralNetwork n: neuralNetworks) {
                if (selectedNetwork - n.getFitness() < 0) {
                    parentOne = n;
                    break;
                } else {
                    selectedNetwork -= n.getFitness();
                }
            }

            selectedNetwork = (Math.random() * totalFitness);

            for (NeuralNetwork n: neuralNetworks) {
                if (selectedNetwork - n.getFitness() < 0) {
                    parentTwo = n;
                    break;
                } else {
                    selectedNetwork -= n.getFitness();
                }
            }

            // Create new Neural Network using the parents as a base
            NeuralNetwork daBaby = new NeuralNetwork(numLayers, numInputs, numOutputs);

            for (int layer = 1; layer < daBaby.getLayers().size(); layer++) {
                List<SimpleNode> currentLayer = daBaby.getLayers().get(layer);

                for (int node = 0; node < currentLayer.size(); node++) {
                    if (currentLayer.get(node) instanceof ConnectedNode currentNode) {
                        ConnectedNode parentOneNode = (ConnectedNode) parentOne.getLayers().get(layer).get(node);
                        ConnectedNode parentTwoNode = (ConnectedNode) parentTwo.getLayers().get(layer).get(node);
                        for (int inputBeingWeighted = 0; inputBeingWeighted < daBaby.getLayers().get(layer - 1).size(); inputBeingWeighted++) {
                            if (Math.random() < mutationChance) {
                                currentNode.setWeight(daBaby.getLayers().get(layer - 1).get(inputBeingWeighted), (Math.random() * 2) - 1);
                                // set it to a random weight
                            } else if (Math.random() < (1 - mutationChance) / 2) {
                                currentNode.setWeight(daBaby.getLayers().get(layer - 1).get(inputBeingWeighted), parentOneNode.getWeights().get(parentOne.getLayers().get(layer - 1).get(inputBeingWeighted)));
                                // Set weight to the parentOnes weight
                            } else {
                                currentNode.setWeight(daBaby.getLayers().get(layer - 1).get(inputBeingWeighted), parentTwoNode.getWeights().get(parentTwo.getLayers().get(layer - 1).get(inputBeingWeighted)));
                                // Set weight to parentTwos weight
                            }
                        }
                    }
                }
                daBaby.getLayers().set(layer, currentLayer);
            }

            // Add the baby to the new list of NeuralNetworks
            nextGeneration.add(daBaby);
        }
        neuralNetworks.addAll(nextGeneration);
    }

    public void snap() {

        neuralNetworks.subList((neuralNetworks.size() / 2), neuralNetworks.size()).clear();

        System.out.println("Snap!");
        for (NeuralNetwork n: neuralNetworks) {
            System.out.println(n.getFitness());

        }
    }

    public void runFitnessTest() {
        if (fitnessTest instanceof TwoPlayerFitnessTest) {
            runTwoPlayerFitnessTest((TwoPlayerFitnessTest) fitnessTest);
        } else if (fitnessTest instanceof OnePlayerFitnessTest) {
            System.out.println("WIP");
        } else {
            System.out.println("Couldn't find a fitness test");
        }
    }

    private void runTwoPlayerFitnessTest(TwoPlayerFitnessTest P2FitnessTest) {
        double totalFitness = 0;
        for (NeuralNetwork player1: neuralNetworks) {
            for (NeuralNetwork player2: neuralNetworks) {
                if (player1 != player2) {
                    P2FitnessTest.start();
                    while (!P2FitnessTest.isFinished()) {
                        if (P2FitnessTest.getWhoseTurn().equals("player1")) {
                            player1.update(fitnessTest.getInputsForNetwork());
                            System.out.println(player1.getSortedOutputs());
                            inputTwoPlayerBestValue(player1);
                        } else {
                            // get inverted inputs...
                            player2.update(invertInputs(fitnessTest.getInputsForNetwork()));
                            System.out.println(player1.getSortedOutputs());
                            inputTwoPlayerBestValue(player2);
                        }
                    }
                    System.out.println(P2FitnessTest.getWinner() + " Wins!");
                    if (P2FitnessTest.getWinner().equals("No one")) {
                        player1.addFitness(5);
                        player2.addFitness(5);
                    } else if (P2FitnessTest.getWinner().equals("player1")) {
                        player1.addFitness(10);
                    } else {
                        player2.addFitness(10);
                    }
                }
            }
        }

        for (NeuralNetwork n: neuralNetworks) {
            totalFitness += n.getFitness();
            System.out.println(n.getFitness());
        }

        for (NeuralNetwork n: neuralNetworks) {
            n.setFitness(n.getFitness() / totalFitness);
        }

        sortNeuralNetworks();
    }

    public List<Double> invertInputs(List<Double> inputs) {
        List<Double> invertedList = new ArrayList<>();
        for (Double input: inputs) {
            if (input == 1.0) {
                invertedList.add(0.0);
            } else {
                invertedList.add(1.0);
            }
        }
        return invertedList;
    }

    public void inputTwoPlayerBestValue(NeuralNetwork network) {
        boolean isFirstGuess = true;

        for (Integer i: network.getSortedOutputs()) {
            if (fitnessTest.isLegalMove(i)) {
                fitnessTest.insertNetworkOutput(i);
                if (isFirstGuess) {
                    network.addFitness(1);
                }
                break;
            } else {
                isFirstGuess = false;
            }
        }
    }

    public void sortNeuralNetworks() {
        neuralNetworks.sort(new NetworkFitnessComparator());
        Collections.reverse(neuralNetworks);
        for (NeuralNetwork n: neuralNetworks) {
            System.out.println(n.getFitness());
        }
    }
}

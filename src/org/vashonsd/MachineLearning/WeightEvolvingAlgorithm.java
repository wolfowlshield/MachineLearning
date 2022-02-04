package org.vashonsd.MachineLearning;

import org.vashonsd.FitnessTest.TwoPlayerFitnessTest;

import java.util.ArrayList;
import java.util.List;

public class WeightEvolvingAlgorithm {

    // Using a standard Genetic Algorithm with Uniform Crossover

    List<NeuralNetwork> neuralNetworks = new ArrayList<>();
    int numInputs;
    int numOutputs;
    int totalFitness;
    TwoPlayerFitnessTest fitnessTest;

    // For a fixed topology network with one hidden layer
    public WeightEvolvingAlgorithm(int batchSize, int numInputs, int numOutputs, TwoPlayerFitnessTest fitnessTest) {
        for (int i = batchSize; i > 0; i--) {
            neuralNetworks.add(new NeuralNetwork(3, numInputs, numOutputs));
        }
        this.fitnessTest = fitnessTest;
    }

    public List<NeuralNetwork> getNeuralNetworks() {
        return neuralNetworks;
    }

    public void runTwoPlayerFitnessTest() {
        for (NeuralNetwork player1: neuralNetworks) {
            for (NeuralNetwork player2: neuralNetworks) {
                if (player1 != player2) {
                    fitnessTest.start();
                    while (!fitnessTest.isFinished()) {
                        if (fitnessTest.getWhoseTurn().equals("player1")) {
                            inputBestValue(player1);
                        } else {
                            inputBestValue(player2);
                        }
                    }
                    System.out.println(fitnessTest.getWinner() + " Wins!");
                    if (fitnessTest.getWinner().equals("No one")) {
                        player1.setFitness(player1.getFitness() + 5);
                        player2.setFitness(player2.getFitness() + 5);
                    } else if (fitnessTest.getWinner().equals("player1")) {
                        player1.setFitness(player1.getFitness() + 10);
                        player2.setFitness(player2.getFitness() - 10);
                    } else {
                        player2.setFitness(player2.getFitness() + 10);
                        player1.setFitness(player1.getFitness() - 10);
                    }
                }
            }
        }

        for (NeuralNetwork n: neuralNetworks) {
            totalFitness += n.getFitness();
        }

        for (NeuralNetwork n: neuralNetworks) {
            n.setFitness(n.getFitness() / totalFitness);
        }

        sortNeuralNetworks();
    }

    public void inputBestValue(NeuralNetwork network) {
        for (Integer i: network.getSortedOutputs()) {
            if (fitnessTest.getInputsForNetwork().get(i - 1) == 0.5) {
                fitnessTest.insertNetworkOutput(i);
                break;
            } else {
                network.setFitness(network.getFitness() - 1);
            }
        }
    }

    public void sortNeuralNetworks() {

    }
}

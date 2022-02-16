package org.vashonsd.MachineLearning;

import java.util.Comparator;

public class NetworkFitnessComparator implements Comparator<NeuralNetwork> {

    @Override
    public int compare(NeuralNetwork firstNetwork, NeuralNetwork secondNetwork) {
        return Double.compare(firstNetwork.getFitness(), secondNetwork.getFitness());
    }
}

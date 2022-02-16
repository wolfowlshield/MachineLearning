package org.vashonsd.MachineLearning.Nodes;

import org.vashonsd.MachineLearning.NeuralNetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConnectedNode extends SimpleNode {

    HashMap<SimpleNode, Double> weights = new HashMap<>();

    public ConnectedNode(int layer, NeuralNetwork parentNetwork) {
        this.layer = layer;

        randomizeWeights(parentNetwork);
    }

    public void randomizeWeights(NeuralNetwork parentNetwork) {
        for (SimpleNode n : parentNetwork.getLayers().get(layer - 1)) {
            weights.put(n, (Math.random() * 2) - 1);
        }
    }

    public void calculateValue() {
        double sumValues = 0;
        for (SimpleNode n : weights.keySet()) {
            sumValues += weights.get(n) * n.getValue();
        }
        value = 1 / (1 + Math.pow(Math.E, (-sumValues)));
    }

    public void setWeight(SimpleNode connection, double weight) {
        if (weights.containsKey(connection)) {
            weights.put(connection, weight);

        } else {
            System.out.println("Not connected to that node");
        }
    }

    public HashMap<SimpleNode, Double> getWeights() {
        return weights;
    }

    public String toString() {
        String result = "";
        int i = 1;

        result = result.concat("Value is " + value + "\n");
        for (SimpleNode n: weights.keySet()) {
            result = result.concat(weights.get(n) + " weighted connection to input " + i + " in layer " + (layer - 1) + "\n");
            i++;
        }
        return result;
    }
}
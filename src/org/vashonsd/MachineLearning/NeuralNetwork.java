package org.vashonsd.MachineLearning;

import org.vashonsd.MachineLearning.Nodes.BiasNode;
import org.vashonsd.MachineLearning.Nodes.ConnectedNode;
import org.vashonsd.MachineLearning.Nodes.SimpleNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class NeuralNetwork {

    List<SimpleNode> inputNodes = new ArrayList<>();
    // List<SimpleNode> hiddenNodes = new ArrayList<>();
    List<SimpleNode> outputNodes = new ArrayList<>();

    List<List<SimpleNode>> layers = new ArrayList<>();

    int numLayers;

    double fitness = 0;

    public NeuralNetwork(int numLayers, int numInputs, int numOutputs) {
        this.numLayers = numLayers;

        layers.add(inputNodes);
        for (int i = 1; i <= numLayers - 2; i++) {
            layers.add(new ArrayList<SimpleNode>()); // Replace with a for loop later for more hidden layers
        }
        layers.add(outputNodes);

        for (int i = numInputs; i > 0; i--) {
            inputNodes.add(new SimpleNode());
        }
        inputNodes.add(new BiasNode());

        for (int i = 1; i <= numLayers - 2; i++) {
            buildHiddenLayer(i);
        }

        for (int i = numOutputs; i > 0; i--) {
            outputNodes.add(new ConnectedNode(numLayers - 1, this));
        }

        calculateOutput();
    }

    public void buildHiddenLayer(int layer) {
        for (int i = 15; i > 0; i--) {
            layers.get(layer).add(new ConnectedNode(layer, this));
        }
        layers.get(layer).add(new BiasNode());
    }

    public void calculateOutput() {
        for (List<SimpleNode> l: layers) {
            for (SimpleNode n : l) {
                if (n.getClass() == ConnectedNode.class) { // Make sure you don't calculate the bias node
                    ((ConnectedNode) n).calculateValue();
                }
            }
        }
    }

    public void update(List<Double> newInput) {
        int i = 0;
        for (SimpleNode n: inputNodes) {
            if (n.getClass() == SimpleNode.class) {
                n.setValue(newInput.get(i));
                i++;
            }
        }
        calculateOutput();
    }

    public List<List<SimpleNode>> getLayers() {
        return layers;
    }

    public List<Integer> getSortedOutputs() {

        HashMap<Double, Integer> unsorted = new HashMap<>(); // Probably should replace this w/ a custom class
                                                             // Java sort entry-set by value
        List<Double> sortedKeys;
        List<Integer> sortedValues = new ArrayList<>();

        double highest = 0;

        for (int i = 0; i < outputNodes.size(); i++) {
            unsorted.put(outputNodes.get(i).getValue(), i + 1);
        }
        sortedKeys = new ArrayList<>(unsorted.keySet());
        Collections.sort(sortedKeys);
        Collections.reverse(sortedKeys);
        for (Double key: sortedKeys) {
            sortedValues.add(unsorted.get(key));
        }

        return sortedValues;
    }

    public String getOutputValues() {
        String result = "";
        for (SimpleNode n: outputNodes) {
            result = result.concat(n.getValue() + " ");
        }
        return result.concat("\n");
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
    public void addFitness(double fitness) {
        this.fitness += fitness;
    }

    public String toString() {

        String result = "The input layer has " + inputNodes.size() + " nodes\n" +
                //"The hidden layer has " + hiddenNodes.size() + " nodes\n" +
                "The output layer has " + outputNodes.size() + " nodes\n";

        int currentNode = 1;

        for (SimpleNode n: layers.get(1)) {
            result = result.concat("Hidden Node #" + currentNode + "\n");
            result = result.concat(n.toString());
            currentNode++;
        }
        currentNode = 1;

        for (SimpleNode n: outputNodes) {
            result = result.concat("Output Node #" + currentNode + "\n");
            result = result.concat(n.toString());
            currentNode++;
        }

        return result;
    }
}

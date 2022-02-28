package org.vashonsd.MachineLearning.Nodes;

public class SimpleNode {

    double value; // Should only contain a number between 0-1
    int layer; // Holds the layer number that this node is in, starting at 0

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}

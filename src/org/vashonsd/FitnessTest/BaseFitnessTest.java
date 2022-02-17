package org.vashonsd.FitnessTest;

import java.util.List;

public interface BaseFitnessTest {
    // I should make this one the one player fitness test as the two player one can easily extend this one.
    // For now, just don't make a fitness test Implement this one, as it won't work with the machine learning algorithm... yet.

    void start();
    boolean isFinished();

    List<Double> getInputsForNetwork();
    void insertNetworkOutput(int networkOut);
    boolean isLegalMove(int move);
}

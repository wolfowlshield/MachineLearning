package org.vashonsd.FitnessTest;

import java.util.List;

public interface BaseFitnessTest {

    void start();
    boolean isFinished();

    List<Double> getInputsForNetwork();
    void insertNetworkOutput(int networkOut);
    boolean isLegalMove(int move);
}

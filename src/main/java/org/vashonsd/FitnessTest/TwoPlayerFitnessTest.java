package org.vashonsd.FitnessTest;

import java.util.List;

public interface TwoPlayerFitnessTest extends BaseFitnessTest {

    boolean isFinished();
    String getWhoseTurn();
    String getWinner();

    List<Double> getInputsForNetwork();
    void insertNetworkOutput(int networkOut);
}

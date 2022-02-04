package org.vashonsd.FitnessTest;

import java.util.List;

public interface TwoPlayerFitnessTest {

    public void start();

    public boolean isFinished();
    public String getWhoseTurn();
    public String getWinner();

    public List<Double> getInputsForNetwork();
    public void insertNetworkOutput(int networkOut);
}

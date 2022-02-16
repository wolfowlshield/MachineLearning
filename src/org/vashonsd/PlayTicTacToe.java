package org.vashonsd;

import org.vashonsd.FitnessTest.TicTacToe;
import org.vashonsd.MachineLearning.WeightEvolvingAlgorithm;

import java.util.Scanner;

public class PlayTicTacToe {

    public void play() {
        // idk, this can't seem to work due to the fact that the results should change based on what player it is, but it dont.
        boolean running = true;
        String userIn;
        Scanner input = new Scanner(System.in);

        TicTacToe game = new TicTacToe();

        WeightEvolvingAlgorithm algorithm = new WeightEvolvingAlgorithm(20, 19, 9, game);

        // System.out.println(algorithm.getNeuralNetworks().get(0).toString());

        while (running) {
            System.out.println("What do you want to do?");
            userIn = input.nextLine();

            switch (userIn) {
                case "play" -> {
                    game.start();
                    while (!game.isFinished()) {

                        algorithm.getNeuralNetworks().get(0).update(game.getInputsForNetwork());

                        System.out.println("\nLowest  <- - - - -> Highest");
                        System.out.println(algorithm.getNeuralNetworks().get(0).getSortedOutputs());

                        System.out.println("Where do you want to play");
                        game.takeTurn(input.nextInt());
                    }
                    System.out.println(game.getWinner() + " Wins!");
                    input.nextLine();
                }
                case "exit", "quit" -> {
                    running = false;
                }

                case "run" -> {
                    algorithm.run(100);
                }
            }
        }
    }
}

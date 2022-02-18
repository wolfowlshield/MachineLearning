package org.vashonsd.FitnessTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class TicTacToe implements TwoPlayerFitnessTest {

    String whosePlaying;
    Scanner input = new Scanner(System.in);
    HashMap<Integer, String> squares = new HashMap<>();

    public TicTacToe() {
    }

    public void start() {
        squares.clear();

        for (int i = 1; i < 10; i++) {
            squares.put(i, " ");
        }

        if (Math.random() * 2 > 1) { // Randomly choose who goes first
            whosePlaying = "X";
        } else {
            whosePlaying = "O";
        }

        System.out.println(this);
        System.out.println(whosePlaying + " goes first");
    }

    public void takeTurn(int input) {
        if (squares.get(input).equals(" ")) {

            squares.put(input, whosePlaying);

            System.out.println(this);
            System.out.println(getInputsForNetwork());

            if (!isFinished()) {
                if (whosePlaying.equals("X")) {
                    whosePlaying = "O";
                } else {
                    whosePlaying = "X";
                }
            }

        } else {
            System.out.println("Someones already played in that square, try again");
        }
    }

    public boolean isFinished() {
        if (!checkForWinner()) {
            if (!squares.containsValue(" ")) {
                // System.out.println("No one wins! It's a draw!");
                return true;
            } else {
                return false;
            }
        }
        // System.out.println(whosePlaying + " Wins!");
        return true;
    }

    public boolean checkForWinner() {

        for (int i = 1; i < 8; i += 3) {
            if (squares.get(i).equals(squares.get(i + 1)) && squares.get(i).equals(squares.get(i + 2)) && !squares.get(i).equals(" ")) {
                return true;
            }
        }
        for (int i = 1; i < 4; i++) {
            if (squares.get(i).equals(squares.get(i + 3)) && squares.get(i).equals(squares.get(i + 6)) && !squares.get(i).equals(" ")) {
                return true;
            }
        }
        if (squares.get(1).equals(squares.get(5)) && squares.get(1).equals(squares.get(9)) && !squares.get(1).equals(" ")) {
            return true;
        }
        if (squares.get(3).equals(squares.get(5)) && squares.get(3).equals(squares.get(7)) && !squares.get(3).equals(" ")) {
            return true;
        }
        return false;
    }

    public String toString() {
        return " " + squares.get(1) + " | " + squares.get(2) + " | " + squares.get(3) +
                "\n---|---|---" +
                "\n " + squares.get(4) + " | " + squares.get(5) + " | " + squares.get(6) +
                "\n---|---|---" +
                "\n " + squares.get(7) + " | " + squares.get(8) + " | " + squares.get(9) ;
    }

    public String getWinner() {
        if (checkForWinner()) {
            if (whosePlaying.equals("X")) {
                return "player1";
            }
            return "player2";
        } else {
            return "No one";
        }
    }

    public String getWhoseTurn() {
        if (whosePlaying.equals("X")) {
            return "player1";
        }
        return "player2";
    }

    public void insertNetworkOutput(int networkOut) {
        takeTurn(networkOut);
    }

    @Override
    public boolean isLegalMove(int move) {
        if (squares.get(move).equals(" ")) {
            return true;
        }
        return false;
    }

    public List<Double> getInputsForNetwork() {
        List<Double> result = new ArrayList<>();
        for (String s: squares.values()) {
            if (s.equals(" ")) {
                result.add(0.0);
                result.add(0.0);
            } else if (s.equals("X")) {
                result.add(1.0);
                result.add(0.0);
            } else {
                result.add(0.0);
                result.add(1.0);
            }
        }
        return result;
    }
}

//  1 | 2 | 3
// ---|---|---
//  4 | 5 | 6
// ---|---|---
//  7 | 8 | 9
import java.util.Random;
import java.util.Scanner;
import java.util.random.*;

public class RPSLS {
    enum Move {
        ROCK, PAPER, SCISSORS,
        LIZARD, SPOCK
    }

    static int winner(Move a, Move b) {
        if (a == b) {
            return 0; // same move
        }
        return switch (a) {
            case ROCK ->
                (b == Move.SCISSORS || b == Move.LIZARD) ? 1 : -1;
            case PAPER ->
                (b == Move.ROCK || b == Move.SPOCK) ? 1 : -1;
            case SCISSORS ->
                (b == Move.PAPER || b == Move.LIZARD) ? 1 : -1;
            case LIZARD ->
                (b == Move.SPOCK || b == Move.PAPER) ? 1 : -1;
            case SPOCK ->
                (b == Move.SCISSORS || b == Move.ROCK) ? 1 : -1;
        };
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rn = new Random();
        int p_score = 0;
        int c_score = 0;

        System.out.println("===== Rock Paper Scissors Lizard Spock =====");
        System.out.println("Moves: ROCK, PAPER, SCISSORS, LIZARD, SPOCK");

        for (int round = 1; round <= 5; round++) {
            System.out.println("\nRound " + round);

            // Computer Move
            Move c_Move = Move.values()[rn.nextInt(5)];

            // Player Move
            System.out.print("Enter your move: ");
            String input = sc.next().toUpperCase();

            Move p_Move;
            try {
                p_Move = Move.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid Move! Round Skipped.");
                continue;
            }

            int result = winner(p_Move, c_Move);
            System.out.println("Player   : " + p_Move);
            System.out.println("Computer : " + c_Move);

            if (result == 1) {
                System.out.println("You Win this Round!");
                p_score++;
            } else if (result == -1) {
                System.out.println("Computer Wins this Round!");
                c_score++;
            } else {
                System.out.println("Round Draw!");
            }
        }

        System.out.println("\n===== Final Result =====");
        System.out.println("Player Score   : " + p_score);
        System.out.println("Computer Score : " + c_score);

        if (p_score > c_score) {
            System.out.println("Overall Winner: You win " + p_score + "-" + c_score);
        } else if (c_score > p_score) {
            System.out.println("Overall Winner: Computer wins " + c_score + "-" + p_score);
        } else {
            System.out.println("Overall Result: Match Draw");
        }
    }
}

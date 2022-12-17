package adventofcode.y2022.day2;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class RockPaperScissorsTwo {
    enum Move{
        ROCK,
        PAPER,
        SCISSOR;

        public static Move getMove(char s) {
            switch (s) {
                case 'X':
                case 'A': return ROCK;
                case 'Y':
                case 'B': return PAPER;
                case 'Z':
                case 'C': return SCISSOR;
            }
            return null;
        }

        public static Move getPlayerMove(char s, Move computerMove) {
            switch (s) {
                case 'X': switch (computerMove) {
                    case ROCK: return SCISSOR;
                    case PAPER: return ROCK;
                    case SCISSOR: return PAPER;
                }
                case 'Y':return computerMove;
                case 'Z': switch (computerMove) {
                    case ROCK: return PAPER;
                    case PAPER: return SCISSOR;
                    case SCISSOR: return ROCK;
                }
            }
            return null;
        }
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        System.out.println(input.stream().map(RockPaperScissorsTwo::playGame).reduce((a, b)->a+b).get());

    }

    private static Integer playGame(String s) {
        Move computerMove = Move.getMove(s.charAt(0));
        Move playerMove = Move.getPlayerMove(s.charAt(2), computerMove);
        int score = 0;
        switch (playerMove) {
            case ROCK -> score = 1;
            case PAPER -> score = 2;
            case SCISSOR -> score = 3;
        }
        score += checkWin(computerMove, playerMove);
        return score;
    }

    private static int checkWin(Move computerMove, Move playerMove) {
        if(computerMove==playerMove)return 3;
        if ((playerMove == Move.PAPER && computerMove == Move.ROCK)
            ||(playerMove == Move.ROCK && computerMove == Move.SCISSOR)
            ||(playerMove == Move.SCISSOR && computerMove == Move.PAPER)) return 6;
        return 0;
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(RockPaperScissorsTwo.class.getResource("input.txt")).toURI()));
    }
}

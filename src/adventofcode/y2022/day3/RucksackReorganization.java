package adventofcode.y2022.day3;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RucksackReorganization {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        System.out.println(input.stream().map(RucksackReorganization::solve).reduce((a,b)->a+b).get());
    }

    private static int solve(String s) {
        List<Integer> firstHalf = s.substring(0,s.length()/2).chars().boxed().collect(Collectors.toList());
        List<Integer> secondHalf = s.substring(s.length()/2).chars().boxed().collect(Collectors.toList());
        firstHalf.retainAll(secondHalf);
        int priority = firstHalf.get(0)>90?firstHalf.get(0)-96:firstHalf.get(0)-38;
        return priority;
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(RucksackReorganization.class.getResource("input.txt")).toURI()));
    }
}

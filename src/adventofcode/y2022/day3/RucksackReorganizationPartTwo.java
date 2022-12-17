package adventofcode.y2022.day3;


import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RucksackReorganizationPartTwo {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        int priority = 0;
        for(int i=0;i<input.size();i++) {
            priority += solve(input.get(i),input.get(i+1), input.get(i+2));
            i+=2;
        }
        System.out.println(priority);
    }

    private static int solve(String s1, String s2, String s3) {
        List<Integer> firstHalf = s1.chars().boxed().collect(Collectors.toList());
        List<Integer> secondHalf = s2.chars().boxed().collect(Collectors.toList());
        List<Integer> thirdHalf = s3.chars().boxed().collect(Collectors.toList());
        firstHalf.retainAll(secondHalf);
        firstHalf.retainAll(thirdHalf);
        int priority = firstHalf.get(0)>90?firstHalf.get(0)-96:firstHalf.get(0)-38;
        return priority;
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(RucksackReorganizationPartTwo.class.getResource("input.txt")).toURI()));
    }
}

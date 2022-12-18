package adventofcode.y2022.day5;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class SupplyStacks {

    static Pattern inputPattern = Pattern.compile("([\\[A-Z\\] ]{3} ?)");
    static Pattern inputMovePattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    public static void main(String[] args) throws IOException, URISyntaxException {

        List<String> input = readInput().stream().filter(inputstring -> !inputstring.startsWith("move")).collect(Collectors.toList());
        List<Stack<String>> list = new ArrayList<>();
        input.stream().map(inputString -> inputPattern.matcher(inputString)).forEach(matcher -> {
                    int count = 0;
                    while (matcher.find()) {
                        if (list.size() == count)
                            list.add(new Stack<>());
                        String container = matcher.group();
                        if(!container.trim().isBlank())
                        list.get(count).add(container.trim().substring(1,2));
                        count++;
                    }
                }
        );
        List<Stack<String>> nlist = list.stream().map(stack -> {Stack<String> nstack = new Stack<>();while (!stack.isEmpty())nstack.add(stack.pop());return nstack;}).collect(Collectors.toList());
        readInput().stream().filter(inputstring -> inputstring.startsWith("move")).map(inputString -> inputMovePattern.matcher(inputString)).forEach(inputString -> {
            inputString.find();
            int t = Integer.parseInt(inputString.group(1));
            int s = Integer.parseInt(inputString.group(2))-1;
            int d = Integer.parseInt(inputString.group(3))-1;
            while(t-->0) {
                nlist.get(d).add(nlist.get(s).pop());
            }
        });
        System.out.println(nlist.stream().map(data->data.pop()).reduce((a,b)->a+b).get());

    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(SupplyStacks.class.getResource("input.txt")).toURI()));
    }
}

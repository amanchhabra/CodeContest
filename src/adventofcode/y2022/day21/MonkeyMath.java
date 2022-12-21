package adventofcode.y2022.day21;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonkeyMath {

    static Pattern twoMonkeyPattern = Pattern.compile("([a-z]{4}): ([a-z]{4}) ([+\\-*/]) ([a-z]{4})");
    static Pattern oneMonkeyPattern = Pattern.compile("([a-z]{4}): (\\d+)");
    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        input.forEach(inputString -> {
            Matcher matcher = twoMonkeyPattern.matcher(inputString);
            if(matcher.find()) {
                Monkey monkey = Monkey.getMonkey(matcher.group(1));
                monkey.monkey1 = Monkey.getMonkey(matcher.group(2));
                monkey.monkey2 = Monkey.getMonkey(matcher.group(4));
                monkey.opr = matcher.group(3).charAt(0);
            } else {
                Matcher matcher1 = oneMonkeyPattern.matcher(inputString);
                if(matcher1.find()) {
                    Monkey monkey = Monkey.getMonkey(matcher1.group(1));
                    monkey.integer = Long.parseLong(matcher1.group(2));
                }
            }
        });

        System.out.println(getInteger(Monkey.getMonkey("root")));
    }

    private static long getInteger(Monkey root) {
        if(root.integer != null) return root.integer;
        if(root.monkey1.integer==null) getInteger(root.monkey1);
        if(root.monkey2.integer==null) getInteger(root.monkey2);
        return root.getInt();
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(MonkeyMath.class.getResource("input.txt")).toURI()));
    }

    static class Monkey{
        static Map<String, Monkey> map = new HashMap<>();
        String name;
        Monkey monkey1;
        Monkey monkey2;
        Long integer;
        char opr;
        static Monkey getMonkey(String name){
            if(map.get(name)==null) {
                Monkey  monkey = new Monkey();
                monkey.name = name;
                map.put(name,monkey);
            }
            return map.get(name);
        }

        long getInt() {
            switch (opr){
                case '+': integer = monkey1.integer+ monkey2.integer; break;
                case '-': integer = monkey1.integer- monkey2.integer; break;
                case '*': integer = monkey1.integer* monkey2.integer; break;
                case '/': integer = monkey1.integer/ monkey2.integer; break;
            }
            return integer;
        }
    }
}

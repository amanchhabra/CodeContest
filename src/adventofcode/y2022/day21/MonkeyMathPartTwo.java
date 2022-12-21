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

public class MonkeyMathPartTwo {

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
                    if(!monkey.name.equalsIgnoreCase("humn")) {
                        monkey.integer = Long.parseLong(matcher1.group(2));
                    }
                }
                else {
                    System.out.println("Parsing failed for "+inputString);
                }
            }
        });

        getInteger(Monkey.getMonkey("root"), null);
        System.out.println(Monkey.getMonkey("humn").integer);
    }

    private static Long getInteger(Monkey root, Long requiredNumber) {
        if(root.integer != null ) return root.integer;
        if(requiredNumber== null && root.monkey1!= null && root.monkey1.integer==null) getInteger(root.monkey1, requiredNumber);
        if(requiredNumber== null &&root.monkey2!= null &&root.monkey2.integer==null) getInteger(root.monkey2, requiredNumber);
        if( root.monkey1!= null && root.monkey1.integer==null && root.name.equalsIgnoreCase("root")){
            root.monkey1.integer = getInteger(root.monkey1, root.monkey2.integer);
        }
        if(root.monkey2!= null && root.monkey2.integer==null && root.name.equalsIgnoreCase("root")){
            root.monkey2.integer = getInteger(root.monkey2, root.monkey1.integer);
        }
        if(requiredNumber == null)
        return root.getInt();
        else {
            if(root.name.equalsIgnoreCase("humn")) {
                root.integer = requiredNumber;
                return requiredNumber;
            }
            if(root.monkey1.integer==null) {
                return getInteger(root.monkey1, root.getNewInt(requiredNumber,root.monkey2));
            } else  if(root.monkey2.integer==null) {
                return getInteger(root.monkey2, root.getNewInt(requiredNumber,root.monkey1));
            }
        }
        return null;
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(MonkeyMathPartTwo.class.getResource("input.txt")).toURI()));
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

        Long getInt() {
            if(name.equalsIgnoreCase("humn")) return null;
            if(monkey1.integer == null||monkey2.integer==null) return null;
            switch (opr){
                case '+': integer = monkey1.integer+ monkey2.integer; break;
                case '-': integer = monkey1.integer- monkey2.integer; break;
                case '*': integer = monkey1.integer* monkey2.integer; break;
                case '/': integer = monkey1.integer/ monkey2.integer; break;
            }
            return integer;
        }

        Long getNewInt(long newInt, Monkey monkey) {
            long int1 = monkey.integer;
            switch (opr){
                case '+': int1= newInt-monkey.integer; break;
                case '-': if (monkey == monkey2) {int1= newInt+monkey.integer;} else {int1 = monkey.integer-newInt;} break;
                case '*': int1= newInt/monkey.integer; break;
                case '/': if (monkey == monkey2) {int1= newInt*monkey.integer;} else {int1 = monkey.integer/newInt;} break;
            }
            return int1;
        }
    }
}

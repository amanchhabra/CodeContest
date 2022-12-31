package adventofcode.y2022.day13;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class DistressSignal {

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        int count = 1;
        int pairs = 0;
       for(int i =0;i<input.size();i++) {
           Boolean matched = match(formatSignal(input.get(i)), formatSignal(input.get(++i)));
           if(matched == null || matched){
               pairs+=count;
           }
           count++;
           i++;
       }
        System.out.println(pairs);
    }

    public DistressSignal() {
    }

    private static Boolean match(Match match1, Match match2) {
        if(match1.matches == null && match2.matches == null) {
            return matchInteger(match1.integer, match2.integer);
        }
        if(match1.matches == null && match2.matches != null) {
            Match match = new Match();
            match.integer = match1.integer;
            match1.matches = new ArrayList<>();
            match1.matches.add(match);
        }
        if(match2.matches == null && match1.matches != null) {
            Match match = new Match();
            match.integer = match2.integer;
            match2.matches = new ArrayList<>();
            match2.matches.add(match);
        }
        if(match1.matches.size() == 0)
            return true;

        for(int i =0;i<match1.matches.size();i++) {
            if(i<match2.matches.size()) {
                Boolean isCompared = match(match1.matches.get(i),match2.matches.get(i));
               if(isCompared == null) continue;
               return isCompared;
            } else {
                return false;
            }
        }
        if(match1.matches.size()<match2.matches.size()) return true;
        return null;
    }

    private static Boolean matchInteger(int int1, int int2) {
        if(int1 < int2) return true;
        else if(int1 == int2) return null;
        else return false;
    }

    private static Match formatSignal(String s) {
        System.out.println(s+"->");
        Match match = new Match();
        match.matches = new ArrayList<>();
        Match curMatch = match;
        int count = 0;
        while (count < s.length()) {
            switch (s.charAt(count)) {
                case '[':
                    Match match1 = new Match();
                    match1.matches = new ArrayList<>();
                    curMatch.matches.add(match1);
                    match1.previous = curMatch;
                    curMatch = match1;
                    break;
                case ']':
                    curMatch = curMatch.previous;
                    break;
                case ',':
                    break;
                default:
                    int in = s.indexOf(',', count);
                    int in1 = s.indexOf(']', count);
                    if(in!=-1 ||in1!=-1) {
                        Match match2 = new Match();
                        match2.integer = Integer.parseInt(s.substring(count, in < in1 ? (in == -1 ? in1 : in) :(in1==-1?in:in1) ));
                        curMatch.matches.add(match2);
                    }
            }
            count++;
        }
        return match;
    }

    static class Match {
        List<Match> matches;
        Match previous;
        Integer integer;
    }
    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(DistressSignal.class.getResource("input.txt")).toURI()));
    }
}

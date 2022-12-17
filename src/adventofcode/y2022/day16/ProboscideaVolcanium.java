package adventofcode.y2022.day16;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

public class ProboscideaVolcanium {

    static Pattern inputPattern = Pattern.compile("^Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? ([A-Z ,]+)*$");

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> lines = readInput();
        lines
                .stream()
                .map(data ->inputPattern.matcher(data))
                .forEach(data -> {data.find(); Valve valve = Valve.getValve(data.group(1));valve.flow = Integer.parseInt(data.group(2)); valve.setConnectedValves(data.group(3));});

        Valve.volvesMap.values().forEach(valve -> {
            findShortestPath(valve, 0);
            Valve.volvesMap.values().forEach(nextValve -> {if(valve != nextValve && nextValve.flow>0){valve.weightedEdges.add(new WeightedEdge(valve, nextValve, nextValve.shortestPath));}});
            resetShortestPath();
        }
        );

        Valve nextValve = Valve.getValve("AA");
        System.out.println(maxPressure(nextValve, 0,0, 30));
    }

    public static int maxPressure(Valve currentValve, int currentPressure, int totalPressure, int minute) {
        if(currentValve.parsed) return totalPressure+(currentPressure*minute);
        if(minute<=0) {
            return 0;
        }
        currentValve.parsed = true;
        if(minute>1){
            minute--;
            currentPressure=currentPressure+currentValve.flow;
            totalPressure+=currentPressure;
        }
        int maxNewPressure = totalPressure;
        int count = 0;
        for(WeightedEdge edge: currentValve.weightedEdges) {
            if(minute-edge.weight>0) { count++;
                int tempEffort = maxPressure(edge.destination, currentPressure, totalPressure+(edge.weight*currentPressure), minute - edge.weight);
                maxNewPressure = Math.max(maxNewPressure, tempEffort);
            }
        }
        if(count==0) {
            maxNewPressure = totalPressure+currentPressure*minute;
        }
        currentValve.parsed = false;
        return maxNewPressure;
    }

    private static void resetShortestPath() {
        for (Valve valve: Valve.volvesMap.values()) {
            valve.shortestPath = 0;
            valve.parsed = false;
        }
    }

    private static void findShortestPath(Valve aa, int path) {
        if(!aa.parsed)
        {
            aa.parsed = true;
            aa.shortestPath = path;
        } else {
            if(aa.shortestPath<=path) return;
            else aa.shortestPath=path;
        }
        for(Valve valve: aa.valveList) {
            findShortestPath(valve, path+1);
        }
    }

    private static class WeightedEdge{
        Valve source;
        Valve destination;
        int weight;

        public WeightedEdge(Valve source, Valve destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    private static class Valve {
        public List<Valve> valveList = new ArrayList<>();
        public List<WeightedEdge> weightedEdges = new ArrayList<>();
        static Map<String, Valve> volvesMap = new HashMap();
        String name;
        int flow;
        int shortestPath;
        boolean parsed = false;

        private Valve(String name ) {
            this.name = name;
        }

        public static Valve getValve(String name ) {
            Valve cv = volvesMap.get(name);
            if(cv == null) {
                cv = new Valve(name);
                volvesMap.put(name, cv);
            }
            return cv;
        }

        public void setConnectedValves(String valves) {
            String[] connectedValves = valves.split(", ");
            for(String cvs: connectedValves) {
                Valve cv = getValve(cvs);
                valveList.add(cv);
            }
        }

        public String toString() {
            return "Valve{" +
                    "name='" + name + '\'' +
                    ", flow=" + flow +
                    '}';
        }
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(ProboscideaVolcanium.class.getResource("input.txt")).toURI()));
    }
}

package adventofcode.y2022.day12;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HillClimbingAlgorithmPartTwo {

    static  int i='a';

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();

        Node[][] path = new Node[input.size()][input.get(0).length()];

        List<Node> allNodes = new ArrayList<>();
        List<Node> startingNodes = new ArrayList<>();

       Node startNode = null;
       Node endNode = null;

        int count = 0;
        for(String inputString: input){
            int j = 0;
            for(char c: inputString.toCharArray()) {
                Node node = new Node();
                if(c=='S') {
                   startNode = node;
                   node.isStartNode = true;
                    c='a';
                } else if(c=='E') {
                    endNode = node;
                    node.isEndNode = true;
                    c='z';
                }
                if(c=='a') {
                    startingNodes.add(node);
                }
                node.height = c;
                path[count][j]=node;
                allNodes.add(node);
                j++;
            }
            count++;
        }

        for (int i=0;i<path.length;i++) {
            for(int j=0;j<path[0].length;j++)
            {
                Node currentNode = path[i][j];
                connectNodes(path, i, j-1,currentNode);
                connectNodes(path, i-1, j, currentNode);
                connectNodes(path, i+1, j,currentNode);
                connectNodes(path, i, j+1,currentNode);
                currentNode.connectedNodes = currentNode.connectedNodes.stream().sorted(new Comparator<Node>() {
                    @Override
                    public int compare(Node o1, Node o2) {
                        return o2.height-o1.height;
                    }
                }).collect(Collectors.toList());
            }
        }

        System.out.println("Starting search");
        int shortestPath = 200000;
        for(Node node: startingNodes) {
            shortestPath = Math.min(shortestPath,getShortestPath(node,0));
            for (Node allNode : allNodes) {
                allNode.leastPath = -1;
            }
        }
        System.out.println(shortestPath);
    }

    private static int getShortestPath(Node startNode,int totalPath) {
        if(startNode.isEndNode){
            return totalPath;
        }
       if(startNode.leastPath!=-1 && startNode.leastPath<totalPath)
          totalPath= startNode.leastPath;
       else
           startNode.leastPath = totalPath;
        startNode.isParsed = true;
        int path=200000;
        for(Node node: startNode.connectedNodes) {
            if(!node.isParsed && (node.leastPath>(totalPath+1)||node.leastPath==-1)) {
                int tPath = getShortestPath(node,totalPath+1);
                path = Math.min(path,tPath);
            }
        }
        startNode.isParsed = false;
        return path;
    }

    private static void connectNodes(Node[][] path, int i, int j, Node currentNode) {
        Node anotherNode = getNode(i, j, path);
        if(anotherNode!= null && anotherNode.height-currentNode.height<=1 )
            currentNode.connectedNodes.add(anotherNode);
    }

    private static Node getNode(int i, int j, Node[][] path) {
        if(i>=0 && i< path.length && j>= 0 && j < path[0].length) return path[i][j];
        else return null;
    }

    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(HillClimbingAlgorithmPartTwo.class.getResource("input_sample.txt")).toURI()));
    }

    static class Node {
        boolean isStartNode = false;
        boolean isEndNode = false;
        boolean isParsed = false;
        boolean isInvalid = false;
        int height;
        int leastPath = -1;
        List<Node> connectedNodes = new ArrayList<>();

        @Override
        public String toString() {
            return ""+(char)height;
        }
    }
}

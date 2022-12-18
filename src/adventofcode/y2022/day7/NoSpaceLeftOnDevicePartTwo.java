package adventofcode.y2022.day7;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NoSpaceLeftOnDevicePartTwo {

    static Pattern cdPattern = Pattern.compile("\\$ cd ([//.a-z]*)");
    static Pattern filePattern = Pattern.compile("(\\d+) ([a-z.]+)");

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = readInput();
        List<Directory> allDirectories = new ArrayList<>();
        Directory rootDirectory = new Directory();
        Directory curDirectory = rootDirectory;
        for (String inputString: input) {
            if (inputString.startsWith("$ cd")) {
                Matcher matcher = cdPattern.matcher(inputString);
                matcher.find();
                String directoryName = matcher.group(1);
                if (directoryName.equalsIgnoreCase("/")) {
                    curDirectory = rootDirectory;
                }  else if (directoryName.equalsIgnoreCase("..")) {
                    curDirectory = curDirectory.parentDirectory;
                } else {
                    Optional<Directory> newDirectory = curDirectory.directories.stream().filter(directory -> directory.name.equals(directoryName)).findAny();
                    if (newDirectory.isPresent()) {
                        curDirectory = newDirectory.get();
                    } else {
                        Directory directory = new Directory();
                        directory.name = directoryName;
                        directory.parentDirectory = curDirectory;
                        curDirectory.directories.add(directory);
                        allDirectories.add(directory);
                        curDirectory = directory;
                    }
                }
            } else if (inputString.startsWith("$ ls")) ;
            else {
                Matcher matcher = filePattern.matcher(inputString);
                if(matcher.find()) {
                    String fileName = matcher.group(2);
                    Optional<File> newFile = curDirectory.files.stream().filter(file -> file.name.equals(fileName)).findAny();
                    if (!newFile.isPresent()) {
                        File file = new File();
                        file.name = fileName;
                        file.size  = Integer.parseInt(matcher.group(1));
                        curDirectory.files.add(file);
                    }
                }
            }
        }
        getSize(rootDirectory);
        int unsedSpace = 70000000 - rootDirectory.size;
        int spaceRequired = 30000000 - unsedSpace;
        System.out.println(allDirectories.stream().map(directory -> directory.size).filter(size -> size >= spaceRequired).sorted().findFirst().get());
    }

    private static void getSize(Directory directory) {
        for(Directory newDirectory: directory.directories) {
            if(newDirectory.size == -1)
                getSize(newDirectory);
        }
        int dirSize = directory.directories.stream().map(dir->dir.size).reduce((a,b)->a+b).orElse(0);
        int fileSize = directory.files.stream().map(file -> file.size).reduce((a,b)->a+b).orElse(0);
        directory.size = dirSize+fileSize;
        return;
    }


    private static List<String> readInput() throws IOException, URISyntaxException {
        return Files.readAllLines(Paths.get(Objects.requireNonNull(NoSpaceLeftOnDevicePartTwo.class.getResource("input.txt")).toURI()));
    }

    static class Directory{
        List<File> files = new ArrayList<>();
        Directory parentDirectory = null;
        List<Directory> directories = new ArrayList<>();
        String name;
        int size=-1;
    }

    static class File{
        int size;
        String name;
    }
}

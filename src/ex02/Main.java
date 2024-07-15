import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        if (!args[0].contains("--current-folder=")) {
            System.err.println("not found arguments");
            System.exit(-1);
        }
        if (new File(args[0].substring("--current-folder=".length())).exists()) {
            navigateAndList(args[0].substring("--current-folder=".length()));
        } else {
            System.err.println("no such file or directory");
        }

    }

    public static File[] listFiles(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles();
        for (File file : files) {
            System.out.println(file.getName() + " KB " + (file.length() / 1000D));
        }
        return files;
    }

    public static String changeDirectory(String currentPath, String currentInput) {
        String[] arrayCurrentInput = currentInput.split("/");
        if (arrayCurrentInput[0].equals("..")) {
            return currentPath.substring(0, currentPath.lastIndexOf('/')) + currentInput.substring(
                    2);
        } else {
            File dir = new File(currentPath + "/" + currentInput);
            if (!dir.exists()) {
                System.out.println("no such file or directory");
                return currentPath;
            }
            return currentPath + "/" + currentInput;
        }
    }

    public static void move(String currentInput, String currentPath) throws IOException {
        String[] arrayCurrentPath = currentInput.split(" ");
        if (arrayCurrentPath[2].contains("/")) {
            Path source = Paths.get(currentPath + "/" + arrayCurrentPath[1]);
            Path target = Paths.get(
                    currentPath + "/" + arrayCurrentPath[2] + "/" + arrayCurrentPath[1]);
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
        } else {
            File fileName = new File(currentPath + "/" + arrayCurrentPath[1]);
            File renamedName = new File(currentPath + "/" + arrayCurrentPath[2]);
            fileName.renameTo(renamedName);
        }
    }

    public static void navigateAndList(String path) throws IOException {
//        String path = "/opt/goinfre/wilmerbl/proj/JavaBootcamp/Java_Bootcamp.Day02-1/src/ex02";
        Scanner console = new Scanner(System.in);
        while (true) {
            String currentInput = console.nextLine();
            if (currentInput.equals("exit")) {
                System.exit(0);
            }
            String[] arrayPath = currentInput.split(" ");
            if (arrayPath[0].equals("ls")) {
                listFiles(path);
            } else if (arrayPath[0].equals("cd")) {
                path = changeDirectory(path, arrayPath[1]);
            } else if (arrayPath[0].equals("mv")) {
                move(currentInput, path);
            }
        }
    }

}

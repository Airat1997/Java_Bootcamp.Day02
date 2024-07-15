import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner console = new Scanner(System.in);
        while (true) {
            String path = console.nextLine();
            if (path.equals("42")) {
                break;
            }
            Map<String, String> typeFiles = new HashMap<>();
            populateFileWithSignatures(typeFiles);
            try {
                identifyFileTypeByContent(typeFiles, path);
            } catch (FileNotFoundException e){
                System.out.println("File not found");
            }
        }
    }

    public static void populateFileWithSignatures(Map<String, String> typeFiles)
            throws IOException {
        try (FileInputStream signatures = new FileInputStream("signatures.txt")) {
            Scanner scanner = new Scanner(signatures);
            while (scanner.hasNextLine()) {
                String currentString = scanner.nextLine();
                typeFiles.put(currentString.split(", ")[0], currentString.split(", ")[1]);
            }
        }
    }

    public static void identifyFileTypeByContent(Map<String, String> typeFiles, String path)
            throws IOException {
        int a;
        String result = "";
        boolean flag = false;
        String format = "";
        try (FileInputStream file = new FileInputStream(path)) {
            int i = 0;
            while (i < 10000) {
                a = file.read();
                if (Integer.toHexString(a).length() == 1) {
                    result += "0";
                }
                result += Integer.toHexString(a) + " ";
                i++;
            }
            for (Entry entry : typeFiles.entrySet()) {
                String key = (String) entry.getKey();
                String value = (String) entry.getValue();
                if (result.toUpperCase().contains(value)) {
                    System.out.println("PROCESSED");
                    format = key;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                recorFileFormat(format + "\n");
            } else {
                System.out.println("UNDEFINED");
            }
        }
    }

    public static void recorFileFormat(String format) throws IOException {
        try (FileOutputStream result = new FileOutputStream("result.txt", true)) {
            byte[] data = format.getBytes();
            result.write(data);
        }
    }

}

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        fillHashSet(hashSet, "1.txt");
        fillHashSet(hashSet, "2.txt");
        HashMap<String, Integer> hashMap = new HashMap<>();
        HashMap<String, Integer> hashMap2 = new HashMap<>();
        for (int i = 0; i < hashSet.toArray().length; i++) {
            hashMap.put((String) hashSet.toArray()[i], 0);
            hashMap2.put((String) hashSet.toArray()[i], 0);
        }
        counterUniqueString("1.txt", hashMap);
        counterUniqueString("2.txt", hashMap2);
        Integer numerator = getNumerator(hashMap, hashMap2);
        double denominator = getDenominator(hashMap, hashMap2);
        System.out.format("Similarity = %.3f", numerator/denominator);
    }

    public static void fillHashSet(HashSet hashSet, String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] currentArray = line.split(" ");
                hashSet.addAll(Arrays.asList(currentArray));
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    public static void counterUniqueString(String path, HashMap hashMap) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            Integer current = 0;
            while ((line = reader.readLine()) != null) {
                int counter = 0;
                String[] currentArray = line.split(" ");
                for (int i = 0; i < currentArray.length; i++) {
                    if (hashMap.containsKey(currentArray[i])) {
                        Integer cur = (Integer) hashMap.get(currentArray[i]);
                        cur += 1;
                        hashMap.put(currentArray[i], cur);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }
    public static Integer getNumerator(HashMap A, HashMap B){
        Integer result = 0;
        Iterator itrA = A.values().iterator();
        Iterator itrB = B.values().iterator();
        for (int i = 0; i < A.size(); i++) {
            Integer a = (Integer) itrA.next();
            Integer b = (Integer) itrB.next();
            result += a*b;
        }
        return result;
    }

    public static double getDenominator(HashMap A, HashMap B){
        double a = 0;
        double b = 0;
        Iterator itrA = A.values().iterator();
        Iterator itrB = B.values().iterator();
        for (int i = 0; i < A.size(); i++) {
            double tempA = (Integer) itrA.next();
            double tempB = (Integer) itrB.next();
            a += tempA*tempA;
            b += tempB*tempB;
        }
        return Math.sqrt(a) * Math.sqrt(b);
    }



}

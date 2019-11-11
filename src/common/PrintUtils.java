package common;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

import static module1.Constant.nucle;

public class PrintUtils {
    public static <T> void printListInOneline(List<T> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i) + " ");
        }
        System.out.println(sb.toString().trim());
    }

    public static <T> void printListByLine(List<T> list) {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static <T> void printSetByOneLine(Set<T> set) {
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            System.out.print(it.next().toString() + " ");
        }
    }

    public static <T> void printCycle(List<T> list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i) + "->");
        }
        System.out.println(sb.delete(sb.length() - 2, sb.length()));
    }


    public static <T> void printSetByLine(Set<T> set) {
        for (T item : set) {
            System.out.println(item);
        }
    }

    public static <T> void writeListToFile(List<T> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output1.txt"));
        for (int i = 0; i < list.size(); i++) {
            writer.write(list.get(i) + "\n");
            writer.flush();
        }
        writer.close();
    }

    public static <T> void writeSetToFile(Set<T> set) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output1.txt"));
        for (T item : set) {
            writer.write(item + "\n");
            writer.flush();
        }
        writer.close();
    }

    public static void printProfile(double[][] profile) {
        for (int i = 0; i < 4; i++) {
            System.out.print(nucle[i] + ":");
            for (int j = 0; j < profile[0].length; j++) {
                System.out.print(profile[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------------------------");
    }


}

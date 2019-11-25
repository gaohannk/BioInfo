package common;

import java.util.*;

import static common.Constant.nucle;

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
        if(list.size()==0){
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i) + "->");
        }

        System.out.println(sb.delete(sb.length() - 2, sb.length()));
    }

    public static <T> void printPath(List<T> list) {
        if(list.size()==0){
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i) + " -> ");
        }

        System.out.println(sb.delete(sb.length() - 4, sb.length()));
    }

    public static <T> void printSetByLine(Set<T> set) {
        for (T item : set) {
            System.out.println(item);
        }
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

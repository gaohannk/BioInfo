package module1;

import java.util.HashMap;
import java.util.Map;

public class Constant {
    public static Map<String, Integer> INDEX = new HashMap<>() {{
        put("A", 0);
        put("C", 1);
        put("G", 2);
        put("T", 3);
    }};

    public static Map<Character, Character> dic = new HashMap<>() {{
        put('T', 'A');
        put('A', 'T');
        put('G', 'C');
        put('C', 'G');
    }};

    public static char[] nucle = new char[]{'A', 'C', 'G', 'T'};

}

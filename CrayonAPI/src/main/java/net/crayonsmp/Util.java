package net.crayonsmp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {
    public static String[] multiSplit(String textToSplit, String... splitRegex){
        List<String> strings = new ArrayList<>();
        strings.add(textToSplit);
        for (String regex : splitRegex){
            List<String> copy = List.copyOf(strings);
            strings.clear();
            for (String s : copy){
                strings.addAll(Arrays.stream(s.split(regex)).toList());
            }
        }
        return strings.toArray(String[]::new);
    }
}

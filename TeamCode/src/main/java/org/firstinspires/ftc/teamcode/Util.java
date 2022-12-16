package org.firstinspires.ftc.teamcode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Util {
    /**
     * Clamp a value between a max and a min value.
     * @param value - The value to clamp.
     * @param min - The minimum value.
     * @param max - The maximum value.
     * @return - The newly clamped value.
     */
    public static double clamp(double value,double min,double max){
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Get the most common element in a list.
     *
     * @param list the list
     * @param <T> the type of element
     * @return the most common element.
     */

    // from https://stackoverflow.com/questions/19031213/java-get-most-common-element-in-a-list
    public static <T> T mostCommon(List<T> list) {
        Map<T, Integer> map = new HashMap<>();

        for (T t : list) {
            Integer val = map.get(t);
            map.put(t, val == null ? 1 : val + 1);
        }

        Entry<T, Integer> max = null;

        for (Entry<T, Integer> e : map.entrySet()) {
            if (max == null || e.getValue() > max.getValue())
                max = e;
        }

        return max.getKey();
    }


}
package com.sng;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores the frequency of a word
 */
public class Frequency<T> {
    private final Map<T, Integer> frequency = new HashMap<>();

    public Frequency() {
    }

    /**
     * Increase the object's frequency count by one
     *
     * @param object object to add
     */
    public void add(T object) {
        if (object == null) return;
        this.frequency.putIfAbsent(object, 0);
        this.frequency.put(object, this.frequency.get(object) + 1);
    }

    /**
     * Get a list of registered objects
     *
     * @return list containing all registered objects
     */
    public List<T> getAllWords() {
        return this.frequency.keySet().stream().toList();
    }

    /**
     * Get frequency of an object
     *
     * @param object object to look for
     * @return the frequency of the specified object
     */
    public Integer getFrequency(T object) {
        Integer count = this.frequency.get(object);
        return count == null ? 0 : count;
    }
}

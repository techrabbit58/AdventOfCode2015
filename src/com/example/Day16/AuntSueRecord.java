package com.example.Day16;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * E X A M P L E
 * Sue 42: akitas: 4, trees: 2, goldfish: 3
 */
@ToString
public class AuntSueRecord {

    private final Map<String, Integer> _inventory;
    private final int _sueId;

    public AuntSueRecord(int sueId) {
        _inventory = new HashMap<>();
        _sueId = sueId;
    }

    public void setProperty(String key, int value) {
        _inventory.put(key, value);
    }

    /**
     * Check a property for the right value.
     * If the property is there and the value is like the given value, the check does succeed.
     * If the property is absent, the check does succeed.
     * If the property is there, but the values don't match, the check fails.
     * @param key the property to check
     * @param value the value to match, if the property is there
     * @return true if match or absent, false if mismatch
     */
    public boolean propertyMatches(String key, int value) {
        return _inventory.getOrDefault(key, value) == value;
    }

    public int sueId() {
        return _sueId;
    }
}

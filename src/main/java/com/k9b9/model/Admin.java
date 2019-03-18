package com.k9b9.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Admin
 */
public class Admin {

    public enum Level {

        ROOT(0),
        ADMIN(100),
        MANAGER(200),
        VIEWER(300);

        private int value;
        private static Map<Integer,Level> map = new HashMap<Integer,Level>();

        private Level(int value) {
            this.value = value;
        }

        static {
            for (Level level : Level.values()) {
                map.put(level.value, level);
            }
        }

        public static Level valueOf(int level) {
            return (Level) map.get(level);
        }

        public int getValue() {
            return value;
        }
    }
}
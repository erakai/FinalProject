package com.kai.fp.objs.entities;

/**
 * @author Kai on May 16, 2019
 */
import java.util.HashMap;
import java.util.Map;

public class StatManager {

    private Map<String, Stat> stats;

    public StatManager() {
        stats = new HashMap<>();
    }

    public void addStat(String statName, String description, int baseValue) {
        Stat newStat = new Stat(statName, description);
        newStat.baseValue = baseValue;
        stats.put(statName, newStat);
    }

    public void addStat(String statName, String description) {
        addStat(statName, description, 0);
    }

    public Stat getStat(String name) {
        return stats.getOrDefault(name, null);
    }

    public void setStat(String name, int newBase) {
        Stat stat = getStat(name);
        if (stat!=null) stat.baseValue = newBase;
    }

    public void incStat(String name, int amount) {
        Stat stat = getStat(name);
        if (stat!=null) stat.positiveChange += amount;
    }

    public void removeIncStat(String name, int amount) {
        Stat stat = getStat(name);
        if (stat!=null) stat.positiveChange -= amount;
    }

    public void decStat(String name, int amount) {
        Stat stat = getStat(name);
        if (stat!=null) stat.negativeChange += amount;
    }

    public void removeDecStat(String name, int amount) {
        Stat stat = getStat(name);
        if (stat!=null) stat.negativeChange -= amount;
    }

    public Map<String, Stat> getStats() {
        return stats;
    }

    public class Stat {
        private String name;
        private String desc;

        public int baseValue = 0;
        public int positiveChange = 0;
        public int negativeChange = 0;

        public Stat(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }

        public String getDesc() {
            return desc;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return baseValue + positiveChange - negativeChange;
        }

        @Override
        public String toString() {
            return "Stat{" +
                    "name='" + name + '\'' +
                    ", value=" + getValue() +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StatManager{" +
                "stats=" + stats +
                '}';
    }
}

package com.kai.fp.objs.entities;

/**
 * @author Kai on May 16, 2019
 */
import java.util.HashMap;
import java.util.Map;

public class StatManager {

    //TODO: total rewrite of this garbage class
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
        stat.baseValue += amount;

        if (name.equals("max health")) {
            incStat("health", amount);
        }

        if (getStat("health").getValue() > getStat("max health").getValue()) {
            getStat("health").baseValue = stat.baseValue;
        }
    }

    public void removeIncStat(String name, int amount) {
        decStat(name, amount);
    }

    public void decStat(String name, int amount) {
        Stat stat = getStat(name);
        if (amount < 0) {
            stat.baseValue += amount;
        } else {
            stat.baseValue -= amount;
        }

        if (name.equals("max health")) {
            getStat("health").baseValue -= amount;
            if (getStat("health").getValue() < 1) {
                Stat health = getStat("health");
                health.baseValue = 1;
            }
            if (getStat("health").getValue() > getStat("max health").getValue()) {
                getStat("health").baseValue = stat.baseValue;
            }
        }
    }

    public void removeDecStat(String name, int amount) {
        incStat(name, amount);
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

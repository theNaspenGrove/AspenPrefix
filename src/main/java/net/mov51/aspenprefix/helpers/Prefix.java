package net.mov51.aspenprefix.helpers;

public class Prefix {
    private final String prefix;
    private final int weight;

    public Prefix(String prefix, int weight) {
        this.prefix = prefix;
        this.weight = weight;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getWeight() {
        return weight;
    }

}

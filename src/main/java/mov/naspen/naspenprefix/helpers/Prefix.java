package mov.naspen.naspenprefix.helpers;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class Prefix {
    private final TextComponent prefix;
    private final int weight;

    public Prefix(String prefix, int weight) {
        this.prefix = LegacyComponentSerializer.legacyAmpersand().deserialize(prefix);
        this.weight = weight;
    }

    public TextComponent getPrefix() {
        return prefix;
    }

    public int getWeight() {
        return weight;
    }

}

package me.devkevin.core.ranks;

import me.devkevin.core.utils.CC;
import org.bukkit.craftbukkit.libs.joptsimple.internal.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum  Rank
{

    NORMAL("", CC.GREEN, "Normal"),
    VERIF(CC.AQUA + "✔", CC.DARK_AQUA, "Verif"),
    CHEATER(CC.RED, "u click bro? ur weird"),
    MEMBER(CC.D_GRAY + "[" + CC.D_GREEN + "M" + CC.D_GRAY + "] ", CC.GREEN, "Member"),
    CLUBBER(CC.D_GRAY + "[" + CC.D_PURPLE + "C\u2605" + CC.D_GRAY + "] ", CC.PINK, "Clubber"),
    DROPPER(CC.D_GRAY + "[" + CC.D_AQUA + "\u2721" + CC.D_GRAY + "] ", CC.AQUA, "Dropper"),
    BUILDER(CC.D_GREEN, "Builder"),
    YOUTUBER(CC.D_GRAY + "[" + CC.D_PURPLE + "YouTuber" + CC.D_GRAY + "] ", CC.LIGHT_PURPLE, "YouTuber"),
    FAMOUS(CC.GOLD, "Famous"),
    TRAINEE(CC.YELLOW, "Trainee"),
    MOD(CC.D_AQUA, "Moderator"),
    ADMIN(CC.RED, "Admin"),
    MEDIA_OWNER(CC.D_RED, "Owner"),
    PLATFORM_ADMINISTRATOR(CC.RED, CC.ITALIC, "Platform-Admin"),
    MANAGER(CC.RED + CC.ITALIC, "Manager"),
    DEVELOPER(CC.AQUA + CC.ITALIC, "Developer"),
    OWNER(CC.D_RED, "Owner");

    public static final Rank[] RANKS;
    private final String prefix;
    private final String color;
    private final String name;
    private int id;

    private Rank(final String prefix, final String color, final String name) {
        this.prefix = prefix;
        this.color = color;
        this.name = name;
        this.id = id;
    }

    private Rank(final String color, final String name) {
        this(CC.DARK_GRAY + "[" + color + name + CC.DARK_GRAY + "] ", color, name);
    }

    public static Rank getByName(final String name) {
        return Arrays.stream(Rank.RANKS).filter( rank -> rank.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public String getName() {
        final StringBuilder builder = new StringBuilder();
        final String split = this.toString().replaceAll("_", " ");
        for (final String s : split.split(" ")) {
            final String[] name = s.toLowerCase().split("");
            name[0] = name[0].toUpperCase();
            builder.append(Strings.join(name, "")).append("-");
        }
        return builder.substring(0, builder.length() - 1);
    }

    public int getPriority() {
        return this.ordinal();
    }

    public boolean hasRank(final Rank requiredRank) {
        return this.getPriority() >= requiredRank.getPriority();
    }

    public boolean isStaff() {
        return this.ordinal() >= Rank.TRAINEE.ordinal();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getColor() {
        return this.color;
    }

    public int getId() {
        return this.id;
    }

    public boolean isBelowOrEqual(Rank rank) {
        return this.ordinal() <= rank.ordinal();
    }

    public boolean isAboveOrEqual(Rank rank) {
        return this.ordinal() >= rank.ordinal();
    }

    public static Rank getRankOrDefault(final int i) {
        Rank rank;
        if (i == 0) {
            rank = Rank.NORMAL;
        }
        else if (i == 1) {
            rank = Rank.VERIF;
        }
        else if (i == 2) {
            rank = Rank.CHEATER;
        }
        else if (i == 3) {
            rank = Rank.MEMBER;
        }
        else if (i == 4) {
            rank = Rank.CLUBBER;
        }
        else if (i == 5) {
            rank = Rank.DROPPER;
        }
        else if (i == 6) {
            rank = Rank.BUILDER;
        }
        else if (i == 7) {
            rank = Rank.YOUTUBER;
        }
        else if (i == 8) {
            rank = Rank.FAMOUS;
        }
        else if (i == 9) {
            rank = Rank.TRAINEE;
        }
        else if (i == 10) {
            rank = Rank.MOD;
        }
        else if (i == 11) {
            rank = Rank.ADMIN;
        }
        else if (i == 12) {
            rank = Rank.PLATFORM_ADMINISTRATOR;
        }
        else if (i == 13) {
            rank = Rank.MANAGER;
        }
        else if (i == 14) {
            rank = Rank.MEDIA_OWNER;
        }
        else if (i == 15) {
            rank = Rank.OWNER;
        }
        else if (i == 16) {
            rank = Rank.DEVELOPER;
        }
        else {
            rank = Rank.NORMAL;
        }
        return rank;
    }

    static {
        RANKS = values();
    }
}

package me.devkevin.core.Profile;

import java.util.*;
import org.bukkit.*;
import org.bukkit.entity.*;

public interface ProfileManager
{
    boolean isCreated(final UUID p0);

    void createProfile(final UUID p0);

    String getPlayerName(final UUID p0);

    OfflinePlayer getOfflinePlayer(final UUID p0);

    Player getPlayer(final UUID p0);

    String getSymbol(final UUID p0);

    boolean isRegister(final UUID p0);

    void setRegister(final UUID p0, final boolean p1);

    int getPassword(final UUID p0);

    void setPassword(final UUID p0, final int p1);

    boolean isLogging(final UUID p0);

    void setLogged(final UUID p0, final boolean p1);
}


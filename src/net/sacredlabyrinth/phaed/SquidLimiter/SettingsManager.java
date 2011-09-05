package net.sacredlabyrinth.phaed.SquidLimiter;

import java.util.LinkedList;
import java.util.List;
import org.bukkit.util.config.Configuration;

/**
*
* @author cc_madelg
*/
public final class SettingsManager
{
    public SquidLimiter plugin;
    public List<Integer> limits;
    public List<String> worlds;
    public int squidsPerChunk;

    /**
*
* @param plugin
*/
    public SettingsManager(SquidLimiter plugin)
    {
        this.plugin = plugin;
        loadConfiguration();
    }

    /**
* Load the configuration
*/
    public void loadConfiguration()
    {
        Configuration config = plugin.getConfiguration();
        config.load();

        List<String> _worlds = new LinkedList<String>();
        _worlds.add("world");

        List<Integer> _limits = new LinkedList<Integer>();
        _limits.add(500);

        worlds = config.getStringList("worlds", _worlds);
        limits = config.getIntList("limits", _limits);
        squidsPerChunk = config.getInt("squids-per-chunk", 1);

        config.setProperty("worlds", worlds);
        config.setProperty("limits", limits);
        config.setProperty("squids-per-chunk", squidsPerChunk);
        config.save();
    }

    /**
* Whether the world has a squid limit in place
* @param world
* @return
*/
    public boolean hasLimit(String world)
    {
        return worlds.contains(world);
    }

    /**
* Gets the world's squid limit
* @param world
* @return
*/
    public int getLimit(String world)
    {
        if (!hasLimit(world))
        {
            return 999999999;
        }

        return limits.get(worlds.indexOf(world));
    }
}


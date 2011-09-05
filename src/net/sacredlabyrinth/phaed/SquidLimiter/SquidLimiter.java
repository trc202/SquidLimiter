package net.sacredlabyrinth.phaed.SquidLimiter;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

/**
*
* @author phaed
*/
public class SquidLimiter extends JavaPlugin
{
    public static Logger logger = Logger.getLogger("Minecraft");
    private SLEntityListener entityListener;
    private SLWorldListener worldListener;
    public SettingsManager settings;
    public SquidManager sm;

    /**
* Parameterized logger
* @param level
* @param msg the message
* @param arg the arguments
*/
    public static void log(Level level, String msg, Object... arg)
    {
        logger.log(level, new StringBuilder().append("[SquidLimiter] ").append(MessageFormat.format(msg, arg)).toString());
    }

    /**
*
*/
    @Override
    public void onEnable()
    {
        settings = new SettingsManager(this);
        sm = new SquidManager(this);

        entityListener = new SLEntityListener(this);
        worldListener = new SLWorldListener(this);

        getServer().getPluginManager().registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Highest, this);
        getServer().getPluginManager().registerEvent(Event.Type.ENTITY_DEATH, entityListener, Event.Priority.Highest, this);
        getServer().getPluginManager().registerEvent(Event.Type.WORLD_LOAD, worldListener, Event.Priority.Highest, this);

        displayStatusInfo();
        sm.countLoadedWorlds();
    }

    private void displayStatusInfo()
    {
        log(Level.INFO, "version {0} loaded", getDescription().getVersion());
    }

    /**
*
*/
    @Override
    public void onDisable()
    {
    }
}
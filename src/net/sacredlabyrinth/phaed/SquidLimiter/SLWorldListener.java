package net.sacredlabyrinth.phaed.SquidLimiter;

import org.bukkit.event.world.*;

/**
* SquidLimiter world listener
*
* @author Phaed
*/
public class SLWorldListener extends WorldListener
{
    private final SquidLimiter plugin;

    /**
*
* @param plugin
*/
    public SLWorldListener(SquidLimiter plugin)
    {
        this.plugin = plugin;
    }

    /**
*
* @param event
*/
    @Override
    public void onWorldLoad(WorldLoadEvent event)
    {
        if (plugin.settings.hasLimit(event.getWorld().getName()))
        {
            plugin.sm.countWorld(event.getWorld());
        }
    }
}
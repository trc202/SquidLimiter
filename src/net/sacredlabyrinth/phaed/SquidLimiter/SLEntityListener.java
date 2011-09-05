package net.sacredlabyrinth.phaed.SquidLimiter;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Squid;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

public class SLEntityListener extends EntityListener
{
    public SquidLimiter plugin;

    public SLEntityListener(SquidLimiter plugin)
    {
        this.plugin = plugin;
    }

    @Override
    public void onCreatureSpawn(CreatureSpawnEvent event)
    {
        Entity entity = event.getEntity();
        World world = event.getLocation().getWorld();
        Chunk chunk = event.getLocation().getBlock().getChunk();

        if(!(entity instanceof Squid))
        {
        	return;
        }
        
        if (!plugin.settings.hasLimit(world.getName()))
        {
            return;
        }

        if (plugin.sm.reachedLimit(world))
        {
            event.setCancelled(true);
            return;
        }

        if (plugin.sm.chunkSquidCount(chunk) >= plugin.settings.squidsPerChunk)
        {
            event.setCancelled(true);
            return;
        }

        if (entity instanceof Squid)
        {
            plugin.sm.incrementCount(world);
        }
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event)
    {
        Entity entity = event.getEntity();
        World world = entity.getLocation().getWorld();

        if(!(entity instanceof Squid))
        {
        	return;
        }
        
        if (!plugin.settings.hasLimit(world.getName()))
        {
            return;
        }

        if (entity instanceof Squid)
        {
            plugin.sm.decrementCount(world);
        }
    }
}


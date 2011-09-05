package net.sacredlabyrinth.phaed.SquidLimiter;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Squid;

/**
*
* @author phaed
*/
public class SquidManager
{
    private HashMap<String, Integer> squidCounter = new HashMap<String, Integer>();
    private final SquidLimiter plugin;

    /**
*
* @param plugin
*/
    public SquidManager(SquidLimiter plugin)
    {
        this.plugin = plugin;
    }

    /**
*
* @param world
* @return
*/
    public int getCount(String world)
    {
        if (!squidCounter.containsKey(world))
        {
            return 0;
        }

        return squidCounter.get(world);
    }

    /**
* Whether the world has reached the squid limit
* @param world
* @return
*/
    public boolean reachedLimit(World world)
    {
        return getCount(world.getName()) > plugin.settings.getLimit(world.getName());
    }

    /**
* Increments the squid counter by one
* @param world
*/
    public void incrementCount(World world)
    {
        int count = 0;

        if (squidCounter.containsKey(world.getName()))
        {
            count = squidCounter.get(world.getName());
        }

        count++;

        squidCounter.put(world.getName(), count);
    }

    /**
* Decrements the squid counter by one
* @param world
*/
    public void decrementCount(World world)
    {
        int count = 0;

        if (squidCounter.containsKey(world.getName()))
        {
            count = squidCounter.get(world.getName());
        }

        count--;

        squidCounter.put(world.getName(), count);
    }

    /**
* Counts all loaded worlds
*/
    public void countLoadedWorlds()
    {
        List<World> worlds = plugin.getServer().getWorlds();

        for (World world : worlds)
        {
            if (plugin.settings.hasLimit(world.getName()))
            {
                countWorld(world);
            }
        }
    }

    /**
* Get the squid count for a world
* @param world
*/
    public void countWorld(World world)
    {
        int count = 0;
        int removed = 0;
        int limit = plugin.settings.getLimit(world.getName());

        List<LivingEntity> livingEntities = world.getLivingEntities();

        for (LivingEntity entity : livingEntities)
        {
            if (entity instanceof Squid)
            {
                count++;

                if (count > limit)
                {
                    entity.remove();
                    removed++;
                }
            }
        }

        squidCounter.put(world.getName(), count);

        if (count > 0)
        {
            SquidLimiter.log(Level.INFO, "({0}) initial count: {1}", world.getName(), count);
        }
        if (removed > 0)
        {
            SquidLimiter.log(Level.INFO, "({0}) removed: {1}", world.getName(), removed);
        }
    }

    /**
* Get the count of squids
* @param world
* @return
*/
    public int getSquidCount(World world)
    {
        int count = 0;

        List<LivingEntity> livingEntities = world.getLivingEntities();

        for (LivingEntity entity : livingEntities)
        {
            if (entity instanceof Squid)
            {
                count++;
            }
        }

        return count;
    }

    /**
* Returns how many other squids in the chunk
* @param chunk
* @return
*/
    public int chunkSquidCount(Chunk chunk)
    {
        Entity[] entities = chunk.getEntities();

        int count = 0;

        for (Entity entity : entities)
        {
            if (entity instanceof Squid)
            {
                count++;
            }
        }

        return count;
    }
}
package net.silthus.wgfactionflags;

import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;

import java.io.File;

public class WorldGuardFactionFlagsPlugin extends JavaPlugin implements Listener {

    private static final String FACTION_CLAIM_FLAG_NAME = "faction-claim";
    public static StateFlag FACTION_CLAIM_FLAG;

    @Getter
    private WorldGuardPlugin worldGuard;

    public WorldGuardFactionFlagsPlugin() {
    }

    public WorldGuardFactionFlagsPlugin(
            JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
    }

    @Override
    public void onLoad() {

        super.onLoad();

        this.worldGuard = getPlugin(WorldGuardPlugin.class);

        FlagRegistry registry = worldGuard.getFlagRegistry();

        try {
            // create a flag with the name "my-custom-flag", defaulting to true
            StateFlag flag = new StateFlag(FACTION_CLAIM_FLAG_NAME, true);
            registry.register(flag);
            FACTION_CLAIM_FLAG = flag; // only set our field if there was no error
            getLogger().info("Sucessfully registered WorldGuard custom flag \"" + FACTION_CLAIM_FLAG_NAME + "\".");
        } catch (FlagConflictException e) {
            // some other plugin registered a flag by the same name already.
            // you can use the existing flag, but this may cause conflicts - be sure to check type
            Flag<?> existing = registry.get(FACTION_CLAIM_FLAG_NAME);
            if (existing instanceof StateFlag) {
                FACTION_CLAIM_FLAG = (StateFlag) existing;
                getLogger().warning("Found existing WorldGuard flag with the name \"" + FACTION_CLAIM_FLAG_NAME + "\". Using this flag, but be aware that things may not work correctly.");
            } else {
                // types don't match - this is bad news! some other plugin conflicts with you
                // hopefully this never actually happens
                getLogger().severe("Failed to register custom flag \"" + FACTION_CLAIM_FLAG_NAME + "\"! A flag with the same name already exists.");
                getLogger().severe("Disabling...");
                setEnabled(false);
            }
        }
    }


    @Override
    public void onEnable() {

        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onFactionChunkClaim(EventFactionsChunksChange event) {

        LocalPlayer player = getWorldGuard().wrapPlayer(event.getMPlayer().getPlayer());

        event.getChunks().stream().map(ps -> ps.asBukkitChunk()).forEach(chunk -> {

            RegionManager regionManager = getWorldGuard().getRegionManager(chunk.getWorld());

            int bx = chunk.getX() << 4;
            int bz = chunk.getZ() << 4;
            BlockVector pt1 = new BlockVector(bx, 0, bz);
            BlockVector pt2 = new BlockVector(bx + 15, 256, bz + 15);
            ProtectedCuboidRegion region = new ProtectedCuboidRegion("ThisIsAnId", pt1, pt2);
            ApplicableRegionSet regions = regionManager.getApplicableRegions(region);

            if (!regions.testState(player, FACTION_CLAIM_FLAG)) {
                event.getMPlayer().message(ChatColor.DARK_RED + "You cannot claim chunks inside this WorldGuard region.");
                event.setCancelled(true);
            }
        });
    }
}

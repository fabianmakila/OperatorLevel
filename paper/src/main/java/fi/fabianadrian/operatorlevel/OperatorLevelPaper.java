package fi.fabianadrian.operatorlevel;

import fi.fabianadrian.operatorlevel.listener.LuckPermsListener;
import fi.fabianadrian.operatorlevel.listener.PlayerListener;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class OperatorLevelPaper extends JavaPlugin {
	private LuckPerms luckPerms;

	@Override
	public void onEnable() {
		PluginManager manager = getServer().getPluginManager();

		if (manager.isPluginEnabled("LuckPerms")) {
			RegisteredServiceProvider<LuckPerms> provider = getServer().getServicesManager().getRegistration(LuckPerms.class);
			if (provider == null) {
				return;
			}

			this.getSLF4JLogger().info("LuckPerms support enabled. Make sure to use meta instead of permissions!");

			this.luckPerms = provider.getProvider();
			new LuckPermsListener(this, this.luckPerms);
		}

		manager.registerEvents(new PlayerListener(this), this);
	}

	public void updateOpLevel(Player player) {
		if (this.luckPerms != null) {
			CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
			updateOpLevelLuckPerms(player, metaData);
		} else {
			updateOpLevelPermission(player);
		}
	}

	public void updateOpLevelLuckPerms(Player player, CachedMetaData metaData) {
		int level = metaData.getMetaValue("operatorlevel", Integer::parseInt).orElse(0);

		// Make sure that the operatorlevel meta is always 0-4
		if (level < 0 || level > 4) {
			this.getSLF4JLogger().warn(
					"Operator level must be between 0 and 4 but {} has a level of {}! Please check your LuckPerms configuration.",
					player.getName(),
					level
			);
			level = Math.clamp(level, 0, 4);
		}

		player.sendOpLevel((byte) level);
	}

	private void updateOpLevelPermission(Player player) {
		byte level = 0;
		for (int i = 4; i > 0; i--) {
			if (player.hasPermission("operatorlevel." + i)) {
				level = (byte) i;
				break;
			}
		}
		player.sendOpLevel(level);
	}
}

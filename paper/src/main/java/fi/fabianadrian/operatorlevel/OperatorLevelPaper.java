package fi.fabianadrian.operatorlevel;

import fi.fabianadrian.operatorlevel.config.ConfigManager;
import fi.fabianadrian.operatorlevel.config.OperatorLevelConfig;
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
	private ConfigManager<OperatorLevelConfig> configManager;

	@Override
	public void onEnable() {
		this.configManager = ConfigManager.create(
				getDataPath(),
				"config.yml",
				OperatorLevelConfig.class,
				getSLF4JLogger()
		);
		this.configManager.load();

		PluginManager manager = getServer().getPluginManager();

		if (manager.isPluginEnabled("LuckPerms")) {
			RegisteredServiceProvider<LuckPerms> provider = getServer().getServicesManager().getRegistration(LuckPerms.class);
			if (provider == null) {
				return;
			}

			this.luckPerms = provider.getProvider();
			new LuckPermsListener(this, this.luckPerms);
		}

		manager.registerEvents(new PlayerListener(this), this);
	}

	public void updateOpLevel(Player player) {
		if (this.configManager.config().luckPermsMeta()) {
			CachedMetaData metaData = this.luckPerms.getPlayerAdapter(Player.class).getMetaData(player);
			updateLevelLuckPermsMeta(player, metaData);
		} else {
			updateLevelPermission(player);
		}
	}

	public void updateOpLevel(Player player, CachedMetaData metaData) {
		if (this.configManager.config().luckPermsMeta()) {
			updateLevelLuckPermsMeta(player, metaData);
		} else {
			updateLevelPermission(player);
		}
	}

	private void updateLevelLuckPermsMeta(Player player, CachedMetaData metaData) {
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

	private void updateLevelPermission(Player player) {
		byte level = 0;
		for (int i = 4; i > 0; i--) {
			if (player.hasPermission("operatorlevel." + i)) {
				level = (byte) i;
				break;
			}
		}
		player.sendOpLevel(level);
	}

	public void reload() {
		this.configManager.load();
	}
}

package fi.fabianadrian.operatorlevel.paper;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.platform.Platform;
import fi.fabianadrian.operatorlevel.paper.command.OperatorLevelCommand;
import fi.fabianadrian.operatorlevel.paper.listener.PlayerListener;
import fi.fabianadrian.operatorlevel.paper.luckperms.PaperLuckPermsManager;
import net.luckperms.api.LuckPerms;
import org.bstats.bukkit.Metrics;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;

public final class OperatorLevelPaper extends JavaPlugin implements Platform<Player> {
	private OperatorLevel operatorLevel;
	private PaperLuckPermsManager luckPermsManager;

	@Override
	public void onEnable() {
		PluginManager manager = getServer().getPluginManager();
		if (manager.isPluginEnabled("LuckPerms")) {
			RegisteredServiceProvider<LuckPerms> provider = this.getServer().getServicesManager().getRegistration(LuckPerms.class);
			if (provider != null) {
				this.luckPermsManager = new PaperLuckPermsManager(this, provider.getProvider());
			}
		}

		this.operatorLevel = new OperatorLevel(this);
		this.operatorLevel.reload();

		OperatorLevelCommand operatorLevelCommand = new OperatorLevelCommand(this);
		operatorLevelCommand.register();

		manager.registerEvents(new PlayerListener(this), this);

		new Metrics(this, 23464);
	}

	@Override
	public void updateOpLevel(Player player) {
		if (player.isOp()) {
			return;
		}

		if (!this.operatorLevel.config().luckPermsMeta()) {
			updateLevelPermission(player);
			return;
		}

		if (this.luckPermsManager == null) {
			logger().warn("luckPermsMeta config option was enabled, but LuckPerms isn't enabled. Falling back to permission-based check.");
			updateLevelPermission(player);
			return;
		}

		int level = this.luckPermsManager.level(player);
		player.sendOpLevel((byte) level);
	}

	@Override
	public void updateOpLevel(UUID uuid, int level) {
		Player player = this.getServer().getPlayer(uuid);
		if (player == null || player.isOp()) {
			return;
		}

		if (!this.operatorLevel.config().luckPermsMeta()) {
			updateLevelPermission(player);
			return;
		}

		player.sendOpLevel((byte) level);
	}

	private void updateLevelPermission(Player player) {
		byte level = 0;
		for (int i = 4; i > 0; i--) {
			if (player.hasPermission("operatorlevel.level." + i)) {
				level = (byte) i;
				break;
			}
		}
		player.sendOpLevel(level);
	}

	@Override
	public Logger logger() {
		return this.getSLF4JLogger();
	}

	@Override
	public Path dataPath() {
		//TODO Switch to this.getDataPath() when minimum version becomes 1.21
		return this.getDataFolder().toPath();
	}

	public void reload() {
		this.operatorLevel.reload();
		this.getServer().getOnlinePlayers().forEach(this::updateOpLevel);
	}
}

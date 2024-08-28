package fi.fabianadrian.operatorlevel.listener;

import fi.fabianadrian.operatorlevel.OperatorLevel;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.event.EventBus;
import net.luckperms.api.event.user.UserDataRecalculateEvent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class LuckPermsListener {
	private final OperatorLevel plugin;

	public LuckPermsListener(OperatorLevel plugin) {
		this.plugin = plugin;

		RegisteredServiceProvider<LuckPerms> provider = plugin.getServer().getServicesManager().getRegistration(LuckPerms.class);
		if (provider == null) {
			return;
		}

		LuckPerms luckPerms = provider.getProvider();
		EventBus eventBus = luckPerms.getEventBus();
		eventBus.subscribe(plugin, UserDataRecalculateEvent.class, this::onUserDataRecalculate);
	}

	private void onUserDataRecalculate(UserDataRecalculateEvent event) {
		Player player = this.plugin.getServer().getPlayer(event.getUser().getUniqueId());
		this.plugin.updateOpLevel(player);
	}
}

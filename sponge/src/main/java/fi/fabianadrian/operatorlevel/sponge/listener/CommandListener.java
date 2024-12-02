package fi.fabianadrian.operatorlevel.sponge.listener;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.sponge.command.SpongeOperatorLevelCommand;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.plugin.PluginContainer;

public final class CommandListener {
	private final PluginContainer container;
	private final SpongeOperatorLevelCommand command;

	public CommandListener(PluginContainer container, OperatorLevel<Player> operatorLevel) {
		this.container = container;
		this.command = new SpongeOperatorLevelCommand(operatorLevel);
	}

	@Listener
	public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event){
		event.register(this.container, this.command.command(), "operatorlevel");
	}
}

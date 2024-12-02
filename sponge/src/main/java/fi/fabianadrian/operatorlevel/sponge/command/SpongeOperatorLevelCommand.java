package fi.fabianadrian.operatorlevel.sponge.command;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.command.OperatorLevelCommand;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

public final class SpongeOperatorLevelCommand extends OperatorLevelCommand<Player> {
	public SpongeOperatorLevelCommand(OperatorLevel<Player> operatorLevel) {
		super(operatorLevel);
	}

	public Command.Parameterized command() {
		var reloadCommand = Command.builder()
				.shortDescription(Component.text("Reloads the plugin."))
				.executor(this::executeReload)
				.build();

		return Command.builder()
				.permission(PERMISSION_RELOAD)
				.addChild(reloadCommand, "reload")
				.build();

	}

	private CommandResult executeReload(CommandContext ctx) {
		super.operatorLevel.load();
		ctx.sendMessage(COMPONENT_RELOAD_COMPLETE);
		return CommandResult.success();
	}
}

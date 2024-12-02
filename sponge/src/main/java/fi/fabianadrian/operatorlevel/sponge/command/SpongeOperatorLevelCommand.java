package fi.fabianadrian.operatorlevel.sponge.command;

import fi.fabianadrian.operatorlevel.common.command.OperatorLevelCommand;
import fi.fabianadrian.operatorlevel.sponge.OperatorLevelSponge;
import net.kyori.adventure.text.Component;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.CommandContext;

public final class SpongeOperatorLevelCommand extends OperatorLevelCommand {
	private final OperatorLevelSponge plugin;

	public SpongeOperatorLevelCommand(OperatorLevelSponge plugin) {
		this.plugin = plugin;
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
		this.plugin.reload();
		ctx.sendMessage(COMPONENT_RELOAD_COMPLETE);
		return CommandResult.success();
	}
}

package fi.fabianadrian.operatorlevel.paper.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import fi.fabianadrian.operatorlevel.common.command.OperatorLevelCommand;
import fi.fabianadrian.operatorlevel.paper.OperatorLevelPaper;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static io.papermc.paper.command.brigadier.Commands.literal;

@SuppressWarnings("UnstableApiUsage")
public final class PaperOperatorLevelCommand extends OperatorLevelCommand<Player> {
	private final LifecycleEventManager<Plugin> manager;

	public PaperOperatorLevelCommand(OperatorLevelPaper plugin, OperatorLevel<Player> operatorLevel) {
		super(operatorLevel);
		this.manager = plugin.getLifecycleManager();
	}

	public void register() {
		LiteralArgumentBuilder<CommandSourceStack> rootBuilder = literal("operatorlevel")
				.requires(stack -> stack.getSender().hasPermission(PERMISSION_RELOAD));

		LiteralCommandNode<CommandSourceStack> reloadNode = rootBuilder.then(literal("reload")
				.executes(this::executeReload)
		).build();

		this.manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();
			commands.register(reloadNode);
		});
	}

	private int executeReload(CommandContext<CommandSourceStack> ctx) {
		this.operatorLevel.load();
		ctx.getSource().getSender().sendMessage(COMPONENT_RELOAD_COMPLETE);
		return Command.SINGLE_SUCCESS;
	}
}

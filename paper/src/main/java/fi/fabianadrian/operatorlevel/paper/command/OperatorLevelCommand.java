package fi.fabianadrian.operatorlevel.paper.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import fi.fabianadrian.operatorlevel.paper.OperatorLevelPaper;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.plugin.Plugin;

import static io.papermc.paper.command.brigadier.Commands.literal;

@SuppressWarnings("UnstableApiUsage")
public final class OperatorLevelCommand {
	private final OperatorLevelPaper plugin;
	private final LifecycleEventManager<Plugin> manager;
	private final Component reloadMessage;

	public OperatorLevelCommand(OperatorLevelPaper plugin) {
		this.plugin = plugin;
		this.manager = plugin.getLifecycleManager();

		this.reloadMessage = MiniMessage.miniMessage().deserialize(
				"<#111827>[<#ef4444>OperatorLevel</#ef4444>]</#111827> <lang:operatorlevel.command.reload>"
		);
	}

	public void register() {
		LiteralArgumentBuilder<CommandSourceStack> rootBuilder = literal("operatorlevel")
				.requires(stack -> stack.getSender().hasPermission("operatorlevel.command.reload"));

		LiteralCommandNode<CommandSourceStack> reloadNode = rootBuilder.then(literal("reload")
				.executes(this::executeReload)
		).build();

		this.manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
			final Commands commands = event.registrar();
			commands.register(reloadNode);
		});
	}

	private int executeReload(CommandContext<CommandSourceStack> ctx) {
		this.plugin.reload();
		ctx.getSource().getSender().sendMessage(this.reloadMessage);
		return Command.SINGLE_SUCCESS;
	}
}

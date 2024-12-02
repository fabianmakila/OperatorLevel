package fi.fabianadrian.operatorlevel.common.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public abstract class OperatorLevelCommand {
	protected static final Component COMPONENT_RELOAD_COMPLETE = MiniMessage.miniMessage().deserialize(
			"<#111827>[<#ef4444>OperatorLevel</#ef4444>]</#111827> <lang:operatorlevel.command.reload>"
	);
	protected static final String PERMISSION_RELOAD = "operatorlevel.command.reload";
}

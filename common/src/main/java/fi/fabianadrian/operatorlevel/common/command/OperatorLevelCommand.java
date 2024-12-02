package fi.fabianadrian.operatorlevel.common.command;

import fi.fabianadrian.operatorlevel.common.OperatorLevel;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public abstract class OperatorLevelCommand<P> {
	protected static final Component COMPONENT_RELOAD_COMPLETE = MiniMessage.miniMessage().deserialize(
			"<#111827>[<#ef4444>OperatorLevel</#ef4444>]</#111827> <lang:operatorlevel.command.reload>"
	);
	protected static final String PERMISSION_RELOAD = "operatorlevel.command.reload";
	protected final OperatorLevel<P> operatorLevel;

	public OperatorLevelCommand(OperatorLevel<P> operatorLevel) {
		this.operatorLevel = operatorLevel;
	}
}

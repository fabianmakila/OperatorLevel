package fi.fabianadrian.operatorlevel.paper.command;

import fi.fabianadrian.operatorlevel.common.command.OperatorLevelCommand;
import fi.fabianadrian.operatorlevel.paper.OperatorLevelPaper;
import net.strokkur.commands.Command;
import net.strokkur.commands.Executes;
import net.strokkur.commands.permission.Permission;
import org.bukkit.command.CommandSender;

@Command("operatorlevel")
public final class PaperOperatorLevelCommand extends OperatorLevelCommand {
	private final OperatorLevelPaper plugin;

	public PaperOperatorLevelCommand(OperatorLevelPaper plugin) {
		this.plugin = plugin;
	}

	@Executes("reload")
	@Permission(PERMISSION_RELOAD)
	void onReload(CommandSender sender) {
		this.plugin.reload();
		sender.sendMessage(COMPONENT_RELOAD_COMPLETE);
	}
}

package net.md_5.bungee.command;

import java.io.File;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.NewServerPing;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.protocol.packet.PacketStatistics;

public class CommandReloadNames extends PlayerCommand {

	public CommandReloadNames() {
		super("reloadnames", "bungeecord.command.reloadnames");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length > 0 && args[0].equalsIgnoreCase("debug")) {
			((ProxiedPlayer) sender).unsafe().sendPacket(new PacketStatistics(1337, 1));// open inventory
			sender.sendMessage(ChatColor.RED + "Debug sent!");
			return;
		}
		File f = new File("names.txt");
		if (!f.exists()) {
			sender.sendMessage(ChatColor.RED + "The 'names.txt' file does not exist!");
			return;
		}
		NewServerPing.loadPlayerListFromFile(f);
	}
}

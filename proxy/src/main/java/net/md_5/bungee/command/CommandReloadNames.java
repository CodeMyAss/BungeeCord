package net.md_5.bungee.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.NewServerPing;

public class CommandReloadNames extends PlayerCommand {

	public CommandReloadNames() {
		super("reloadnames", "bungeecord.command.reloadnames");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		File f = new File("names.txt");
		if (!f.exists()) {
			sender.sendMessage(ChatColor.RED + "The 'names.txt' file does not exist!");
			return;
		}
		ArrayList<String> lines = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));

			String line;
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			NewServerPing.setPlayerList(lines);
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

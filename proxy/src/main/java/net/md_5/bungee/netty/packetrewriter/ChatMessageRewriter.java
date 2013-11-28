package net.md_5.bungee.netty.packetrewriter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.buffer.ByteBuf;
import net.md_5.bungee.netty.Var;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatMessageRewriter extends PacketRewriter

{
	private final JsonParser parser = new JsonParser();
	private static Map<String, String> colors = new ConcurrentHashMap<String, String>();
	JsonParser jsonParser = new JsonParser();

	static {
		colors.put("0", "black");
		colors.put("1", "dark_blue");
		colors.put("2", "dark_green");
		colors.put("3", "dark_aqua");
		colors.put("4", "dark_red");
		colors.put("5", "dark_purple");
		colors.put("6", "gold");
		colors.put("7", "gray");
		colors.put("8", "dark_gray");
		colors.put("9", "blue");
		colors.put("a", "green");
		colors.put("b", "aqua");
		colors.put("c", "red");
		colors.put("d", "light_purple");
		colors.put("e", "yellow");
		colors.put("f", "white");
		colors.put("k", "obfuscated");
		colors.put("l", "bold");
		colors.put("m", "strikethrough");
		colors.put("n", "underline");
		colors.put("o", "italic");
		colors.put("r", "white");
	}

	@Override
	public void rewriteClientToServer(ByteBuf in, ByteBuf out) {
		String message = Var.readString(in, true);
		Var.writeString(message, out, false);
	}

	@Override
	public void rewriteServerToClient(ByteBuf in, ByteBuf out) {
		String message = Var.readString(in, false);
		Var.writeString(fixMessage(message), out, true);
	}

	public String fixMessage(String msg) {
		String json = msg;
		JsonObject data = null;
		data = (JsonObject) parser.parse(json);
		String message = data.get("text").getAsString();
		String[] color = { "white", "false", "false", "false", "false", "false" };
		String jsonMessage = "{'text':'','extra':[";
		jsonMessage = jsonMessage + "{'color':'" + color[0] + "','bold':'" + color[1] + "','italic':'" + color[2] + "','underlined':'" + color[3] + "','strikethrough':'" + color[4] + "','obfuscated':'" + color[5] + "','text':'";
		message = message + " ";
		char[] chars = message.toCharArray();
		Boolean checkColor = Boolean.valueOf(false);
		String fillWord = "";
		for (char c : chars) {
			if ((checkColor.booleanValue()) && (!String.valueOf(c).equalsIgnoreCase("§")) && (!isValidColor(String.valueOf(c)))) {
				checkColor = Boolean.valueOf(false);
			} else if ((!checkColor.booleanValue()) && (String.valueOf(c).equalsIgnoreCase("§"))) {
				checkColor = Boolean.valueOf(true);
			} else if (String.valueOf(c).equalsIgnoreCase(" ")) {
				if (checkSteamUrl(fillWord)) {
					String oldUnderLined = color[3];
					color[3] = "true";
					jsonMessage = jsonMessage + "'},";
					jsonMessage = jsonMessage + "{'color':'" + color[0] + "','bold':'" + color[1] + "','italic':'" + color[2] + "','underlined':'" + color[3] + "','strikethrough':'" + color[4] + "','obfuscated':'" + color[5] + "','text':'";
					jsonMessage = jsonMessage + fillWord;
					jsonMessage = jsonMessage + "','clickEvent':{'action':'open_url','value':'" + fillWord + "'}";
					jsonMessage = jsonMessage + ",'hoverEvent':{'action':'show_text','value':'Click to open Steam URL: " + fillWord + "'}";
					color[3] = oldUnderLined;
					jsonMessage = jsonMessage + "},";
					jsonMessage = jsonMessage + "{'color':'" + color[0] + "','bold':'" + color[1] + "','italic':'" + color[2] + "','underlined':'" + color[3] + "','strikethrough':'" + color[4] + "','obfuscated':'" + color[5] + "','text':' ";
				} else if (checkUrl(fillWord)) {
					String oldUnderLined = color[3];
					jsonMessage = jsonMessage + "'},";
					jsonMessage = jsonMessage + "{'color':'" + color[0] + "','bold':'" + color[1] + "','italic':'" + color[2] + "','underlined':'" + color[3] + "','strikethrough':'" + color[4] + "','obfuscated':'" + color[5] + "','text':'";
					jsonMessage = jsonMessage + fillWord;
					jsonMessage = jsonMessage + "','clickEvent':{'action':'open_url','value':'" + fillWord + "'}";
					jsonMessage = jsonMessage + ",'hoverEvent':{'action':'show_text','value':'Click to go to: " + fillWord + "'}";
					color[3] = oldUnderLined;
					jsonMessage = jsonMessage + "},";
					jsonMessage = jsonMessage + "{'color':'" + color[0] + "','bold':'" + color[1] + "','italic':'" + color[2] + "','underlined':'" + color[3] + "','strikethrough':'" + color[4] + "','obfuscated':'" + color[5] + "','text':' ";
				} else {
					jsonMessage = jsonMessage + fillWord + " ";
				}
				fillWord = "";
			} else if ((checkColor.booleanValue()) && (isValidColor(String.valueOf(c)))) {
				color = setColor(color, String.valueOf(c));
				checkColor = Boolean.valueOf(false);
				jsonMessage = jsonMessage + fillWord + "'},";
				jsonMessage = jsonMessage + "{'color':'" + color[0] + "','bold':'" + color[1] + "','italic':'" + color[2] + "','underlined':'" + color[3] + "','strikethrough':'" + color[4] + "','obfuscated':'" + color[5] + "','text':'";
				fillWord = "";
			} else if (String.valueOf(c).equalsIgnoreCase("\\")) {
				fillWord = fillWord + "\\\\";
			} else if (String.valueOf(c).equalsIgnoreCase("'")) {
				fillWord = fillWord + "\\'";
			} else {
				fillWord = fillWord + String.valueOf(c);
			}
		}
		jsonMessage = jsonMessage + "'}]}";
		JsonParser jsonParser = new JsonParser();
		JsonObject jo = (JsonObject) jsonParser.parse(jsonMessage);
		return jo.toString();
	}

	protected boolean checkSteamUrl(String checkWord) {
		try {
			if (checkWord.startsWith("steam://"))
				return true;
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	protected boolean checkUrl(String checkWord) {
		return checkWord.matches("^(?:(https?)://)?([-\\w_\\.]{2,}\\.[a-z]{2,4})(/\\S*)?$");
	}

	private String[] setColor(String[] color, String valueOf) {
		if (valueOf.equalsIgnoreCase("r")) {
			return new String[] { "white", "false", "false", "false", "false", "false" };
		}
		if (valueOf.equalsIgnoreCase("l"))
			color[1] = "true";
		else if (valueOf.equalsIgnoreCase("o"))
			color[2] = "true";
		else if (valueOf.equalsIgnoreCase("n"))
			color[3] = "true";
		else if (valueOf.equalsIgnoreCase("m"))
			color[4] = "true";
		else if (valueOf.equalsIgnoreCase("k"))
			color[5] = "true";
		else if (valueOf.equalsIgnoreCase("0"))
			color[0] = (colors.get("0"));
		else if (valueOf.equalsIgnoreCase("1"))
			color[0] = (colors.get("1"));
		else if (valueOf.equalsIgnoreCase("2"))
			color[0] = (colors.get("2"));
		else if (valueOf.equalsIgnoreCase("3"))
			color[0] = (colors.get("3"));
		else if (valueOf.equalsIgnoreCase("4"))
			color[0] = (colors.get("4"));
		else if (valueOf.equalsIgnoreCase("5"))
			color[0] = (colors.get("5"));
		else if (valueOf.equalsIgnoreCase("6"))
			color[0] = (colors.get("6"));
		else if (valueOf.equalsIgnoreCase("7"))
			color[0] = (colors.get("7"));
		else if (valueOf.equalsIgnoreCase("8"))
			color[0] = (colors.get("8"));
		else if (valueOf.equalsIgnoreCase("9"))
			color[0] = (colors.get("9"));
		else if (valueOf.equalsIgnoreCase("a"))
			color[0] = (colors.get("a"));
		else if (valueOf.equalsIgnoreCase("b"))
			color[0] = (colors.get("b"));
		else if (valueOf.equalsIgnoreCase("c"))
			color[0] = (colors.get("c"));
		else if (valueOf.equalsIgnoreCase("d"))
			color[0] = (colors.get("d"));
		else if (valueOf.equalsIgnoreCase("e"))
			color[0] = (colors.get("e"));
		else if (valueOf.equalsIgnoreCase("f")) {
			return new String[] { "white", "false", "false", "false", "false", "false" };
		}

		return color;
	}

	private boolean isValidColor(String valueOf) {
		return colors.containsKey(valueOf);
	}
}

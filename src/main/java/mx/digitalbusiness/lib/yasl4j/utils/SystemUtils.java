package mx.digitalbusiness.lib.yasl4j.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class SystemUtils {
	private SystemUtils() {}

	public static String getOS(String os) {
		os = os != null? os.toLowerCase() : "";
		if (os.indexOf("nux") >= 0 || os.indexOf("aix") > 0)
			return "GNU/Linux";
		else if (os.indexOf("nix") >= 0)
			return "Unix";
		else if (os.indexOf("win") >= 0)
			return "Microsoft Windows";
		else if (os.indexOf("mac") >= 0)
			return "Mac OS";
		else
			return "Unknown";
	}
	
	public static String getOS() {
		return getOS(System.getProperty("os.name"));
	}

	public static String[] readResourceFile(String fileName) throws IOException {
		List<String> lines = new ArrayList<>();
		try (BufferedReader fileIn = new BufferedReader(
				new InputStreamReader(SystemUtils.class.getResourceAsStream("/" + fileName), "UTF-8"))) {
			String line = null;
			while ((line = fileIn.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines.toArray(new String[lines.size()]);
	}

	public static String[] readFile(String filePath) throws IOException {
		List<String> lines = new ArrayList<>();
		try (BufferedReader fileIn = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = fileIn.readLine()) != null) {
				lines.add(line);
			}
		}
		return lines.toArray(new String[lines.size()]);
	}

	public static void writeFile(String filePath, boolean append, String... lines) throws IOException {
		try (PrintWriter fileOut = new PrintWriter(new FileWriter(filePath, append))) {
			for (String line : lines)
				fileOut.println(line);
		}
	}

	public static void writeFile(String filePath, String... lines) throws IOException {
		writeFile(filePath, false, lines);
	}

	public static void appendFile(String filePath, String... lines) throws IOException {
		writeFile(filePath, true, lines);
	}

	public static String getCurrentDateTime() {
		return DATE_TIME_FORMATTER.format(new Date(System.currentTimeMillis()));
	}

	public static void abort() {
		System.err.println("PROGRAM WILL EXIT!");
		System.exit(1);
	}
	
	public static final SimpleDateFormat DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

}

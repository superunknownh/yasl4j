package mx.digitalbusiness.lib.yasl4j;

enum LogLevel {
	DEBUG(1, "DEBUG"),
	INFO(2, "INFO "),
	WARN(3, "WARN "),
	ERROR(4, "ERROR"),
	FATAL(5, "FATAL");

	private LogLevel(int level, String description) {
		this.level = level;
		this.description = description;
	}

	public int getLevel() {
		return level;
	}

	public String getDescription() {
		return description;
	}

	private final int level;
	private final String description;
}

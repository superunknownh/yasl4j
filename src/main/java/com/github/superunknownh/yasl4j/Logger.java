package com.github.superunknownh.yasl4j;

import static com.github.superunknownh.yasl4j.utils.Constants.*;
import static com.github.superunknownh.yasl4j.utils.StringUtils.*;
import static com.github.superunknownh.yasl4j.utils.SystemUtils.*;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class allow to log messages in different levels for both console and
 * file. <br />
 * <br />
 * In order to work with this logger, please set the next environment variables:
 * <br />
 * <ul>
 * <li>{@code YASL4J_LOG_LEVEL} The log level. Options available are:
 * <ul>
 * <li>{@code DEBUG} (default)
 * <li>{@code INFO}
 * <li>{@code WARN}
 * <li>{@code ERROR}
 * <li>{@code FATAL}
 * </ul>
 * </li>
 * <li>{@code YASL4J_LOG_ROTATE_DAILY} Set to {@code true} if you want to rotate
 * daily in order to create the file as: {@code $LOG_DIR/$module.yyyy-MM-dd.log}
 * or set to {@code false} for create a single file as:
 * {@code $LOG_DIR/$module.log}.</li>
 * <li>{@code YASL4J_LOG_DIR} The file system absolute path where the log files
 * will be generated. If not set, the log to file will not be performed.</li>
 * </ul>
 *
 * @author Hugo
 * @since 1.0
 *
 * @param <T> The module as a class type.
 */
public class Logger<T> {

	/**
	 * Creates a new object using a raw module string.
	 * 
	 * @param module A raw module string.
	 */
	public Logger(String module) {
		this.module = module;
		handleEnvironmentVariables();
		handleLogFile();
	}

	/**
	 * Creates a new object using a class type.
	 * 
	 * @param classType The module as a class type.
	 */
	public Logger(Class<T> classType) {
		this(classType.getSimpleName());
	}

	/**
	 * Get the configured module.
	 * 
	 * @return The configured module.
	 */
	public String getModule() {
		return module;
	}

	/**
	 * Get the log level.
	 * 
	 * @return The log level.
	 */
	public LogLevel getLogLevel() {
		return logLevel;
	}

	/**
	 * Set the log level.
	 * 
	 * @param logLevel The log level to set.
	 */
	public void setLogLevel(LogLevel logLevel) {
		this.logLevel = logLevel;
	}

	/**
	 * Check if the log rotates daily (for file creation).
	 * 
	 * @return {@code true} if the log rotates daily, {@code false} otherwise.
	 */
	public boolean isRotateDaily() {
		return rotateDaily;
	}

	/**
	 * Set the log daily rotation.
	 * 
	 * @param rotateDaily {@code true} for rotate daily, {@code false} for create a
	 *                    single file.
	 */
	public void setRotateDaily(boolean rotateDaily) {
		this.rotateDaily = rotateDaily;
	}

	/**
	 * Configures the state of the object according the environment variables.
	 */
	private void handleEnvironmentVariables() {
		this.logLevel = LogLevel.valueOf(getString(System.getenv(LOG_LEVEL_ENV), "DEBUG"));
		this.rotateDaily = getBoolean(getString(System.getenv(LOG_ROTATE_DAILY_ENV), "false"));
		String logDirectoryString = System.getenv(LOG_DIR_ENV);
		this.logDirectory = logDirectoryString != null ? new File(logDirectoryString) : null;
	}

	/*
	 * Creates the log file in order to print to it.
	 */
	private void handleLogFile() {
		if (logDirectory != null) {
			if (!logDirectory.exists() && !logDirectory.mkdirs()) {
				log(LogLevel.ERROR, Logger.class.getSimpleName(), "handleLogFile",
						String.format("Please create the directory '%s'", logDirectory.getAbsolutePath()));
			}
			String fileName = String.format("%s%s.log", this.module,
					(rotateDaily ? "." + DATE_FORMATTER.format(new Date()) : ""));
			logFile = new File(logDirectory.getAbsolutePath() + FILE_SEPARATOR + fileName);
			if (!logFile.exists()) {
				try {
					logFile.createNewFile();
				} catch (IOException ex) {
					log(LogLevel.ERROR, Logger.class.getSimpleName(), "handleLogFile",
							String.format("%s: %s", ex.getClass().getName(), ex.getMessage()));
				}
			}
		}
	}

	/**
	 * Logs a message.
	 * 
	 * @param level     The log level.
	 * @param module    The module.
	 * @param submodule The submodule.
	 * @param message   The message.
	 */
	private void log(LogLevel level, String module, String submodule, Object message) {
		message = message != null ? message : "";
		if (level.getLevel() >= this.logLevel.getLevel()) {
			String logMessage = String.format("%s [%s] [%s] %s - %s", getCurrentDateTime(), level.getDescription(), module,
					submodule, message.toString());
			System.out.println(logMessage);
			try {
				if (logFile != null) {
					appendFile(logFile.getAbsolutePath(), logMessage);
				}
			} catch (IOException ex) {
				System.err.println(String.format("%s [%s] [%s] %s - %s: %s", getCurrentDateTime(), LogLevel.ERROR,
						Logger.class.getSimpleName(), "log", ex.getClass().getName(), ex.getMessage()));
			}
		}
	}

	/**
	 * Logs a debug message.
	 * 
	 * @param submodule The submodule (like method) where the log is raised.
	 * @param message   The message to log.
	 */
	public void debug(String submodule, Object message) {
		log(LogLevel.DEBUG, this.module, submodule, message);
	}

	/**
	 * Logs an informative message.
	 * 
	 * @param submodule The submodule (like method) where the log is raised.
	 * @param message   The message to log.
	 */
	public void info(String submodule, Object message) {
		log(LogLevel.INFO, this.module, submodule, message);
	}

	/**
	 * Logs a warning message.
	 * 
	 * @param submodule The submodule (like method) where the log is raised.
	 * @param message   The message to log.
	 */
	public void warn(String submodule, Object message) {
		log(LogLevel.WARN, this.module, submodule, message);
	}

	/**
	 * Logs an error message.
	 * 
	 * @param submodule The submodule (like method) where the log is raised.
	 * @param message   The message to log.
	 */
	public void error(String submodule, Object message) {
		log(LogLevel.ERROR, this.module, submodule, message);
	}

	/**
	 * Logs a fatal message. Also ends the application.
	 * 
	 * @param submodule The submodule (like method) where the log is raised.
	 * @param message   The message to log.
	 */
	public void fatal(String submodule, Object message) {
		log(LogLevel.FATAL, this.module, submodule, message);
		abort();
	}

	private final String module;
	private LogLevel logLevel;
	private boolean rotateDaily;
	private File logDirectory;
	private File logFile;

	private static final String LOG_LEVEL_ENV = "YASL4J_LOG_LEVEL";
	private static final String LOG_ROTATE_DAILY_ENV = "YASL4J_LOG_ROTATE_DAILY";
	private static final String LOG_DIR_ENV = "YASL4J_LOG_DIR";

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

}

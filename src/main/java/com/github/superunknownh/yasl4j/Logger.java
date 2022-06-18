package com.github.superunknownh.yasl4j;

import static com.github.superunknownh.superutils.FileUtils.appendFile;
import static com.github.superunknownh.superutils.StringUtils.getString;
import static com.github.superunknownh.superutils.SystemUtils.getCurrentDateTime;

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
  public Logger(Class<T> classType) { this(classType.getSimpleName()); }

  /**
   * Get the configured module.
   *
   * @return The configured module.
   */
  public String getModule() { return module; }

  /**
   * Get the log level.
   *
   * @return The log level.
   */
  public LogLevel getLogLevel() { return logLevel; }

  /**
   * Set the log level.
   *
   * @param logLevel The log level to set.
   */
  public void setLogLevel(LogLevel logLevel) { this.logLevel = logLevel; }

  /**
   * Check if the log rotates daily (for file creation).
   *
   * @return {@code true} if the log rotates daily, {@code false} otherwise.
   */
  public boolean isRotateDaily() { return rotateDaily; }

  /**
   * Set the log daily rotation.
   *
   * @param rotateDaily {@code true} for rotate daily, {@code false} for create
   *     a
   *                    single file.
   */
  public void setRotateDaily(boolean rotateDaily) {
    this.rotateDaily = rotateDaily;
  }

  /**
   * Configures the state of the object according the environment variables.
   */
  private void handleEnvironmentVariables() {
    this.logLevel =
        LogLevel.valueOf(getString(System.getenv(LOG_LEVEL_ENV), "DEBUG"));
    this.rotateDaily = Boolean.valueOf(
        getString(System.getenv(LOG_ROTATE_DAILY_ENV), "false"));
    String logDirectoryString = System.getenv(LOG_DIR_ENV);
    this.logDirectory =
        logDirectoryString != null ? new File(logDirectoryString) : null;
  }

  /*
   * Creates the log file in order to print to it.
   */
  private void handleLogFile() {
    if (logDirectory != null) {
      if (!logDirectory.exists() && !logDirectory.mkdirs()) {
        log(LogLevel.ERROR, Logger.class.getSimpleName(), "handleLogFile",
            String.format("Please create the directory '%s'",
                          logDirectory.getAbsolutePath()));
      }
      String fileName = String.format(
          "%s%s.log", this.module,
          (rotateDaily ? "." + DATE_FORMATTER.format(new Date()) : ""));
      logFile =
          new File(logDirectory.getAbsolutePath() + FILE_SEPARATOR + fileName);
      if (!logFile.exists()) {
        try {
          logFile.createNewFile();
        } catch (IOException ex) {
          log(LogLevel.ERROR, Logger.class.getSimpleName(), "handleLogFile",
              String.format("%s: %s", ex.getClass().getName(),
                            ex.getMessage()));
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
  private void log(LogLevel level, String module, String submodule,
                   Object message) {
    message = message != null ? message : "";
    if (level.getLevel() >= this.logLevel.getLevel()) {
      String logMessage = String.format(
          "%s [%s] [%s] %s - %s", getCurrentDateTime(), level.getDescription(),
          module, submodule, message.toString());
      System.out.println(logMessage);
      try {
        if (logFile != null) {
          appendFile(logFile.getAbsolutePath(), logMessage);
        }
      } catch (IOException ex) {
        System.err.println(
            String.format("%s [%s] [%s] %s - %s: %s", getCurrentDateTime(),
                          LogLevel.ERROR, Logger.class.getSimpleName(), "log",
                          ex.getClass().getName(), ex.getMessage()));
      }
    }
  }

  /**
   * Logs a debug message.
   *
   * @param format Format string or message.
   * @param args   Arguments referenced by the format specifiers in the format
   *         string (usage of %).
   */
  public void debug(String format, Object... args) {
    log(LogLevel.DEBUG, this.module,
        new Throwable().getStackTrace()[1].getMethodName(),
        String.format(format, args));
  }

  /**
   * Logs an informative message.
   *
   * @param format Format string or message.
   * @param args   Arguments referenced by the format specifiers in the format
   *         string (usage of %).
   */
  public void info(String format, Object... args) {
    log(LogLevel.DEBUG, this.module,
        new Throwable().getStackTrace()[1].getMethodName(),
        String.format(format, args));
  }

  /**
   * Logs a warning message.
   *
   * @param format Format string or message.
   * @param args   Arguments referenced by the format specifiers in the format
   *         string (usage of %).
   */
  public void warn(String format, Object... args) {
    log(LogLevel.DEBUG, this.module,
        new Throwable().getStackTrace()[1].getMethodName(),
        String.format(format, args));
  }

  /**
   * Logs an error message.
   *
   * @param format Format string or message.
   * @param args   Arguments referenced by the format specifiers in the format
   *         string (usage of %).
   */
  public void error(String format, Object... args) {
    log(LogLevel.DEBUG, this.module,
        new Throwable().getStackTrace()[1].getMethodName(),
        String.format(format, args));
  }

  /**
   * Logs a fatal message. Also ends the application.
   *
   * @param format Format string or message.
   * @param args   Arguments referenced by the format specifiers in the format
   *         string (usage of %).
   */
  public void fatal(String format, Object... args) {
    log(LogLevel.DEBUG, this.module,
        new Throwable().getStackTrace()[1].getMethodName(),
        String.format(format, args));
    System.exit(1);
  }

  /**
   * Logs an exception with the format: {@code ex.getClass().getName():
   * ex.getMessage()} in the {@link LogLevel.ERROR} log level. Also, if {@link
   * LogLevel.DEBUG} level is set, will call {@code ex.printStackTrace();}
   *
   * @param submodule The submodule that called the log.
   * @param ex The Exception to log.
   */
  public void exception(String submodule, Exception ex) {
    log(LogLevel.ERROR, this.module,
        new Throwable().getStackTrace()[1].getMethodName(),
        String.format("%s - %s: %s", submodule, ex.getClass().getName(), ex.getMessage()));
    if (logLevel == LogLevel.DEBUG) {
      ex.printStackTrace();
    }
  }

  /**
   * Logs an exception with the format: {@code ex.getClass().getName():
   * ex.getMessage()} in the {@link LogLevel.ERROR} log level. Also, if {@link
   * LogLevel.DEBUG} level is set, will call {@code ex.printStackTrace();}
   *
   * @param ex The Exception to log.
   */
  public void exception(Exception ex) {
    log(LogLevel.ERROR, this.module,
        new Throwable().getStackTrace()[1].getMethodName(),
        String.format("%s: %s", ex.getClass().getName(), ex.getMessage()));
    if (logLevel == LogLevel.DEBUG) {
      ex.printStackTrace();
    }
  }

  private final String module;
  private LogLevel logLevel;
  private boolean rotateDaily;
  private File logDirectory;
  private File logFile;

  private static final String LOG_LEVEL_ENV = "YASL4J_LOG_LEVEL";
  private static final String LOG_ROTATE_DAILY_ENV = "YASL4J_LOG_ROTATE_DAILY";
  private static final String LOG_DIR_ENV = "YASL4J_LOG_DIR";

  private static final String FILE_SEPARATOR =
      System.getProperty("file.separator");

  private static final SimpleDateFormat DATE_FORMATTER =
      new SimpleDateFormat("yyyy-MM-dd");
}

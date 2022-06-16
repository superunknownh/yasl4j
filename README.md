# yasl4j

Yet Another Simple Logger for Java (YASL4J) is a Java library for log messages in different levels for both console and file.

## Maven

You can include this library adding this as a dependency in your `pom.xml`:

```xml
<dependency>
    <groupId>com.github.superunknownh.yasl4j</groupId>
    <artifactId>yasl4j</artifactId>
    <version>2.2</version>
</dependency>
```

## Setup

In order to work with this logger, please set the next environment variables: 

  - `YASL4J_LOG_LEVEL` The log level. Options available are:
    - `DEBUG` (default) 
    - `INFO`
    - `WARN` 
    - `ERROR`
    - `FATAL`
  - `YASL4J_LOG_ROTATE_DAILY` Set to `true` if you want to rotate daily in order to create the file as: `$LOG_DIR/$module.yyyy-MM-dd.log` or set to `false` for create a single file as: `$LOG_DIR/$module.log.`
  - `YASL4J_LOG_DIR` The file system absolute path where the log files will be generated. <b>If not set, log to file will not be performed.</b>

## Example

Once you have configured the environment variables, you can use the library like the next example:

```java
// create a logger by passing the class name
Logger<?> logger = new Logger<>(LoggerTest.class);

// you can choose which call is better for you:

// 2022-06-16 18:06:07.508 [DEBUG] [LoggerTest] codeBlock - This is a DEBUG message.
logger.debug("codeBlock", "This is a DEBUG message.");
// 2022-06-16 18:06:07.508 [DEBUG] [LoggerTest] main - This is a DEBUG message.
logger.debug("This is a DEBUG message");
// 2022-06-16 18:06:07.508 [DEBUG] [LoggerTest] main - This is a DEBUG message with 1 arg!
logger.debug("This is a DEBUG message with %d arg!", 1);

// each call is available for debug, info, warn, error and fatal levels
logger.info("This is an INFORMATIVE message.");
logger.warn("This is a WARNING message.");
logger.error("This is an ERROR message.");
logger.fatal("This is a FATAL message.");

// a new way to log exceptions
try {
  ...
} catch (Exception ex) {
  logger.exception(ex);
}
```
# yasl4j

Yet Another Simple Logger for Java (YASL4J) is a Java library for log messages in different levels for both console and file.

## Maven

You can include this library adding this as a dependency in your `pom.xml`:

```xml
<dependency>
    <groupId>mx.digitalbusiness.lib</groupId>
    <artifactId>yasl4j</artifactId>
    <version>1.0</version>
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
  - `YASL4J_LOG_DIR` The file system absolute path where the log files will be generated. If not set, the log to file will not be performed.

## Example

Once you have configured the environment variables, you can use the library like the next example:

```java
Logger<LoggerTest> logger = new Logger<>(LoggerTest.class);

logger.debug("testDebug", "This is a DEBUG message.");
logger.info("testInfo", "This is an INFORMATIVE message.");
logger.warn("testWarn", "This is a WARNING message.");
logger.error("testError", "This is an ERROR message.");
logger.fatal("testFatal", "This is a FATAL message.");
```
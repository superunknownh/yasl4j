package com.github.superunknownh.yasl4j;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class LoggerTest {

  @Test
  public void
  testLogger_GivenRawModule_MustCreateTheLoggerObjectWithThatModule() {
    String expResult = "LoggerTest";
    Logger<LoggerTest> logger = new Logger<>("LoggerTest");
    String result = logger.getModule();
    assertEquals(expResult, result);
  }

  @Test
  public void
  testLogger_GivenClassModule_MustCreateTheLoggerObjectWithThatModule() {
    String expResult = "LoggerTest";
    Logger<LoggerTest> logger = new Logger<>(LoggerTest.class);
    String result = logger.getModule();
    assertEquals(expResult, result);
  }

  @Test
  public void testSetGetLogLevel() {
    LogLevel expResult = LogLevel.ERROR;
    Logger<LoggerTest> logger = new Logger<>(LoggerTest.class);
    logger.setLogLevel(LogLevel.ERROR);
    LogLevel result = logger.getLogLevel();
    assertEquals(expResult, result);
  }

  @Test
  public void testSetGetRotateDaily() {
    boolean expResult = true;
    Logger<LoggerTest> logger = new Logger<>(LoggerTest.class);
    logger.setRotateDaily(true);
    boolean result = logger.isRotateDaily();
    assertEquals(expResult, result);
  }

  @Test
  public void testDebug() {
    Logger<LoggerTest> logger = new Logger<>(LoggerTest.class);
    logger.debug("testDebug", "This is a DEBUG message.");
  }

  @Test
  public void testInfo() {
    Logger<LoggerTest> logger = new Logger<>(LoggerTest.class);
    logger.info("testInfo", "This is an INFORMATIVE message.");
  }

  @Test
  public void testWarn() {
    Logger<LoggerTest> logger = new Logger<>(LoggerTest.class);
    logger.warn("testWarn", "This is a WARNING message.");
  }

  @Test
  public void testError() {
    Logger<LoggerTest> logger = new Logger<>(LoggerTest.class);
    logger.error("testError", "This is an ERROR message.");
  }

  @Test
  @Ignore
  public void testFatal() {
    Logger<LoggerTest> logger = new Logger<>(LoggerTest.class);
    logger.fatal("testFatal", "This is a FATAL message.");
  }
}

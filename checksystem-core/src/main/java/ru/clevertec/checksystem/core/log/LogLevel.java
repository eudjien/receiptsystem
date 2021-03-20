package ru.clevertec.checksystem.core.log;

import org.apache.logging.log4j.Level;

public enum LogLevel {

    INFO(Level.INFO),
    DEBUG(Level.DEBUG),
    ERROR(Level.ERROR),
    TRACE(Level.TRACE),
    OFF(Level.OFF);

    private final Level level;

    LogLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    @Override
    public String toString() {
        return level.toString();
    }
}

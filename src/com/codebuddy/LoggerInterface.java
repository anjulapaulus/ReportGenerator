package com.codebuddy;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerInterface {
    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    FileHandler fh;



    public void log(String level, String message){
        try {
            fh = new FileHandler("cms.log");
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            LOGGER.log(Level.parse(level),message);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}

package com.mh.galgame.util;


/**
 * 用于处理调试信息，因为在不同平台上的System.out.println()不一定适用
 */
public class Logger {

    public interface Handler{
        void handle(String msg, boolean ln);
    }


    private static Handler handler;

    public static void log(Object msg) {
        if (handler!=null) {
            handler.handle(String.valueOf(msg), false);
        }
    }

    public static void logln(Object msg) {
        if (handler!=null) {
            handler.handle(String.valueOf(msg), true);
        }
    }

    public static Handler getHandler() {
        return handler;
    }

    public static void setHandler(Handler handler) {
        Logger.handler = handler;
    }
}

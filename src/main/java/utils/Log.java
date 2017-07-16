package utils;

/**
 * Created by nicks189 on 7/11/17.
 */
public class Log {
    private static Log instance = new Log();

    public static Log getSingleton() {
        return instance;
    }

    private Log() {
    }

    public boolean add(String message) {
        return false;
    }
}

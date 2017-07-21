package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by nicks189 on 7/11/17.
 */
public class Log {
    private static Log instance = new Log();

    public static Log getSingleton() {
        return instance;
    }

    private File filePath = null;

    private Log() {}

    public void addFile(File filePath) {
        this.filePath = filePath;
    }

    public boolean add(String message) {
        if(filePath == null) {
            System.out.println("No log file selected; use log.addFile(file)");
            return false;
        }

        if (!filePath.exists()) {
            try {
                filePath.createNewFile();
            } catch(IOException e) {
                System.out.println("Can't create log file.");
                return false;
            }
        }

        if(!filePath.canWrite()) {
            System.out.println("Can't write to log file.");
            return false;
        }

        try {

            FileWriter writer = new FileWriter(filePath, true);
            Timestamp timeStamp = new Timestamp(new Date().getTime());
            writer.write(timeStamp + ": " + message + "\n");
            writer.close();

        } catch(IOException e) {

            System.out.println("Something went wrong when writing to log file.");
            return false;

        }

        return true;
    }
}

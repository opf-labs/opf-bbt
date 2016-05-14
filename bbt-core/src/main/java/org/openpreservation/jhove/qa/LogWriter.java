package org.openpreservation.jhove.qa;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;

/**
 * 
 * @author  <a href="mailto:carl@openpreservation.org">Carl Wilson</a>.
 *
 */
public class LogWriter {

    private List<String> logs;

    public LogWriter() {
        this.logs = new ArrayList<String>();
    }

    public void submitLog(final String log) {
        this.logs.add(log);
    }

    public void flush(final String key) {
        // TODO pick up file and write.
        StringBuffer buffer = new StringBuffer();
        newLine(buffer, "###");
        newLine(buffer, "Comparing Base with " + key + " at " + new Date());
        newLine(buffer, "");

        for (String log : this.logs) {
            newLine(buffer, log);
        }

        System.out.println(buffer.toString());
        write(buffer);
        this.logs.clear();
    }

    private static void write(final StringBuffer buffer) {
        File logFile = new File(System.getProperty("java.io.tmpdir")
                + File.separator + "/bbt-logs/log.txt");
        System.out.println("Writing logs to: " + logFile.getAbsolutePath());
        try {
            FileUtils.writeStringToFile(logFile, buffer.toString(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void newLine(final StringBuffer buffer, final String line) {
        buffer.append(line + "\n");
    }

}

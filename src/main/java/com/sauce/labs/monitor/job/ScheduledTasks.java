package com.sauce.labs.monitor.job;

import com.sauce.labs.monitor.util.CommonConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {

    @Value("${server.name}")
    String serverName;

    @Value("${connection.timeout}")
    int timeout;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstant.DATE_PATTERN);

    /**
     * Scheduler to check health of a server
     * @throws IOException
     */
    @Scheduled(fixedRate = 5000)
    public void healthCheck() throws IOException {
        StringBuffer healthStatus = new StringBuffer();
        try {
            URL server = new URL(serverName);
            HttpURLConnection connection = (HttpURLConnection) server.openConnection();
            connection.setRequestMethod(CommonConstant.GET);
            connection.setConnectTimeout(timeout);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                healthStatus = new StringBuffer(CommonConstant.STATUS_OK);
            } else {
                healthStatus = new StringBuffer(CommonConstant.STATUS_NOT_OK);
            }

        } catch (ConnectException e) {
            healthStatus = new StringBuffer(CommonConstant.STATUS_ERROR);
            log.error(CommonConstant.CONNECTION_ERROR);
        }
        /**
         * Store the status in temp file.
         */
        try {
            File monitorLog = File.createTempFile(CommonConstant.FILE_NAME, CommonConstant.EXTENSION);
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(monitorLog, true));
            log.info(monitorLog.toString());
            fileWriter.append(dateFormat.format(new Date())+" "+healthStatus);
            fileWriter.close();
        }catch (Exception e){
            log.error(CommonConstant.FILE_WRITE_EXCEPTION);
        }
    }
}

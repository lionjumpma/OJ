package com.sprint.sandbox.utils;

import cn.hutool.core.date.StopWatch;
import com.sprint.sandbox.model.ExecuteMessage;
import org.apache.tomcat.util.buf.Utf8Encoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class ProcessUtils {
    public static ExecuteMessage runProcess(Process process,String opName){
        ExecuteMessage message=new ExecuteMessage();

        try {
            StopWatch watch=new StopWatch();
            watch.start();
            int exitVal = process.waitFor();
            message.setExitValue(exitVal);
            if (exitVal == 0) {
                System.out.println(opName+"success");
                //String stream = getInputStream(process);
                //message.setMessage(stream);
                StringBuilder builder = new StringBuilder();
                String line;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8));
                while ((line = bufferedReader.readLine()) != null) {
                    builder.append(line);
                    //System.out.println(line);
                }
                message.setMessage(builder.toString());


            } else {
                System.out.println(opName+"fail err code" + exitVal);
                getInputStream(process);
                String line;
                BufferedReader errbufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder sbuilder=new StringBuilder();

                while ((line = errbufferedReader.readLine()) != null) {
                    System.out.println(line);
                    sbuilder.append(line);
                }
                message.setErrorMessage(sbuilder.toString());


            }
            watch.stop();
            message.setTime(watch.getLastTaskTimeMillis());;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return message;

    }
    private static String getInputStream(Process process) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
            //System.out.println(line);
        }
        System.out.println(builder.toString());
        return builder.toString();
    }
}

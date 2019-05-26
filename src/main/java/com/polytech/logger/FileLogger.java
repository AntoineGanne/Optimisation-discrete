package com.polytech.logger;

import com.sun.istack.internal.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
    static final int LENGTH_TO_SEND=100;
    @NotNull
    private String filePath;
    private StringBuilder stringBuilder=new StringBuilder();
    private BufferedWriter bufferedWriter;

    public FileLogger(String filePath) {
        this.filePath = filePath;
        try{
            FileWriter writer = new FileWriter(filePath);
            bufferedWriter=new BufferedWriter(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String content){
        stringBuilder.append(content);
        try {
            bufferedWriter.write(content);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        if(stringBuilder.length()>LENGTH_TO_SEND){
//            writeToFile();
//            stringBuilder=new StringBuilder();
//        }

    }

    private void writeToFile(){
        try (FileWriter writer = new FileWriter(filePath)) {
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(stringBuilder.toString());
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}

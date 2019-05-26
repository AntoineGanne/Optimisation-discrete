package com.polytech.logger;

import com.sun.istack.internal.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
    static final int LENGTH_TO_SEND=100;
    @NotNull
    private String filePath;
    private BufferedWriter bufferedWriter;
    private StringBuilder stringBuilder=new StringBuilder();

    public FileLogger(String filePath) {
        this.filePath = filePath;
    }

    public void write(String content){
        stringBuilder.append(content);
        if(stringBuilder.length()>LENGTH_TO_SEND){
            writeToFile();
        }
        stringBuilder=new StringBuilder();
    }

    private void writeToFile(){
        try (FileWriter writer = new FileWriter(filePath)) {
            bufferedWriter = new BufferedWriter(writer);
            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}

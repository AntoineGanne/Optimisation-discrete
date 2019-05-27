package com.polytech.logger;

import com.sun.istack.internal.NotNull;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
    static final int LENGTH_TO_SEND=100;
    static final String CRLF="\r\n";
    static final String TAB="\t";
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
    }
    public void writeLine(String content){
        write(content+CRLF);
    }

}

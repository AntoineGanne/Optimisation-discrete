package com.polytech.logger;

import com.sun.istack.internal.NotNull;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileLogger {
    static final int LENGTH_TO_SEND=100;
    static final String CRLF="\r\n";
    static final String TAB="\t";
    @NotNull
    private String filePath;
    private StringBuilder stringBuilder=new StringBuilder();
    private Writer fstream;

    public FileLogger(String filePath) {
        this.filePath = filePath;
        try{
            fstream=new OutputStreamWriter(new FileOutputStream(filePath),StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String content){
        stringBuilder.append(content);
        try {
            fstream.write(content);
            fstream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void writeLine(String content){
        write(content+CRLF);
    }

}

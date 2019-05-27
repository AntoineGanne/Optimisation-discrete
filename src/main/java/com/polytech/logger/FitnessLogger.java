package com.polytech.logger;

import com.polytech.util.ConfigurationUtil;

public class FitnessLogger extends FileLogger {

    public FitnessLogger(String filePath) {
        super(filePath);
    }

    public void writeEntete(){
        super.write("step"+TAB+"configuration"+TAB+"fitness"+CRLF);
    }

    public void writeLineFitness(int step,int[] config,long fitness){
        String line=step+TAB+ConfigurationUtil.ConfigToString(config)+TAB+fitness;
        super.writeLine(line);
    }


}

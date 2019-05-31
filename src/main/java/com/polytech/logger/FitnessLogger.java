package com.polytech.logger;

import com.polytech.landscape.ElementaryOperation;
import com.polytech.util.ConfigurationUtil;

import java.util.ArrayList;

public class FitnessLogger extends FileLogger {

    public FitnessLogger(String filePath) {
        super(filePath);
    }

    public void writeEntete(){
        super.write("step"+TAB+"configuration"+TAB+"fitness"+CRLF);
    }

    public void writeEnteteTabou(){
        super.write("step"+TAB+"configuration"+TAB+"fitness"+TAB+"tabous"+CRLF);
    }

    public void writeLineFitness(int step,int[] config,long fitness){
        String line=step+TAB+ConfigurationUtil.ConfigToString(config)+TAB+fitness;
        super.writeLine(line);
    }

    public void writeLineFitnessTabou(int step, int[] config, long fitness, ArrayList<ElementaryOperation> tabouList){
        StringBuilder sb=new StringBuilder();
        sb.append(step).append(TAB).append(ConfigurationUtil.ConfigToString(config)).append(TAB).append(fitness);
        sb.append(TAB);
        for(int i=0;i<tabouList.size();i++){
            sb.append(tabouList.get(i).toString());
            if(i!=tabouList.size()-1){
                sb.append(",");
            }
        }
        super.writeLine(sb.toString());
    }


}

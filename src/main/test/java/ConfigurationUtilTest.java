import com.polytech.model.ProblemModel;
import com.polytech.util.ConfigurationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationUtilTest {



    @Test
    @DisplayName("test de calcul de fitness sur le fichier test")
    void getFitnessTest() {
        ProblemModel pm;

        try {
            pm=new ProblemModel("./data/test.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        int[] configuration;
        assertEquals (pm.getN(),3);
        configuration= new int[]{1, 2, 3};
        double fitness = ConfigurationUtil.getFitness(configuration, pm.getWeight(), pm.getDist());
        assertEquals(fitness,14);
    }

    @Test
    @DisplayName("test de calcul de fitness sur le fichier tai12")
    void getFitnessTaillard() {
        ProblemModel pm;

        try {
            pm=new ProblemModel("./data/tai12.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        int[] configuration;
        assertEquals (pm.getN(),12);
        configuration= new int[]{8,1,6,2,11,10,3,5,9,7,12,4};
        double fitness = ConfigurationUtil.getFitness(configuration, pm.getWeight(), pm.getDist());
        assertEquals(fitness,224416);
    }

    @Test
    @DisplayName("test de génération de config aléatoire")
    void randomConfigurationTest(){
        int n=20;
        int[] rdmConfig = ConfigurationUtil.randomConfiguration(n);
        for(int machineID=1;machineID<=n;++machineID){
            boolean presenceInConfig=false;
            for(int i=0;i<n;i++){
                if(rdmConfig[i]==machineID){
                    presenceInConfig=true;
                    break;
                }
            }
            if(!presenceInConfig){
                fail("Au moins une machine non présente dans la configuration aléatoire");
            }
        }
    }
}
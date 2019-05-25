import com.polytech.landscape.BasicPermutation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PermutationTest {

    @Test
    @DisplayName("expected neighbors from basicPermutation of {1,2,3,4}")
    void applyOperation() {
        BasicPermutation bp=new BasicPermutation();
        int[] configuration=new int[]{1,2,3,4};
        List<int[]> neighbors = bp.getNeighbors(configuration);

        for(int[] neighbor:neighbors){
            int differencesCount=0;
            for(int i=0;i<configuration.length;++i){
                if(neighbor[i]!=configuration[i]){
                    differencesCount++;
                }
            }
            if(differencesCount!=2){
                fail("neighbor doesn't have exactly 2 differences");
            }
        }
    }
}
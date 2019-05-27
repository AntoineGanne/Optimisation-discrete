import com.polytech.landscape.BasicPermutation;
import com.polytech.landscape.ElementaryOperation;
import com.polytech.landscape.Permutation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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

    @Test
    @DisplayName("tests equals")
    void testEquals(){
        ElementaryOperation e1=new Permutation(0,1);
        ElementaryOperation e1prime=new Permutation(0,1);
        assert(e1.equals(e1prime));
    }

    @Test
    @DisplayName("tests hascode")
    void testHashcode(){
        ElementaryOperation e1=new Permutation(0,1);
        ElementaryOperation e1prime=new Permutation(0,1);
        assertEquals(e1.hashCode(),e1prime.hashCode());

        ElementaryOperation e2=new Permutation(2,3);
        ElementaryOperation e3=new Permutation(4,5);

        ArrayList<ElementaryOperation> list= new ArrayList<>();
        list.add(e1);
        list.add(e2);
        list.add(e3);

        list.removeIf(e1prime::equals);

        assertEquals(list.size(),2);

        list.add(e1);


    }
}
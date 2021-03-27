package tp_AliMeddeb_2GinfoA;

import java.util.Random;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static tp_AliMeddeb_2GinfoA.Trieuse.triInsertion;

class TrieuseTest {

    public static void main(String args[]){
        Vector<EntierComparable> vec = new Vector(20);
        for (int i=0;i<20;i++) vec.add(new EntierComparable(new Random().nextInt(100)));
        System.out.print("Vecteur avant tri:\n"+vec);
        vec=triInsertion(vec);
        System.out.print("\nVecteur apres tri:\n"+vec);




    }


}
package tp_AliMeddeb_2GinfoA;

import java.lang.Comparable;

public class EntierComparable implements Comparablee {
    int entier;

    public int getEntier() {
        return entier;
    }

    public void setEntier(int entier) {
        this.entier = entier;
    }

    public EntierComparable(int entier) {
        this.entier = entier;
    }

    @Override
    public boolean plusPetitQue(Object o) {
        return (this.entier < ( ((EntierComparable)o).entier )) ;
    }

    @Override
    public String toString() {
        return "" +entier;
    }


}

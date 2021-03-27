package tp_AliMeddeb_2GinfoA;

import java.util.Vector;

public class Trieuse {
    public static Vector triInsertion(Vector v) {
        Vector res = new Vector(); //le vecteur résultat
        int i;   //indice pour le parcours de v
        int p;  //indice pour la position d’insertion dans res
        for (i=0; i < v.size(); i++){
            // on range dans elti le ième élément de v
            Comparablee elti = (Comparablee) v.elementAt(i);
            // recherche de la position p de elti dans le vecteur resultat
            p = 0;
            while(p < res.size() && !(elti.plusPetitQue((Comparablee) res.elementAt(p))))
                p++;
// insertion de elti à la position p dans le vecteur resultat
            res.insertElementAt(elti ,p);
        }
        return res;
    }

}

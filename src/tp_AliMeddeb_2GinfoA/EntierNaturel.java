package tp_AliMeddeb_2GinfoA;

import tp_AliMeddeb_2GinfoA.exception.ErrConst;
import tp_AliMeddeb_2GinfoA.exception.ErrDifference;
import tp_AliMeddeb_2GinfoA.exception.ErrProduit;
import tp_AliMeddeb_2GinfoA.exception.ErrSomme;

public class EntierNaturel {
    private int N;

    public EntierNaturel(int n) throws ErrConst {
        if (n<0) throw new ErrConst();
        N = n;
    }

    public int somme(int x) throws ErrSomme {
        long s=(long)N+x;
        if (s > Integer.MAX_VALUE) throw new ErrSomme();
        return (int) s;
    }

    public int produit(int x) throws ErrProduit {
        long s=(long)N*x;
        if (s > Integer.MAX_VALUE) throw new ErrProduit();
        return (int) s;
    }

    public int difference(int x) throws ErrDifference {
        int d = N - x;
        if (d<0) throw new ErrDifference();
        return d;
    }

    public static void main(String args[]){
        try {
            EntierNaturel n = new EntierNaturel(1000);
            System.out.println(n.somme(50));
            System.out.println(n.produit(50));
            System.out.println(n.difference(1020));
        }
        catch (ErrConst e){
            e.printStackTrace();
        }

        catch (ErrSomme e) {
            e.printStackTrace();
        }
        catch(ErrProduit e){
            e.printStackTrace();
        }
        catch(ErrDifference e){
            e.printStackTrace();
        }

    }
}

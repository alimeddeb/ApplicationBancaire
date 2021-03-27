package tp_AliMeddeb_2GinfoA;

import org.junit.jupiter.api.Test;
import tp_AliMeddeb_2GinfoA.exception.ErrNegatif;
import tp_AliMeddeb_2GinfoA.exception.ErrPretLimiteDepasse;
import tp_AliMeddeb_2GinfoA.exception.ErrValeurNegative;

import java.math.BigDecimal;
import java.time.LocalDate;

class Compte_vipTest {

    @Test
    void getPret() throws ErrPretLimiteDepasse, ErrValeurNegative, ErrNegatif {
        Agence agence = new Agence(1,"agence tunis");
        Client_VIP vip = new Client_VIP("meddeb", "ali","321", agence, "google", 125,  new BigDecimal(25000));
        Compte_vip compte_vip = new Compte_vip("12345", new BigDecimal(0), vip, LocalDate.now());
        compte_vip.pret(new BigDecimal(200));

    }
}
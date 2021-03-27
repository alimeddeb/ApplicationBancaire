package tp_AliMeddeb_2GinfoA;

import org.junit.jupiter.api.Test;
import tp_AliMeddeb_2GinfoA.exception.ErrClientIntrouvable;
import tp_AliMeddeb_2GinfoA.exception.ErrCompteIntrouvable;
import tp_AliMeddeb_2GinfoA.exception.ErrNegatif;
import tp_AliMeddeb_2GinfoA.exception.ErrTypeCompte;

import java.math.BigDecimal;
import java.sql.SQLException;

class ConnectorTest {
    Agence agence = new Agence(1,"ageence tunis");
    Client_salarie client = new Client_salarie("meddeb", "ali","13461329",agence, "ingenieur", 1500);
    Client_VIP vip = new Client_VIP("meddeb", "ali","321", agence, "google", 125,  new BigDecimal(25000));

    @Test
    void check_client() throws ErrClientIntrouvable, SQLException, ClassNotFoundException {
        Connector con = new Connector();
        con.check_client("123");
        con.check_client("321");
        con.check_client("");
        con.check_client("1231");
    }


    @Test
    void check_account() throws SQLException, ErrNegatif, ClassNotFoundException, ErrTypeCompte, ErrCompteIntrouvable {
        Connector con = new Connector();
        System.out.println(con.check_account(client, "compte"));
        System.out.println(con.check_account(client, "compte_epargne"));
        System.out.println(con.check_account(vip, "compte_vip"));
    }
}
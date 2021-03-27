package tp_AliMeddeb_2GinfoA;

import tp_AliMeddeb_2GinfoA.exception.*;

import java.math.BigDecimal;
import java.sql.*;

public class Connector {
    Connection con;

    public Connector() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        String db_path = "jdbc:mysql://localhost:3306/appBancaire";
        try {
            this.con = DriverManager.getConnection(db_path, "root", "");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public ResultSet executeQuery(String query) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String db_path = "jdbc:mysql://localhost:3306/appBancaire";
        this.con = DriverManager.getConnection(db_path, "root", "");
        Statement stmt = null;
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;


    }


    public Admin check_login(String login, String password) throws SQLException, ErrLoginIncorrect, ClassNotFoundException {
        String query = "Select * from admin where login_admin='{1}' and mdp_admin='{2}'";
        query = query.replace("{1}", login);
        query = query.replace("{2}", password);
        ResultSet rs;
        Admin admin = null;
        rs = executeQuery(query);
        if (rs.next() == false) {
            throw new ErrLoginIncorrect();
        } else {
            admin = new Admin(rs.getString("nom_admin"), rs.getString("prenom_admin"));
        }

        return admin;
    }

    public Client check_client(String CIN) throws SQLException, ErrClientIntrouvable, ClassNotFoundException {
        Agence agence = new Agence(1, "ageence tunis");
        String query = "Select * from client where CIN='{1}'";
        query = query.replace("{1}", CIN);
        ResultSet rs;
        rs = executeQuery(query);
        if (rs.next() == false) {
            System.out.println("Client Introuvable");
            throw new ErrClientIntrouvable();
        } else if (rs.getBoolean("client_vip")) {
            System.out.println("Client VIP");
            return new Client_VIP(rs.getString("nom"), rs.getString("prenom"), CIN, agence, rs.getString("NOM_ENTREPRISE"), rs.getInt("NOMBRE_EMPLOYES"), rs.getBigDecimal("CHIFFRE_AFFAIRE"));
        } else {
            System.out.println("Client Normal");
            return new Client_salarie(rs.getString("nom"), rs.getString("prenom"), CIN, agence, rs.getString("PROFESSION"), rs.getFloat("salaire"));
        }
    }

    public CompteAbstrait check_account(Client client, String type) throws ErrCompteIntrouvable, SQLException, ClassNotFoundException, ErrNegatif, ErrTypeCompte {

        String query = "Select {1}.* from client join {1} on client.id_client = {1}.id_client where CIN='{2}'";
        query = query.replace("{1}", type);
        query = query.replace("{2}", client.getCIN());
        ResultSet rs;
        rs = executeQuery(query);
        if (rs.next() == false) {
            throw new ErrCompteIntrouvable();
        } else {
            if (type == "compte") {
                return new Compte(rs.getString("RIB"), rs.getBigDecimal("solde"), client, rs.getDate("Date_creation").toLocalDate());
            } else if (type == "compte_epargne") {
                Compte_epargne compte_epargne = new Compte_epargne(rs.getString("RIB"), rs.getBigDecimal("solde"), client, rs.getDate("Date_creation").toLocalDate(), rs.getBigDecimal("MONTANT_DERNIER_VERSEMENT"), rs.getDate("DATE_DERNIER_VERSEMENT").toLocalDate());
                compte_epargne.setActif(rs.getBoolean("actif"));
                return compte_epargne;
            } else if ((type == "compte_vip") && (client instanceof Client_VIP)) {
                Compte_vip compte_vip = new Compte_vip(rs.getString("RIB"), rs.getBigDecimal("solde"), (Client_VIP) client, rs.getDate("Date_creation").toLocalDate());
                compte_vip.setPret(rs.getBigDecimal("pret"));
                return compte_vip;    }
            else {
                throw new ErrTypeCompte();

            }
        }
    }

    public void bd_modifier_solde_compte(CompteAbstrait compte) throws SQLException, ErrTypeCompte {
        String type;
        String query = "UPDATE {1} SET Solde = ? WHERE RIB = ?;";
        PreparedStatement pstmt;
        if (compte instanceof Compte) {
            query = query.replace("{1}", "compte");
            pstmt = con.prepareStatement(query);
            pstmt.setBigDecimal(1, compte.getSolde());
            pstmt.setString(2, compte.getRib());
            pstmt.executeUpdate();
        }
        else if (compte instanceof Compte_epargne) {
            System.out.println("UPDATING compte epargne");
            query = "UPDATE compte_epargne SET Solde = ?, DATE_DERNIER_VERSEMENT = ?, MONTANT_DERNIER_VERSEMENT = ?, actif = ? WHERE RIB = ?;";
            pstmt = con.prepareStatement(query);
            pstmt.setBigDecimal(1, compte.getSolde());
            pstmt.setDate(2, (Date.valueOf(((Compte_epargne) compte).getDate_dernier_versement())));
            pstmt.setBigDecimal(3, ((Compte_epargne) compte).getMontant_dernier_versement());
            pstmt.setBoolean(4, ((Compte_epargne) compte).isActif());
            pstmt.setString(5, ((Compte_epargne) compte).getRib());
            pstmt.executeUpdate();
        }
        else if (compte instanceof Compte_vip) {
            query = "UPDATE {1} SET Solde = ?, pret = ? WHERE RIB = ?;";
            query = query.replace("{1}", "compte_vip");
            pstmt = con.prepareStatement(query);
            pstmt.setBigDecimal(1, compte.getSolde());
            pstmt.setBigDecimal(2, ((Compte_vip) compte).getPret());
            pstmt.setString(3, compte.getRib());
            pstmt.executeUpdate();
        }
        else throw new ErrTypeCompte();

    }

    public Client get_client_from_Rib(String RIB) throws ErrClientIntrouvable, SQLException, ClassNotFoundException {
        String query = "\n" +
                "Select CIN from client\n" +
                "Where id_client =\n" +
                "    (SELECT ID_CLIENT from\n" +
                "        (SELECT  `ID_CLIENT`, RIB FROM compte \n" +
                "        UNION\n" +
                "        (SELECT `ID_CLIENT`, RIB FROM compte_epargne )\n" +
                "        UNION\n" +
                "        (SELECT `ID_CLIENT`, RIB FROM compte_vip )) as all_accounts\n" +
                "    WHERE RIB = '{1}')";
        query = query.replace("{1}", RIB);
        ResultSet rs;
        rs = executeQuery(query);
        if (rs.next() == false) {
            System.out.println("Client Introuvable");
            throw new ErrClientIntrouvable();
        }
        String cin = rs.getString("CIN");
        return check_client(cin);

    }

    public int bd_getClientId(String CIN) throws ErrClientIntrouvable, SQLException, ClassNotFoundException {
        String query = "Select ID_CLIENT from client WHere CIN='{1}'";
        query=query.replace("{1}",CIN);
        ResultSet rs;
        rs = executeQuery(query);
        if (rs.next() == false) {
            System.out.println("Client Introuvable");
            throw new ErrClientIntrouvable();
        }
        return rs.getInt("id_client");
    }

    public void bd_ajout_compte(Client client,CompteAbstrait compte, String type) throws SQLException, ErrClientIntrouvable, ClassNotFoundException {
        String query = "";
        int id_client = bd_getClientId(client.getCIN());
        if (compte instanceof Compte){
            query = "INSERT INTO `compte`(ID_CLIENT, RIB, SOLDE, DATE_CREATION) VALUES (?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id_client);
            pstmt.setString(2, compte.getRib());
            pstmt.setBigDecimal(3, compte.getSolde());
            pstmt.setDate(4,  java.sql.Date.valueOf(compte.getDate_creation()));
            pstmt.executeUpdate();
        }
        else if (compte instanceof Compte_epargne){
            query = "INSERT INTO `compte_epargne`(`ID_CLIENT`, `RIB`, `SOLDE`, `DATE_CREATION`, `DATE_DERNIER_VERSEMENT`, `MONTANT_DERNIER_VERSEMENT`, `ACTIF`) VALUES (?,?,?,?,?,?,?);";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id_client);
            pstmt.setString(2, compte.getRib());
            pstmt.setBigDecimal(3, compte.getSolde());
            pstmt.setDate(4,  java.sql.Date.valueOf(compte.getDate_creation()));
            pstmt.setDate(5, java.sql.Date.valueOf(((Compte_epargne) compte).getDate_dernier_versement()));
            pstmt.setBigDecimal(6,(((Compte_epargne) compte).getMontant_dernier_versement()) );
            pstmt.setBoolean(7, ((Compte_epargne) compte).isActif());
            pstmt.executeUpdate();
        }
        else if (compte instanceof Compte_vip){
            query = "INSERT INTO `compte_vip`(ID_CLIENT, RIB, SOLDE,PRET, DATE_CREATION) VALUES (?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id_client);
            pstmt.setString(2, compte.getRib());
            pstmt.setBigDecimal(3, compte.getSolde());
            pstmt.setBigDecimal(4, ((Compte_vip) compte).getPret());
            pstmt.setDate(5,  java.sql.Date.valueOf(compte.getDate_creation()));
            pstmt.executeUpdate();
        }
    }
    public void bd_supprimer_compte(CompteAbstrait compte, String type) throws SQLException {
        if (type == "compte"){
            String query = "DELETE FROM compte WHERE RIB=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, compte.getRib());
            pstmt.executeUpdate();
        }
        else if (type == "compte_epargne"){
            String query = "DELETE FROM compte_epargne WHERE RIB=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, compte.getRib());
            pstmt.executeUpdate();
        }
        else if (type == "compte_vip"){
            String query = "DELETE FROM compte_vip WHERE RIB=?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, compte.getRib());
            pstmt.executeUpdate();
        }
        else throw new SQLException("Account type Error");
    }
}

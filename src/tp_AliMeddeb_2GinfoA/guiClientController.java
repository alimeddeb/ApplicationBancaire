package tp_AliMeddeb_2GinfoA;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tp_AliMeddeb_2GinfoA.exception.ErrClientIntrouvable;
import tp_AliMeddeb_2GinfoA.exception.ErrCompteIntrouvable;
import tp_AliMeddeb_2GinfoA.exception.ErrNegatif;
import tp_AliMeddeb_2GinfoA.exception.ErrTypeCompte;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class guiClientController {
    // fields
    public Text nomClient;
    public Text cinClient;
    public Text prenomClient;
    public Text professionClient;
    public Text salaireClient;
    public AnchorPane details_salarie;
    public Text entrepriseClient;
    public Text nbEmployesClient;
    public Text chiffreAffaireClient;
    public AnchorPane logo_VIP;
    public AnchorPane details_vip;
    public static CompteAbstrait compte;
    public static Compte_epargne compte_epargne;
    //boutons
    public Button bouton_retour;
    public Button bouton_consulter_compte_epargne;
    public Button bouton_consulter_compte_vip;
    public Button bouton_consulter_compte_normal;
    public Button bouton_ajout_compte_normal;
    public Button bouton_ajout_compte_epargne;
    public Button bouton_supprimer_compte_normal;
    public Button bouton_supprimer_compte_epargne;
    public Button bouton_ajout_compte_vip;
    public Button bouton_supprimer_compte_vip;

    Client client = guiChercherClientController.getClient();


    public void initialize() throws SQLException {
        bouton_consulter_compte_normal.setVisible(false);
        bouton_consulter_compte_vip.setVisible(false);
        nomClient.setText(client.getNom());
        prenomClient.setText(client.getPrenom());
        cinClient.setText(client.getCIN());

        //Disable all buttons
        bouton_ajout_compte_normal.setDisable(true);
        bouton_ajout_compte_epargne.setDisable(true);
        bouton_supprimer_compte_normal.setDisable(true);
        bouton_supprimer_compte_epargne.setDisable(true);
        bouton_ajout_compte_vip.setDisable(true);
        bouton_supprimer_compte_vip.setDisable(true);

        //enable buttons by default
        bouton_consulter_compte_epargne.setDisable(false);
        bouton_consulter_compte_vip.setDisable(false);
        bouton_consulter_compte_normal.setDisable(false);

        Connector con = new Connector();
        if (client instanceof Client_salarie){
            // CLient est salarie
            bouton_consulter_compte_normal.setVisible(true);
            details_vip.setVisible(false);
            logo_VIP.setVisible(false);
            details_salarie.setVisible(true);
            professionClient.setText(client.getProfession());
            salaireClient.setText( Double.toString(((Client_salarie) client).getSalaire()) +" TND" );
            System.out.println(client instanceof Client_salarie);
            try {compte = con.check_account(client, "compte");
                bouton_consulter_compte_normal.setDisable(false);
                bouton_ajout_compte_normal.setDisable(true);
                bouton_supprimer_compte_normal.setDisable(false);
            }
            catch (ErrCompteIntrouvable e ) {
                bouton_consulter_compte_normal.setDisable(true);
                bouton_ajout_compte_normal.setDisable(false);
                bouton_supprimer_compte_normal.setDisable(true);
            }
            catch (Exception e) { e.printStackTrace();}
        }
        else if (client instanceof Client_VIP) {
            bouton_consulter_compte_vip.setVisible(true);
            System.out.println(client instanceof Client_VIP);
            logo_VIP.setVisible(true);
            details_vip.setVisible(true);
            details_salarie.setVisible(false);
            entrepriseClient.setText(((Client_VIP)client).getNomEntreprise());
            nbEmployesClient.setText(Integer.toString(((Client_VIP)client).getNombreEmploye()));
            chiffreAffaireClient.setText((((Client_VIP)client).getChiffreAffaire()).toString());
            try {
                compte = con.check_account(client, "compte_vip");
                bouton_consulter_compte_vip.setDisable(false);
                bouton_ajout_compte_vip.setDisable(true);
                bouton_supprimer_compte_vip.setDisable(false);
            }
            catch (ErrCompteIntrouvable e ) {
                bouton_consulter_compte_vip.setDisable(true);
                bouton_ajout_compte_vip.setDisable(false);
                bouton_supprimer_compte_vip.setDisable(true);
            }
            catch (Exception e) { e.printStackTrace();}
        }

        try {
            compte_epargne = (Compte_epargne) con.check_account(client, "compte_epargne");
            bouton_consulter_compte_epargne.setDisable(false);
            bouton_ajout_compte_epargne.setDisable(true);
            bouton_supprimer_compte_epargne.setDisable(false);
        }
        catch (ErrCompteIntrouvable e ) {
            bouton_consulter_compte_epargne.setDisable(true);
            bouton_ajout_compte_epargne.setDisable(false);
            bouton_supprimer_compte_epargne.setDisable(true);
        }
        catch (Exception e) { e.printStackTrace();}

    }

    public void consulter_compte_normal(MouseEvent mouseEvent) throws SQLException {
        Connector con = new Connector();
        Parent root = null;
        try {
            compte = con.check_account(client, "compte");
            root = FXMLLoader.load(getClass().getResource("guiCompte.fxml"));
            Stage interface_compte = new Stage();
            interface_compte.setTitle("Gestion Compte");
            interface_compte.setScene(new Scene(root, 850, 500));
            interface_compte.show();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consulter_compte_epargne(MouseEvent mouseEvent) throws SQLException {
        Connector con = new Connector();
        Parent root = null;
        try {
            compte = con.check_account(client, "compte_epargne");
            root = FXMLLoader.load(getClass().getResource("guiCompte.fxml"));
            Stage interface_compte = new Stage();
            interface_compte.setTitle("Gestion Compte Epargne");
            interface_compte.setScene(new Scene(root, 850, 450));
            interface_compte.show();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void consulter_compte_vip(MouseEvent mouseEvent) throws SQLException {
        Connector con = new Connector();
        Parent root = null;
        try {
            compte = con.check_account(client, "compte_vip");
            root = FXMLLoader.load(getClass().getResource("guiCompte.fxml"));
            Stage interface_compte = new Stage();
            interface_compte.setTitle("Gestion Compte VIP");
            interface_compte.setScene(new Scene(root, 850, 450));
            interface_compte.show();

        }catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void retour(MouseEvent mouseEvent) {
        Stage s = (Stage) bouton_retour.getScene().getWindow();
        s.close();
    }

    public static CompteAbstrait getCompte() {
        return compte;
    }

    public static Compte_epargne getCompteEpargne() {
        return compte_epargne;
    }

    public void ajout_compte_normal(MouseEvent mouseEvent) throws ErrNegatif, SQLException, ErrClientIntrouvable, ClassNotFoundException {
        Connector con = new Connector();
        Compte compte_normal = new Compte(Numeral_String_builder(15), new BigDecimal(0), client, LocalDate.now());
        con.bd_ajout_compte(client, compte_normal, "compte");
        initialize();
    }

    public void supprimer_compte_normal(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, ErrTypeCompte, ErrNegatif, ErrCompteIntrouvable {
        String typeCompte = "compte";
        String alert_type = "normal";
        supprimer_compte(typeCompte, alert_type);
        initialize();
    }

    public void ajout_compte_epargne(MouseEvent mouseEvent) throws SQLException, ErrNegatif, ErrClientIntrouvable, ClassNotFoundException {
        Connector con = new Connector();
        Compte_epargne compte_epargne = new Compte_epargne(Numeral_String_builder(15), new BigDecimal(0), client, LocalDate.now(), new BigDecimal(0), LocalDate.now());
        con.bd_ajout_compte(client, compte_epargne, "compte_epargne");
        initialize();
    }

    public void supprimer_compte_epargne(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, ErrTypeCompte, ErrNegatif, ErrCompteIntrouvable  {
        String typeCompte = "compte_epargne";
        String alert_type = "épargne";
        supprimer_compte(typeCompte, alert_type);
        initialize();
    }

    public void ajout_compte_vip(MouseEvent mouseEvent) throws SQLException, ErrNegatif, ErrClientIntrouvable, ClassNotFoundException{
        Connector con = new Connector();
        Compte_vip compte_vip = new Compte_vip(Numeral_String_builder(15), new BigDecimal(0), (Client_VIP)client, LocalDate.now());
        con.bd_ajout_compte(client, compte_vip, "compte_vip");
        initialize();
    }

    public void supprimer_compte_vip(MouseEvent mouseEvent) throws SQLException, ClassNotFoundException, ErrTypeCompte, ErrNegatif, ErrCompteIntrouvable {
        String typeCompte = "compte_vip";
        String alert_type = "VIP";
        supprimer_compte(typeCompte, alert_type);
        initialize();
    }

    private void supprimer_compte(String typeCompte, String alert_type) throws SQLException, ErrCompteIntrouvable, ClassNotFoundException, ErrNegatif, ErrTypeCompte {
        String result = input_compte_cin(alert_type);
        if (result.equals(client.getCIN())){
            Connector con = new Connector();
            CompteAbstrait compte = con.check_account(client, typeCompte);
            String rib= compte.getRib();
            con.bd_supprimer_compte(compte,typeCompte);
            alerte_succes_suppression_compte(alert_type,rib);
            initialize();
        }
        else {
            alerte_erreure_suppression_compte_cin();
        }
    }

    public static String Numeral_String_builder(int len){
        String Numeral_String="";
        for (int i=0; i<len; i++){
            String chiffre = Integer.toString((int)(Math.random() * 10));
            Numeral_String = Numeral_String + chiffre;
        }
    return Numeral_String;
    }

    public void alerte_erreure_suppression_compte_cin(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur Suppression");
        alert.setHeaderText("Erreur Suppression: Ce Cin n'est pas celui du client!");
        alert.showAndWait();
    }

    public void alerte_succes_suppression_compte(String type, String rib){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Compte "+type+" (RIB: " + rib + ") supprimé avec succès!");
        alert.showAndWait();
    }

    public String input_compte_cin(String type){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Suppression Compte "+type);
        dialog.setHeaderText("Voulez vous vraiment supprimer le compte "+type+" du client?");
        dialog.setContentText("Si oui, entrez son CIN:");
        Optional<String> result = dialog.showAndWait();
        return result.get();

    }
}

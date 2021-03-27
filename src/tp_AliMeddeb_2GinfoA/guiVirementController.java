package tp_AliMeddeb_2GinfoA;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tp_AliMeddeb_2GinfoA.exception.*;

import java.math.BigDecimal;
import java.sql.SQLException;

public class guiVirementController {
    //Origine
    public Text nomCompletClient;
    public Text compteType;
    public Text compteRIB;
    public Text compteSolde;
    //Destination
    public Text nomCompletDestination;
    public Text RIBcompteDestination;
    //..
    public TextField rib_destination;
    public TextField montant_envoi;
    public Button bouton_retour;
    public Button bouton_virement;
    public Button bouton_verifier_RIB;
    public Button bouton_envoi_virement;
    public Text compteSoldeMin;

    CompteAbstrait compte = guiClientController.getCompte();
    Client client = guiChercherClientController.getClient();
    CompteAbstrait compte_destination=null;
    Client client_destination = null;

    public void initialize(){
        bouton_envoi_virement.setDisable(true);
        montant_envoi.setDisable(true);
        nomCompletClient.setText(client.getNom() +" "+ client.getPrenom());
        compteRIB.setText(compte.getRib());
        compteSoldeMin.setText("0 TND");
        compteSolde.setText((compte.getSolde()).toString() +" TND");
        if (compte instanceof Compte_vip) compteSoldeMin.setText(((Compte_vip) compte).getValeurMinSolde().toString() +" TND");
    }


    public void retour(MouseEvent mouseEvent) {
        Stage s = (Stage) bouton_retour.getScene().getWindow();
        s.close();
    }

    public void verifier_rib_destination(MouseEvent mouseEvent) {
        String rib = rib_destination.getText();
        try {
            Connector con = new Connector();
            client_destination = con.get_client_from_Rib(rib);
            bouton_envoi_virement.setDisable(false);
            montant_envoi.setDisable(false);
        }catch (ErrClientIntrouvable errClientIntrouvable) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur: Compte Introuvable");
            alert.setHeaderText("Ce Rip ne correspond à aucun compte de la base de données");
            alert.setContentText("Veuillez réverifier le RIb du  client destinataire");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            Connector con = new Connector();
            try {
                client_destination = con.get_client_from_Rib(rib);
                try{
                    compte_destination = con.check_account(client_destination, "compte");
                }
                catch (ErrCompteIntrouvable e1){
                    try{
                        compte_destination = con.check_account(client_destination, "compte_epargne");
                    }
                    catch (ErrCompteIntrouvable e2){
                        try {compte_destination = con.check_account(client_destination, "compte_vip");}
                        catch (ErrCompteIntrouvable e3){e3.printStackTrace();}
                    }
                }
            } catch (ErrClientIntrouvable errClientIntrouvable) {
                errClientIntrouvable.printStackTrace();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (ErrTypeCompte errTypeCompte) {
                errTypeCompte.printStackTrace();
            } catch (ErrNegatif errNegatif) {
                errNegatif.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        nomCompletDestination.setText(client_destination.getNom() +" " + client_destination.getPrenom());
        RIBcompteDestination.setText(rib_destination.getText());
    }


    public void envoi_virement(MouseEvent mouseEvent) throws ErrCompteEpargneBloque {
        BigDecimal montant;
        String montantString = montant_envoi.getText();
        try {
            montant = new BigDecimal(montantString);
            compte.virement(compte_destination, montant);
            Connector con = new Connector();
            con.bd_modifier_solde_compte(compte);
            con.bd_modifier_solde_compte(compte);
            con.bd_modifier_solde_compte(compte_destination);
            initialize();
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur: Montant invalide");
            alert.setHeaderText("Erreur Montant: le montant désiré doit etre composé uniquement de chiffres!");
            alert.showAndWait();
        }
        catch (ErrValeurNegative e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur: Montant Négatif");
            alert.setHeaderText("Erreur Valeur Montant: Veuillez entrer une valeure supérieure à 0!");
            alert.showAndWait();
        }
        catch (ErrNegatif errNegatif) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur: Solde Insuffisant");
            alert.setHeaderText("Le solde du compte est insuffisant pour le retrait de " + montantString + " TND");
            alert.showAndWait();

        } catch (SQLException | ErrTypeCompte throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur Système");
            alert.setHeaderText("Erreur Système, Veuillez contacter le support!");
            alert.setContentText(throwables.getMessage());
            alert.showAndWait();
        }
    }



}

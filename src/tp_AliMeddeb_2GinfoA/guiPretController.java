package tp_AliMeddeb_2GinfoA;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tp_AliMeddeb_2GinfoA.exception.ErrNegatif;
import tp_AliMeddeb_2GinfoA.exception.ErrPretLimiteDepasse;
import tp_AliMeddeb_2GinfoA.exception.ErrValeurNegative;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class guiPretController {
    public Text pretActuel;
    public Text pretMaximum;
    public Button bouton_retour;
    public Button bouton_ppret;
    public TextField montantPret;
    public Text compteSolde;
    public TextField montantRepayer;
    public Button bouton_repayer;

    CompteAbstrait compte = guiClientController.getCompte();
    Client client = guiChercherClientController.getClient();

    public void initialize() {
        pretMaximum.setText((((Client_VIP) client).getChiffreAffaire().multiply(new BigDecimal(0.3))).setScale(0, RoundingMode.UP).toString());
        pretActuel.setText((((Compte_vip) compte).getPret()).toString());
        compteSolde.setText((compte.getSolde()).toString() + " TND");
    }

    public void executer_pret(MouseEvent mouseEvent) {
        try {
            BigDecimal montant = new BigDecimal(montantPret.getText());
            ((Compte_vip) compte).pret(montant);
            Connector con = new Connector();
            con.bd_modifier_solde_compte(compte);
            initialize();
        } catch (NumberFormatException e) {
            alerte_erreur_input();
        } catch (ErrValeurNegative errValeurNegative) {
            alerte_erreur_input_negatif();
        } catch (ErrPretLimiteDepasse errPretLimiteDepasse) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur: Limite de pret dépassée");
            alert.setHeaderText("Vous ne pouvez pas emprunter plus que " + pretMaximum.getText() + " TND");
            alert.showAndWait();
        } catch (Exception throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur Système");
            alert.setHeaderText("Erreur Système, Veuillez contacter le support!");
            alert.setContentText(throwables.getMessage());
            alert.showAndWait();
            throwables.printStackTrace();
        }

    }

    public void repayer(MouseEvent mouseEvent) {
        try {
            BigDecimal montant = new BigDecimal(montantRepayer.getText());
            ((Compte_vip) compte).payer_pret(montant);
            Connector con = new Connector();
            con.bd_modifier_solde_compte(compte);
            initialize();
        } catch (NumberFormatException e) {
            alerte_erreur_input();
        } catch (ErrValeurNegative errValeurNegative) {
            alerte_erreur_input_negatif();
        } catch (ErrNegatif errNegatif) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur: Solde insuffisant");
            alert.setHeaderText("Le solde est insuffisant pour repayer ce montant");
            alert.showAndWait();

        }catch (Exception throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur Système");
            alert.setHeaderText("Erreur Système, Veuillez contacter le support!");
            alert.setContentText(throwables.getMessage());
            alert.showAndWait();
            throwables.printStackTrace();
        }
    }

    public void alerte_erreur_input(){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur: Montant invalide");
            alert.setHeaderText("Erreur Montant: le montant désiré doit etre composé uniquement de chiffres!");
            alert.showAndWait();
    }

    public void alerte_erreur_input_negatif(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Erreur: Montant Négatif");
        alert.setHeaderText("Erreur Valeur Montant: Veuillez entrer une valeure supérieure à 0!");
        alert.showAndWait();
    }



    public void retour(MouseEvent mouseEvent) {
        Stage s = (Stage) bouton_retour.getScene().getWindow();
        s.close();
    }


}

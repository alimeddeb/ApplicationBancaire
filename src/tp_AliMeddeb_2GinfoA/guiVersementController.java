package tp_AliMeddeb_2GinfoA;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tp_AliMeddeb_2GinfoA.exception.ErrCompteEpargneBloque;
import tp_AliMeddeb_2GinfoA.exception.ErrTypeCompte;
import tp_AliMeddeb_2GinfoA.exception.ErrValeurNegative;

import java.math.BigDecimal;
import java.sql.SQLException;

public class guiVersementController {

    public Text nomCompletClient;
    public Text compteType;
    public Text compteRIB;
    public Text compteSolde;
    public TextField montantVirement;
    public Button bouton_retour;
    public Button bouton_virement;

    CompteAbstrait compte = guiClientController.getCompte();
    Client client = guiChercherClientController.getClient();

    public void initialize(){
        nomCompletClient.setText(client.getNom() +" "+ client.getPrenom());
        compteRIB.setText(compte.getRib());
        compteSolde.setText((compte.getSolde()).toString() +" TND");
    }


    public void retour(MouseEvent mouseEvent) {
        Stage s = (Stage) bouton_retour.getScene().getWindow();
        s.close();
    }

    public void versement(MouseEvent mouseEvent) {
        BigDecimal montant;
        String montantString = montantVirement.getText();
        try {
            montant = new BigDecimal(montantString);
            compte.versement(montant);
            Connector con = new Connector();
            con.bd_modifier_solde_compte(compte);
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
        } catch (ErrCompteEpargneBloque errCompteEpargneBloque) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur: Compte Bloqué");
            alert.setHeaderText("Votre Compte est bloqué!");
            alert.showAndWait();
        }
        catch (SQLException | ErrTypeCompte throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur Système");
            alert.setHeaderText("Erreur Système, Veuillez contacter le support!");
            alert.setContentText(throwables.getMessage());
            alert.showAndWait();
        }
    }
}

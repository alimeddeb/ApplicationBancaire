package tp_AliMeddeb_2GinfoA;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class guiCompteController {
    public Text nomCompletClient;
    public Text compteType;
    public Text compteRIB;
    public Text compteDateCreation;
    public Text compteSolde;
    public Text compteActif;
    public Text compteDateVersement;
    public Text compteMontantVersement;
    public AnchorPane compteInfoEpargne;
    public Button bouton_retrait;
    public Button bouton_virement;
    public Button bouton_versement;
    public AnchorPane interfaceCompte;
    public Button bouton_pret;
    public Button bouton_refresh;

    CompteAbstrait compte = guiClientController.getCompte();
    Compte_epargne compte_epargne = guiClientController.getCompteEpargne();
    Client client = guiChercherClientController.getClient();
    public Button bouton_retour;

    public void initialize(){
        compteInfoEpargne.setVisible(false);
        bouton_pret.setVisible(false);
        bouton_virement.setDisable(false);
        nomCompletClient.setText(client.getNom() +" "+ client.getPrenom());
        compteRIB.setText(compte.getRib());
        compteDateCreation.setText(compte.getDate_creation().toString());
        compteSolde.setText((compte.getSolde()).toString());
        if (compte instanceof Compte) {
            compteType.setText("Compte Normal");
        }
        else if (compte instanceof Compte_epargne) {
            compteInfoEpargne.setVisible(true);
            compteType.setText("Compte épargne");
            bouton_virement.setDisable(true);

            // modifying text fields
            compteRIB.setText(compte_epargne.getRib());
            compteDateCreation.setText(compte_epargne.getDate_creation().toString());
            compteSolde.setText((compte_epargne.getSolde()).toString());
            if ((compte_epargne).isActif()) {
                compteActif.setText("Actif");
                compteActif.setStyle("-fx-text-fill: green;");
            }
            else {
                compteActif.setText("Bloqué");
                compteActif.setStyle("-fx-text-fill: red;");
            }
            compteDateVersement.setText(compte_epargne.getDate_dernier_versement().toString());
            compteMontantVersement.setText(compte_epargne.getMontant_dernier_versement().toString());

        }
        else if (compte instanceof Compte_vip) {
            bouton_pret.setVisible(true);
            compteType.setText("Compte VIP");
        }


    }


    public void retrait(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("guiRetrait.fxml"));
        Stage client_recherche = new Stage();
        client_recherche.setTitle("Retrait Especes");
        client_recherche.setScene(new Scene(root, 850, 450));
        client_recherche.show();

    }

    public void virement(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("guiVirement.fxml"));
        Stage client_recherche = new Stage();
        client_recherche.setTitle("Virement Bancaire");
        client_recherche.setScene(new Scene(root, 850, 450));
        client_recherche.show();
    }

    public void versement(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("guiVersement.fxml"));
        Stage client_recherche = new Stage();
        client_recherche.setTitle("Versement Especes");
        client_recherche.setScene(new Scene(root, 850, 450));
        client_recherche.show();

    }

    public void pret(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("guiPret.fxml"));
        Stage client_recherche = new Stage();
        client_recherche.setTitle("Pret");
        client_recherche.setScene(new Scene(root, 850, 450));
        client_recherche.show();

    }

    public void retour(MouseEvent mouseEvent) {
        Stage s = (Stage) bouton_retour.getScene().getWindow();
        s.close();
    }

    public void refresh(MouseEvent mouseEvent) {
        initialize();
    }
}

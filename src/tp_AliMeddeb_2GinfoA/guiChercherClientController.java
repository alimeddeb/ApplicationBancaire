package tp_AliMeddeb_2GinfoA;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tp_AliMeddeb_2GinfoA.Client;
import tp_AliMeddeb_2GinfoA.Connector;
import tp_AliMeddeb_2GinfoA.exception.ErrClientIntrouvable;

import java.sql.SQLException;

public class guiChercherClientController {
    public TextField cin;
    public static Client client;
    public Button bouton_deconnexion;

    public void consulter(MouseEvent mouseEvent) {
        try{
            Connector con = new Connector();
            client = con.check_client(cin.getText());
            Parent root = FXMLLoader.load(getClass().getResource("guiClient.fxml"));
            Stage client_profil = new Stage();
            client_profil.setTitle("Client: "+ client.getPrenom() + " "+client.getNom());
            client_profil.setScene(new Scene(root, 850, 500));
            cin.setText("");
            client_profil.show();

        } catch (ErrClientIntrouvable errClientIntrouvable) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Client Introuvable");
            alert.setHeaderText("Erreur: Ce client ne figure  pas dans la base de données");
            alert.setContentText("Veuillez réverifier le CIN");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur Système");
            alert.setHeaderText("Erreur Système, Veuillez contacter le support!");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void se_deconnecter(MouseEvent mouseEvent) {
        Stage s = (Stage) bouton_deconnexion.getScene().getWindow();
        s.close();
    }

    public static Client getClient() {
        return client;
    }
}

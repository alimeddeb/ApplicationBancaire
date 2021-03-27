package tp_AliMeddeb_2GinfoA;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tp_AliMeddeb_2GinfoA.Connector;
import tp_AliMeddeb_2GinfoA.exception.ErrLoginIncorrect;

public class guiLoginController {
    public PasswordField mdp;
    public TextField login;


    @FXML
    public void authentification(MouseEvent mouseEvent) {
        try {
            Connector con = new Connector();
            con.check_login(login.getText(), mdp.getText());
            System.out.println("SUCCES LOGIN");
            Parent root = FXMLLoader.load(getClass().getResource("guiChercherClient.fxml"));
            Stage client_recherche = new Stage();
            client_recherche.setTitle("Chercher Client");
            client_recherche.setScene(new Scene(root, 850, 450));
            login.setText("");
            mdp.setText("");
            client_recherche.show();
        }
        catch (ErrLoginIncorrect e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Erreur Authentification");
            alert.setHeaderText("Erreur authentification: Login ou mot de passe incorrect!");
            alert.setContentText("Veuillez réessayer");
            alert.showAndWait();
        }
        catch (Exception throwables) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur Système");
            alert.setHeaderText("Erreur Système, Veuillez contacter le support!");
            alert.setContentText(throwables.getMessage());
            alert.showAndWait();
        }

    }

    public void quitter_application(MouseEvent mouseEvent) {
        System.exit(0);
    }
}

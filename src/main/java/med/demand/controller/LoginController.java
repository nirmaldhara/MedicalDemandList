/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.controller;

import med.demand.dao.LoginDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author ndhara
 */
public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private TextField userid;
    @FXML
    private TextField password;

    @FXML
    private Label errorMsgUsrid;
    @FXML
    private Label errorMsgUsrpassword;
    
    @FXML
    private Label invalidAuth;
    
    Button b;
    Button b1;
    Button b2;
    Button b3;
    Button b4;
    Button b5;
private MainController mainController;
    
    public void closeLogin() {
        Stage stage1 = (Stage) btnLogin.getScene().getWindow();
        stage1.close();
    }

    private boolean validate() {
        boolean flag = true;
        if (userid.getText().equals("")) {
            flag = false;
            errorMsgUsrid.setText("User id can not be blank");
        } else {
            errorMsgUsrid.setText("");
        }
        if (password.getText().equals("")) {
            flag = false;
            errorMsgUsrpassword.setText("Password can not be blank");
        } else {
            errorMsgUsrpassword.setText("");
        }
        return flag;
    }

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        //label.setText("Hello World!");
        if (validate()) {
            if (LoginDAO.isValiduser(userid.getText(), password.getText())) {
                //newBillMenu.setDisable(false);
                b.setDisable(false);
                b1.setDisable(false);
                b2.setDisable(false);
                b3.setDisable(false);
                b4.setDisable(false);
                b5.setDisable(false);
                 //frame = fxmlLoader.load();
  
                closeLogin();

            }
            else{
                invalidAuth.setText("Invalid Authentication");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        invalidAuth.setText("");
        errorMsgUsrpassword.setText("");
        errorMsgUsrid.setText("");
    }

    /**
     * @param mainController the mainController to set
     */
    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    
    public void eanbleButtons(Button b,Button b1, Button b2, Button b3, Button b4,Button b5)
    {
        
        this.b=b;
        this.b1=b1;
        this.b2=b2;
        this.b3=b3;
        this.b4=b4;
        this.b5=b5;
           
        
    }
}

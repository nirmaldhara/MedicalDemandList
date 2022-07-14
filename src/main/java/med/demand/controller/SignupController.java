/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.controller;

import med.demand.dao.SignUpDAO;
import med.demand.model.SignUp;
import med.demand.util.Util;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author ndhara
 */
public class SignupController implements Initializable {

    @FXML
    TextField txtName;
    @FXML
    TextField txtEmail;
    @FXML
    TextField txtMobile;
    @FXML
    TextField txtUserId;
    @FXML
    TextField txtPassword;
    @FXML
    TextField txtRepassword;
    @FXML
    ComboBox comboGender;

    @FXML
    Button btnSignup;

    @FXML
    Label lblBlankName;
    @FXML
    Label lblblankEmail;

    @FXML
    Label lblblankMobile;

    @FXML
    Label lblblankUserid;

    @FXML
    Label lblblankPassword;

    @FXML
    Label lblblankRepassword;

    @FXML
    Label lblmsgSignup;

    @FXML
    Label lblblankGender;

    private boolean validateForm() {
        boolean flag = true;
        if (Util.isblank(txtName)) {
            lblBlankName.setText("Name can not be blank");
            flag = false;
        } else {
            lblBlankName.setText("");
        }
        if (Util.isblank(txtEmail)) {
            lblblankEmail.setText("Email can not be blank");
            flag = false;
        }

        else {
            lblblankEmail.setText("");
        }
        if (Util.isblank(txtMobile)) {
            lblblankMobile.setText("Mobile no can not be blank");
            flag = false;
        }
        else if(!Util.isValidMobile(txtMobile.getText())){
            lblblankMobile.setText("Not a valid Mobile No");
            flag = false;
        }
            else {
            lblblankMobile.setText("");
        }
        if (Util.isblank(txtUserId)) {
            lblblankUserid.setText("User id can not be blank");
            flag = false;
        } else {
            lblblankUserid.setText("");
        }

        if (Util.isblank(txtPassword)) {
            lblblankPassword.setText("Password can not be blank");
            flag = false;
        } else {
            lblblankPassword.setText("");
        }

        if (Util.isblank(txtRepassword)) {
            lblblankRepassword.setText("Retype Password can not be blank");
            flag = false;
        } else {
            lblblankRepassword.setText("");
        }

        if (!txtPassword.getText().equals(txtRepassword.getText())) {
            lblmsgSignup.setText("Password and Retype Password does not match");
            flag = false;
        }


        else{
            lblblankGender.setText("");
        }

        return flag;
    }

    @FXML
    private void doSignUp(ActionEvent event) throws IOException {
        SignUp signup = new SignUp();
        if (validateForm()) {
            try {
                signup.setName(txtName.getText());
                signup.setEmailid(txtEmail.getText());
                signup.setMobile(txtMobile.getText());
                signup.setGender(comboGender.getSelectionModel().getSelectedItem().toString());
                signup.setPassword(txtPassword.getText());
                signup.setUserid(txtUserId.getText());
                signup.setStatus(1);
                Date d = new Date();
                signup.setCreatedate(d.toString());
                SignUpDAO.addSignup(signup);
                lblmsgSignup.setTextFill(Color.web("#01962b"));
                lblmsgSignup.setText("User created successfully");
            } catch (SQLException ex) {
                Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void isUserIdExists(ActionEvent event) throws IOException, SQLException {

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        lblBlankName.setText("");
        lblblankEmail.setText("");
        lblblankMobile.setText("");
        lblblankUserid.setText("");
        lblblankPassword.setText("");
        lblblankRepassword.setText("");
        lblmsgSignup.setText("");
        lblblankGender.setText("");
        btnSignup.setDisable(true);
        txtUserId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                try {
                    //this will run whenever text is changed
                    boolean exist = SignUpDAO.isUserExist(txtUserId.getText());
                    if (exist) {
                        lblmsgSignup.setText("User id already exist");
                        btnSignup.setDisable(true);
                    } else {
                        btnSignup.setDisable(false);
                        lblmsgSignup.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

       txtEmail.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> observable, final String oldValue, final String newValue) {
                try {
                    //this will run whenever text is changed
                    boolean exist = SignUpDAO.isEmailExist(txtEmail.getText());
                    if (exist) {
                        lblmsgSignup.setText("Email id already used.");
                        btnSignup.setDisable(true);
                    } else {
                        btnSignup.setDisable(false);
                        lblmsgSignup.setText("");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SignupController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

}

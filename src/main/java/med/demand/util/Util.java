/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package med.demand.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.apache.commons.validator.routines.EmailValidator;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ndhara
 */
public class Util {
    
    public static boolean isLogedIn(){
        boolean flag=false;
        
        return flag;
    }
    public static void showErrorMessage(String header, String message){
        Alert dialog = new Alert(Alert.AlertType.ERROR);
            dialog.setHeaderText(header);
            dialog.setContentText(message);
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(400, 200);
            dialog.showAndWait();
    }
    
    public static boolean isblank(TextField text){
        if(text.getText().equals(""))
        {
            System.out.println("true");
            return true;
        }
        System.out.println("false");
        return false;
    }


    public static boolean isValidEmail(String emailStr) {
        return EmailValidator.getInstance().isValid(emailStr);
    }

    public static boolean isValidIntDec(String emailStr) {
        Matcher matcher = Pattern.compile("^[1-9]\\d*(\\.\\d+)?$", Pattern.CASE_INSENSITIVE).matcher(emailStr);

        return matcher.matches();
    }

    public static boolean isValidMobile(String mobile) {
        Matcher matcher = Pattern.compile("^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$", Pattern.CASE_INSENSITIVE).matcher(mobile);

        return matcher.matches();
    }
    public static boolean isblank(ComboBox cmb){

boolean flag =false;
        if(cmb.getValue()==null||cmb.getValue().equals(""))
        {

            flag= true;
        }
        for(Object str:cmb.getItems()){
            if((str.toString().equals(cmb.getEditor().getText()))){

                    flag= false;
                    break;


            }
            else{
                flag= true;
            }
        }

        return flag;
    }
    public static String currentDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("dd_MM_yyyy");
        Date date = new Date();
        return formatter.format(date);
    }
}

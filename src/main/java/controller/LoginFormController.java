package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

public class LoginFormController {
    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane login_form;

    @FXML
    private TextField login_username;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_showPassword;

    @FXML
    private CheckBox login_checkBox;

    @FXML
    private Button login_loginBtn;

    @FXML
    private ComboBox login_user;

    @FXML
    private Hyperlink login_registerHere;

    @FXML
    private AnchorPane register_form;

    @FXML
    private TextField register_email;

    @FXML
    private TextField register_showPassword;

    @FXML
    private TextField register_username;

    @FXML
    private PasswordField register_password;

    @FXML
    private CheckBox register_checkBox;

    @FXML
    private Button register_signUpBtn;

    @FXML
    private Hyperlink register_loginHere;


    public void initialize(){

    }


    public void switchForm(javafx.event.ActionEvent actionEvent) {
        if (actionEvent.getSource() == login_registerHere){
            //--Registration form will show
            login_form.setVisible(false);
            register_form.setVisible(true);
        } else if (actionEvent.getSource() == register_loginHere) {
            //--Login form will show
            login_form.setVisible(true);
            register_form.setVisible(false);
        }
    }
}

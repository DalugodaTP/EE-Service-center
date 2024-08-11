package controller;

import dto.CustomerDto;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController {
    public Hyperlink passwordReset_loginHere;
    public AnchorPane resetPassword_form;
    public TextField resetPassword_email;
    public TextField resetPassword_Otp;
    public Button register_signUpBtn1;
    public MFXPasswordField resetPassword_newPassword;
    public MFXPasswordField resetPassword_confirmPassword;
    public Hyperlink login_resetPassword;
    @FXML
    private AnchorPane main_form;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Label txtPortalDescription;

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
        setRole();

        //--change the subtext based on the combo box selection
        login_user.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, role) -> {
            if (role == "Admin"){
                txtPortalDescription.setText("Admin Portal");
            } else if (role == "User") {
                txtPortalDescription.setText("User Portal");
            }
        }));
    }

    private void setRole() {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("User");
        list.add("Admin");
        login_user.setItems(list);
        login_user.getSelectionModel().selectFirst();
    }

    public void switchForm(javafx.event.ActionEvent actionEvent) {
        if (actionEvent.getSource() == login_registerHere){
            //--Registration form will show
            login_form.setVisible(false);
            resetPassword_form.setVisible(false);
            register_form.setVisible(true);
        } else if (actionEvent.getSource() == register_loginHere || actionEvent.getSource() == passwordReset_loginHere) {
            //--Login form will show
            login_form.setVisible(true);
            resetPassword_form.setVisible(false);
            register_form.setVisible(false);
        } else if (actionEvent.getSource() == login_resetPassword) {
            //--Password reset form will show
            login_form.setVisible(false);
            resetPassword_form.setVisible(true);
            register_form.setVisible(false);
        }
    }

    public void loginButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage)login_form.getScene().getWindow();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ControlPanelForm.fxml"))));
            stage.centerOnScreen();
            stage.setResizable(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Control Panel window in the path is missing");
        }
    }

    public void signUpButtonOnAction(ActionEvent actionEvent) {
    }

    public void sendOtpButtonOnAction(ActionEvent actionEvent) {
    }
}

package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.*;
import com.gui.projectmanagement.functions.AlertLib;
import com.gui.projectmanagement.functions.Notification;
import com.gui.projectmanagement.functions.Time;
import com.gui.projectmanagement.network.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.gui.projectmanagement.authentication.RegisterManager ;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginController implements Initializable {
    @FXML
    private Button btn_tmp ;

    @FXML
    private AnchorPane anchorpane_login ;

    @FXML
    private AnchorPane anchorpane_regester1 ;

    @FXML
    private AnchorPane anchorpane_regester2 ;

    @FXML
    private AnchorPane anchorpane_regester3 ;

    // A_login
    @FXML
    private TextField username_login ;

    @FXML
    private PasswordField password_login ;

    @FXML
    private TextField password_show_login ;

    @FXML
    private ImageView eye_password ;

    @FXML
    private HBox hbox_password_login ;

    @FXML
    private Button btn_eye ;

    @FXML
    private HBox hbox_contain_btneye ;

    // Regester 1

    @FXML
    private TextField first_name ;

    @FXML
    private TextField last_name ;

    @FXML
    private DatePicker day_of_birth ;

    @FXML
    private ComboBox<String> gender ;

    // Regester 2

    @FXML
    private TextField email ;

    @FXML
    private TextField phonenumber ;

    // Regester 3

    @FXML
    private TextField username_regester ;

    @FXML
    private PasswordField password_regester1 ;

    @FXML
    private PasswordField password_regester2 ;

    @FXML
    private TextField password_show_regester1 ;

    @FXML
    private TextField password_show_regester2 ;

    @FXML
    private ImageView eye_password_regester1 ;

    @FXML
    private ImageView eye_password_regester2 ;

    @FXML
    private Button btn_eye1 ;

    @FXML
    private Button btn_eye2 ;

    @FXML
    private HBox hbox_password_regester1 ;

    @FXML
    private HBox hbox_password_regester2 ;

    //

    @FXML
    private Rectangle background ;

    // Login

    @FXML
    private AnchorPane a_forgot_password ;

    @FXML
    private AnchorPane a_forgot_1 ;

    @FXML
    private TextField username_search ;

    @FXML
    private Button find ;

    @FXML
    private AnchorPane a_forgot_2 ;

    @FXML
    private Label message ;

    @FXML
    private TextField code ;

    @FXML
    private Button confirm ;

    @FXML
    private Button finish ;

    @FXML
    private AnchorPane a_forgot_3 ;

    @FXML
    private TextField new_password ;

    private boolean is_show_password_login = false ;
    private boolean is_show_password_regester1 = false ;
    private boolean is_show_password_regester2 = false ;

    private Image eye_open = new Image(getClass().getResourceAsStream("/com/gui/projectmanagement/images/EyeOpen.png"));
    private Image eye_close = new Image(getClass().getResourceAsStream("/com/gui/projectmanagement/images/EyeClose.png"));

    private String password_login_value ;
    private String password_regester_value ;
    private String password_regester2_value ;

    private String[] gender_array = {"Male", "Female", "Other"} ;
    private ObservableList<String> gender_obs = FXCollections.observableArrayList(gender_array);

    //  Infor regester
    private String firstname_value ;
    private String lastname_value ;
    private LocalDate day_of_birth_value ;
    private String gender_value ;
    private String email_value ;
    private String phonenumber_value ;
    private String username_regester_value ;

    private StreamObject so ;

    ClientStream sf = new ClientStream() ;

    ProjectStream ps = new ProjectStream() ;

    GuestStream gs = new GuestStream() ;

    String imaPart = "/com/gui/projectmanagement/images/Background.png" ;
    Image background_img = new Image(getClass().getResourceAsStream(imaPart));

    String imaPart2 = "/com/gui/projectmanagement/images/app.png" ;

    Image icon_app = new Image(getClass().getResourceAsStream(imaPart2));

    AlertLib alert = new AlertLib() ;

    private static double xOffset = 0;
    private static double yOffset = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clear();

        if (!background_img.isError()) {
            ImagePattern pi = new ImagePattern(background_img) ;
            background.setFill(pi);
        }

        gender.setItems(gender_obs);
        Client c = new Client();
        if (so != null) {
            System.out.println("Connected to the server.");
        }

        password_login.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hbox_password_login.setStyle("-fx-border-color: black;");
            } else {
                hbox_password_login.setStyle(null);
            }
        });

        password_show_login.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hbox_password_login.setStyle("-fx-border-color: black;");
            } else {
                hbox_password_login.setStyle(null);
            }
        });

        password_login.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                password_login_value = password_login.getText();
                password_show_login.setText(password_login_value);
                if (!password_login_value.isEmpty()){
                    btn_eye.setVisible(true);
                } else {
                    btn_eye.setVisible(false);
                }
            }
        });

        password_show_login.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                password_login_value = password_show_login.getText();
                password_login.setText(password_login_value);
                if (!password_login_value.isEmpty()){
                    btn_eye.setVisible(true);
                } else {
                    btn_eye.setVisible(false);
                }
            }
        });

        day_of_birth.valueProperty().addListener(((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (!RegisterManager.dayOfBirthValidator(newValue)) {
                    day_of_birth.setStyle("-fx-border-color: red;");
                } else {
                    day_of_birth.setStyle(null);
                }
                day_of_birth_value = newValue ;
            }
        }));

        gender.setOnAction(event -> {
            gender_value = gender.getValue() ;
        });

        email.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String email_tmp = email.getText() ;
                if (!RegisterManager.emailValidator(email_tmp) && !email_tmp.isEmpty()) {
                    email.setStyle("-fx-border-color: red;");
                } else {
                    email.setStyle(null);
                }
                email_value = email_tmp ;
            }
        });

        phonenumber.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String phonenumber_tmp = phonenumber.getText() ;
                if (!RegisterManager.phonenumberValidator(phonenumber_tmp) && !phonenumber_tmp.isEmpty()) {
                    phonenumber.setStyle("-fx-border-color: red;");
                } else {
                    phonenumber.setStyle(null);
                }
                phonenumber_value = phonenumber_tmp ;
            }
        });

        username_regester.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String username_tmp = username_regester.getText() ;
                if (!RegisterManager.usernameValidator(username_tmp) && !username_tmp.isEmpty()) {
                    username_regester.setStyle("-fx-border-color: red;");
                } else {
                    username_regester.setStyle(null);
                }
                username_regester_value = username_tmp ;
            }
        });

        password_regester1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hbox_password_regester1.setStyle("-fx-border-color: black;");
            } else {
                String password_regester_tmp = password_regester1.getText();
                if (!RegisterManager.passwordValidator(password_regester_tmp) && !password_regester_tmp.isEmpty()) {
                    hbox_password_regester1.setStyle("-fx-border-color: red;");
                } else {
                    hbox_password_regester1.setStyle(null);
                }
            }
        });

        password_show_regester1.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hbox_password_regester1.setStyle("-fx-border-color: black;");
            } else {
                String password_regester_tmp = password_regester1.getText();
                if (!RegisterManager.passwordValidator(password_regester_tmp) && !password_regester_tmp.isEmpty()) {
                    hbox_password_regester1.setStyle("-fx-border-color: red;");
                } else {
                    hbox_password_regester1.setStyle(null);
                }
            }
        });

        password_regester2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hbox_password_regester2.setStyle("-fx-border-color: black;");
            } else {
                String password_regester_tmp = password_show_regester2.getText();
                if (!RegisterManager.confirmPassword(password_regester_value, password_regester_tmp) && !password_regester_tmp.isEmpty()) {
                    hbox_password_regester2.setStyle("-fx-border-color: red;");
                } else {
                    hbox_password_regester2.setStyle(null);
                }
            }
        });

        password_show_regester2.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hbox_password_regester2.setStyle("-fx-border-color: black;");
            } else {
                String password_regester_tmp = password_show_regester2.getText();
                if (!RegisterManager.confirmPassword(password_regester_value, password_regester_tmp) && !password_regester_tmp.isEmpty()) {
                    hbox_password_regester2.setStyle("-fx-border-color: red;");
                } else {
                    hbox_password_regester2.setStyle(null);
                }
            }
        });

        password_regester1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String password_regester_tmp = password_regester1.getText();
                if (!RegisterManager.passwordValidator(password_regester_tmp) && !password_regester_tmp.isEmpty()) {
                    hbox_password_regester1.setStyle("-fx-border-color: red;");
                } else {
                    hbox_password_regester1.setStyle(null);
                }

                password_regester_value = password_regester_tmp ;
                password_show_regester1.setText(password_regester_value);

                if (!password_regester_value.isEmpty()){
                    btn_eye1.setVisible(true);
                } else {
                    btn_eye1.setVisible(false);
                }
            }
        });

        password_show_regester1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String password_regester_tmp = password_show_regester1.getText();
                if (!RegisterManager.passwordValidator(password_regester_tmp) && !password_regester_tmp.isEmpty()) {
                    hbox_password_regester1.setStyle("-fx-border-color: red;");
                } else {
                    hbox_password_regester1.setStyle(null);
                }

                password_regester_value = password_regester_tmp ;
                password_regester1.setText(password_regester_value);

                if (!password_regester_value.isEmpty()){
                    btn_eye1.setVisible(true);
                } else {
                    btn_eye1.setVisible(false);
                }
            }
        });

        password_regester2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String password_regester2_tmp = password_regester2.getText() ;
                if (!RegisterManager.confirmPassword(password_regester_value, password_regester2_tmp) && !password_regester2_tmp.isEmpty()) {
                    hbox_password_regester2.setStyle("-fx-border-color: red;");
                } else {
                    hbox_password_regester2.setStyle(null);
                }

                password_regester2_value = password_regester2_tmp ;
                password_show_regester2.setText(password_regester2_value);

                if (!password_regester2_value.isEmpty()){
                    btn_eye2.setVisible(true);
                } else {
                    btn_eye2.setVisible(false);
                }
            }
        });

        password_show_regester2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String password_regester2_tmp = password_show_regester2.getText() ;
                if (!RegisterManager.confirmPassword(password_regester_value, password_regester2_tmp) && !password_regester2_tmp.isEmpty()) {
                    hbox_password_regester2.setStyle("-fx-border-color: red;");
                } else {
                    hbox_password_regester2.setStyle(null);
                }

                password_regester2_value = password_regester2_tmp ;
                password_regester2.setText(password_regester2_value);

                if (!password_regester2_value.isEmpty()){
                    btn_eye2.setVisible(true);
                } else {
                    btn_eye2.setVisible(false);
                }
            }
        });

        find.setOnAction(event -> {
            String username_rs = username_search.getText() ;
            if (!username_rs.equals("")) {
                boolean result = gs.forgotPassword_frist(so, username_rs) ;
                if (result) {
                    a_forgot_1.setVisible(false);
                    a_forgot_2.setVisible(true);
                    username_search.setText("");
                } else {
                    error("Username does not exist!");
                }
            }
        });

        confirm.setOnAction(event -> {
            String code = this.code.getText();
            if (code != null) {
                boolean result = gs.forgotPassword_mid(so, code) ;
                if (result) {
                    a_forgot_2.setVisible(false);
                    a_forgot_3.setVisible(true);
                    this.code.setText("");
                } else {
                    error("Incorrect code!") ;
                }
            }
        });

        finish.setOnAction(event -> {
            String new_pw = this.new_password.getText() ;
            if (new_pw != null) {
                if (new_pw.length() < 8) {
                    error("Password must be at least 8 characters long!");
                } else {
                    boolean result = gs.forgotPassword_last(so, getMd5(new_pw)) ;
                    if (result) {
                        alert("Change successful!") ;
                        this.a_forgot_3.setVisible(false);
                        this.a_forgot_password.setVisible(false);
                        this.anchorpane_login.setVisible(true);
                    } else {
                        error("Unsuccess change!");
                    }
                    new_password.setText("");
                }
            }
        });
    }

    @FXML
    private void forgotPassword (ActionEvent event) {
        this.anchorpane_login.setVisible(false);
        this.a_forgot_password.setVisible(true);
        this.a_forgot_1.setVisible(true);
    }

    @FXML
    private void login(ActionEvent event) {
        String username = username_login.getText() ;
        if (username.isEmpty() || password_login_value.isEmpty()) {
            // alert.error("Username or password is incorrect!");
            // throw new RuntimeException("Userame or Password is empty.") ;
        }
        LoginObject login_object = new LoginObject(username, getMd5(password_login_value)) ;
        ClientData client_data = gs.login(so, login_object);
        if (client_data != null) {
            windowSwitch(client_data, so);
            System.out.println("Logged in successfully!") ;
        } else {
            error("Username or Password is incorrect!") ;
        }
    }

    @FXML
    private void regester (ActionEvent event) {
        anchorpane_login.setVisible(false);
        anchorpane_regester1.setVisible(true);
    }

    @FXML
    private void showPassword (ActionEvent event) {
        if (is_show_password_login == false) {
            is_show_password_login = true ;
        } else {
            is_show_password_login = false ;
        }

        if (is_show_password_login == true ) {
            eye_password.setImage(eye_open);
            password_login.setVisible(false);
            password_show_login.setVisible(true);
        } else {
            eye_password.setImage(eye_close);
            password_show_login.setVisible(false);
            password_login.setVisible(true);
        }
    }

    // Regester 1

    @FXML
    private void backToLogin (ActionEvent event) {
        anchorpane_regester1.setVisible(false);
        anchorpane_login.setVisible(true);
    }

    @FXML
    private void goToRegester2 (ActionEvent event) {
        anchorpane_regester1.setVisible(false);
        anchorpane_regester2.setVisible(true);
    }

    // Regester 2

    @FXML
    private void backToRegester1 (ActionEvent event) {
        anchorpane_regester2.setVisible(false);
        anchorpane_regester1.setVisible(true);
    }

    @FXML
    private void goToRegester3 (ActionEvent event) {
        anchorpane_regester2.setVisible(false);
        anchorpane_regester3.setVisible(true);
    }

    // Regester 3

    @FXML
    private void showPasswordRegester1 (ActionEvent event) {
        if (is_show_password_regester1 == false) {
            is_show_password_regester1 = true ;
        } else {
            is_show_password_regester1 = false ;
        }

        if (is_show_password_regester1 == true ) {
            eye_password_regester1.setImage(eye_open);
            password_regester1.setVisible(false);
            password_show_regester1.setVisible(true);
        } else {
            eye_password_regester1.setImage(eye_close);
            password_show_regester1.setVisible(false);
            password_regester1.setVisible(true);
        }
    }

    @FXML
    private void showPasswordRegester2 (ActionEvent event) {
        if (is_show_password_regester2 == false) {
            is_show_password_regester2 = true ;
        } else {
            is_show_password_regester2 = false ;
        }

        if (is_show_password_regester2 == true ) {
            eye_password_regester2.setImage(eye_open);
            password_regester2.setVisible(false);
            password_show_regester2.setVisible(true);
        } else {
            eye_password_regester2.setImage(eye_close);
            password_show_regester2.setVisible(false);
            password_regester2.setVisible(true);
        }
    }

    @FXML
    private void backToRegester2 (ActionEvent event) {
        anchorpane_regester3.setVisible(false);
        anchorpane_regester2.setVisible(true);
    }

    @FXML
    private void goToAuthentication (ActionEvent event) {
        firstname_value = first_name.getText() ;
        lastname_value = last_name.getText() ;

        if (firstname_value.isEmpty()) {
            first_name.setStyle("-fx-border-color: red;");
            anchorpane_regester3.setVisible(false);
            anchorpane_regester1.setVisible(true);
            Notification.showAlert(Alert.AlertType.ERROR, "Invalid registration information", "Please enter valid information!");
        } else if (lastname_value.isEmpty()) {
            last_name.setStyle("-fx-border-color: red;");
            anchorpane_regester3.setVisible(false);
            anchorpane_regester1.setVisible(true);
            Notification.showAlert(Alert.AlertType.ERROR, "Invalid registration information", "Please enter valid information!");
        } else if (!RegisterManager.dayOfBirthValidator(day_of_birth_value)) {
            day_of_birth.setStyle("-fx-border-color: red;");
            anchorpane_regester3.setVisible(false);
            anchorpane_regester1.setVisible(true);
            Notification.showAlert(Alert.AlertType.ERROR, "Invalid registration information", "Please enter valid information!");
        } else if (gender_value == null) {
            gender.setStyle("-fx-border-color: red;");
            anchorpane_regester3.setVisible(false);
            anchorpane_regester1.setVisible(true);
            Notification.showAlert(Alert.AlertType.ERROR, "Invalid registration information", "Please enter valid information!");
        } else if (!RegisterManager.emailValidator(email_value)) {
            email.setStyle("-fx-border-color: red;");
            anchorpane_regester3.setVisible(false);
            anchorpane_regester2.setVisible(true);
            Notification.showAlert(Alert.AlertType.ERROR, "Invalid registration information", "Please enter valid information!");
        } else if (!RegisterManager.phonenumberValidator(phonenumber_value)) {
            phonenumber.setStyle("-fx-border-color: red;");
            anchorpane_regester3.setVisible(false);
            anchorpane_regester2.setVisible(true);
            Notification.showAlert(Alert.AlertType.ERROR, "Invalid registration information", "Please enter valid information!");
        } else if (!RegisterManager.usernameValidator(username_regester_value)) {
            username_regester.setStyle("-fx-border-color: red;");
            Notification.showAlert(Alert.AlertType.ERROR, "Invalid registration information", "Please enter valid information!");
        } else if (!RegisterManager.passwordValidator(password_regester_value)) {
            hbox_password_regester1.setStyle("-fx-border-color: red;");
            Notification.showAlert(Alert.AlertType.ERROR, "Invalid registration information", "Please enter valid information!");
        } else if (!RegisterManager.confirmPassword(password_regester_value, password_regester2_value)) {
            hbox_password_regester2.setStyle("-fx-border-color: red;");
            Notification.showAlert(Alert.AlertType.ERROR, "Invalid registration information", "Please enter valid information!");
        } else if (!RegisterManager.emailExists(email_value)) {
            email.setStyle("-fx-border-color: red;");
            anchorpane_regester3.setVisible(false);
            anchorpane_regester2.setVisible(true);
            Notification.showAlert(Alert.AlertType.ERROR, "Invalid registration information", "Please enter valid information!");
        } else {
            RegesterObject ro = new RegesterObject(firstname_value, lastname_value, Time.timeConversion(day_of_birth_value), gender_value, email_value, phonenumber_value, username_regester_value, password_regester_value) ;
            gs.regester_first(so, ro);
            Platform.runLater(() -> {
                try {
                    URL url = getClass().getResource("/com/gui/projectmanagement/view/ConfirmCode.fxml");
                    FXMLLoader loader = new FXMLLoader(url);
                    Parent root = loader.load();
                    ConfirmCodeController ccc = loader.getController();
                    ccc.loadStreams(so);
                    ccc.loadRegesterObject(ro);
                    ccc.loadLoginController(this);
                    Scene scene = new Scene(root,290,380);
                    scene.setFill(Color.TRANSPARENT);
                    Stage new_window = new Stage() ;

                    root.setOnMousePressed((MouseEvent mouseEvent) -> {
                        xOffset = mouseEvent.getSceneX();
                        yOffset = mouseEvent.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                        new_window.setX(mouseEvent.getScreenX() - xOffset);
                        new_window.setY(mouseEvent.getScreenY() - yOffset);
                    });

                    new_window.initStyle(StageStyle.TRANSPARENT);
                    new_window.initModality(Modality.APPLICATION_MODAL);
                    new_window.setScene(scene);
                    new_window.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    @FXML
    public void close(ActionEvent event) {
        Stage present_window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        present_window.close();
    }

    @FXML
    public void minimize(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setIconified(true);
    }

    public void clear() {
        first_name.setText("");
        last_name.setText("");
        day_of_birth.setValue(null);
        gender.setValue(null);
        email.setText("");
        phonenumber.setText("");
        username_regester.setText("");
        password_regester1.setText("");
        password_regester2.setText("");
        password_show_regester1.setText("");
        password_show_regester2.setText("");
    }

    public void regesterSuccess() {
        this.anchorpane_regester3.setVisible(false);
        this.anchorpane_login.setVisible(true);
    }

    public void windowSwitch(ClientData client_data, StreamObject so) {
        List<ProjectPreview> projects = ps.getProjects(so, client_data.getId()) ;
        List<ContactObject> contacts = sf.getContacts(so, client_data.getId()) ;
        List<ContactObject> interactors = sf.getInteractor(so, client_data.getId());
        List<RequestAddContact> requests = sf.getRequest(so, client_data.getId()) ;
        List<FeedBackObject> feebacks = sf.getFeedBacks(so, client_data.getId()) ;
        Stage present_window = (Stage) anchorpane_login.getScene().getWindow() ;
        Processing pss = new Processing(so) ;
        pss.openThread();
        openInterfaceClient(present_window, so, client_data, projects, contacts, interactors, requests, feebacks, pss);
    }

    public void openInterfaceClient(Stage login_window, StreamObject so, ClientData client_data, List<ProjectPreview> projects, List<ContactObject> contacts, List<ContactObject> interactors, List<RequestAddContact> requests, List<FeedBackObject> feebacks, Processing pss) {
        Platform.runLater(() -> {
            login_window.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/InterfaceClient.fxml"));
                Parent root = loader.load() ;
                InterfaceClientController icc = loader.getController() ;
                icc.loadStreams(so);
                icc.loadAccess(client_data);
                icc.loadData(projects);
                icc.loadContacts(contacts);
                icc.loadInteractors(interactors);
                icc.loadRequests(requests);
                icc.loadFeedBack(feebacks);
                icc.loadProcessing(pss);
                Stage new_window = new Stage() ;
                new_window.initStyle(StageStyle.TRANSPARENT);

                root.setOnMousePressed((MouseEvent mouseEvent) -> {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                    new_window.setX(mouseEvent.getScreenX() - xOffset);
                    new_window.setY(mouseEvent.getScreenY() - yOffset);
                });


                Scene scene = new Scene(root, 1100, 600) ;
                scene.setFill(Color.TRANSPARENT);
                new_window.setScene(scene);

                new_window.getIcons().add(icon_app) ;
                new_window.setTitle("Task Pad");

                new_window.show() ;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void error(String message) {
        URL url = LoginController.class.getResource("/com/gui/projectmanagement/view/AlertError.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        try {
            Parent root = loader.load();

            AlertErrorController aec = loader.getController() ;
            aec.load(message);

            Scene scene = new Scene(root, 350, 190);
            scene.setFill(Color.TRANSPARENT);
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.initStyle(StageStyle.TRANSPARENT);

            root.setOnMousePressed((MouseEvent mouseEvent) -> {
                xOffset = mouseEvent.getSceneX();
                yOffset = mouseEvent.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                primaryStage.setY(mouseEvent.getScreenY() - yOffset);
            });

            primaryStage.initModality(Modality.APPLICATION_MODAL);

            primaryStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void alert(String message) {
        Platform.runLater(() -> {
            URL url = LoginController.class.getResource("/com/gui/projectmanagement/view/Alert.fxml");
            FXMLLoader loader = new FXMLLoader(url);
            try {
                Parent root = loader.load();

                AlertController ac = loader.getController() ;
                ac.load(message);
                Scene scene = new Scene(root, 350, 190);
                scene.setFill(Color.TRANSPARENT);
                Stage primaryStage = new Stage();
                primaryStage.setScene(scene);
                primaryStage.setResizable(false);
                primaryStage.initStyle(StageStyle.TRANSPARENT);

                root.setOnMousePressed((MouseEvent mouseEvent) -> {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                    primaryStage.setX(mouseEvent.getScreenX() - xOffset);
                    primaryStage.setY(mouseEvent.getScreenY() - yOffset);
                });

                primaryStage.showAndWait();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static String getMd5(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadHost(String address, int port) {
        Client c = new Client() ;
        so = c.start(address, port) ;
    }
}

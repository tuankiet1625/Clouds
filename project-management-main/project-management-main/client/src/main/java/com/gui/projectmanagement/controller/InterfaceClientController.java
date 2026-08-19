package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.cache.Interactors;
import com.gui.projectmanagement.cache.Messages;
import com.gui.projectmanagement.entity.*;
import com.gui.projectmanagement.functions.FileFunction;
import com.gui.projectmanagement.functions.Time;
import com.gui.projectmanagement.functions.Weather;
import com.gui.projectmanagement.network.*;
import com.gui.projectmanagement.ui.InterfaceClientUI;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

public class InterfaceClientController implements Initializable, Network, Data, Access {

    @FXML
    AnchorPane anchorpane_main ;

    @FXML
    AnchorPane anchorpane_add_project ;

    @FXML
    private AnchorPane a_client_profile ;

    @FXML
    private AnchorPane a_inforclient  ;

    @FXML
    private AnchorPane a_contact  ;

    @FXML
    private AnchorPane a_change ;

    // Main

    @FXML
    private Button btn_user ;

    @FXML
    private Button btn_close_acontact ;

    @FXML
    private Button btn_setting ;

    @FXML
    private Button messenger ;

    @FXML
    private Button project ;

    @FXML
    private Button notification ;

    @FXML
    private Rectangle avata_user ;

    @FXML
    private AnchorPane a_project ;

    @FXML
    private AnchorPane a_messenger ;

    @FXML
    private AnchorPane a_notification ;

    @FXML
    private DatePicker day_of_birth;

    @FXML
    private TextField email;

    @FXML
    private TextField fullname;

    @FXML
    private TextField password;

    @FXML
    private TextField phonenumber;

    @FXML
    private TextField username;

    // Create Project
    @FXML
    private TextField name_project ;

    @FXML
    private TextArea describe_project ;

    @FXML
    private DatePicker start_day ;

    @FXML
    private DatePicker expected_end ;

    @FXML
    private TextField budget_project ;

    // Project

    @FXML
    private ListView<ProjectPreview> list_project ;

    @FXML
    private TextField tf_search ;

    @FXML
    private Label total ;

    @FXML
    private Label in_progress ;

    @FXML
    private Label cancelled ;

    @FXML
    private Label pause ;

    @FXML
    private Label complete ;

    @FXML
    private Label on_hold ;

    @FXML
    private Label delay ;

    @FXML
    private Button btn_total ;

    @FXML
    private Button btn_in_progress ;

    @FXML
    private Button btn_cancelled ;

    @FXML
    private Button btn_pause ;

    @FXML
    private Button btn_complete ;

    @FXML
    private Button btn_on_hold ;

    @FXML
    private Button btn_delay ;

    @FXML
    private HBox hbox_total ;

    @FXML
    private HBox hbox_in_progress ;

    @FXML
    private HBox hbox_cancelled ;

    @FXML
    private HBox hbox_pause ;

    @FXML
    private HBox hbox_complete ;

    @FXML
    private HBox hbox_on_hold ;

    @FXML
    private HBox hbox_delay ;

    @FXML
    private TextField email_search ;

    @FXML
    private ListView<ContactObject> list_interactor ;

    // Message

    @FXML
    public ListView<Message> list_message ;

    @FXML
    private TextField input_message ;

    @FXML
    private Rectangle interactor_avata ;

    @FXML
    private Label interactor_name ;

    @FXML
    private Button openFile ;

    @FXML
    private Button openImage ;

    @FXML
    private Button like ;

    @FXML
    private Label file_name_sd_f ;

    @FXML
    private Label file_size_sd_f ;

    @FXML
    private AnchorPane a_display_file ;

    @FXML
    private Button cancel ;

    @FXML
    private Button send ;

    @FXML
    private Rectangle avata_detail ;

    @FXML
    private Label name_inteact_detail ;

    @FXML
    private Label relationship_detail ;

    @FXML
    private TextField search_interactor ;

    @FXML
    private AnchorPane a_download ;

    /* Emojis */

    @FXML
    private AnchorPane a_emojis ;

    @FXML
    private Button button_emjs ;

    @FXML
    private Button btn_like ;

    @FXML
    private Button btn_happy ;

    @FXML
    private Button btn_love ;

    @FXML
    private Button btn_sad ;

    @FXML
    private Button btn_smile ;

    @FXML
    private Button btn_confused ;

    // Client Information

    @FXML
    private AnchorPane a_basic_information ;

    @FXML
    private AnchorPane a_contact_information ;

    @FXML
    private AnchorPane a_login_information ;

    @FXML
    private AnchorPane a_clientinfor2 ;

    @FXML
    private Rectangle avata ;

    @FXML
    private Label full_name ;

    @FXML
    private Label dob ;

    @FXML
    private Label gender ;

    @FXML
    private Label username_if ;

    @FXML
    private Label password_if ;

    @FXML
    private Label email_if ;

    @FXML
    private Label phonenumber_if ;

    @FXML
    private Button basic_information ;

    @FXML
    private Button contact_information ;

    @FXML
    private Button login_information ;

    @FXML
    private Button change_fullname ;

    @FXML
    private Button change_dob ;

    @FXML
    private Button change_gender ;

    @FXML
    private Button change_avata ;

    @FXML
    private Button btn_save ;

    @FXML
    private Button btn_cancel ;

    @FXML
    private Label name_pp ;

    @FXML
    private TextField input_tf ;

    @FXML
    private DatePicker day_of_birth_input ;

    @FXML
    private ComboBox<String> gender_input ;

    @FXML
    private Button btn_apply ;

    @FXML
    private Button btn_cancel_outside ;

    // Contact

    @FXML
    private ListView list_contact ;

    @FXML
    private TextField tf_search_contact_on_list ;

    @FXML
    private AnchorPane a_search ;

    @FXML
    private Button contact_list ;

    @FXML
    private Button invitation ;

    @FXML
    private Button find ;
    // Profile

    @FXML
    private Rectangle avata_profile ;

    @FXML
    private Label client_name_search ;

    @FXML
    private Label client_gender_search ;

    // Notification

    @FXML
    private ListView list_request ;

    //

    @FXML
    private Button addFriend ;

    @FXML
    private Button request_1 ;

    @FXML
    private Button feedback ;

    @FXML
    private ListView list_feedback ;

    @FXML
    private AnchorPane a_feedback ;

    @FXML
    private Label project_name ;

    @FXML
    private Label task_name ;

    @FXML
    private Label responder ;

    @FXML
    private TextArea feedback_data ;

    @FXML
    private Label response_date ;

    // Weather
    @FXML
    private Label city ;

    @FXML
    private Label temperature ;

    @FXML
    private Rectangle icon_img ;

    // Delete Project

    @FXML
    private AnchorPane a_confirm_delete_project ;

    @FXML
    private AnchorPane a_delete_first ;

    @FXML
    private Label notification_confirm ;

    @FXML
    private AnchorPane a_delete_last ;

    @FXML
    private Label notification_send_code ;

    @FXML
    private TextField input_code ;

    public StreamObject so ;

    public ClientData client_data ;

    ClientStream sf = new ClientStream() ;

    InterfaceClientUI icu = new InterfaceClientUI(this) ;

    List<ProjectPreview> projects = null ;

    List<ContactObject> contacts = null ;

    // Data

    Interactors interactors = new Interactors() ;

    Messages mse = new Messages() ;

    //

    ContactObject interactor = null ;

    Processing pss = null ;

    String imaPart = "/com/gui/projectmanagement/images/User5.png" ;
    Image user_img = new Image(getClass().getResourceAsStream(imaPart));
    File file = null ;

    File img_file = null ;

    // Emoji
    String emj_Confused = "/com/gui/projectmanagement/images/Emoji/Confused.png" ;

    String emj_Happy = "/com/gui/projectmanagement/images/Emoji/Happy.png" ;

    String emj_Love = "/com/gui/projectmanagement/images/Emoji/Love.png" ;

    String emj_Sad = "/com/gui/projectmanagement/images/Emoji/Sad.png" ;

    String emj_Smile = "/com/gui/projectmanagement/images/Emoji/Smile.png" ;

    String emj_Like = "/com/gui/projectmanagement/images/Emoji/Like.png" ;

    Map<String, String> emojis = new HashMap<>() ;

    boolean is_open_emoji = false ;

    ContactObject client_search = null ;

    List<RequestAddContact> request = null ;

    List<FeedBackObject> feedbacks = null ;

    // Change information

    String fullname_tmp = "";

    LocalDate dob_tmp = null ;

    String gender_tmp = "" ;

    byte[] avata_tmp = null ;

    String pp_interact = "" ;

    private double xOffset = 0;
    private double yOffset = 0;

    private String[] gender_array = {"Male", "Female", "Other"} ;
    private ObservableList<String> gender_obs = FXCollections.observableArrayList(gender_array);

    BasicInformation bi = null ;
    BasicInformation bi_tmp = null ;

    String imaPart2 = "/com/gui/projectmanagement/images/Project/Folder.png" ;

    Image icon_folder = new Image(getClass().getResourceAsStream(imaPart2));

    DataService ds = new DataService() ;

    ProjectStream ps = new ProjectStream() ;

    String project_delete = "";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WeatherObject wo = Weather.getWeatherObject();
        this.city.setText(wo.getCity());
        this.temperature.setText(String.format("%.1f", wo.getTemperature()) + " °C");
        ImagePattern ip = new ImagePattern(wo.getIcon_img());
        icon_img.setFill(ip);

        emojis.putAll(Map.of(
                "Confused", emj_Confused,
                "Happy", emj_Happy,
                "Love", emj_Love,
                "Sad", emj_Sad,
                "Smile", emj_Smile,
                "Like", emj_Like
        ));

        button_emjs.setOnAction(event -> {
            if (!is_open_emoji) {
                a_emojis.setVisible(true);
                is_open_emoji = true ;
            } else {
                a_emojis.setVisible(false);
                is_open_emoji = false ;
            }
        });

        btn_like.setOnAction(event -> {
            if (interactor != null) {
                try {
                    File file = new File(getClass().getResource(emojis.get("Like")).toURI()) ;
                    byte[] img_byte = FileFunction.fileToByte(file) ;
                    ImageSend is = new ImageSend(img_byte, img_byte.length) ;
                    MessageSend ms = new MessageSend(client_data.getId(), interactor.getId(), "Image", null, is, null) ;
                    sf.sendMessage(so, ms);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btn_happy.setOnAction(event -> {
            if (interactor != null) {
                try {
                    File file = new File(getClass().getResource(emojis.get("Happy")).toURI()) ;
                    byte[] img_byte = FileFunction.fileToByte(file);
                    ImageSend is = new ImageSend(img_byte, img_byte.length);
                    MessageSend ms = new MessageSend(client_data.getId(), interactor.getId(), "Image", null, is, null);
                    sf.sendMessage(so, ms);
                    a_emojis.setVisible(false);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btn_confused.setOnAction(event -> {
            if (interactor != null) {
                try {
                    File file = new File(getClass().getResource(emojis.get("Confused")).toURI()) ;
                    byte[] img_byte = FileFunction.fileToByte(file);
                    ImageSend is = new ImageSend(img_byte, img_byte.length);
                    MessageSend ms = new MessageSend(client_data.getId(), interactor.getId(), "Image", null, is, null);
                    sf.sendMessage(so, ms);
                    a_emojis.setVisible(false);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btn_sad.setOnAction(event -> {
            if (interactor != null) {
                try {
                    File file = new File(getClass().getResource(emojis.get("Sad")).toURI()) ;
                    byte[] img_byte = FileFunction.fileToByte(file);
                    ImageSend is = new ImageSend(img_byte, img_byte.length);
                    MessageSend ms = new MessageSend(client_data.getId(), interactor.getId(), "Image", null, is, null);
                    sf.sendMessage(so, ms);
                    a_emojis.setVisible(false);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btn_smile.setOnAction(event -> {
            if (interactor != null) {
                try {
                    File file = new File(getClass().getResource(emojis.get("Smile")).toURI()) ;
                    byte[] img_byte = FileFunction.fileToByte(file);
                    ImageSend is = new ImageSend(img_byte, img_byte.length);
                    MessageSend ms = new MessageSend(client_data.getId(), interactor.getId(), "Image", null, is, null);
                    sf.sendMessage(so, ms);
                    a_emojis.setVisible(false);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btn_love.setOnAction(event -> {
            if (interactor != null) {
                try {
                    File file = new File(getClass().getResource(emojis.get("Love")).toURI()) ;
                    byte[] img_byte = FileFunction.fileToByte(file);
                    ImageSend is = new ImageSend(img_byte, img_byte.length);
                    MessageSend ms = new MessageSend(client_data.getId(), interactor.getId(), "Image", null, is, null);
                    sf.sendMessage(so, ms);
                    a_emojis.setVisible(false);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        basic_information.setOnAction(event -> {
            basic_information.setStyle("-fx-background-color:  #dcdcdc;");
            contact_information.setStyle("-fx-background-color: transparent;");
            login_information.setStyle("-fx-background-color: transparent;");
            a_contact_information.setVisible(false);
            a_login_information.setVisible(false);
            a_basic_information.setVisible(true);
        });

        contact_information.setOnAction(event -> {
            contact_information.setStyle("-fx-background-color:  #dcdcdc;");
            basic_information.setStyle("-fx-background-color: transparent;");
            login_information.setStyle("-fx-background-color: transparent;");
            a_basic_information.setVisible(false);
            a_login_information.setVisible(false);
            a_contact_information.setVisible(true);
        });

        login_information.setOnAction(event -> {
            login_information.setStyle("-fx-background-color:  #dcdcdc;");
            basic_information.setStyle("-fx-background-color: transparent;");
            contact_information.setStyle("-fx-background-color: transparent;");
            a_basic_information.setVisible(false);
            a_contact_information.setVisible(false);
            a_login_information.setVisible(true);
        });

        btn_total.setOnAction(event -> {
            hbox_total.setStyle("-fx-background-color: #dcdcdc ; ");

            setStyleHbox(hbox_cancelled);
            setStyleHbox(hbox_complete);
            setStyleHbox(hbox_delay);
            setStyleHbox(hbox_pause);
            setStyleHbox(hbox_on_hold);
            setStyleHbox(hbox_in_progress);
            icu.displayListProject(list_project, projects);
        });

        btn_in_progress.setOnAction(event -> {
            hbox_in_progress.setStyle("-fx-background-color: #dcdcdc ; ");

            setStyleHbox(hbox_cancelled);
            setStyleHbox(hbox_complete);
            setStyleHbox(hbox_delay);
            setStyleHbox(hbox_pause);
            setStyleHbox(hbox_on_hold);
            setStyleHboxTop(hbox_total);
            List<ProjectPreview> projects_tmp = projectFilter(projects, "In Progress") ;
            icu.displayListProject(list_project, projects_tmp);
        });

        btn_cancelled.setOnAction(event -> {
            hbox_cancelled.setStyle("-fx-background-color: #dcdcdc ; ");

            setStyleHbox(hbox_total);
            setStyleHbox(hbox_complete);
            setStyleHbox(hbox_delay);
            setStyleHbox(hbox_pause);
            setStyleHbox(hbox_on_hold);
            setStyleHboxTop(hbox_in_progress);
            List<ProjectPreview> projects_tmp = projectFilter(projects, "Cancalled") ;
            icu.displayListProject(list_project, projects_tmp);
        });

        btn_complete.setOnAction(event -> {
            hbox_complete.setStyle("-fx-background-color: #dcdcdc ; ");

            setStyleHbox(hbox_cancelled);
            setStyleHbox(hbox_total);
            setStyleHbox(hbox_delay);
            setStyleHboxTop(hbox_pause);
            setStyleHbox(hbox_on_hold);
            setStyleHbox(hbox_in_progress);
            List<ProjectPreview> projects_tmp = projectFilter(projects, "Completed") ;
            icu.displayListProject(list_project, projects_tmp);
        });

        btn_on_hold.setOnAction(event -> {
            hbox_on_hold.setStyle("-fx-background-color: #dcdcdc ; ");

            setStyleHbox(hbox_cancelled);
            setStyleHboxTop(hbox_complete);
            setStyleHbox(hbox_delay);
            setStyleHbox(hbox_pause);
            setStyleHbox(hbox_total);
            setStyleHbox(hbox_in_progress);
            List<ProjectPreview> projects_tmp = projectFilter(projects, "On Hold") ;
            icu.displayListProject(list_project, projects_tmp);
        });

        btn_delay.setOnAction(event -> {
            hbox_delay.setStyle("-fx-background-color: #dcdcdc ; ");

            setStyleHbox(hbox_cancelled);
            setStyleHbox(hbox_complete);
            setStyleHbox(hbox_total);
            setStyleHbox(hbox_pause);
            setStyleHboxTop(hbox_on_hold);
            setStyleHbox(hbox_in_progress);
            List<ProjectPreview> projects_tmp = projectFilterDelay(projects) ;
            icu.displayListProject(list_project, projects_tmp);
        });

        btn_pause.setOnAction(event -> {
            hbox_pause.setStyle("-fx-background-color: #dcdcdc ; ");

            setStyleHboxTop(hbox_cancelled);
            setStyleHbox(hbox_complete);
            setStyleHbox(hbox_delay);
            setStyleHbox(hbox_total);
            setStyleHbox(hbox_on_hold);
            setStyleHbox(hbox_in_progress);
            List<ProjectPreview> projects_tmp = projectFilter(projects, "Pause") ;
            icu.displayListProject(list_project, projects_tmp);
        });

        /*

        hbox_total.setOnMouseEntered(e -> {
            hbox_total.setStyle("-fx-background-color: #f8f8ff ; ");
        }) ;

        hbox_in_progress.setOnMouseEntered(e -> {
            hbox_in_progress.setStyle("-fx-background-color: #f8f8ff ; ");
        }) ;

        hbox_cancelled.setOnMouseEntered(e -> {
            hbox_cancelled.setStyle("-fx-background-color: #f8f8ff ; ");
        }) ;

        hbox_complete.setOnMouseEntered(e -> {
            hbox_complete.setStyle("-fx-background-color: #f8f8ff ; ");
        }) ;

        hbox_on_hold.setOnMouseEntered(e -> {
            hbox_on_hold.setStyle("-fx-background-color: #f8f8ff ; ");
        }) ;

        hbox_delay.setOnMouseEntered(e -> {
            hbox_delay.setStyle("-fx-background-color: #f8f8ff ; ");
        }) ;

        hbox_pause.setOnMouseEntered(e -> {
            hbox_pause.setStyle("-fx-background-color: #f8f8ff ; ");
        }) ;

         */


        email_search.setOnAction(event -> {
            String client_email = email_search.getText() ;
            if (client_email != "") {
                sf.searchClient(so, client_email);
            }
        });

        list_project.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ProjectPreview pp = list_project.getSelectionModel().getSelectedItem();
                ps.sendRqProjectControl(so, pp.getId());
            }
        });

        list_interactor.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ContactObject interactor = list_interactor.getSelectionModel().getSelectedItem();
                this.interactor = interactor ;
                if (interactor != null) {
                    updateInteractor(interactor);
                    if (this.mse.containsId(interactor.getId())) {
                        icu.displayMessage(client_data, interactor, mse.getMessage(interactor.getId()), list_message, so, false);
                    } else {
                        sf.sendRqMessages(so, client_data.getId(), interactor.getId());
                    }
                }
            }
        });

        search_interactor.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String result = search_interactor.getText() ;
                if (result.equals("")) {
                    displayInteractors(interactors.getInteractors());
                } else {
                    List<ContactObject> interactor_list = new ArrayList<>() ;
                    for (ContactObject contact : interactors.getInteractors()) {
                        if (contact.getFullname().contains(result)) {
                            interactor_list.add(contact) ;
                        }
                    }
                    displayInteractors(interactor_list);
                }
            }
        });

        input_message.setOnAction(event -> {
            String message = input_message.getText() ;
            if (message != "") {
                MessageSend ms = new MessageSend(client_data.getId(), interactor.getId(), "Message", message, null, null) ;
                sf.sendMessage(so, ms) ;
                input_message.setText("");
            }
        });

        btn_setting.setOnMouseClicked(event -> {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem("Personal information");
            menuItem.setOnAction(e -> {
                a_project.setVisible(false);
                a_messenger.setVisible(false);
                a_inforclient.setVisible(true);
            });
            contextMenu.getItems().add(menuItem);

            contextMenu.show(btn_setting, event.getScreenX(), event.getScreenY());
        });

        tf_search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String input_search = tf_search.getText() ;
                if (input_search.equals("")) {
                    icu.displayListProject(list_project, projects);
                } else {
                    List<ProjectPreview> list_project_tmp = new ArrayList<>() ;
                    for (ProjectPreview pp : projects) {
                        if (pp.getName().contains(input_search)) {
                            list_project_tmp.add(pp) ;
                        }
                    }
                    icu.displayListProject(list_project, list_project_tmp);
                }
            }
        });

        avata_user.setOnMouseClicked(event -> {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem menuItem = new MenuItem("Contacts");
            menuItem.setOnAction(e -> {
                anchorpane_main.setDisable(true);
                a_contact.setVisible(true);

                if (contacts == null) {
                    // Send rq
                    sf.sendRqContacts(so, client_data.getId());
                }
            });
            contextMenu.getItems().add(menuItem);

            contextMenu.show(avata_user, event.getScreenX(), event.getScreenY());
        });

        btn_close_acontact.setOnAction(event -> {
            a_contact.setVisible(false);
            anchorpane_main.setDisable(false);
        });

        openFile.setOnAction(event -> {
            Stage stage_choose_image = (Stage) this.anchorpane_main.getScene().getWindow();
            FileChooser file_choose = new FileChooser();
            file_choose.setTitle("Choose File");
            File file = file_choose.showOpenDialog(stage_choose_image);
            if (file != null) {
                long byte_size_file = file.length();
                DecimalFormat df = new DecimalFormat("#.##");
                double kb_size_file = (double) byte_size_file / 1024 ;
                double mb_size_file = (double) kb_size_file / 1024 ;
                if (byte_size_file < 52428800){
                    if (byte_size_file < 500){
                        file_size_sd_f.setText(byte_size_file + " Bytes");
                    } else {
                        if (kb_size_file < 500){
                            file_size_sd_f.setText(Double.parseDouble(df.format(kb_size_file)) + " KB");
                        } else {
                            file_size_sd_f.setText(Double.parseDouble(df.format(mb_size_file)) + " MB");
                        }
                    }
                    file_size_sd_f.setStyle(null) ;
                    this.file = file ;
                    send.setDisable(false);
                } else {
                    file_size_sd_f.setText(Double.parseDouble(df.format(mb_size_file)) + " MB");
                    file_size_sd_f.setStyle("-fx-text-fill: red ;");
                    send.setDisable(true);
                }
                String name_file = file.getName() ;
                file_name_sd_f.setText(name_file);
                a_display_file.setVisible(true);
            }
        });

        cancel.setOnAction(event -> {
            this.file = null ;
            file_name_sd_f.setText("");
            file_size_sd_f.setText("");
            a_display_file.setVisible(false);
        });

        send.setOnAction(event -> {
            if (file != null && interactor != null) {
                FileSend fs = new FileSend(file.getName(), FileFunction.fileToByte(file), (int) file.length()) ;
                MessageSend ms = new MessageSend(client_data.getId(), interactor.getId(), "File", null, null, fs) ;
                sf.sendMessage(so, ms);
                a_display_file.setVisible(false);
                file = null ;
            }
        });

        openImage.setOnAction(event -> {
            if (interactor != null) {
                Stage stage_choose_image = (Stage) this.anchorpane_main.getScene().getWindow();
                FileChooser file_choose = new FileChooser();
                file_choose.setTitle("Choose Image");
                FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image File", "*.png" );
                file_choose.getExtensionFilters().add(imageFilter);
                File image_file = file_choose.showOpenDialog(stage_choose_image);
                if (image_file != null) {
                    Image image = new Image(image_file.toURI().toString());
                    if (!image.isError()) {
                        byte[] img_byte = FileFunction.fileToByte(image_file) ;
                        ImageSend is = new ImageSend(img_byte, img_byte.length) ;
                        MessageSend ms = new MessageSend(client_data.getId(), interactor.getId(), "Image", null, is, null) ;
                        sf.sendMessage(so, ms);
                    }
                } else {

                }
            }
        });

        contact_list.setOnAction(event -> {
            contact_list.setStyle("-fx-background-color:  #dcdcdc;" +
                    "-fx-background-radius: 3px;");
            invitation.setStyle("-fx-background-color: transparent;");
            find.setStyle("-fx-background-color: transparent;");

            a_search.setVisible(false);
            list_contact.setVisible(true);
        });

        invitation.setOnAction(event -> {
            invitation.setStyle("-fx-background-color:  #dcdcdc;" +
                    "-fx-background-radius: 3px;");
            contact_list.setStyle("-fx-background-color: transparent;");
            find.setStyle("-fx-background-color: transparent;");
        });

        find.setOnAction(event -> {
            find.setStyle("-fx-background-color:  #dcdcdc;" +
                    "-fx-background-radius: 3px;");
            contact_list.setStyle("-fx-background-color: transparent;");
            invitation.setStyle("-fx-background-color: transparent;");
            list_contact.setVisible(false);
            a_search.setVisible(true);
        });

        list_feedback.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                FeedBackObject fbo = (FeedBackObject) list_feedback.getSelectionModel().getSelectedItem();
                if (fbo != null) {
                    openFeedBack(fbo);
                }
            }
        });

        btn_cancel.setOnAction(event -> {
            this.a_change.setVisible(false);
            this.a_clientinfor2.setDisable(false);
            pp_interact = "" ;
        });

        btn_apply.setOnAction(event -> {
            if (pp_interact.equals("#fullname")) {
                String fn_vl = input_tf.getText() ;
                if (fn_vl != "") {
                    full_name.setText(fn_vl);
                    bi_tmp.setFullname(fn_vl);
                }
            } else if (pp_interact.equals("#dob")) {
                LocalDate dob_vl = day_of_birth_input.getValue();
                if (dob_vl != null) {
                    this.dob.setText(Time.timeConversion(dob_vl));
                    bi_tmp.setDay_of_birth(Time.timeConversion(dob_vl));
                }
            } else if (pp_interact.equals("#gender")) {
                String gender_vl = gender_input.getValue();
                if (gender_vl != null) {
                    gender.setText(gender_vl);
                    bi_tmp.setGender(gender_vl);
                }
            }
            this.input_tf.setText("");
            this.day_of_birth_input.setValue(null);
            this.gender_input.setValue(null);
            this.a_change.setVisible(false);
            this.a_clientinfor2.setDisable(false);
            pp_interact = "" ;
        });

        change_fullname.setOnAction(event -> {
            pp_interact = "#fullname" ;
            gender_input.setVisible(false);
            day_of_birth_input.setVisible(false);
            this.input_tf.setVisible(true);
            input_tf.setText(client_data.getFullname());
            this.a_clientinfor2.setDisable(true);
            this.name_pp.setText("Full Name");
            a_change.setVisible(true);
        });

        change_gender.setOnAction(event -> {
            this.btn_apply.setDisable(false);
            gender_input.setItems(gender_obs);
            pp_interact = "#gender" ;
            gender_input.setVisible(true);
            gender_input.setValue(client_data.getGender());
            day_of_birth_input.setVisible(false);
            input_tf.setVisible(false);
            this.a_clientinfor2.setDisable(true);
            this.name_pp.setText("Gender");
            a_change.setVisible(true);
        });

        change_dob.setOnAction(event -> {
            this.btn_apply.setDisable(false);
            pp_interact = "#dob" ;
            gender_input.setVisible(false);
            day_of_birth_input.setVisible(true);
            LocalDate dob_vl = Time.timeConversion(client_data.getDay_of_birth()) ;
            day_of_birth_input.setValue(dob_vl);
            input_tf.setVisible(false);
            this.a_clientinfor2.setDisable(true);
            this.name_pp.setText("Day of Birth");
            a_change.setVisible(true);
        });

        change_avata.setOnAction(event -> {
            Stage stage_choose_image = (Stage) this.anchorpane_main.getScene().getWindow();
            FileChooser file_choose = new FileChooser();
            file_choose.setTitle("Choose Image");
            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter("Image File", "*.png" );
            file_choose.getExtensionFilters().add(imageFilter);
            File image_file = file_choose.showOpenDialog(stage_choose_image);
            if (image_file != null) {
                Image image = new Image(image_file.toURI().toString());
                if (!image.isError()) {
                    byte[] img_byte = FileFunction.fileToByte(image_file) ;
                    bi_tmp.setAvata(img_byte);
                    setAvata(this.avata, image);
                }
            } else {

            }
        });

        input_tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String fullname_tmp = input_tf.getText() ;
                if (fullname_tmp.equals("") && pp_interact.equals("#fullname")) {
                    btn_apply.setDisable(true);
                } else if (!fullname_tmp.equals("") && pp_interact.equals("#fullname")){
                    btn_apply.setDisable(false);
                }
            }
        });

        btn_save.setOnAction(event -> {
            if (!bi_tmp.equals(bi)) {
                sf.changeBasicInformation(so, bi_tmp);
            }
        });

        btn_cancel_outside.setOnAction(event -> {
            if (bi_tmp != bi) {
                full_name.setText(client_data.getFullname());
                dob.setText(client_data.getDay_of_birth());
                gender.setText(client_data.getGender());
                if (client_data.getAvata() == null) {
                    setAvata(this.avata, user_img);
                    setAvata(this.avata_user, user_img);
                } else {
                    Image avt = com.gui.projectmanagement.functions.Image.byteToImage(client_data.getAvata()) ;
                    if (!avt.isError()) {
                        setAvata(this.avata, avt);
                        setAvata(this.avata_user, avt);
                    } else {
                        setAvata(this.avata, user_img);
                        setAvata(this.avata_user, user_img);
                    }
                }
            }
        });
    }

    public void updateBasicInformation() {
        this.bi = new BasicInformation(bi_tmp.getClient_id(), bi_tmp.getFullname(), bi_tmp.getAvata(), bi_tmp.getDay_of_birth(), bi_tmp.getGender()) ;
        this.client_data.setFullname(bi_tmp.getFullname());
        this.client_data.setAvata(bi_tmp.getAvata());
        this.client_data.setDay_of_birth(bi_tmp.getDay_of_birth());
        this.client_data.setGender(bi_tmp.getGender());

        Image avt = com.gui.projectmanagement.functions.Image.byteToImage(client_data.getAvata()) ;
        if (!avt.isError()) {
            setAvata(this.avata_user, avt);
        } else {
            setAvata(this.avata_user, user_img);
        }
    }

    public void updateInteractor(List<ContactObject> interactors) {
        this.interactors.constructorInteractors(interactors);
        displayInteractors(interactors);
    }

    public void displayInteractors(List<ContactObject> interactors) {
        Platform.runLater(() -> {
            ObservableList<ContactObject> interactors_obs = FXCollections.observableArrayList(interactors);
            list_interactor.setItems(interactors_obs);
            list_interactor.setCellFactory(param -> new ListCell<ContactObject>() {

                @Override
                protected void updateItem(ContactObject interactor, boolean empty) {
                    super.updateItem(interactor, empty);
                    if (empty || interactor == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/InteractorItem.fxml"));
                            AnchorPane anchorpane = loader.load();
                            anchorpane.getStyleClass().add("anchorpane_interactor");
                            InteractorItemController icc = loader.getController() ;
                            icc.loadData(interactor);
                            setGraphic(anchorpane);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        });
    }

    @FXML
    public void openMessenger (ActionEvent event) {
        a_inforclient.setVisible(false);
        a_project.setVisible(false);
        a_messenger.setVisible(true);
        messenger.setStyle("-fx-border-color:  transparent transparent #808080 transparent ;" +
                            "-fx-border-width: 3px ;");
        project.setStyle(null);
    }

    public void updateMessage(Message message) {
        if (interactors.contain(message.getReceiver_id())) { // Receive_id : Người mới gửi
            interactors.moveInteractor(message.getReceiver_id());
            if (this.mse.containsId(message.getReceiver_id())) {
                this.mse.updateMessages(message.getReceiver_id(), message);
            }
        } else {
            if (interactor.getId().equals(message.getReceiver_id())) {
                interactors.add(interactor);
                displayInteractors(interactors.getInteractors());
                if (this.mse.containsId(interactor.getId())) {
                    this.mse.updateMessages(interactor.getId(), message);
                } else {
                    List<Message> l_ms = new ArrayList<>() ;
                    l_ms.add(message) ;
                    this.mse.putDataMessage(message.getReceiver_id(), l_ms);
                }
            }
        }
        updateInteractor(interactors.getInteractors());
        icu.displayMessage(client_data, interactor, this.mse.getMessage(interactor.getId()), list_message, so, false);
    }

    public void displayMessage(List<Message> messages) {
        this.mse.putDataMessage(interactor.getId(), messages);
        icu.displayMessage(client_data, interactor, this.mse.getMessage(interactor.getId()), list_message, so, false);
    }

    public void receiveMessage(Message message) {
        if (interactors.contain(message.getSender_id())) {
            interactors.moveInteractor(message.getSender_id());
            if (this.mse.containsId(message.getSender_id())) {
                this.mse.updateMessages(message.getSender_id(), message);
                if (interactor.equals(message.getSender_id())) {
                    displayMessage(this.mse.getMessage(message.getSender_id()));
                }
            }
        } else {
            ContactObject interactor = findContact(message.getSender_id(), contacts) ;
            if (interactor != null) {
                interactors.add(interactor);
            } else {
                sf.sendRqGetInteractor(so, message.getSender_id());
            }
        }

        displayInteractors(interactors.getInteractors());

        if (interactor != null) {
            if (interactor.getId().equals(message.getSender_id())) {
                displayMessage(this.mse.getMessage(interactor.getId()));
            }
        }
    }

    @FXML
    private ProgressBar progress_dowloading ;

    @FXML
    private Label result_download ;

    public void downloadFile(FileObject fo) {
        Platform.runLater(() -> {
            String description = getFileExtension(fo.getName_file()).toUpperCase() + " Files";
            String extensions = "*." + getFileExtension(fo.getName_file()) ;
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(description, extensions);
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName(fo.getName_file());
            File file = fileChooser.showSaveDialog(null);
            if (file != null ){
                a_download.setVisible(true);
                progress_dowloading.setProgress(0);
                result_download.setText("Downloading " + "'" + fo.getName_file() + "'" + " ...");

                Task<Void> saveTask = new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            for (int i = 0; i < fo.getFile_data().length; i++) {
                                fos.write(fo.getFile_data()[i]);
                                double progress = (double) (i + 1) / fo.getFile_data().length;
                                updateProgress(progress, 1);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                            // ERROR
                        }
                        return null;
                    }
                };

                progress_dowloading.progressProperty().bind(saveTask.progressProperty());

                saveTask.setOnSucceeded(e -> {
                    Platform.runLater(() -> {
                        System.out.println("File '" + fo.getName_file() + "' saved successfully.");
                        a_download.setVisible(false);
                        progress_dowloading.progressProperty().unbind();
                    });
                });

                saveTask.setOnFailed(e -> {
                    Platform.runLater(() -> {
                        a_download.setVisible(false);
                        error("Error,An error occurred while saving file '" + fo.getName_file() + "'.");
                        progress_dowloading.progressProperty().unbind();
                    });
                });

                Thread thread = new Thread(saveTask);
                thread.start();
            }
        });
    }

    public String getFileExtension(String fileName) {
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    public void updateListProject(ProjectObject po) {
        ClientPreview creator = new ClientPreview(client_data.getId(), client_data.getFullname()) ;
        ClientPreview manager = new ClientPreview(client_data.getId(), client_data.getFullname()) ;
        ProjectPreview pp = new ProjectPreview("Manager", po.getId(), po.getName(), po.getStart_date(), po.getEnd_date(), po.getStatus(), po.getBudget(), manager, creator, 1, 0) ;
        this.projects.add(0, pp) ;
        icu.displayListProject(list_project, this.projects);
        updateProjects(this.projects);
    }

    public void displayContacts(List<ContactObject> contacts) {
        this.contacts = contacts ;
        Platform.runLater(() -> {
            ObservableList<ContactObject> contacts_obs = FXCollections.observableArrayList(contacts);
            list_contact.setItems(contacts_obs);
            list_contact.setCellFactory(param -> new ListCell<ContactObject>() {

                @Override
                protected void updateItem(ContactObject contact, boolean empty) {
                    super.updateItem(contact, empty);
                    if (empty || contact == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/ContactItem.fxml"));
                            AnchorPane anchorpane = loader.load();
                            anchorpane.getStyleClass().add("contact_item");
                            ContactItemController cic = loader.getController() ;
                            cic.loadContact(contact);
                            cic.loadICC(InterfaceClientController.this);
                            setGraphic(anchorpane);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        });
    }

    public void displayProfileClient(ContactObject client) {
        if (client != null) {
            Platform.runLater(() -> {
                if (isContact(contacts, client)) {
                    addFriend.setDisable(true);
                    addFriend.setText("Contact");
                } else {
                    addFriend.setDisable(false);
                    addFriend.setText("Add Friend");
                }
                System.out.println(client.getFullname());
                this.client_search = client ;
                a_client_profile.setVisible(true);
                client_name_search.setText(client.getFullname());
                client_gender_search.setText(client.getGender());
                if (client.getAvata() == null) {
                    setAvata(avata_profile, user_img);
                } else {
                    Image avata = com.gui.projectmanagement.functions.Image.byteToImage(client.getAvata()) ;
                    if (!avata.isError()) {
                        ImagePattern ip = new ImagePattern(avata) ;
                        avata_profile.setFill(ip);
                    }
                }
            });
        } else {
            error("This person does not exist!");
        }
    }

    public void setAvata(Rectangle rectangle, Image image) {
        ImagePattern pattern = new ImagePattern(image);
        rectangle.setFill(pattern);
    }

    public void updateInteractor(ContactObject interactor) {
        Platform.runLater(() -> {
            interactor_name.setText(interactor.getFullname());
            name_inteact_detail.setText(interactor.getFullname());
            if (isContact(contacts, interactor)) {
                relationship_detail.setText("Contact");
            } else {
                relationship_detail.setText("Stranger");
            }

            if (interactor.getAvata() == null) {
                setAvata(interactor_avata, user_img);
                setAvata(avata_detail, user_img);
            } else {
                Image avata = com.gui.projectmanagement.functions.Image.byteToImage(interactor.getAvata()) ;
                if (!avata.isError()) {
                    ImagePattern ip = new ImagePattern(avata) ;
                    interactor_avata.setFill(ip);
                    avata_detail.setFill(ip);
                }
            }
        });
    }

    public void newInteractor(ContactObject new_intt) {
        this.interactors.add(new_intt);
        displayInteractors(this.interactors.getInteractors());
    }

    public boolean isContact(List<ContactObject> contacts, ContactObject client) {
        for (ContactObject contact : contacts) {
            if (contact.getId().equals(client.getId())) {
                return true ;
            }
        }
        return false ;
    }

    @FXML
    public void openProject (ActionEvent event) {
        a_inforclient.setVisible(false);
        a_messenger.setVisible(false);
        a_project.setVisible(true);
        project.setStyle("-fx-border-color:  transparent transparent #808080 transparent ;" +
                "-fx-border-width: 3px ;");
        messenger.setStyle(null);
    }

    public void openProjectView(List<ClientObject> members, List<TaskPreview> tasks, String project_id) {
        ProjectControl pc = null ;
        for (ProjectPreview pp : projects) {
            if (pp.getId().equals(project_id)) {
                pc = new ProjectControl(pp, tasks, members) ;
            }
        }

        ProjectControl finalPc = pc;

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/ProjectView.fxml"));
                Parent root = loader.load() ;
                ProjectViewController pvc = loader.getController() ;
                pvc.loadStreams(so);
                pvc.loadAccess(client_data);
                pvc.loadData(finalPc);
                pvc.loadProcessing(pss);
                if (contacts == null) {
                    System.out.println("aaaa");
                }
                pvc.loadContacts(contacts);
                // pss.loadInterfaceProject(pvc);
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

                Scene scene = new Scene(root, 1000, 580) ;
                scene.setFill(Color.TRANSPARENT);
                new_window.setScene(scene);

                new_window.getIcons().add(icon_folder) ;

                new_window.show() ;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    boolean is_open_notification = false ;

    @FXML
    public void openNotification (ActionEvent event) {
        if (!is_open_notification) {
            if (request == null) {
                sf.sendRqRquestAddContact(so, client_data.getId()) ;
            }

            if (feedbacks == null) {
                ps.sendRequestFeedBacks(so, client_data.getId()) ;
            }

            a_notification.setVisible(true) ;
            is_open_notification = true ;
        } else {
            // Close
            a_notification.setVisible(false);
            is_open_notification = false ;
        }
    }

    @FXML
    public void request(ActionEvent event) {
        request_1.setStyle("-fx-background-color: white");
        feedback.setStyle("-fx-background-color: transparent;");

        list_feedback.setVisible(false);
        list_request.setVisible(true);
    }

    @FXML
    public void feedback(ActionEvent event) {
        feedback.setStyle("-fx-background-color: white");
        request_1.setStyle("-fx-background-color: transparent;");

        list_request.setVisible(false);
        list_feedback.setVisible(true);
    }

    @FXML
    public void resetNotification(ActionEvent event) {
        sf.sendRqRquestAddContact(so, client_data.getId()) ;
        ps.sendRequestFeedBacks(so, client_data.getId()) ;
    }

    public void updateRequest(RequestAddContact request) {
        this.request.add(0, request) ;
        displayRequestAddContact(this.request);
    }

    public void contructorRequest(List<RequestAddContact> requests) {
        this.request = requests ;
        displayRequestAddContact(requests);
    }

    public void displayRequestAddContact(List<RequestAddContact> requests) {
        Platform.runLater(() -> {
            ObservableList<RequestAddContact> requests_obs = FXCollections.observableArrayList(request);
            list_request.setItems(requests_obs);
            list_request.setCellFactory(param -> new ListCell<RequestAddContact>() {

                @Override
                protected void updateItem(RequestAddContact request, boolean empty) {
                    super.updateItem(request, empty);
                    if (empty || request == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/NotificationFriendRequest.fxml"));
                            AnchorPane anchorpane = loader.load();
                            anchorpane.getStyleClass().add("item_request");
                            RequestFriendItem rfi = loader.getController() ;
                            rfi.loadData(request);
                            rfi.loadClientId(client_data.getId());
                            rfi.loadStreams(so);
                            setGraphic(anchorpane);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        });
    }

    public void constructorFeedBack(List<FeedBackObject> feebacks) {
        this.feedbacks = feebacks ;
        displayFeedBack(feedbacks);
    }

    public void updateFeedBack(FeedBackObject feedback) {
        this.feedbacks.add(0, feedback) ;
        displayFeedBack(this.feedbacks);
    }

    public void openFeedBack(FeedBackObject feeback) {
        this.anchorpane_main.setDisable(true);
        a_notification.setDisable(true);
        a_feedback.setVisible(true);

        Platform.runLater(() -> {
            if (feeback != null) {
                project_name.setText(feeback.getProject_name());
                task_name.setText(feeback.getTask_name());
                responder.setText(feeback.getSender_feedback().getFullname());
                feedback_data.setText(feeback.getFeedback());
                response_date.setText(feeback.getFeedback_date());
            } else {
                project_name.setText("");
                task_name.setText("");
                responder.setText("");
                feedback_data.setText("");
                response_date.setText("");
            }
        });
    }

    @FXML
    public void closeFeedBack(ActionEvent event) {
        a_feedback.setVisible(false);
        this.anchorpane_main.setDisable(false);
        a_notification.setDisable(false);
    }

    public void displayFeedBack(List<FeedBackObject> feedbacks) {
        Platform.runLater(() -> {
            ObservableList<FeedBackObject> feebacks_obs = FXCollections.observableArrayList(feedbacks);
            list_feedback.setItems(feebacks_obs);
            list_feedback.setCellFactory(param -> new ListCell<FeedBackObject>() {

                @Override
                protected void updateItem(FeedBackObject feedback, boolean empty) {
                    super.updateItem(feedback, empty);
                    if (empty || feedback == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/NotificationFeedBack.fxml"));
                            AnchorPane anchorpane = loader.load();
                            anchorpane.getStyleClass().add("item_feedback");
                            FeedBackItem fbi = loader.getController() ;
                            fbi.load(feedback);
                            setGraphic(anchorpane);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        });
    }

    public void updateContact(String newContact_id) {
        for (RequestAddContact rac : request) {
            if (rac.getContact_send_request().getId().equals(newContact_id)) {
                contacts.add(rac.getContact_send_request()) ;
                request.remove(rac) ;
                break ;
            }
        }
        displayContacts(contacts);

        // Update Notiofication
        displayRequestAddContact(request);
    }

    public void updateNewContact(ContactObject contact) {
        this.contacts.add(0, contact) ;
        displayContacts(this.contacts);
    }

    @FXML
    public void close(ActionEvent event) {
        sf.disconnectFirst(so) ;
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    public void minimize(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setIconified(true);
    }

    @FXML
    public void newProject(ActionEvent event) {
        icu.newProject(so, client_data, this);
    }

    @FXML
    public void closeAddProject(ActionEvent event) {
        anchorpane_add_project.setVisible(false);
        anchorpane_main.setDisable(false);
    }

    public void detaiProject(ProjectPreview pp) {
        ps.sendRqProjectControl(so, pp.getId());
    }

    @FXML
    public void addFriend(ActionEvent event) {
        if (client_search != null) {
            sf.sendRequestAddContact(so, client_data.getId(), client_search.getId());
            addFriend.setDisable(true);
        }
    }

    @FXML
    public void message(ActionEvent event) {
        if (client_search != null) {
            interactor = client_search ;
            updateMessage(interactor);
        }
    }

    public void deleteProject(ProjectPreview pp) {
        anchorpane_main.setDisable(true);
        a_confirm_delete_project.setVisible(true);
        notification_confirm.setText("Delete " + pp.getName() + "?");
        this.project_delete = pp.getId() ;
    }

    @FXML
    public void confirmDeletion(ActionEvent event) {
        a_delete_first.setVisible(false);
        a_delete_last.setVisible(true);

        ps.deleteProjectFirst(so) ;
    }

    @FXML
    public void finishDelete(ActionEvent event) {
        String code = input_code.getText() ;
        if (code != "") {
            ps.deleteProjectLast(so, code, project_delete);
        }
    }

    @FXML
    public void closeDelete(ActionEvent event) {
        this.input_code.setText("");
        this.a_delete_last.setVisible(false);
        this.a_delete_first.setVisible(true);
        a_confirm_delete_project.setVisible(false);
        anchorpane_main.setDisable(false);
    }

    public void deleteProjectSuccess(String project_id) {
        this.input_code.setText("");
        this.a_delete_last.setVisible(false);
        this.a_delete_first.setVisible(true);
        this.a_confirm_delete_project.setVisible(false);
        this.anchorpane_main.setDisable(false);
        for (ProjectPreview pp : projects) {
            if (pp.getId().equals(project_id)) {
                projects.remove(pp) ;
                break ;
            }
        }
        icu.displayListProject(list_project, projects);
    }

    public void updateMessage(ContactObject interactor) {
        messenger.setStyle("-fx-border-color:  transparent transparent #808080 transparent ;" +
                "-fx-border-width: 3px ;");
        project.setStyle(null);

        this.interactor = interactor ;

        if (interactors.contain(interactor.getId())) {
            if (mse.containsId(interactor.getId())) {
                icu.displayMessage(client_data, interactor, this.mse.getMessage(interactor.getId()), list_message, so, false);
            } else {
                sf.sendRqMessages(so, client_data.getId(), interactor.getId()) ;
            }
        } else {

        }

        updateInteractor(interactor);

        a_client_profile.setVisible(false);
        a_contact.setVisible(false);
        a_project.setVisible(false);
        a_inforclient.setVisible(false);
        a_messenger.setVisible(true);
        anchorpane_main.setDisable(false);
    }

    @FXML
    public void closeProfile(ActionEvent event) {
        a_client_profile.setVisible(false);
    }

    public void setStyleHbox(HBox hbox) {
        hbox.setStyle("-fx-background-color: transparent ;") ;
    }

    public void setStyleHboxTop(HBox hbox) {
        hbox.setStyle("-fx-background-color: transparent ;");
    }

    public int countProject(List<ProjectPreview> projects, String status) {
        int count = 0 ;
        for (ProjectPreview pp : projects) {
            if (status.equals("All")) {
                count ++ ;
            } else if (pp.getStatus().equals(status)) {
                count ++ ;
            }
        }
        return count ;
    }

    public int countDelay(List<ProjectPreview> projects) {
        int count = 0 ;
        for (ProjectPreview pp : projects) {
            LocalDate str_date = Time.timeConversion(pp.getStart_date()) ;
            LocalDate end_date = Time.timeConversion(pp.getEnd_date()) ;
            if (str_date.isAfter(end_date)) {
                count ++ ;
            }
        }
        return count ;
    }

    public List<ProjectPreview> projectFilter(List<ProjectPreview> projects, String status) {
        List<ProjectPreview> project_tmp = new ArrayList<>() ;
        for (ProjectPreview pp : projects) {
            if (pp.getStatus().equals(status)) {
                project_tmp.add(pp) ;
            }
        }
        return project_tmp ;
    }

    public List<ProjectPreview> projectFilterDelay(List<ProjectPreview> projects) {
        List<ProjectPreview> project_tmp = new ArrayList<>() ;
        for (ProjectPreview pp : projects) {
            LocalDate start_date = Time.timeConversion(pp.getStart_date()) ;
            LocalDate end_date = Time.timeConversion(pp.getEnd_date()) ;
            if (start_date.isAfter(end_date)) {
                project_tmp.add(pp) ;
            }
        }
        return project_tmp ;
    }

    public void error(String message) {
        Platform.runLater(() -> {
            URL url = InterfaceClientController.class.getResource("/com/gui/projectmanagement/view/AlertError.fxml");
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
        });
    }

    @Override
    public void loadStreams(StreamObject so) {
        this.so = so;
    }

    @Override
    public void loadProcessing(Processing pss) {
        this.pss = pss ;
        pss.loadInterfaceClient(this);
    }

    @Override
    public void loadAccess(ClientData client_data) {
        this.client_data = client_data ;
        bi = new BasicInformation(client_data.getId(), client_data.getFullname(), client_data.getAvata(), client_data.getDay_of_birth(), client_data.getGender()) ;
        bi_tmp = new BasicInformation(client_data.getId(), client_data.getFullname(), client_data.getAvata(), client_data.getDay_of_birth(), client_data.getGender()) ;
        // Load UI
        if (client_data != null) {
            full_name.setText(client_data.getFullname());
            dob.setText(client_data.getDay_of_birth());
            gender.setText(client_data.getGender());
            username_if.setText("...");
            password_if.setText("...");
            email_if.setText(client_data.getEmail());
            phonenumber_if.setText(client_data.getPhonenumber());
            if (client_data.getAvata() == null) {
                setAvata(this.avata, user_img);
                setAvata(this.avata_user, user_img);
            } else {
                Image avt = com.gui.projectmanagement.functions.Image.byteToImage(client_data.getAvata()) ;
                if (!avt.isError()) {
                    setAvata(this.avata, avt);
                    setAvata(this.avata_user, avt);
                } else {
                    setAvata(this.avata, user_img);
                    setAvata(this.avata_user, user_img);
                }
            }
        }

        // sf.sendRqContacts(so, client_data.getId());
    }

    @Override
    public void loadData(Object object) {
        List<ProjectPreview> projects = (List<ProjectPreview>) object ;
        this.projects = projects ;

        icu.displayListProject(list_project, projects);

        updateProjects(projects);
    }

    public void updateProjects(List<ProjectPreview> projects) {
        Platform.runLater(() -> {
            int toltal = countProject(projects, "All") ;
            int in_progress = countProject(projects, "In Progress") ;
            int cancelled = countProject(projects, "Cancelled") ;
            int pause = countProject(projects, "Pause") ;
            int complete = countProject(projects, "Complete") ;
            int on_hold = countProject(projects, "On Hold") ;
            int delay = countDelay(projects) ;

            this.total.setText(String.valueOf(toltal));
            this.in_progress.setText(String.valueOf(in_progress));
            this.cancelled.setText(String.valueOf(cancelled));
            this.pause.setText(String.valueOf(pause));
            this.on_hold.setText(String.valueOf(on_hold));
            this.complete.setText(String.valueOf(complete));
            this.delay.setText(String.valueOf(delay));

            hbox_total.setStyle("-fx-background-color: #dcdcdc ; " +
                    "-fx-background-radius: 3px ;" +
                    "-fx-border-color: transparent ;");

            setStyleHbox(hbox_cancelled);
            setStyleHbox(hbox_complete);
            setStyleHbox(hbox_delay);
            setStyleHbox(hbox_pause);
            setStyleHbox(hbox_on_hold);
            setStyleHbox(hbox_in_progress);
            icu.displayListProject(list_project, projects);
        });
    }

    public void loadContacts(List<ContactObject> contacts) {
        displayContacts(contacts);
    }

    public void loadInteractors (List<ContactObject> interactors) {
        this.interactors.constructorInteractors(interactors);
        displayInteractors(interactors);
    }

    public void loadRequests(List<RequestAddContact> requests) {
        this.request = requests ;
        displayRequestAddContact(requests);
    }

    public void loadFeedBack(List<FeedBackObject> feebacks) {
        this.feedbacks = feebacks ;
        displayFeedBack(feedbacks);
    }

    public void alert(String message) {
        Platform.runLater(() -> {
            URL url = InterfaceClientController.class.getResource("/com/gui/projectmanagement/view/Alert.fxml");
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

    public ContactObject findContact(String contact_id, List<ContactObject> contacts) {
        for (ContactObject contact : contacts) {
            if (contact.getId().equals(contact_id)) {
                return contact ;
            }
        }
        return null ;
    }

}

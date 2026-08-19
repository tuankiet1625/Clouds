package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.entity.*;
import com.gui.projectmanagement.network.ProjectStream;
import com.gui.projectmanagement.network.ClientStream;
import com.gui.projectmanagement.network.StreamObject;
import com.gui.projectmanagement.network.Processing;
import com.gui.projectmanagement.ui.ProjectViewUI;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;

public class ProjectViewController implements Initializable, Network, Access, Data {

    @FXML
    private AnchorPane a_main ;

    @FXML
    private AnchorPane a_file_detail ;

    @FXML
    private Button btn_close_file_detail ;

    @FXML
    private AnchorPane a_members ;

    @FXML
    private AnchorPane a_feedback ;

    @FXML
    private Button close_feedback ;

    @FXML
    private ListView<ClientObject> list_member ;

    @FXML
    private Label project_name ;

    @FXML
    private Label role ;

    @FXML
    private Label status ;

    @FXML
    private Label task_name ;

    @FXML
    private Label creator ;

    @FXML
    private Label responsibler ;

    @FXML
    private TextArea request ;

    @FXML
    private Label date_created ;

    @FXML
    private Label deadline ;

    @FXML
    private Button button_file ;

    @FXML
    private Label uploader ;

    @FXML
    private Label upload_time ;

    @FXML
    private Label file_name ;

    @FXML
    private Label size ;

    @FXML
    private Label extension ;

    @FXML
    private Button button_change_file ;

    @FXML
    private TreeView<TaskPreview> task_tree ;

    @FXML
    private Button feedback ;

    @FXML
    private Label name_project_fb ;

    @FXML
    private Label name_task_fb ;

    @FXML
    private Label receive_fb ;

    @FXML
    private TextArea feedback_fb ;

    @FXML
    private Button finish_fb ;

    @FXML
    private Button cancel_fb ;

    @FXML
    private Button btn_discard ;

    @FXML
    private AnchorPane a_dowload_product ;

    @FXML
    private ProgressBar progressbar_dowload ;

    @FXML
    private Label prb_lb ;

    @FXML
    private Button dowload ;

    private StreamObject so = null;

    private ClientData client_data = null;

    private ProjectControl pc = null;

    TaskObject task_interact = null ;

    ClientStream sf = new ClientStream() ;

    ProjectStream ps = new ProjectStream() ;

    ProjectViewUI pui = new ProjectViewUI() ;

    ProductPreview pp = null ;

    ProductTemp pt = null ;

    ProductTemp pt2 = null ;

    Processing pss ;

    TreeItem<TaskPreview> task_selected = null ;

    TreeItem<TaskPreview> parent = null ;

    List<ContactObject> contacts = null;

    private double xOffset = 0;
    private double yOffset = 0;

    String imaPart = "/com/gui/projectmanagement/images/AddMember/Members.png" ;

    Image icon_member = new Image(getClass().getResourceAsStream(imaPart));

    // List<ClientObject>

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem = new MenuItem("New Task");
        menuItem.setOnAction(event -> {
            parent = task_selected ;
            NewTaskControl ntc = new NewTaskControl(task_selected, pc.getMembers()) ;
            pui.newTask(client_data, so, ntc) ;
        });
        contextMenu.getItems().add(menuItem);

        task_tree.setOnMousePressed(event -> { // Click chuột phải
            if (event.getButton() == MouseButton.SECONDARY) {
                TreeItem<TaskPreview> selectedItem = task_tree.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    contextMenu.show(task_tree, event.getScreenX(), event.getScreenY());
                    task_selected = selectedItem ;
                }
            } else {
                contextMenu.hide();
            }
        });

        task_tree.setOnMouseClicked(event -> { // Click 2 lần
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                if (this.pt != null) {
                    Platform.runLater(() -> {
                        pui.openConfirm();

                        if (AlertConfirmController.result) {
                            this.pt = null ;
                            TreeItem<TaskPreview> selectedItem = task_tree.getSelectionModel().getSelectedItem() ;
                            task_selected = selectedItem ;
                            if (selectedItem != null) {
                                String task_id = selectedItem.getValue().getTask_id();
                                ps.sendRqTask(so, task_id);
                            }
                        }
                    });
                } else {
                    TreeItem<TaskPreview> selectedItem = task_tree.getSelectionModel().getSelectedItem() ;
                    task_selected = selectedItem ;
                    if (selectedItem != null) {
                        String task_id = selectedItem.getValue().getTask_id();
                        ps.sendRqTask(so, task_id);
                    }
                }
            }
        });

        feedback_fb.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String result = feedback_fb.getText() ;
                if (result.equals("")) {
                    finish_fb.setDisable(true);
                } else {
                    finish_fb.setDisable(false);
                }
            }
        });

        btn_discard.setOnAction(event -> {
           if (pt != null) {
               pt = null ;
               if (pp != null ) {
                   button_file.setText(pp.getFile_name());
                   uploader.setText("  " + findMember(pc.getMembers(), pp.getCreator()).getFullname());
                   upload_time.setText("  " + pp.getFinish_day());
               } else {
                   button_file.setText("Upload Files");
                   uploader.setText("");
                   upload_time.setText("");
               }
           }
        });

        dowload.setOnAction(event -> {
            if (pp != null) {
                ps.sendRqProductObject(so, pp.getProduct_id());
            }
            this.a_file_detail.setVisible(false);
            a_main.setDisable(false);
        });
    }

    public void changeTask(TaskObject task, ProductPreview pp){
        Platform.runLater(() -> {
            this.task_interact = task ;
            this.task_name.setText(task.getTask_name());
            String creator = task.getCreator() ;
            String responsibler = task.getUndertaker() ;
            ClientObject creator_object = findMember(pc.getMembers(), creator) ;
            ClientObject responsibler_object = findMember(pc.getMembers(), responsibler) ;
            this.creator.setText("  " + creator_object.getFullname());
            this.responsibler.setText("  " + responsibler_object.getFullname());
            this.request.setText("  " + task.getJob_requirements());
            this.date_created.setText("  " + task.getRequest_date());
            this.deadline.setText("  " + task.getDeadline());

            if (pp != null) {
                String file_size = convertFileSize(pp.getFile_size());
                this.size.setText(file_size);
                this.button_file.setText(pp.getFile_name());
                ClientObject uploader_object = findMember(pc.getMembers(), pp.getCreator());
                this.uploader.setText("  " + uploader_object.getFullname());
                this.upload_time.setText("  " + pp.getFinish_day());
                this.pp = pp;

                if (pp.getCreator().equals(client_data.getId())) {
                    this.feedback.setDisable(true);
                } else {
                    this.feedback.setDisable(false);
                }

            } else {
                this.pp = null ;
                this.button_file.setText("Upload Files");
                this.uploader.setText("  ") ;
                this.upload_time.setText("  ") ;
                this.feedback.setDisable(true);
            }
            this.pt = null ;
        });
    }

    public void buildTreeItem(TaskObject task_object) {
        TaskPreview tp = new TaskPreview(task_object.getTask_id(), task_object.getClassify(), task_object.getProject_id(), task_object.getContainer_id(), task_object.getTask_name()) ;
        TreeItem<TaskPreview> taskItem = new TreeItem<>(tp);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/TaskItem.fxml"));
        try {
            AnchorPane anchorpane = loader.load();
            TaskItemController tic = loader.getController() ;
            tic.loadNameTask(taskItem.getValue().getTask_name());
            taskItem.setGraphic(anchorpane);

            parent.getChildren().add(taskItem) ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void closeFileDetail(ActionEvent event) {
        this.a_file_detail.setVisible(false);
        this.a_main.setDisable(false);
    }

    @FXML
    public void uploadFile(ActionEvent event) {
        if (this.pp == null && this.pt == null) {
            Stage stage_choose_image = (Stage) this.project_name.getScene().getWindow();
            FileChooser file_choose = new FileChooser();
            file_choose.setTitle("Choose File");
            File file_tmp = file_choose.showOpenDialog(stage_choose_image);
            if (file_tmp != null) {
                String file_name = file_tmp.getName();
                long byte_size_file = file_tmp.length();
                int size = (int) byte_size_file;
                pt = new ProductTemp(client_data.getId(), file_tmp, file_name, size);
                this.file_name.setText(file_name);
                this.button_file.setText(file_name);
                double kb_size_file = (double) byte_size_file / 1024;
                double mb_size_file = (double) kb_size_file / 1024;
                DecimalFormat df = new DecimalFormat("#.##");
                if (byte_size_file < 52428800) {
                    if (byte_size_file < 500) {
                        this.size.setText(byte_size_file + " Bytes");
                    } else {
                        if (kb_size_file < 500) {
                            this.size.setText(Double.parseDouble(df.format(kb_size_file)) + " KB");
                        } else {
                            this.size.setText(Double.parseDouble(df.format(mb_size_file)) + " MB");
                        }
                    }
                    this.size.setStyle(null);
                } else {
                    this.size.setText(Double.parseDouble(df.format(mb_size_file)) + " MB");
                    this.size.setStyle("-fx-text-fill: red ;");
                }
                extension.setText(getFileExtension(file_name));
            }
            this.dowload.setDisable(true);
        } else if (pt != null) {
            this.a_main.setDisable(true);
            this.a_file_detail.setVisible(true);
            file_name.setText(pt.getFile_name());
            setFileSize(size, pt.getFile_size());
            extension.setText(getFileExtension(pt.getFile_name()));
            this.dowload.setDisable(true);

            if (pt.getFile_size() > 52428800) {
                this.size.setStyle("-fx-text-fill: red ;");
            } else {
                this.size.setStyle(null);
            }
        } else if (this.pp != null) {
            this.a_main.setDisable(true);
            this.a_file_detail.setVisible(true);
            file_name.setText(pp.getFile_name());
            setFileSize(size, pp.getFile_size());
            extension.setText(getFileExtension(pp.getFile_name()));
            this.dowload.setDisable(false);

            if (pp.getFile_size() > 52428800) {
                this.size.setStyle("-fx-text-fill: red ;");
            } else {
                this.size.setStyle(null);
            }
        }
    }

    @FXML
    public void changeFile(ActionEvent event) {
        Stage stage_choose_image = (Stage) this.project_name.getScene().getWindow();
        FileChooser file_choose = new FileChooser();
        file_choose.setTitle("Choose File");
        File file_tmp = file_choose.showOpenDialog(stage_choose_image);
        if (file_tmp != null) {
            String file_name = file_tmp.getName() ;
            long byte_size_file = file_tmp.length();
            int size = (int) byte_size_file;
            pt2 = new ProductTemp(client_data.getId(), file_tmp, file_name, size) ;
            this.file_name.setText(file_name);
            extension.setText(getFileExtension(file_name));
            double kb_size_file = (double) byte_size_file / 1024 ;
            double mb_size_file = (double) kb_size_file / 1024 ;
            DecimalFormat df = new DecimalFormat("#.##");
            if (byte_size_file < 52428800){
                if (byte_size_file < 500){
                    this.size.setText(byte_size_file + " Bytes");
                } else {
                    if (kb_size_file < 500){
                        this.size.setText(Double.parseDouble(df.format(kb_size_file)) + " KB");
                    } else {
                        this.size.setText(Double.parseDouble(df.format(mb_size_file)) + " MB");
                    }
                }
                this.size.setStyle(null);
            } else {
                this.size.setText(Double.parseDouble(df.format(mb_size_file)) + " MB");
                this.size.setStyle("-fx-text-fill: red ;");
            }
            this.dowload.setDisable(true);
        } else {

        }
    }

    @FXML
    public void apply(ActionEvent event) {
        if (pt2 != null) {
            if (pt2.getFile_size() < 52428800) {
                pt = new ProductTemp(pt2.getUploader(), pt2.getFile_data(), pt2.getFile_name(), pt2.getFile_size()) ;
                pt2 = null ;
                this.button_file.setText(pt.getFile_name());
                btn_discard.setDisable(false);

                this.a_file_detail.setVisible(false);
                this.a_main.setDisable(false);
            } else {
                error("Documents larger than 50Mb in size!");
            }
        } else {
            this.a_file_detail.setVisible(false);
            this.a_main.setDisable(false);
        }


    }

    @FXML
    public void complete(ActionEvent event) {
        if (pt != null) {
            try {
                String task_id = this.task_interact.getTask_id() ;
                byte[] file_byte = new byte[(int) pt.getFile_data().length()];
                FileInputStream fis = new FileInputStream(pt.getFile_data());
                BufferedInputStream bis = new BufferedInputStream(fis);
                bis.read(file_byte, 0, file_byte.length);
                bis.close();
                CreateProductObject cpo = new CreateProductObject("", file_byte, pt.getFile_name(), pt.getFile_size(), pt.getUploader(), task_interact.getTask_id()) ;
                ps.createProduct(so, cpo) ;
                this.pt = null ;
                btn_discard.setDisable(true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void showMembers(ActionEvent event) {
        this.a_main.setDisable(true);
        this.a_members.setVisible(true);
    }

    @FXML
    public void closeShowMembers(ActionEvent event) {
        this.a_members.setVisible(false);
        this.a_main.setDisable(false);
    }

    @FXML
    private void addMember(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/AddMember.fxml"));
            Parent root = loader.load() ;
            NewMemberController nec = loader.getController() ;
            nec.loadStreams(so);
            // nec.loadAccess(client_data);
            nec.loadData(contacts);
            nec.loadProjectControl(pc);
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

            Scene scene = new Scene(root, 550, 405) ;
            scene.setFill(Color.TRANSPARENT);
            new_window.setScene(scene);

            new_window.getIcons().add(icon_member) ;

            new_window.show() ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void closeProject(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.close();
    }

    @FXML
    public void feedback(ActionEvent event) {
        if (task_interact != null && pp != null) {
            a_main.setDisable(true);
            this.a_feedback.setVisible(true);
            name_project_fb.setText(this.pc.getPp().getName());
            name_task_fb.setText(task_interact.getTask_name());

            ClientObject uploader_object = findMember(pc.getMembers(), pp.getCreator());
            if (uploader_object == null) {
                receive_fb.setText("...");
            } else {
                receive_fb.setText(uploader_object.getFullname());
            }

        } else {
            name_project_fb.setText("");
            name_task_fb.setText("");
            receive_fb.setText("");
        }
    }

    @FXML
    public void closeFeedBack(ActionEvent event) {
        a_feedback.setVisible(false);
        a_main.setDisable(false);
    }

    @FXML
    public void finishFeedBack(ActionEvent event) {
        if (true) { // condition ?
            String feedback_sender = client_data.getId() ;
            String project_id  = pc.getPp().getId() ;
            String task_id = task_interact.getTask_id();
            String product_id = pp.getProduct_id() ;
            String feedback = feedback_fb.getText() ;
            CreateFeedBack cfb = new CreateFeedBack(feedback_sender, project_id, task_id, product_id, feedback) ;
            ps.sendFeedBack(so, cfb);
            this.a_feedback.setVisible(false);
            this.a_main.setDisable(false);
        }
    }

    @FXML
    public void minimize(ActionEvent event) {
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setIconified(true);
    }

    @Override
    public void loadStreams(StreamObject so) {
        this.so = so ;
    }

    @Override
    public void loadProcessing(Processing pss) {
        this.pss = pss ;
        pss.loadInterfaceProject(this);
    }

    @Override
    public void loadAccess(ClientData client_data) {
        this.client_data = client_data ;
    }

    @Override
    public void loadData(Object object) {
        ProjectControl pc = (ProjectControl) object  ;
        this.pc = pc ;

        updateWindow(pc);
    }

    public void updateWindow(ProjectControl pc) {
        Platform.runLater(() -> {
            this.project_name.setText(pc.getPp().getName());
            this.role.setText(pc.getPp().getClient_role());
            this.status.setText(pc.getPp().getStatus());

            TreeItem root = pui.buildTreeFromTasks(pc.getTasks());
            task_tree.setRoot(root);

            pui.displayMembers(list_member, pc.getMembers());

            task_selected = null ;
            task_interact = null ;

            creator.setText("");
            responsibler.setText("");
            request.setText("");
            date_created.setText("");
            deadline.setText("");
            button_file.setText("");
            uploader.setText("");
            upload_time.setText("");
        });
    }

    public void loadContacts(List<ContactObject> contacts) {
        this.contacts = contacts ;
    }

    public ClientObject findMember(List<ClientObject> members, String member_id) {
        for (ClientObject member : members) {
            if (member.getId().equals(member_id)) {
                return member ;
            }
        }
        return null ;
    }

    public String convertFileSize(int size) {
        DecimalFormat df = new DecimalFormat("#.##");
        double kb_size_file = (double) size / 1024 ;
        double mb_size_file = kb_size_file / 1024 ;
        if (size < 500){
            return size + " Bytes" ;
        } else {
            if (kb_size_file < 500){
                return Double.parseDouble(df.format(kb_size_file)) + " KB" ;
            } else {
                return Double.parseDouble(df.format(mb_size_file)) + " MB" ;
            }
        }
    }

    public void updateMembers(String new_member_id) {
        if (new_member_id != null) {
            for (ContactObject new_member : contacts) {
                if (new_member.getId().equals(new_member_id)) {
                    ClientObject new_mb = new ClientObject(new_member.getId(), new_member.getFullname(), new_member.getEmail(), new_member.getPhonenumber(), new_member.getAvata(), pc.getPp().getId(), "Member") ;
                    pc.getMembers().add(new_mb) ;
                    pui.displayMembers(list_member, pc.getMembers());
                }
            }
        } else {
            System.out.println("aaaaa");
        }
    }

    @FXML
    private void resetTask(ActionEvent event) {
        if (task_interact != null) {
            sf.sendRqResetTask(so, task_interact.getTask_id());
        }
    }

    public void updateTask(TaskObject task, ProductPreview product) {
        if (task_selected != null) {
            if (task != null &&
                    task_interact.getTask_id().equals(task.getTask_id()) &&
                    task_interact.getTask_id().equals(task_selected.getValue().getTask_id())
            ) {
                TaskPreview task_preview = new TaskPreview(task.getTask_id(), task.getClassify(), task.getProject_id(), task.getContainer_id(), task.getTask_name());
                task_selected.setValue(task_preview);
                changeTask(task, product);
            }
        } else {
            System.out.println("aa");
        }
    }

    @FXML
    private void resetProject(ActionEvent event) {
        sf.sendRqResetProject(so, this.pc.getPp().getId()) ;
    }

    public void updateProject(ProjectControl pc) { // Project preview is null
        if (pc != null) {
            this.pc.setMembers(pc.getMembers());
            this.pc.setTasks(pc.getTasks());
            updateWindow(this.pc);
        }
    }

    public void setFileSize(Label file_size, int size) {
        DecimalFormat df = new DecimalFormat("#.##");
        double kb_size_file = (double) size / 1024 ;
        double mb_size_file = kb_size_file / 1024 ;
        if (size < 52428800){
            if (size < 500){
                file_size.setText(size + " Bytes");
            } else {
                if (kb_size_file < 500){
                    file_size.setText(Double.parseDouble(df.format(kb_size_file)) + " KB");
                } else {
                    file_size.setText(Double.parseDouble(df.format(mb_size_file)) + " MB");
                }
            }
        } else {
            file_size.setText(Double.parseDouble(df.format(mb_size_file)) + " MB");
        }
    }

    public void dowloadProduct(FileObject fo) {
        Platform.runLater(() -> {
            String description = getFileExtension(fo.getName_file()).toUpperCase() + " Files";
            String extensions = "*." + getFileExtension(fo.getName_file()) ;
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(description, extensions);
            fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setInitialFileName(fo.getName_file());
            File file = fileChooser.showSaveDialog(null);
            if (file != null ){
                a_dowload_product.setVisible(true);
                progressbar_dowload.setProgress(0);
                prb_lb.setText("Downloading " + "'" + fo.getName_file() + "'" + " ...");

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

                progressbar_dowload.progressProperty().bind(saveTask.progressProperty());

                saveTask.setOnSucceeded(e -> {
                    Platform.runLater(() -> {
                        System.out.println("File '" + fo.getName_file() + "' saved successfully.");
                        a_dowload_product.setVisible(false);
                        progressbar_dowload.progressProperty().unbind();
                    });
                });

                saveTask.setOnFailed(e -> {
                    Platform.runLater(() -> {
                        a_dowload_product.setVisible(false);
                        // error("Error,An error occurred while saving file '" + fo.getName_file() + "'.");
                        progressbar_dowload.progressProperty().unbind();
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

    public void error(String message) {
        Platform.runLater(() -> {
            URL url = ProjectViewController.class.getResource("/com/gui/projectmanagement/view/AlertError.fxml");
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

}

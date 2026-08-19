package com.gui.projectmanagement.ui;

import com.gui.projectmanagement.controller.*;
import com.gui.projectmanagement.entity.*;
import com.gui.projectmanagement.network.StreamObject;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class InterfaceClientUI {

    String imaPart = "/com/gui/projectmanagement/images/Project/Folder.png" ;

    String imaPart2 = "/com/gui/projectmanagement/images/Project/Folder.png" ;

    Image icon_folder = new Image(getClass().getResourceAsStream(imaPart));

    Image icon_new_project = new Image(getClass().getResourceAsStream(imaPart2));

    InterfaceClientController icc = null ;

    public InterfaceClientUI (InterfaceClientController icc) {
        this.icc = icc ;
    }

    public void displayListProject(ListView list_view, List<ProjectPreview> list_project) {
        Platform.runLater(() -> {
            ObservableList<ProjectPreview> project_obs = FXCollections.observableArrayList(list_project);
            list_view.setItems(project_obs);
            list_view.setCellFactory(param -> new ListCell<ProjectPreview>() {

                @Override
                protected void updateItem(ProjectPreview project_preview, boolean empty) {
                    super.updateItem(project_preview, empty);
                    if (empty || project_preview == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/ProjectItem.fxml"));
                            AnchorPane anchorpane = loader.load();
                            anchorpane.getStyleClass().add("anchorpane_project");
                            ProjectItemController pc = loader.getController() ;
                            pc.loadInterfaceClientController(icc);
                            pc.setProject(project_preview);
                            setGraphic(anchorpane);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        });
    }

    public static void changeUI (AnchorPane a1, AnchorPane a2) {
        a2.setVisible(true);
        a1.setDisable(true);
    }

    private double xOffset = 0;
    private double yOffset = 0;

    public void openProjectView(StreamObject so, ClientData client_data, ProjectControl pc) { // Data ?
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/ProjectView.fxml"));
            Parent root = loader.load() ;
            ProjectViewController pvc = loader.getController() ;
            pvc.loadStreams(so);
            pvc.loadAccess(client_data);
            pvc.loadData(pc);
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

            Scene scene = new Scene(root, 1000, 600) ;
            new_window.setScene(scene);
            new_window.show() ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void newProject(StreamObject so ,ClientData client_data, InterfaceClientController icc) {
        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/NewProject.fxml"));
                Parent root = loader.load() ;
                NewProjectControler npc = loader.getController() ;
                npc.loadStreams(so);
                npc.loadInterface(icc);
                npc.loadAccess(client_data);
                Scene scene = new Scene(root, 450, 500) ;
                scene.setFill(Color.TRANSPARENT);
                Stage window = new Stage() ;
                window.initStyle(StageStyle.TRANSPARENT);

                root.setOnMousePressed((MouseEvent mouseEvent) -> {
                    xOffset = mouseEvent.getSceneX();
                    yOffset = mouseEvent.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent mouseEvent) -> {
                    window.setX(mouseEvent.getScreenX() - xOffset);
                    window.setY(mouseEvent.getScreenY() - yOffset);
                });

                window.getIcons().add(icon_new_project) ;

                window.setScene(scene);
                window.showAndWait();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void displayMessage(ClientData client, ContactObject interactor, List<Message> messages, ListView<Message> list_message, StreamObject so, boolean is_receiver) {
        Platform.runLater(() -> {
            ObservableList<Message> ms_obs = FXCollections.observableArrayList(messages);
            Collections.reverse(ms_obs);
            list_message.setItems(ms_obs);

            boolean is_at_bottom  = false ;
            ScrollBar vertical_scrollBar = (ScrollBar) list_message.lookup(".scroll-bar:vertical");
            if (vertical_scrollBar != null) {
                double scroll_value = vertical_scrollBar.getValue();
                double threshold = 0.95;
                if (scroll_value >= threshold) {
                    is_at_bottom = true ;
                }
            }

            list_message.setCellFactory(param -> new ListCell<Message>() {

                @Override
                protected void updateItem(Message message, boolean empty) {
                    super.updateItem(message, empty);
                    if (empty || interactor == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            if (message.getType_message().equals("Message")) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/MessageItem.fxml"));
                                AnchorPane anchorpane = loader.load();
                                MessageItemController mic = loader.getController() ;
                                mic.load(client, interactor, message);
                                setGraphic(anchorpane);
                                setText(null);
                            } else if (message.getType_message().equals("Image")) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/ImageItem.fxml"));
                                AnchorPane anchorpane = loader.load();
                                ImageItemController iic = loader.getController() ;
                                iic.load(client, interactor, message);
                                setGraphic(anchorpane);
                                setText(null);
                            } else if (message.getType_message().equals("File")) {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/FileItem.fxml"));
                                AnchorPane anchorpane = loader.load();
                                FileItemController fic = loader.getController() ;
                                fic.load(client, interactor, message, so);
                                setGraphic(anchorpane);
                                setText(null);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
            if (!is_receiver || is_at_bottom){
                int last_index = ms_obs.size() - 1;
                list_message.scrollTo(last_index);
            }
        });
    }

}

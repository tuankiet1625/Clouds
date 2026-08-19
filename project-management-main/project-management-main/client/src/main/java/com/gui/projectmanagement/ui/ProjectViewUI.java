package com.gui.projectmanagement.ui;

import com.gui.projectmanagement.controller.MemberItemController;
import com.gui.projectmanagement.controller.NewTaskController;
import com.gui.projectmanagement.controller.TaskItemController;
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
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

public class ProjectViewUI {
    public TreeItem<TaskPreview> buildTreeFromTasks(List<TaskPreview> tasks) {
        TreeItem<TaskPreview> root = null;
        for (TaskPreview task : tasks) {
            if (task.getClassify().equals("Container")) {
                root = new TreeItem<>(new TaskPreview(task.getTask_id(), "Container", task.getProject_id(), null, task.getTask_name()));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/TaskItem.fxml"));
                try {
                    AnchorPane anchorpane = loader.load();
                    TaskItemController tic = loader.getController() ;
                    tic.loadNameTask(root.getValue().getTask_name());
                    root.setGraphic(anchorpane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                tasks.remove(task) ;
                Iterator<TaskPreview> iterator = tasks.iterator();
                buildTree(root, tasks);
                break ;
            }
        }
        return root;
    }

    private void buildTree(TreeItem<TaskPreview> parent, List<TaskPreview> tasks) {
        for (TaskPreview task : tasks) {
            if (task.getContainer_id().equals(parent.getValue().getTask_id())) {
                TreeItem<TaskPreview> taskItem = new TreeItem<>(task);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/TaskItem.fxml"));
                try {
                    AnchorPane anchorpane = loader.load();
                    TaskItemController tic = loader.getController() ;
                    tic.loadNameTask(taskItem.getValue().getTask_name());
                    taskItem.setGraphic(anchorpane);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                parent.getChildren().add(taskItem);
                buildTree(taskItem, tasks);
            }
        }
    }

    public void buildTreeItem(TreeItem<TaskPreview> parent, TaskPreview task) {
        TreeItem<TaskPreview> taskItem = new TreeItem<>(task);
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

    private static double xOffset = 0;
    private static double yOffset = 0;

    String imaPart = "/com/gui/projectmanagement/images/NewTask/Task.png" ;

    Image icon_task = new Image(getClass().getResourceAsStream(imaPart));

    public void newTask(ClientData client_data, StreamObject so, NewTaskControl ntc) {
        Platform.runLater(() -> {
            try {
                URL url = getClass().getResource("/com/gui/projectmanagement/view/NewTask.fxml");
                FXMLLoader loader = new FXMLLoader(url);
                Parent root = loader.load()  ;

                NewTaskController ntcer = loader.getController() ;
                ntcer.loadAccess(client_data);
                ntcer.loadStreams(so);
                ntcer.loadData(ntc);

                Scene scene = new Scene(root, 450, 500);
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

                primaryStage.getIcons().add(icon_task) ;

                primaryStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void openConfirm() {
        URL url = ProjectViewUI.class.getResource("/com/gui/projectmanagement/view/AlertWarning.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        try {
            Parent root = loader.load();
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

    public void displayMembers(ListView list_members, List<ClientObject> members) {
        Platform.runLater(() -> {
            ObservableList<ClientObject> member_obs = FXCollections.observableArrayList(members);
            list_members.setItems(member_obs);
            list_members.setCellFactory(param -> new ListCell<ClientObject>() {

                @Override
                protected void updateItem(ClientObject member, boolean empty) {
                    super.updateItem(member, empty);
                    if (empty || member == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gui/projectmanagement/view/MemberItem.fxml"));
                            AnchorPane anchorpane = loader.load();
                            anchorpane.getStyleClass().add("item_member");
                            MemberItemController mic = loader.getController() ;
                            mic.loadMemberData(member);
                            setGraphic(anchorpane);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        });
    }
}

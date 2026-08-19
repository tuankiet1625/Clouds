package com.gui.projectmanagement.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TreeViewExample extends Application {

    private TreeView<String> treeView;
    TextField newNodeNameField = new TextField();

    @Override
    public void start(Stage primaryStage) {
        // Tạo gốc cây
        TreeItem<String> rootNode = new TreeItem<>("Root");

        // Tạo TreeView từ gốc
        treeView = new TreeView<>(rootNode);

        // Tạo TextField để nhập tên node mới
        newNodeNameField.setPromptText("Enter new node name");

        // Tạo Button để thêm node
        Button addButton = new Button("Add Node");
        addButton.setOnAction(e -> addNode());

        // Sắp xếp TextField và Button trong VBox
        VBox controlBox = new VBox(10);
        controlBox.getChildren().addAll(newNodeNameField, addButton);

        StackPane root = new StackPane();
        root.getChildren().addAll(treeView, controlBox);

        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("TreeView Example");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addNode() {
        TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String nodeName = newNodeNameField.getText();
            if (!nodeName.isEmpty()) {
                TreeItem<String> newNode = new TreeItem<>(nodeName);
                selectedItem.getChildren().add(newNode);
                newNodeNameField.clear(); // Clear the TextField after adding node
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


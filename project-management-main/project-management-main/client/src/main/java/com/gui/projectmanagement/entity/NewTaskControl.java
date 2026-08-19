package com.gui.projectmanagement.entity;

import javafx.scene.control.TreeItem;

import java.util.List;

public class NewTaskControl {
    private TreeItem<TaskPreview> parent ;
    private List<ClientObject> members ;

    public NewTaskControl(TreeItem<TaskPreview> parent, List<ClientObject> members) {
        this.parent = parent;
        this.members = members;
    }

    public TreeItem<TaskPreview> getParent() {
        return parent ;
    }

    public List<ClientObject> getMembers() {
        return members;
    }

    public void setMembers(List<ClientObject> members) {
        this.members = members;
    }
}

package com.gui.projectmanagement.entity;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class TypeData {
    // Type ?
    public static final Type CLIENT_DATA = new TypeToken<ClientData>() {}.getType();
    public static final Type BOOLEAN = new TypeToken<Boolean>() {}.getType();
    public static final Type LOGIN_OBJECT = new TypeToken<LoginObject>() {}.getType();
    public static final Type REGESTER_OBJECT = new TypeToken<RegesterObject>() {}.getType();
    public static final Type PROJECT_OBJECT = new TypeToken<ProjectObject>() {}.getType();
    public static final Type STRING = new TypeToken<String>() {}.getType();
    public static final Type PROJECTS = new TypeToken<List<ProjectPreview>>() {}.getType();
    public static final Type TASKS = new TypeToken<List<TaskPreview>>() {}.getType();
    public static final Type MEMBERS = new TypeToken<List<ClientObject>>() {}.getType();
    public static final Type TASK = new TypeToken<TaskObject>() {}.getType();
    // public static final Type CLIENT = new TypeToken<ClientObject1>() {}.getType();
    public static final Type PRODUCT = new TypeToken<ProductPreview>() {}.getType();
    public static final Type PRODUCT_OBJECT = new TypeToken<ProductObject>() {}.getType();
    public static final Type PROJECT = new TypeToken<ProjectObject>() {}.getType();

    public static final Type CONTACTS = new TypeToken<List<ContactObject>>() {}.getType();
    public static final Type CLIENT = new TypeToken<ContactObject>() {}.getType();
    public static final Type MESSAGE = new TypeToken<Message>() {}.getType();
    public static final Type MESSAGES = new TypeToken<List<Message>>() {}.getType();

    public static final Type PROJECT_CONTROL = new TypeToken<ProjectControl>() {}.getType();
    public static final Type REQUESTS = new TypeToken<List<RequestAddContact>>() {}.getType();
    public static final Type FEEDBACKS = new TypeToken<List<FeedBackObject>>() {}.getType();

    public static final Type REQUEST = new TypeToken<RequestAddContact>() {}.getType();
    public static final Type FEEDBACK = new TypeToken<FeedBackObject>() {}.getType();
    public static final Type FILE = new TypeToken<FileObject>() {}.getType();


}

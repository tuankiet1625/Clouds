package com.gui.projectmanagement.network;

import com.gui.projectmanagement.entity.*;

import java.util.List;

public class ProjectStream {

    DataService ds = new DataService() ;

    public void createProject(StreamObject so, CreateProjectObject cpo) {
        ds.send(so.getDos(), "@new_project");
        ds.send(so.getDos(), cpo);
    }

    public ProjectObject receiveProjectJustCreate(StreamObject so) {
        ProjectObject po = ds.receive(so.getDis(), TypeData.PROJECT_OBJECT) ;
        return po ;
    }

    public List<ProjectPreview> getProjects(StreamObject so, String client_id){
        ds.send(so.getDos(), "@projects");
        ds.send(so.getDos(), client_id);
        List<ProjectPreview> projects = ds.receive(so.getDis(), TypeData.PROJECTS) ;
        return projects ;
    }

    public void createTask(StreamObject so, CreateTaskProject ctp) {

    }

    public void sendRqProjectControl(StreamObject so, String project_id) {
        ds.send(so.getDos(), "@project_control");
        ds.send(so.getDos(), project_id);
    }

    public ProjectControl receiveProjectControl(StreamObject so) {
        ProjectControl project = ds.receive(so.getDis(), TypeData.PROJECT_CONTROL) ;
        return project ;
    }

    public List<ClientObject> receiveMembers(StreamObject so) {
        List<ClientObject> members = ds.receive(so.getDis(), TypeData.MEMBERS) ;
        return members ;
    }

    public void sendRqTask(StreamObject so, String task_id) {
        ds.send(so.getDos(), "@task");
        ds.send(so.getDos(), task_id);
    }

    public TaskObject receiveTask(StreamObject so) {
        TaskObject task = ds.receive(so.getDis(), TypeData.TASK) ;
        return task ;
    }

    public void sendRqProduct(StreamObject so, String product_id) {
        ds.send(so.getDos(), "@product");
        ds.send(so.getDos(), product_id);
    }

    public ProductPreview receiveProduct(StreamObject so) {
        ProductPreview pp = ds.receive(so.getDis(), TypeData.PRODUCT) ;
        return pp ;
    }

    public void createProduct(StreamObject so, CreateProductObject cpo) {
        ds.send(so.getDos(), "@upload_product");
        ds.send(so.getDos(), cpo);
    }

    public ProductObject receveProductJustCreate(StreamObject so) {
        ProductObject po = ds.receive(so.getDis(), TypeData.PRODUCT_OBJECT) ;
        return po ;
    }

    public TaskObject receiveTaskJustCreate(StreamObject so) {
        TaskObject task = ds.receive(so.getDis(), TypeData.TASK) ;
        return task ;
    }

    public void sendRqTask(StreamObject so, CreateTaskProject ctp) {
        ds.send(so.getDos(), "@new_task");
        ds.send(so.getDos(), ctp);
    }

    public void sendRequestFeedBacks(StreamObject so, String client_id) {
        ds.send(so.getDos(), "@feedbacks");
        ds.send(so.getDos(), client_id);
    }

    public void sendFeedBack(StreamObject so, CreateFeedBack cfb) {
        ds.send(so.getDos(), "@send_feedbacks");
        ds.send(so.getDos(), cfb);
    }

    public void sendRqProductObject(StreamObject so, String product_id) {
        ds.send(so.getDos(), "@product_object");
        ds.send(so.getDos(), product_id);
    }

    public FileObject getProductObject(StreamObject so) {
        FileObject fo = ds.receive(so.getDis(), TypeData.FILE) ;
        return fo ;
    }

    public void deleteProjectFirst(StreamObject so) {
        ds.send(so.getDos(), "@delete_project_first");
    }

    public void deleteProjectLast(StreamObject so, String code, String project_id) {
        ds.send(so.getDos(), "@delete_project_last");
        ds.send(so.getDos(), code);
        ds.send(so.getDos(), project_id);
    }

    public String receiveResultDeleteProject(StreamObject so) {
        String result_code = ds.receive(so.getDis(), TypeData.STRING) ;
        if (result_code.equals("@code_is_correct")) {
            String result_role = ds.receive(so.getDis(), TypeData.STRING) ;
            if (result_role.equals("@role_valid")) {
                String result_delete = ds.receive(so.getDis(), TypeData.STRING) ;
                if (result_delete.equals("@delete_success")) {
                    String project_id_just_delet = ds.receive(so.getDis(), TypeData.STRING) ;
                    return project_id_just_delet ;
                } else {
                    return "#delete_unsuccess" ;
                }
            } else {
                return "#role_invalid" ;
            }
        } else {
            return "#code_incorrect" ;
        }
    }


}

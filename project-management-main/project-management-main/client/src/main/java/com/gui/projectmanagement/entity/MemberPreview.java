package com.gui.projectmanagement.entity;

public class MemberPreview {
    private String member_id ;
    private String fullname ;
    private String email ;
    private String role ;
    private String project_id ;

    public MemberPreview(String member_id, String fullname, String role, String project_id) {
        this.member_id = member_id;
        this.fullname = fullname;
        this.role = role;
        this.project_id = project_id;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }
}

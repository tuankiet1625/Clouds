package com.gui.projectmanagement.authentication;

import com.gui.projectmanagement.entity.ClientObject;

import java.time.LocalDate;

public class NewTaskManager {
    public boolean deadlineValidator(LocalDate deadline) {
        LocalDate date_now = LocalDate.now() ;
        if (deadline.isBefore(date_now)) {
            return false ;
        }
        return true ;
    }

    public boolean validatorInfor(String location, String name_task, String request, ClientObject undertaker, LocalDate deadline) {
        if (location == "" || name_task == "" || request == "" || undertaker == null || !deadlineValidator(deadline)) {
            return false ;
        }
        return true ;
    }
}

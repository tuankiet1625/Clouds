package com.gui.projectmanagement.authentication;

import java.time.LocalDate;

public class NewProjectManager {
    public boolean endDateValidator(LocalDate end_date) {
        if (end_date == null) {
            return false ;
        }
        LocalDate date_now = LocalDate.now() ;
        if (end_date.isBefore(date_now)) {
            return false ;
        }
        return true ;
    }
    public boolean createProjectValidator (String name_project, String descrie, LocalDate end_date, String budget) {
        if (name_project == "" || descrie == "" || !endDateValidator(end_date) || budget == "") {
            return false ;
        }
        return true ;
    }
}

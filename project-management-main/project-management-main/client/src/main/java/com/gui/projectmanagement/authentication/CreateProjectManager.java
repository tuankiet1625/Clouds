package com.gui.projectmanagement.authentication;

import com.gui.projectmanagement.entity.CreateProjectObject;

import java.time.LocalDate;

public class CreateProjectManager {
    public static boolean createProjectValidator(String name_project, LocalDate start_date, LocalDate expected_end) {
        if (name_project.isEmpty() ||
            start_date == null ||
            expected_end == null ||
            start_date.isAfter(expected_end)) {
            return false ;
        }
        return true ;
    }
}

package com.gui.projectmanagement.cache;

import com.gui.projectmanagement.entity.ContactObject;

import java.util.ArrayList;
import java.util.List;

public class Interactors {
    List<ContactObject> interactors = null ;

    public void constructorInteractors(List<ContactObject> interactors) {
        this.interactors = interactors ;
    }

    public List<ContactObject> getInteractors() {
        return interactors ;
    }

    public void add(ContactObject interact) {
        interactors.add(0, interact) ;
    }

    public boolean contain(String client_id) {
        synchronized (interactors) {
            for (ContactObject interactor : interactors) {
                if (interactor.getId().equals(client_id)) {
                    return true;
                }
            }
            return false;
        }
    }

    public ContactObject getInteractor(String client_id) {
        synchronized (interactors) {
            for (ContactObject interactor : interactors) {
                if (interactor.getId().equals(client_id)) {
                    return interactor;
                }
            }
            return null;
        }
    }


    public void moveInteractor(String client_id) {
        ContactObject interactor = getInteractor(client_id) ;
        interactors.remove(interactor) ;
        if (interactor != null) {
            interactors.add(0, interactor) ;
        }
    }
}

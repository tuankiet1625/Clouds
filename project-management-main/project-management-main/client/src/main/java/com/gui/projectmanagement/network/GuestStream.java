package com.gui.projectmanagement.network;

import com.gui.projectmanagement.entity.ClientData;
import com.gui.projectmanagement.entity.LoginObject;
import com.gui.projectmanagement.entity.RegesterObject;
import com.gui.projectmanagement.entity.TypeData;

public class GuestStream {

    DataService ds = new DataService() ;

    public ClientData login(StreamObject so, LoginObject lo) {
        ClientData client_data = null;
        ds.send(so.getDos(), "@login");
        ds.send(so.getDos(), lo);
        String result = ds.receive(so.getDis(), TypeData.STRING);
        if (result.equals("@accept")) {
            client_data = ds.receive(so.getDis(), TypeData.CLIENT_DATA);
        } else if (result.equals("@refuse")) {
            System.out.println("Login failed!");
        }
        return client_data;
    }

    public void regester_first(StreamObject so, RegesterObject ro) {
        ds.send(so.getDos(), "@regester");
        ds.send(so.getDos(), ro);
    }

    public String regester_last(StreamObject so, String code) {
        ds.send(so.getDos(), code);
        String result = ds.receive(so.getDis(), TypeData.STRING);
        return result ;
    }

    public boolean forgotPassword_frist(StreamObject so, String username) {
        ds.send(so.getDos(), "@forgot_password");
        ds.send(so.getDos(), username);
        return ds.receive(so.getDis(), TypeData.BOOLEAN) ;
    }

    public boolean forgotPassword_mid(StreamObject so, String code) {
        ds.send(so.getDos(), code);
        return ds.receive(so.getDis(), TypeData.BOOLEAN) ;
    }

    public boolean forgotPassword_last(StreamObject so, String new_password) {
        ds.send(so.getDos(), new_password);
        return ds.receive(so.getDis(), TypeData.BOOLEAN) ;
    }
}

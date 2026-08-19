package com.gui.projectmanagement.controller;

import com.gui.projectmanagement.network.StreamObject;
import com.gui.projectmanagement.network.Processing;

public interface Network {
    public void loadStreams(StreamObject so);

    public void loadProcessing(Processing pss) ;

}

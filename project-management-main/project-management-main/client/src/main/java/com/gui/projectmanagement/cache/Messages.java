package com.gui.projectmanagement.cache;

import com.gui.projectmanagement.entity.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Messages {
    private static final Map<String, List<Message>> messages = new HashMap<>();

    public void putDataMessage(String id_interact, List<Message> messages) {
        synchronized (messages) {
            this.messages.put(id_interact, messages);
        }
    }

    public void updateMessages(String id_interact, Message message){
        synchronized (messages) {
            messages.get(id_interact).add(0, message);
        }
    }

    public List<Message> getMessage(String id_interact) {
        synchronized (messages) {
            return messages.get(id_interact);
        }
    }

    public boolean containsId(String id_interact) {
        synchronized (messages) {
            return messages.containsKey(id_interact);
        }
    }

    public Object removeDataMessage(String id_interact) {
        return messages.remove(id_interact);
    }

}

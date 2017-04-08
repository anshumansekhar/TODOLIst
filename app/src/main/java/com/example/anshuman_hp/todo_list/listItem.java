package com.example.anshuman_hp.todo_list;

/**
 * Created by Anshuman-HP on 08-04-2017.
 */

public class listItem {
    String Desc;
    String priority;

    public listItem(String descprition, String priority) {
        Desc = descprition;
        this.priority = priority;
    }

    public listItem() {
    }

    public String getDescprition() {
        return Desc;
    }

    public void setDescprition(String descprition) {
        Desc = descprition;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}

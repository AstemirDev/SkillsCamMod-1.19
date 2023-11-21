package org.astemir.cammod.scene.action;


import java.util.concurrent.CopyOnWriteArrayList;

public class ActionController {

    public static ActionController INSTANCE = new ActionController();

    public CopyOnWriteArrayList<BindAction> actions = new CopyOnWriteArrayList<>();


    public CopyOnWriteArrayList<BindAction> getActions() {
        return actions;
    }

    public void addAction(BindAction action){
        this.actions.add(action);
    }

    public void clear(){
        actions.clear();
    }


    public static ActionController getInstance() {
        return INSTANCE;
    }
}

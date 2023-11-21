package org.astemir.cammod.client.screen.ui.nodes.button;

public enum ButtonStyle {

    DEFAULT(0),OLD_FASHIONED(1);

    private int id;

    ButtonStyle(int id) {
        this.id = id;
    }


    public int getId() {
        return id;
    }
}

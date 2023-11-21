package org.astemir.cammod.client.input;

import java.util.ArrayList;
import java.util.List;

public class KeyBinds {

    private static List<KeyBind> binds = new ArrayList<>();

    public static void addBind(KeyBind bind){
        binds.add(bind);
    }

    public static List<KeyBind> getBinds() {
        return binds;
    }
}

package org.astemir.cammod.scene.action;

import net.minecraft.world.entity.player.Player;

public abstract class BindAction{

    private BindType type;

    public BindAction(BindType type) {
        this.type = type;
    }

    public abstract void run(Player player);

    public BindType getType() {
        return type;
    }
}

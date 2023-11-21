package org.astemir.cammod.common.event;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;

public class PlayerKeyInputEvent extends Event {

    private Player player;
    private int key;
    private int action;

    public PlayerKeyInputEvent(Player player, int key, int action) {
        this.player = player;
        this.key = key;
        this.action = action;
    }

    public Player getPlayer() {
        return player;
    }

    public int getKey() {
        return key;
    }

    public int getAction() {
        return action;
    }
}

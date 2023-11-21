package org.astemir.cammod.client.event;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.astemir.cammod.client.input.KeyBind;
import org.astemir.cammod.client.input.KeyBinds;


public class TickEvents {


    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent e){
        Minecraft minecraft = Minecraft.getInstance();
        if (e.phase == TickEvent.Phase.END){
            return;
        }
        for (KeyBind bind : KeyBinds.getBinds()) {
            bind.checkPressed();
        }
    }
}

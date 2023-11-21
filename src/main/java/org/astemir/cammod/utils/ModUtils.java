package org.astemir.cammod.utils;

import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.network.PacketArgument;

public class ModUtils {

    public static void runCommand(ServerPlayer player,String command){
        player.getServer().getCommands().performPrefixedCommand(createCommandSourceStack(player.getLevel(),player.position(),player),command);
    }

    public static CommandSourceStack createCommandSourceStack(ServerLevel level, Vec3 pos, ServerPlayer p_59736_) {
        String s = p_59736_ == null ? "Bind" : p_59736_.getName().getString();
        Component component = (Component)(p_59736_ == null ? Component.literal("Bind") : p_59736_.getDisplayName());
        return new CommandSourceStack(CommandSource.NULL, pos, Vec2.ZERO, level, 2, s, component, level.getServer(), p_59736_);
    }

    public static void executeOnClient(Entity player, int event, PacketArgument... arguments){
        WorldEventHandler.playClientEvent(player.level,player.blockPosition(),event,arguments);
    }

    public static void executeOnServer(Entity player,int event,PacketArgument... arguments){
        WorldEventHandler.playServerEvent(player.level,player.blockPosition(), event,arguments);
    }

    public static void executeOnClient(Level level, BlockPos pos, int event, PacketArgument... arguments){
        WorldEventHandler.playClientEvent(level,pos, event,arguments);
    }

    public static void executeOnServer(Level level, BlockPos pos, int event, PacketArgument... arguments){
        WorldEventHandler.playServerEvent(level,pos, event,arguments);
    }

    public static void sendMessage(Player player,String text){
        player.sendSystemMessage(Component.literal(text));
    }
}

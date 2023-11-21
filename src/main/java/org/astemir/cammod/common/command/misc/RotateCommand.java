package org.astemir.cammod.common.command.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.server.commands.ExecuteCommand;
import net.minecraft.server.commands.TeleportCommand;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.TradeWithPlayerGoal;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandPart;
import org.astemir.api.common.commands.build.CommandVariant;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.common.handler.WorldEventHandler;
import org.astemir.api.common.world.WorldEventListener;
import org.astemir.api.common.world.WorldUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.NetworkUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.client.renderer.clone.ModelClone;
import org.astemir.cammod.scene.listener.ClientSceneListener;
import org.astemir.cammod.utils.ModUtils;


public class RotateCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("rotate");
        CommandArgument target = CommandArgument.entities("target");
        CommandArgument yaw = CommandArgument.doubleArg("yaw");
        CommandArgument pitch = CommandArgument.doubleArg("pitch");
        builder.variants(
                CommandVariant.arguments(target,yaw,pitch).execute((p)->{
                        float yaw1 = (float) -yaw.getDouble(p);
                        float pitch1 = (float) -pitch.getDouble(p);
                        for (Entity entity : target.getEntities(p)) {
                            entity.setYHeadRot(yaw1);
                            entity.setYRot(yaw1);
                            entity.setYBodyRot(yaw1);
                            entity.yRotO = yaw1;
                            entity.setXRot(pitch1);
                            entity.xRotO = pitch1;
                        }
                        return 1;
                    }
                )
        );
        dispatcher.register(builder.build());
    }

}

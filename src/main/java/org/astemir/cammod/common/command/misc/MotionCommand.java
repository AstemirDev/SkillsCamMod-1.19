package org.astemir.cammod.common.command.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandPart;
import org.astemir.api.common.commands.build.CommandVariant;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.scene.listener.ClientSceneListener;
import org.astemir.cammod.utils.ModUtils;


public class MotionCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("motion");
        CommandArgument target = CommandArgument.entities("target");
        CommandArgument vector = CommandArgument.vector3("vector");
        builder.variants(
                CommandVariant.arguments(target,vector).execute((p)->{
                        for (Entity entity : target.getEntities(p)) {
                            Vec3 vec3 = vector.getVector3(p);
                            if (entity instanceof Player) {
                                ModUtils.executeOnClient(p.getSource().getLevel(), entity.blockPosition(), ClientSceneListener.EVENT_MOTION, PacketArgument.integer(entity.getId()), PacketArgument.vec3(vec3));
                            }else{
                                entity.setDeltaMovement(vec3);
                            }
                        }
                       return 1;
                    }
                )
        );
        dispatcher.register(builder.build());
    }

}

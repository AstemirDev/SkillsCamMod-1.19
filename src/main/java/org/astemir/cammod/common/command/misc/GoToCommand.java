package org.astemir.cammod.common.command.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandVariant;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.scene.listener.ClientSceneListener;
import org.astemir.cammod.utils.ModUtils;


public class GoToCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("goto");
        CommandArgument target = CommandArgument.entities("target");
        CommandArgument position = CommandArgument.vector3("position");
        CommandArgument speed = CommandArgument.doubleArg("speed");
        builder.variants(
                CommandVariant.arguments(target,position).execute((p)->{
                            for (Entity entity : target.getEntities(p)) {
                                Vec3 vec3 = position.getVector3(p);
                                if (entity instanceof Mob mob){
                                    mob.getNavigation().moveTo(vec3.x,vec3.y,vec3.z,0.5f);
                                }
                            }
                            return 1;
                        }
                ),
                CommandVariant.arguments(target,position,speed).execute((p)->{
                        for (Entity entity : target.getEntities(p)) {
                            Vec3 vec3 = position.getVector3(p);
                            if (entity instanceof Mob mob){
                                mob.getNavigation().moveTo(vec3.x,vec3.y,vec3.z,speed.getDouble(p));
                            }
                        }
                       return 1;
                    }
                )
        );
        dispatcher.register(builder.build());
    }

}

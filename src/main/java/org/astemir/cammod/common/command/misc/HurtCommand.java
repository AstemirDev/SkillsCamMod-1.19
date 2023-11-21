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


public class HurtCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("hurt");
        CommandArgument target = CommandArgument.entities("target");
        CommandArgument value = CommandArgument.doubleArg("value");
        builder.execute((p)->{
            p.getSource().getPlayer().hurt(DamageSource.GENERIC, 20f);
            return 1;
        });
        builder.variants(
                CommandVariant.arguments(target,value).execute((p)->{
                        for (Entity entity : target.getEntities(p)) {
                            if (entity instanceof LivingEntity livingEntity){
                                livingEntity.hurt(DamageSource.GENERIC, (float) value.getDouble(p));
                            }
                        }
                        return 1;
                    }
                ),
                CommandVariant.arguments(target).execute((p)->{
                            for (Entity entity : target.getEntities(p)) {
                                if (entity instanceof LivingEntity livingEntity){
                                    livingEntity.hurt(DamageSource.GENERIC, 20f);
                                }
                            }
                            return 1;
                        }
                )
        );
        dispatcher.register(builder.build());
    }

}

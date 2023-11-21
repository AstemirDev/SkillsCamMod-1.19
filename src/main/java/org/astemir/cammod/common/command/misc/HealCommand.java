package org.astemir.cammod.common.command.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandVariant;


public class HealCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("heal");
        CommandArgument target = CommandArgument.entities("target");
        CommandArgument value = CommandArgument.doubleArg("value");
        builder.execute((p)->{
            p.getSource().getPlayer().heal(20f);
            return 1;
        });
        builder.variants(
                CommandVariant.arguments(target,value).execute((p)->{
                        for (Entity entity : target.getEntities(p)) {
                            if (entity instanceof LivingEntity livingEntity){
                                livingEntity.heal((float) value.getDouble(p));
                            }
                        }
                        return 1;
                    }
                ),
                CommandVariant.arguments(target).execute((p)->{
                            for (Entity entity : target.getEntities(p)) {
                                if (entity instanceof LivingEntity livingEntity){
                                    livingEntity.heal(20f);
                                }
                            }
                            return 1;
                        }
                )
        );
        dispatcher.register(builder.build());
    }

}

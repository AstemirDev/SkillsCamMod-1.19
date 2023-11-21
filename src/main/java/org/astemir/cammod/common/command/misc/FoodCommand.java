package org.astemir.cammod.common.command.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.HelpCommand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandVariant;


public class FoodCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("food");
        CommandArgument target = CommandArgument.entities("target");
        CommandArgument value = CommandArgument.integer("value");
        builder.variants(
                CommandVariant.arguments(target,value).execute((p)->{
                        for (Entity entity : target.getEntities(p)) {
                            if (entity instanceof Player livingEntity){
                                livingEntity.getFoodData().setFoodLevel(value.getInt(p));
                            }
                        }
                        return 1;
                    }
                ),
                CommandVariant.arguments(target).execute((p)->{
                            for (Entity entity : target.getEntities(p)) {
                                if (entity instanceof Player livingEntity){
                                    livingEntity.getFoodData().setFoodLevel(20);
                                }
                            }
                            return 1;
                        }
                )
        );
        builder.execute((p)->{
            p.getSource().getPlayer().getFoodData().setFoodLevel(20);
            return 1;
        });
        dispatcher.register(builder.build());
    }

}

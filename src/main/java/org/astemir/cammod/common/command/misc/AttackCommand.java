package org.astemir.cammod.common.command.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandVariant;

import java.util.Optional;


public class AttackCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("attack");
        CommandArgument target1 = CommandArgument.entities("target1");
        CommandArgument target2 = CommandArgument.entities("target2");
        builder.variants(
                CommandVariant.arguments(target1,target2).execute((p)->{
                            for (Entity entity : target1.getEntities(p)) {
                                if (entity instanceof Mob mob){
                                    Optional<? extends Entity> optionalEntity = target2.getEntities(p).stream().filter((e)->(e instanceof LivingEntity)).findFirst();
                                    if (optionalEntity.isPresent()) {
                                        mob.setTarget((LivingEntity) optionalEntity.get());
                                    }
                                }
                            }
                            return 1;
                        }
                )
        );
        dispatcher.register(builder.build());
    }

}

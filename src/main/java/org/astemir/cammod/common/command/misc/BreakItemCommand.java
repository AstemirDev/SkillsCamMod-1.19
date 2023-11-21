package org.astemir.cammod.common.command.misc;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandVariant;


public class BreakItemCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("breakitem");
        CommandArgument target = CommandArgument.entities("target");
        CommandArgument value = CommandArgument.integer("value");
        builder.variants(
                CommandVariant.arguments(target,value).execute((p)->{
                        for (Entity entity : target.getEntities(p)) {
                            if (entity instanceof LivingEntity livingEntity){
                                ItemStack mainHand = livingEntity.getMainHandItem();
                                ItemStack offhand = livingEntity.getOffhandItem();
                                if (mainHand != null && !mainHand.isEmpty()) {
                                    int damage = mainHand.getItem().damageItem(mainHand,value.getInt(p), livingEntity, (player) -> player.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                                    mainHand.hurt(damage,((LivingEntity) entity).getRandom(),null);
                                }else
                                if (offhand != null && !offhand.isEmpty()) {
                                    int damage =  offhand.getItem().damageItem(offhand,value.getInt(p), livingEntity, (player) -> player.broadcastBreakEvent(EquipmentSlot.OFFHAND));
                                    offhand.hurt(damage,((LivingEntity) entity).getRandom(),null);
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

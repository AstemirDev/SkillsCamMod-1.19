package org.astemir.cammod.common.command.effect;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.astemir.api.common.commands.build.CommandArgument;

public class SpeedCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandArgument value = CommandArgument.integer("value");
        LiteralArgumentBuilder builder = (LiteralArgumentBuilder) Commands.literal("s").then(value.build().executes((Command<CommandSourceStack>) context ->{
            ServerPlayer player = context.getSource().getPlayer();
            int effectPower = value.getInt(context);
            if (effectPower > 0){
                int power = effectPower-1;
                player.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
                addEffect(player,MobEffects.MOVEMENT_SPEED,power);
            }else{
                int power = Math.abs(effectPower)-1;
                player.removeEffect(MobEffects.MOVEMENT_SPEED);
                addEffect(player,MobEffects.MOVEMENT_SLOWDOWN,power);
            }
            return 1;
        }));
        dispatcher.register(builder);
    }

    public static void addEffect(ServerPlayer player, MobEffect effect, int power){
        if (player.hasEffect(effect)){
            int oldEffectPower = player.getEffect(effect).getAmplifier();
            if (power != oldEffectPower){
                player.removeEffect(effect);
                player.addEffect(new MobEffectInstance(effect, 99999, power, false, false));
            }
        }else{
            player.addEffect(new MobEffectInstance(effect, 99999, power, false, false));
        }
    }
}

package org.astemir.cammod.common.command.effect;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.astemir.api.common.commands.build.CommandBuilder;

public class ClearEffectsCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("c");
        builder.execute((p)->{
            Player player = p.getSource().getPlayer();
            for (MobEffectInstance activeEffect : player.getActiveEffects()) {
                player.removeEffect(activeEffect.getEffect());
            }
            return 1;
        });
        dispatcher.register(builder.build());
    }

}

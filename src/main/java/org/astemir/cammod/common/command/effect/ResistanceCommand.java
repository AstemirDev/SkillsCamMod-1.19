package org.astemir.cammod.common.command.effect;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.astemir.api.common.commands.build.CommandBuilder;

public class ResistanceCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("r");
        dispatcher.register((LiteralArgumentBuilder<CommandSourceStack>) builder.build().executes((Command<CommandSourceStack>) (p)->{
            p.getSource().getPlayer().addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE,999999,99,false,false));
            return 1;
        }));
    }

}

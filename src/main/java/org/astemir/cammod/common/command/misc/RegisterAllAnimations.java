package org.astemir.cammod.common.command.misc;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandPart;
import org.astemir.api.common.commands.build.CommandVariant;
import org.astemir.api.common.world.WorldUtils;
import org.astemir.api.math.components.Vector3;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.scene.listener.ClientSceneListener;
import org.astemir.cammod.utils.ModUtils;
import org.astemir.cammod.utils.MorphUtils;


public class RegisterAllAnimations {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("reganims");
        CommandArgument target = CommandArgument.entities("target");
        CommandArgument modelName = new CommandArgument("model_name", StringArgumentType.string());
        builder.variants(
            CommandVariant.arguments(target,modelName).
                    execute((p)-> {
                        for (Entity entity : target.getEntities(p)) {
                            ModUtils.executeOnClient(p.getSource().getPlayer(), ClientSceneListener.EVENT_GET_ALL_ANIMATIONS_FOR_ENTITY, PacketArgument.integer(entity.getId()),PacketArgument.str(modelName.getString(p)));
                        }
                        return 1;
                    })
        );
        dispatcher.register(builder.build());
    }

}

package org.astemir.cammod.common.command.scene;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.CompoundTagArgument;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandPart;
import org.astemir.api.common.commands.build.CommandVariant;
import org.astemir.api.math.components.Vector3;
import org.astemir.cammod.utils.ModUtils;
import org.astemir.cammod.utils.MorphUtils;



public class SMorphCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("smorph");
        CommandArgument entityType = CommandArgument.summonArgument("entity");
        CommandArgument animName = CommandArgument.word("animation");
        CommandArgument animLength = CommandArgument.doubleArg("length");
        CommandArgument animSpeed = CommandArgument.doubleArg("speed");
        CommandArgument animSmoothness = CommandArgument.doubleArg("smoothness");
        CommandArgument entityNbt = new CommandArgument("nbt", CompoundTagArgument.compoundTag());
        CommandArgument modelName = new CommandArgument("model_name", StringArgumentType.string());
        CommandArgument vector = CommandArgument.vector3("vector");
        builder.variants(
                CommandVariant.arguments(CommandPart.create("to"),entityType.suggestion(SuggestionProviders.SUMMONABLE_ENTITIES)).
                        execute((p)->morphEntity(p.getSource(),entityType.getSummonArgument(p))),
                CommandVariant.arguments(CommandPart.create("to"),entityType.suggestion(SuggestionProviders.SUMMONABLE_ENTITIES),entityNbt).
                        execute((p)->morphEntity(p.getSource(),entityType.getSummonArgument(p),CompoundTagArgument.getCompoundTag(p,"nbt"))),
                CommandVariant.arguments(CommandPart.create("position"),vector).
                        execute((p)-> {
                            MorphUtils.setRenderPosition(p.getSource().getPlayer(), Vector3.from(vector.getVector3(p)));
                            return 1;
                        }),
                CommandVariant.arguments(CommandPart.create("rotation"),vector).
                        execute((p)-> {
                            MorphUtils.setRenderRotation(p.getSource().getPlayer(), Vector3.from(vector.getVector3(p)));
                            return 1;
                        }),
                CommandVariant.arguments(CommandPart.create("scale"),vector).
                        execute((p)-> {
                            MorphUtils.setRenderScale(p.getSource().getPlayer(), Vector3.from(vector.getVector3(p)));
                            return 1;
                        }),
                CommandVariant.arguments(CommandPart.create("reg_anim"),animName,animLength).
                        execute((p)-> {
                            MorphUtils.addCustomAnimation(p.getSource().getPlayer(), animName.getString(p), (float) animLength.getDouble(p));
                            return 1;
                        }),
                CommandVariant.arguments(CommandPart.create("reg_anim"),animName,animLength,animSpeed).
                        execute((p)-> {
                            MorphUtils.addCustomAnimation(p.getSource().getPlayer(), animName.getString(p), (float) animLength.getDouble(p), (float) animSpeed.getDouble(p),2.0f);
                            return 1;
                        }),
                CommandVariant.arguments(CommandPart.create("reg_anim"),animName,animLength,animSpeed,animSmoothness).
                        execute((p)-> {
                            MorphUtils.addCustomAnimation(p.getSource().getPlayer(), animName.getString(p), (float) animLength.getDouble(p), (float) animSpeed.getDouble(p),(float) animSmoothness.getDouble(p));
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("reg_anim_all"),modelName).
                        execute((p)-> {
                            MorphUtils.addAllAnimations(p.getSource().getPlayer(),modelName.getString(p));
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("reg_anim_clear")).
                        execute((p)-> {
                            MorphUtils.clearRegisteredAnimations(p.getSource().getPlayer());
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("provoke"),CommandPart.create("type"),entityType.suggestion(SuggestionProviders.SUMMONABLE_ENTITIES)).
                        execute((p)->{
                            MorphUtils.setProvokeId(p.getSource().getPlayer(),entityType.getSummonArgument(p));
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("provoke"),CommandPart.create("enable")).
                        execute((p)->{
                            MorphUtils.setProvoke(p.getSource().getPlayer(),true);
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("provoke"),CommandPart.create("disable")).
                        execute((p)->{
                            MorphUtils.setProvoke(p.getSource().getPlayer(),false);
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("disable"))
                        .execute((p)->unmorphEntity(p.getSource())),

                CommandVariant.arguments(CommandPart.create("reset"))
                        .execute((p)->resetEntity(p.getSource()))
        );
        dispatcher.register(builder.build());
    }


    public static int morphEntity(CommandSourceStack source, ResourceLocation entityType){
        return morphEntity(source,entityType,new CompoundTag());
    }

    public static int morphEntity(CommandSourceStack source, ResourceLocation entityType, CompoundTag tag){
        ModUtils.sendMessage(source.getPlayer(),"Вы успешно превратились в "+entityType.toString());
        MorphUtils.morphTo(source.getPlayer(),entityType,tag);
        return 1;
    }

    public static int unmorphEntity(CommandSourceStack source){
        ModUtils.sendMessage(source.getPlayer(),"Превращения отключены.");
        MorphUtils.unmorph(source.getPlayer());
        return 1;
    }

    public static int resetEntity(CommandSourceStack source){
        ModUtils.sendMessage(source.getPlayer(),"Превращения сброшены.");
        MorphUtils.reset(source.getPlayer());
        return 1;
    }
}

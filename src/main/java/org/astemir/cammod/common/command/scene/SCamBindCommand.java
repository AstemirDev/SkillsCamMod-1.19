package org.astemir.cammod.common.command.scene;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.SuggestionProviders;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandPart;
import org.astemir.api.common.commands.build.CommandVariant;
import org.astemir.cammod.SkillsCamMod;
import org.astemir.cammod.scene.MainScene;
import org.astemir.cammod.scene.action.*;
import org.astemir.cammod.utils.ModUtils;
import org.astemir.cammod.utils.MorphUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static org.astemir.cammod.utils.ModUtils.runCommand;


public class SCamBindCommand {

    public static List<String> KEY_NAMES = new ArrayList<>();
    static {
        KEY_NAMES.add("key.mouse.left");
        KEY_NAMES.add("key.mouse.right");
        KEY_NAMES.add("key.mouse.middle");
        KEY_NAMES.add("key.mouse.4");
        KEY_NAMES.add("key.mouse.5");
        KEY_NAMES.add("key.mouse.6");
        KEY_NAMES.add("key.mouse.7");
        KEY_NAMES.add("key.mouse.8");
        KEY_NAMES.add("key.keyboard.0");
        KEY_NAMES.add("key.keyboard.1");
        KEY_NAMES.add("key.keyboard.2");
        KEY_NAMES.add("key.keyboard.3");
        KEY_NAMES.add("key.keyboard.4");
        KEY_NAMES.add("key.keyboard.5");
        KEY_NAMES.add("key.keyboard.6");
        KEY_NAMES.add("key.keyboard.7");
        KEY_NAMES.add("key.keyboard.8");
        KEY_NAMES.add("key.keyboard.9");
        KEY_NAMES.add("key.keyboard.a");
        KEY_NAMES.add("key.keyboard.b");
        KEY_NAMES.add("key.keyboard.c");
        KEY_NAMES.add("key.keyboard.d");
        KEY_NAMES.add("key.keyboard.e");
        KEY_NAMES.add("key.keyboard.f");
        KEY_NAMES.add("key.keyboard.g");
        KEY_NAMES.add("key.keyboard.h");
        KEY_NAMES.add("key.keyboard.i");
        KEY_NAMES.add("key.keyboard.j");
        KEY_NAMES.add("key.keyboard.k");
        KEY_NAMES.add("key.keyboard.l");
        KEY_NAMES.add("key.keyboard.m");
        KEY_NAMES.add("key.keyboard.n");
        KEY_NAMES.add("key.keyboard.o");
        KEY_NAMES.add("key.keyboard.p");
        KEY_NAMES.add("key.keyboard.q");
        KEY_NAMES.add("key.keyboard.r");
        KEY_NAMES.add("key.keyboard.s");
        KEY_NAMES.add("key.keyboard.t");
        KEY_NAMES.add("key.keyboard.u");
        KEY_NAMES.add("key.keyboard.v");
        KEY_NAMES.add("key.keyboard.w");
        KEY_NAMES.add("key.keyboard.x");
        KEY_NAMES.add("key.keyboard.y");
        KEY_NAMES.add("key.keyboard.z");
        KEY_NAMES.add("key.keyboard.f1");
        KEY_NAMES.add("key.keyboard.f2");
        KEY_NAMES.add("key.keyboard.f3");
        KEY_NAMES.add("key.keyboard.f4");
        KEY_NAMES.add("key.keyboard.f5");
        KEY_NAMES.add("key.keyboard.f6");
        KEY_NAMES.add("key.keyboard.f7");
        KEY_NAMES.add("key.keyboard.f8");
        KEY_NAMES.add("key.keyboard.f9");
        KEY_NAMES.add("key.keyboard.f10");
        KEY_NAMES.add("key.keyboard.f11");
        KEY_NAMES.add("key.keyboard.f12");
        KEY_NAMES.add("key.keyboard.f13");
        KEY_NAMES.add("key.keyboard.f14");
        KEY_NAMES.add("key.keyboard.f15");
        KEY_NAMES.add("key.keyboard.f16");
        KEY_NAMES.add("key.keyboard.f17");
        KEY_NAMES.add("key.keyboard.f18");
        KEY_NAMES.add("key.keyboard.f19");
        KEY_NAMES.add("key.keyboard.f20");
        KEY_NAMES.add("key.keyboard.f21");
        KEY_NAMES.add("key.keyboard.f22");
        KEY_NAMES.add("key.keyboard.f23");
        KEY_NAMES.add("key.keyboard.f24");
        KEY_NAMES.add("key.keyboard.f25");
        KEY_NAMES.add("key.keyboard.num.lock");
        KEY_NAMES.add("key.keyboard.keypad.0");
        KEY_NAMES.add("key.keyboard.keypad.1");
        KEY_NAMES.add("key.keyboard.keypad.2");
        KEY_NAMES.add("key.keyboard.keypad.3");
        KEY_NAMES.add("key.keyboard.keypad.4");
        KEY_NAMES.add("key.keyboard.keypad.5");
        KEY_NAMES.add("key.keyboard.keypad.6");
        KEY_NAMES.add("key.keyboard.keypad.7");
        KEY_NAMES.add("key.keyboard.keypad.8");
        KEY_NAMES.add("key.keyboard.keypad.9");
        KEY_NAMES.add("key.keyboard.keypad.add");
        KEY_NAMES.add("key.keyboard.keypad.decimal");
        KEY_NAMES.add("key.keyboard.keypad.enter");
        KEY_NAMES.add("key.keyboard.keypad.equal");
        KEY_NAMES.add("key.keyboard.keypad.multiply");
        KEY_NAMES.add("key.keyboard.keypad.divide");
        KEY_NAMES.add("key.keyboard.keypad.subtract");
        KEY_NAMES.add("key.keyboard.down");
        KEY_NAMES.add("key.keyboard.left");
        KEY_NAMES.add("key.keyboard.right");
        KEY_NAMES.add("key.keyboard.up");
        KEY_NAMES.add("key.keyboard.apostrophe");
        KEY_NAMES.add("key.keyboard.backslash");
        KEY_NAMES.add("key.keyboard.comma");
        KEY_NAMES.add("key.keyboard.equal");
        KEY_NAMES.add("key.keyboard.grave.accent");
        KEY_NAMES.add("key.keyboard.left.bracket");
        KEY_NAMES.add("key.keyboard.minus");
        KEY_NAMES.add("key.keyboard.period");
        KEY_NAMES.add("key.keyboard.right.bracket");
        KEY_NAMES.add("key.keyboard.semicolon");
        KEY_NAMES.add("key.keyboard.slash");
        KEY_NAMES.add("key.keyboard.space");
        KEY_NAMES.add("key.keyboard.tab");
        KEY_NAMES.add("key.keyboard.left.alt");
        KEY_NAMES.add("key.keyboard.left.control");
        KEY_NAMES.add("key.keyboard.left.shift");
        KEY_NAMES.add("key.keyboard.left.win");
        KEY_NAMES.add("key.keyboard.right.alt");
        KEY_NAMES.add("key.keyboard.right.control");
        KEY_NAMES.add("key.keyboard.right.shift");
        KEY_NAMES.add("key.keyboard.right.win");
        KEY_NAMES.add("key.keyboard.enter");
        KEY_NAMES.add("key.keyboard.escape");
        KEY_NAMES.add("key.keyboard.backspace");
        KEY_NAMES.add("key.keyboard.delete");
        KEY_NAMES.add("key.keyboard.end");
        KEY_NAMES.add("key.keyboard.home");
        KEY_NAMES.add("key.keyboard.insert");
        KEY_NAMES.add("key.keyboard.page.down");
        KEY_NAMES.add("key.keyboard.page.up");
        KEY_NAMES.add("key.keyboard.caps.lock");
        KEY_NAMES.add("key.keyboard.pause");
        KEY_NAMES.add("key.keyboard.scroll.lock");
        KEY_NAMES.add("key.keyboard.menu");
        KEY_NAMES.add("key.keyboard.print.screen");
        KEY_NAMES.add("key.keyboard.world.1");
        KEY_NAMES.add("key.keyboard.world.2");
    }

    public static final SuggestionProvider<CommandSourceStack> KEYS = SuggestionProviders.register(new ResourceLocation(SkillsCamMod.MOD_ID,"keys"), (p_212438_, p_212439_) -> {
        return SharedSuggestionProvider.suggest(KEY_NAMES, p_212439_);
    });

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("sbind");
        CommandArgument key = CommandArgument.word("key").suggestion(KEYS);
        CommandArgument name = CommandArgument.word("name");
        CommandArgument stringArgument = new CommandArgument("argument", StringArgumentType.greedyString());
        List<CommandVariant> variants = new ArrayList<>();
        for (ActionType actionType : ActionType.values()) {
            variants.add(bindKeyPressed(actionType,key,stringArgument));
            variants.add(bindKeyReleased(actionType,key,stringArgument));
            variants.add(bindAnimationEnd(actionType,name,stringArgument));
            variants.addAll(binds(actionType, stringArgument));
        }
        variants.add(new CommandVariant(new CommandPart("reset")).execute((p)->{
            MorphUtils.stopAnimations(p.getSource().getPlayer());
            ActionController.getInstance().clear();
            return 1;
        }));
        variants.add(new CommandVariant(new CommandPart("default_attack_new")).execute((p)->{
            ServerPlayer player = p.getSource().getPlayer();
            defaultCmd(player);
            runCommand(player,"sbind bind play_anim press key.mouse.left animation.model.attack1");
            runCommand(player,"sbind bind stop_anim anim_end animation.model.attack1 animation.model.attack1");
            runCommand(player,"sbind bind play_anim press key.mouse.right animation.model.attack2");
            runCommand(player,"sbind bind stop_anim anim_end animation.model.attack2 animation.model.attack2");
            return 1;
        }));
        variants.add(new CommandVariant(new CommandPart("default_attack_old")).execute((p)->{
            ServerPlayer player = p.getSource().getPlayer();
            defaultCmd(player);
            runCommand(player,"sbind bind play_anim press key.mouse.left animation.model.attack1");
            runCommand(player,"sbind bind stop_anim release key.mouse.left animation.model.attack1");
            runCommand(player,"sbind bind play_anim press key.mouse.right animation.model.attack2");
            runCommand(player,"sbind bind stop_anim release key.mouse.right animation.model.attack2");
            return 1;
        }));
        variants.add(new CommandVariant(new CommandPart("default_dig")).execute((p)->{
            ServerPlayer player = p.getSource().getPlayer();
            defaultCmd(player);
            runCommand(player,"sbind bind play_anim press key.mouse.left animation.model.dig1");
            runCommand(player,"sbind bind stop_anim release key.mouse.left animation.model.dig1");
            runCommand(player,"sbind bind play_anim press key.mouse.right animation.model.dig2");
            runCommand(player,"sbind bind stop_anim release key.mouse.right animation.model.dig2");
            return 1;
        }));
        variants.add(new CommandVariant(new CommandPart("default_consume")).execute((p)->{
            ServerPlayer player = p.getSource().getPlayer();
            defaultCmd(player);
            runCommand(player,"sbind bind play_anim press key.mouse.right animation.model.consume");
            runCommand(player,"sbind bind stop_anim release key.mouse.right animation.model.consume");
            runCommand(player,"sbind bind stop_anim anim_end animation.model.consume animation.model.consume");
            return 1;
        }));
        variants.add(new CommandVariant(new CommandPart("default_shoot")).execute((p)->{
            ServerPlayer player = p.getSource().getPlayer();
            defaultCmd(player);
            runCommand(player,"sbind bind play_anim press key.mouse.right animation.model.shoot");
            runCommand(player,"sbind bind stop_anim release key.mouse.right animation.model.shoot");
            runCommand(player,"sbind bind stop_anim anim_end animation.model.shoot animation.model.shoot");
            return 1;
        }));
        builder.variants(variants.toArray(new CommandVariant[variants.size()]));
        dispatcher.register(builder.build());
    }

    private static void defaultCmd(ServerPlayer player){
        runCommand(player,"sbind bind play_anim idle animation.model.idle");
        runCommand(player,"sbind bind stop_anim idle animation.model.walk");
        runCommand(player,"sbind bind play_anim walk animation.model.walk");
        runCommand(player,"sbind bind stop_anim walk animation.model.idle");
        runCommand(player,"sbind bind play_anim press key.keyboard.left.control animation.model.run");
        runCommand(player,"sbind bind stop_anim press key.keyboard.left.control animation.model.walk");
        runCommand(player,"sbind bind stop_anim press key.keyboard.left.control animation.model.idle");
        runCommand(player,"sbind bind stop_anim press key.keyboard.left.control animation.model.crouching");
        runCommand(player,"sbind bind stop_anim press key.keyboard.left.control animation.model.crouching_idle");
        runCommand(player,"sbind bind stop_anim release key.keyboard.left.control animation.model.run");
        runCommand(player,"sbind bind stop_anim idle animation.model.crouching");
        runCommand(player,"sbind bind stop_anim idle animation.model.crouching_idle");
        runCommand(player,"sbind bind play_anim sneak_idle animation.model.crouching_idle");
        runCommand(player,"sbind bind stop_anim sneak_idle animation.model.crouching");
        runCommand(player,"sbind bind stop_anim sneak_idle animation.model.idle");
        runCommand(player,"sbind bind stop_anim sneak_idle animation.model.walk");
        runCommand(player,"sbind bind stop_anim sneak_idle animation.model.run");
        runCommand(player,"sbind bind play_anim sneak_walk animation.model.crouching");
        runCommand(player,"sbind bind stop_anim sneak_walk animation.model.crouching_idle");
        runCommand(player,"sbind bind stop_anim sneak_walk animation.model.idle");
        runCommand(player,"sbind bind stop_anim sneak_walk animation.model.walk");
        runCommand(player,"sbind bind stop_anim sneak_walk animation.model.run");
        runCommand(player,"sbind bind play_anim press key.keyboard.q animation.model.dig1");
        runCommand(player,"sbind bind stop_anim anim_end animation.model.dig1 animation.model.dig1");
    }


    public static void performAction(CommandContext<CommandSourceStack> context, ActionType action, CommandArgument argument){
        switch (action){
            case PLAY_ANIMATION ->{
                MorphUtils.playAnimation(context.getSource().getPlayer(),argument.getString(context));
                break;
            }
            case STOP_ANIMATION -> {
                MorphUtils.stopAnimation(context.getSource().getPlayer(),argument.getString(context));
                break;
            }
            case RUN_COMMAND -> {
                ServerPlayer player = context.getSource().getPlayer();
                if (player != null){
                    runCommand(player,argument.getString(context));
                }
            }
        }
    }


    public static CommandVariant bindAnimationEnd(ActionType action,CommandArgument name,CommandArgument argument){
        return new CommandVariant(new CommandPart("bind"),new CommandPart(action.getArgumentName()),new CommandPart("anim_end"),name,argument).execute((p)->{
            ActionController.getInstance().getActions().add(new BindAnimation(BindType.ANIMATION_END,name.getString(p)) {
                @Override
                public void run(Player player) {
                    if (player.getUUID().equals(p.getSource().getPlayer().getUUID())) {
                        performAction(p, action, argument);
                    }
                }
            });
            return 1;
        });
    }


    public static CommandVariant bindKeyPressed(ActionType action,CommandArgument key,CommandArgument argument){
        return new CommandVariant(new CommandPart("bind"),new CommandPart(action.getArgumentName()),new CommandPart("press"),key,argument).execute((p)->{
            ActionController.getInstance().getActions().add(new BindButton(BindType.BUTTON_PRESSED,getKey(key.getString(p))) {
                @Override
                public void run(Player player) {
                    if (player.getUUID().equals(p.getSource().getPlayer().getUUID())) {
                        performAction(p, action, argument);
                    }
                }
            });
            return 1;
        });
    }

    public static CommandVariant bindKeyReleased(ActionType action,CommandArgument key,CommandArgument argument){
        return new CommandVariant(new CommandPart("bind"),new CommandPart(action.getArgumentName()),new CommandPart("release"),key,argument).execute((p)->{
            ActionController.getInstance().getActions().add(new BindButton(BindType.BUTTON_RELEASED,getKey(key.getString(p))) {
                @Override
                public void run(Player player) {
                    if (player.getUUID().equals(p.getSource().getPlayer().getUUID())) {
                        performAction(p, action, argument);
                    }
                }
            });
            return 1;
        });
    }

    public static List<CommandVariant> binds(ActionType action,CommandArgument argument){
        List<CommandVariant> variantList = new ArrayList<>();
        for (BindType bind : BindType.getDefaultBinds()) {
            variantList.add(new CommandVariant(new CommandPart("bind"),new CommandPart(action.getArgumentName()),new CommandPart(bind.name().toLowerCase()),argument).execute((p)->{
                ActionController.getInstance().getActions().add(new BindAction(bind){
                    @Override
                    public void run(Player player) {
                        if (player.getUUID().equals(p.getSource().getPlayer().getUUID())) {
                            performAction(p, action, argument);
                        }
                    }
                });
                return 1;
            }));
        }
        return variantList;
    }


    public static int getKey(String name){
        InputConstants.Key keyConstant = InputConstants.getKey(name);
        if (keyConstant != null){
            return keyConstant.getValue();
        }
        return -1;
    }


    private enum ActionType{

        PLAY_ANIMATION("play_anim"),STOP_ANIMATION("stop_anim"),RUN_COMMAND("run_cmd");

        private String argumentName;

        ActionType(String argumentName) {
            this.argumentName = argumentName;
        }

        public String getArgumentName() {
            return argumentName;
        }
    }
}

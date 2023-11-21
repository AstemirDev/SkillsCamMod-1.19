package org.astemir.cammod.common.command;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.astemir.cammod.common.command.effect.ClearEffectsCommand;
import org.astemir.cammod.common.command.effect.ResistanceCommand;
import org.astemir.cammod.common.command.effect.SpeedCommand;
import org.astemir.cammod.common.command.misc.*;
import org.astemir.cammod.common.command.scene.SCamBindCommand;
import org.astemir.cammod.common.command.scene.SCamCommand;
import org.astemir.cammod.common.command.scene.SMorphCommand;

public class SCamCommands {


    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent e){
        RegisterAllAnimations.register(e.getDispatcher());
        AddGoalCommand.register(e.getDispatcher());
        AttackCommand.register(e.getDispatcher());
        ClearEffectsCommand.register(e.getDispatcher());
        GoToCommand.register(e.getDispatcher());
        RotateCommand.register(e.getDispatcher());
        FoodCommand.register(e.getDispatcher());
        HealCommand.register(e.getDispatcher());
        HurtCommand.register(e.getDispatcher());
        MotionCommand.register(e.getDispatcher());
        SCamCommand.register(e.getDispatcher());
        SCamBindCommand.register(e.getDispatcher());
        SMorphCommand.register(e.getDispatcher());
        SPropertyCommand.register(e.getDispatcher());
        ResistanceCommand.register(e.getDispatcher());
        SpeedCommand.register(e.getDispatcher());
        BreakItemCommand.register(e.getDispatcher());
    }
}

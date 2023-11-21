package org.astemir.cammod.common.event;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.astemir.api.common.entity.utils.EntityUtils;
import org.astemir.api.network.PacketArgument;
import org.astemir.cammod.scene.action.*;
import org.astemir.cammod.scene.building.BlockChange;
import org.astemir.cammod.scene.MainScene;
import org.astemir.cammod.scene.listener.ServerSceneListener;
import org.astemir.cammod.utils.ModUtils;
import org.astemir.cammod.utils.MorphUtils;
import org.astemir.cammod.utils.SceneUtils;

public class CommonEvents {

    @SubscribeEvent
    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent e){
//        Level level = e.getEntity().getLevel();
//        if (level instanceof ServerLevel serverLevel){
//            for (ServerPlayer player : serverLevel.players()) {
//                MorphUtils.updatePlayerMorph(player);
//            }
//        }
    }

    @SubscribeEvent
    public static void onWorldUpdate(TickEvent.LevelTickEvent e){
        if (e.phase != TickEvent.Phase.START){
            return;
        }
        if (e.level.getServer() != null) {
            MainScene.getInstance().updateServer(e.level.getServer());
        }
        MainScene.getInstance().updateClient();
    }

    @SubscribeEvent
    public static void onEntityClick(PlayerInteractEvent.EntityInteract e){
        if (e.getEntity().getItemInHand(InteractionHand.MAIN_HAND).is(Items.SADDLE)){
            if (MainScene.getInstance().getActorManager().isActor(e.getTarget())){
                e.getEntity().startRiding(e.getTarget());
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e){
        if (e.phase != TickEvent.Phase.START){
            return;
        }
        Player player = e.player;
        if (player.level.isClientSide) {
            for (BindAction action : ActionController.getInstance().getActions()) {
                if (player.isSprinting()){
                    ModUtils.executeOnServer(player, ServerSceneListener.EVENT_SPRINT, PacketArgument.integer(player.getId()));
                }
                if (player.isInWater()){
                    if (action.getType() == BindType.IN_WATER) {
                        action.run(player);
                    }
                }
                if (player.hurtTime > 0){
                    if (action.getType() == BindType.HURT) {
                        action.run(player);
                    }
                }
                if (player.isOnGround()){
                    if (action.getType() == BindType.ON_GROUND) {
                        action.run(player);
                    }
                }else{
                    if (action.getType() == BindType.IN_AIR) {
                        action.run(player);
                    }
                }
                if (!EntityUtils.isMoving(player, -0.15f, 0.15f)) {
                    if (player.isCrouching()){
                        if (action.getType() == BindType.SNEAK_IDLE) {
                            action.run(player);
                        }
                    }else {
                        if (action.getType() == BindType.IDLE) {
                            action.run(player);
                        }
                    }
                }else {
                    if (player.isCrouching()) {
                        if (action.getType() == BindType.SNEAK_WALK) {
                            action.run(player);
                        }
                    }else{
                        if (action.getType() == BindType.WALK) {
                            action.run(player);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onKeyInput(PlayerKeyInputEvent e) {
        for (BindAction action : ActionController.getInstance().getActions()) {
            if (action instanceof BindButton bindButton){
                if (bindButton.getType() == BindType.BUTTON_PRESSED){
                    if (e.getAction() == InputConstants.PRESS && e.getKey() == bindButton.getKey()){
                        bindButton.run(e.getPlayer());
                    }
                }
                if (bindButton.getType() == BindType.BUTTON_RELEASED){
                    if (e.getAction() == InputConstants.RELEASE && e.getKey() == bindButton.getKey()){
                        bindButton.run(e.getPlayer());
                    }
                }
            }
        }
    }


    @SubscribeEvent
    public static void onBlockPlace(BlockEvent.EntityPlaceEvent e){
        MainScene scene = MainScene.getInstance();
        if (scene.isRecording()) {
            BlockChange change = new BlockChange(e.getPos(), e.getPlacedBlock()).previousState(Blocks.AIR.defaultBlockState());
            scene.getCurrentTickState().getBlockChanges().add(change);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent e){
        MainScene scene = MainScene.getInstance();
        if (scene.isRecording()) {
            BlockChange change = new BlockChange(e.getPos()).previousState(e.getState());
            if (!e.getPlayer().isCreative()) {
                change = change.hasDrop();
            }
            scene.getCurrentTickState().getBlockChanges().add(change);
        }
    }


    @SubscribeEvent
    public static void onCommandInput(CommandEvent e){

    }
}

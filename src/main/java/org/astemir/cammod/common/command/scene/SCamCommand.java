package org.astemir.cammod.common.command.scene;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.Vec2Argument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandPart;
import org.astemir.api.common.commands.build.CommandVariant;
import org.astemir.api.math.components.Vector2;
import org.astemir.api.math.components.Vector3;
import org.astemir.cammod.client.ClientProperties;
import org.astemir.cammod.scene.MainScene;
import org.astemir.cammod.scene.camera.CameraController;
import org.astemir.cammod.scene.camera.CameraEasing;
import org.astemir.cammod.utils.SceneUtils;


public class SCamCommand {


    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("scam");
        CommandArgument entityId = CommandArgument.integer("id");
        CommandArgument time = CommandArgument.doubleArg("time");
        CommandArgument enabled = CommandArgument.bool("enabled");
        CommandArgument offset = CommandArgument.vector3("offset");
        CommandArgument position = CommandArgument.vector3("position");
        CommandArgument rotation = CommandArgument.vector2("rotation");
        CommandArgument speed = CommandArgument.doubleArg("speed");
        builder.variants(
                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("position"),position)
                        .execute((p)->{
                            ClientProperties properties = MainScene.getInstance().getProperties();
                            properties.cameraPosition = position.getVector3(p);
                            properties.customCameraPosition = true;
                            return 1;
                        }),
                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("offset"),offset)
                        .execute((p)->{
                            ClientProperties properties = MainScene.getInstance().getProperties();
                            properties.cameraOffset = offset.getVector3(p);
                            properties.customCameraOffset = true;
                            return 1;
                        }),
                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("rotation"),rotation)
                        .execute((p)->{
                            ClientProperties properties = MainScene.getInstance().getProperties();
                            properties.cameraRotation = Vec2Argument.getVec2(p,rotation.getArgumentName());
                            properties.customCameraRotation = true;
                            return 1;
                        }),
                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("look"),time)
                        .execute((p)->{
                            CommandSourceStack source = p.getSource();
                            ServerPlayer player = source.getPlayer();
                            CameraController.getInstance().getAnimator().getCameraPath().add(Vector3.from(player.getEyePosition()), new Vector2(player.getYHeadRot(), player.getXRot()), (float) time.getDouble(p),0.25f, CameraEasing.DEFAULT);
                            return 1;
                        }),
                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("look"),time,speed)
                        .execute((p)->{
                            CommandSourceStack source = p.getSource();
                            ServerPlayer player = source.getPlayer();
                            CameraController.getInstance().getAnimator().getCameraPath().add(Vector3.from(player.getEyePosition()), new Vector2(player.getYHeadRot(), player.getXRot()), (float) time.getDouble(p),(float) speed.getDouble(p), CameraEasing.DEFAULT);
                            return 1;
                        }),
                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("look"),time,speed,new CommandPart("default"))
                        .execute((p)->{
                            CommandSourceStack source = p.getSource();
                            ServerPlayer player = source.getPlayer();
                            CameraController.getInstance().getAnimator().getCameraPath().add(Vector3.from(player.getEyePosition()), new Vector2(player.getYHeadRot(), player.getXRot()), (float) time.getDouble(p),(float) speed.getDouble(p), CameraEasing.DEFAULT);
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("look"),time,speed,new CommandPart("ease_in"))
                        .execute((p)->{
                            CommandSourceStack source = p.getSource();
                            ServerPlayer player = source.getPlayer();
                            CameraController.getInstance().getAnimator().getCameraPath().add(Vector3.from(player.getEyePosition()), new Vector2(player.getYHeadRot(), player.getXRot()), (float) time.getDouble(p),(float) speed.getDouble(p), CameraEasing.EASE_IN);
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("look"),time,speed,new CommandPart("ease_out"))
                        .execute((p)->{
                            CommandSourceStack source = p.getSource();
                            ServerPlayer player = source.getPlayer();
                            CameraController.getInstance().getAnimator().getCameraPath().add(Vector3.from(player.getEyePosition()), new Vector2(player.getYHeadRot(), player.getXRot()), (float) time.getDouble(p),(float) speed.getDouble(p), CameraEasing.EASE_OUT);
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("look"),time,speed,new CommandPart("ease_in_out"))
                        .execute((p)->{
                            CommandSourceStack source = p.getSource();
                            ServerPlayer player = source.getPlayer();
                            CameraController.getInstance().getAnimator().getCameraPath().add(Vector3.from(player.getEyePosition()), new Vector2(player.getYHeadRot(), player.getXRot()), (float) time.getDouble(p),(float) speed.getDouble(p), CameraEasing.EASE_IN_OUT);
                            return 1;
                        }),


                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("unlock"),enabled).execute((p)->{
                    ClientProperties properties = MainScene.getInstance().getProperties();
                    properties.unlockedCamera = enabled.getBoolean(p);
                    return 1;
                }),

                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("unlock"),enabled).execute((p)->{
                    ClientProperties properties = MainScene.getInstance().getProperties();
                    properties.unlockedCamera = enabled.getBoolean(p);
                    return 1;
                }),

                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("fakesmooth"),enabled).execute((p)->{
                    ClientProperties properties = MainScene.getInstance().getProperties();
                    properties.smoothCamera = enabled.getBoolean(p);
                    return 1;
                }),

                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("fakesmooth"),enabled).execute((p)->{
                    ClientProperties properties = MainScene.getInstance().getProperties();
                    properties.smoothCamera = enabled.getBoolean(p);
                    return 1;
                }),

                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("panic"),enabled).execute((p)->{
                    ClientProperties properties = MainScene.getInstance().getProperties();
                    properties.panic = enabled.getBoolean(p);
                    return 1;
                }),

                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("panic"),enabled).execute((p)->{
                    ClientProperties properties = MainScene.getInstance().getProperties();
                    properties.panic = enabled.getBoolean(p);
                    return 1;
                }),

                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("reset"))
                        .execute((p)->{
                            ClientProperties properties = MainScene.getInstance().getProperties();
                            properties.unlockedCamera = false;
                            properties.customCameraOffset = false;
                            properties.customCameraPosition = false;
                            properties.customCameraRotation = false;
                            properties.smoothCamera = false;
                            CameraController.getInstance().reset();
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("camera"),CommandPart.create("start"))
                        .execute((p)->{
                            CameraController.getInstance().enable();
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("nametags"))
                        .execute((p)->{
                            CommandSourceStack source = p.getSource();
                            if (SceneUtils.enableNameTagsVisibility()){
                                source.sendSuccess(Component.literal("Отображение айди включено"), true);
                            }else{
                                source.sendSuccess(Component.literal("Отображение айди выключено"), true);
                            }
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("play"))
                        .execute((p)->{
                            CommandSourceStack source = p.getSource();
                            SceneUtils.playOrStop(source.getLevel());
                            if (MainScene.getInstance().isPlaying()){
                                source.sendSuccess(Component.literal("Воспроизведение выключено"), true);
                            }else{
                                if (!MainScene.getInstance().isRecording()) {
                                    source.sendSuccess(Component.literal("Воспроизведение включено"), true);
                                }else{
                                    source.sendSuccess(Component.literal("Нельзя начать воспроизведение во время записи"), true);
                                }
                            }
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("rec"),CommandPart.create("start")).
                        execute((p)->{
                            CommandSourceStack source = p.getSource();
                            if (SceneUtils.startRecording(source.getLevel())) {
                                source.sendSuccess(Component.literal("Запись движений начата"), true);
                            }else{
                                source.sendSuccess(Component.literal("Запись уже идет"), true);
                            }
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("rec"),CommandPart.create("stop")).
                        execute((p)->{
                            CommandSourceStack source = p.getSource();
                            if (SceneUtils.stopRecording(source.getPlayer(),source.getLevel(),source.getPosition())) {
                                source.sendSuccess(Component.literal("Запись движений остановлена"), true);
                            }else{
                                source.sendSuccess(Component.literal("Запись не началась"), true);
                            }
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("rec"),CommandPart.create("pause")).
                        execute((p)->{
                            CommandSourceStack source = p.getSource();
                            if (SceneUtils.pauseOrResume()) {
                                source.sendSuccess(Component.literal("Запись на паузе"), true);
                            }else{
                                source.sendSuccess(Component.literal("Запись продолжена"), true);
                            }
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("del"),entityId).
                        execute((p)->{
                            CommandSourceStack source = p.getSource();
                            int id = entityId.getInt(p);
                            if (MainScene.getInstance().getActorManager().deleteActorById(id)){
                                source.sendSuccess(Component.literal("Цель удалена "+id), true);
                            }else{
                                source.sendSuccess(Component.literal("Цель отсутствует "+id).withStyle(ChatFormatting.RED), true);
                            }
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("show"),entityId).
                        execute((p)->{
                            CommandSourceStack source = p.getSource();
                            int id = entityId.getInt(p);
                            if (MainScene.getInstance().getActorManager().show(id)){
                                source.sendSuccess(Component.literal("Цель показана "+id), true);
                            }else{
                                source.sendSuccess(Component.literal("Цель отсутствует "+id).withStyle(ChatFormatting.RED), true);
                            }
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("hide"),entityId).
                        execute((p)->{
                            CommandSourceStack source = p.getSource();
                            int id = entityId.getInt(p);
                            if (MainScene.getInstance().getActorManager().hide(id)){
                                source.sendSuccess(Component.literal("Цель спрятана "+id), true);
                            }else{
                                source.sendSuccess(Component.literal("Цель отсутствует "+id).withStyle(ChatFormatting.RED), true);
                            }
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("del_last")).
                        execute((p)->{
                            CommandSourceStack source = p.getSource();
                            if (MainScene.getInstance().getActorManager().deleteActorLast()){
                                source.sendSuccess(Component.literal("Последняя цель удалена"), true);
                            }else{
                                source.sendSuccess(Component.literal("Нет целей на сцене").withStyle(ChatFormatting.RED), true);
                            }
                            return 1;
                        }),

                CommandVariant.arguments(CommandPart.create("clear")).
                        execute((p)->{
                            CommandSourceStack source = p.getSource();
                            MainScene.getInstance().getActorManager().deleteAllActors();
                            source.sendSuccess(Component.literal("Очистка всех манекенов успешна"),true);
                            return 1;
                        })
        );
        dispatcher.register(builder.build());
    }

}

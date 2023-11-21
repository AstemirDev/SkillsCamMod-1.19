package org.astemir.cammod.common.command.misc;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import org.astemir.api.common.commands.build.CommandArgument;
import org.astemir.api.common.commands.build.CommandBuilder;
import org.astemir.api.common.commands.build.CommandPart;
import org.astemir.api.common.commands.build.CommandVariant;
import org.astemir.api.math.components.Color;
import org.astemir.cammod.client.ClientProperties;
import org.astemir.cammod.scene.MainScene;


public class SPropertyCommand {

    
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        CommandBuilder builder = new CommandBuilder("sproperty");
        CommandArgument value = CommandArgument.doubleArg("value");
        CommandArgument r = CommandArgument.doubleArg("red");
        CommandArgument g = CommandArgument.doubleArg("green");
        CommandArgument b = CommandArgument.doubleArg("blue");
        CommandArgument a = CommandArgument.doubleArg("alpha");
        CommandArgument xRot = CommandArgument.doubleArg("rotation-x");
        CommandArgument yRot = CommandArgument.doubleArg("rotation-y");
        CommandArgument scale = CommandArgument.doubleArg("scale");
        CommandArgument far = CommandArgument.doubleArg("far");
        CommandArgument near = CommandArgument.doubleArg("near");
        ClientProperties properties = MainScene.getInstance().getProperties();
        builder.variants(
                CommandVariant.arguments(CommandPart.create("darkness"),CommandPart.create("set"),value).execute((p)->{
                    properties.setDarkness((float) value.getDouble(p));
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("darkness"),CommandPart.create("reset")).execute((p)->{
                    properties.resetDarkness();
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("stars"),CommandPart.create("brightness"),value).execute((p)->{
                    properties.setStarBrightness((float) value.getDouble(p));
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("stars"),CommandPart.create("reset")).execute((p)->{
                    properties.resetStarBrightness();
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("sky"),CommandPart.create("color"),r,g,b,a).execute((p)->{
                    properties.skyColor = new Color((float)r.getDouble(p),(float)g.getDouble(p),(float)b.getDouble(p),(float)a.getDouble(p));
                    properties.customSkyColor = true;
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("sky"),CommandPart.create("reset")).execute((p)->{
                    properties.resetSkyColor();
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("sunrise"),CommandPart.create("color"),r,g,b,a).execute((p)->{
                    properties.sunriseColor = new Color((float)r.getDouble(p),(float)g.getDouble(p),(float)b.getDouble(p),(float)a.getDouble(p));
                    properties.customSunriseColor = true;
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("sunrise"),CommandPart.create("reset")).execute((p)->{
                    properties.customSunriseColor = false;
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("fog"),CommandPart.create("color"),r,g,b,a).execute((p)->{
                    properties.setFogColor(new Color((float)r.getDouble(p),(float)g.getDouble(p),(float)b.getDouble(p),(float)a.getDouble(p)));
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("fog"),CommandPart.create("reset")).execute((p)->{
                    properties.resetFogColor();
                    properties.resetFogShape();
                    properties.resetFogDistance();
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("fog"),CommandPart.create("distance"),near,far).execute((p)->{
                    properties.setFogNearDistance((float)near.getDouble(p));
                    properties.setFogFarDistance((float)far.getDouble(p));
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("fog"),CommandPart.create("shape"),CommandPart.create("cylinder")).execute((p)->{
                    properties.setFogShape(FogShape.CYLINDER);
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("fog"),CommandPart.create("shape"),CommandPart.create("sphere")).execute((p)->{
                    properties.setFogShape(FogShape.SPHERE);
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("clouds"),CommandPart.create("color"),r,g,b,a).execute((p)->{
                    properties.setCloudsColor(new Color((float)r.getDouble(p),(float)g.getDouble(p),(float)b.getDouble(p),(float)a.getDouble(p)));
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("clouds"),CommandPart.create("reset")).execute((p)->{
                    properties.resetCloudsColor();
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("sun"),CommandPart.create("color"),r,g,b,a).execute((p)->{
                    properties.setSunColor(new Color((float)r.getDouble(p),(float)g.getDouble(p),(float)b.getDouble(p),(float)a.getDouble(p)));
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("sun"),CommandPart.create("rotation"),xRot,yRot).execute((p)->{
                    properties.setSunXRot((float) xRot.getDouble(p));
                    properties.setSunYRot((float) yRot.getDouble(p));
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("sun"),CommandPart.create("size"),scale).execute((p)->{
                    properties.setSunSize((float) scale.getDouble(p));
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("sun"),CommandPart.create("reset")).execute((p)->{
                    properties.resetSunRot();
                    properties.resetSunSize();
                    properties.resetSunColor();
                    return 1;
                }),
                CommandVariant.arguments(CommandPart.create("reset")).execute((p)->{
                    properties.resetCloudsColor();
                    properties.resetFogColor();
                    properties.resetSkyColor();
                    properties.resetFogDistance();
                    properties.resetFogShape();
                    properties.resetStarBrightness();
                    properties.resetCameraRotation();
                    properties.resetDarkness();
                    properties.resetSunRot();
                    properties.resetSunSize();
                    properties.resetSunriseColor();
                    properties.resetSunColor();
                    return 1;
                })
        );
        dispatcher.register(builder.build());
    }

}

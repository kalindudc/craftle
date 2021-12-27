package com.craftlemod.common.mixin;

import com.craftlemod.common.CraftleMod;
import com.craftlemod.common.registry.CraftleBlocks;
import com.craftlemod.common.registry.CraftleItems;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin {

    @Inject(method = "loadModelFromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceManager;getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;"), cancellable = true)
    public void loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
        if (!CraftleMod.MODID.equals(id.getNamespace())) {
            return;
        }

        String modelJson = "";
        String[] pathSegments = id.getPath().split("/");
        if (pathSegments[0].equalsIgnoreCase("item")) {
            modelJson = CraftleItems.createModelJson(id.getPath().split("/")[pathSegments.length - 1]);
        }
        else if (pathSegments[0].equalsIgnoreCase("block")) {
            modelJson = CraftleBlocks.createModelJson(id.getPath().split("/")[pathSegments.length - 1]);
        }
        if ("".equals(modelJson)) {
            return;
        }
        //We check if the json string we get is valid before continuing.
        JsonUnbakedModel model = JsonUnbakedModel.deserialize(modelJson);
        model.id = id.toString();
        cir.setReturnValue(model);
    }
}

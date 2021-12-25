package com.craftlemod.common.mixin;

import com.craftlemod.common.Craftlemod;
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
        if (!Craftlemod.MODID.equals(id.getNamespace())) {
            return;
        }

        // TODO: come up with a better way to split the identifier in to the id
        String modelJson = CraftleItems.createItemModelJson(id.getPath().split("/")[1]);
        if ("".equals(modelJson)) {
            return;
        }
        //We check if the json string we get is valid before continuing.
        JsonUnbakedModel model = JsonUnbakedModel.deserialize(modelJson);
        model.id = id.toString();
        cir.setReturnValue(model);
    }
}

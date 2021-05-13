package com.craftle_mod.data.client;

import com.craftle_mod.common.Craftle;
import com.craftle_mod.common.item.CraftleBlockItem;
import com.craftle_mod.common.registries.CraftleItems;
import com.craftle_mod.common.resource.IResourceType;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class CraftleItemModelProvider extends ItemModelProvider {

    public CraftleItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Craftle.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));

        for (RegistryObject<Item> itemObj : CraftleItems.ITEMS.getEntries()) {
            Item item = itemObj.get();
            if (item instanceof CraftleBlockItem) {
                withExistingParent(item.getRegistryName().toString(), modLoc("block/" + item.getRegistryName().getPath()));
            } else if (item instanceof IResourceType) {
                builder(item.getRegistryName().getPath(),
                    ((IResourceType) item).getType().getName() + "/" + item.getRegistryName().getPath(), itemGenerated);
            } else {
                builder(item.getRegistryName().getPath(), itemGenerated);
            }
        }

    }

    private ItemModelBuilder builder(String name, String path, ModelFile itemGenerated) {
        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + path);
    }

    private ItemModelBuilder builder(String name, ModelFile itemGenerated) {
        return builder(name, name, itemGenerated);
    }
}

package com.craftle_mod.common.tags.util;

import com.craftle_mod.common.resource.ResourceTypes;
import net.minecraft.tags.ITag.INamedTag;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

public class ResourceTag<T> {

    private final ResourceTypes type;
    private final String name;
    private final INamedTag<T> tag;
    private IOptionalNamedTag<T> commonTag;

    public ResourceTag(ResourceTypes type, String name, INamedTag<T> tag) {
        this.type = type;
        this.name = name;
        this.tag = tag;
        commonTag = null;
    }

    public ResourceTypes getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public INamedTag<T> getTag() {
        return tag;
    }

    public IOptionalNamedTag<T> getCommonTag() {
        return commonTag;
    }

    public ResourceTag<T> addCommonTag(IOptionalNamedTag<T> commonTag) {
        this.commonTag = commonTag;
        return this;
    }
}

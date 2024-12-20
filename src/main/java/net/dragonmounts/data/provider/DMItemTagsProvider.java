package net.dragonmounts.data.provider;

import net.dragonmounts.DragonMounts;
import net.dragonmounts.data.tag.DMBlockTags;
import net.dragonmounts.data.tag.DMItemTags;
import net.dragonmounts.init.DMBlocks;
import net.dragonmounts.init.DMItems;
import net.dragonmounts.item.DragonScaleBowItem;
import net.dragonmounts.item.DragonScalesItem;
import net.dragonmounts.registry.DragonType;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class DMItemTagsProvider extends ItemTagsProvider {
    public DMItemTagsProvider(DataGenerator generator, BlockTagsProvider blockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(generator, blockTagsProvider, DragonMounts.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(Tags.Items.SHEARS)
                .add(DMItems.DIAMOND_SHEARS)
                .add(DMItems.NETHERITE_SHEARS);
        this.tag(ItemTags.PIGLIN_LOVED)
                .add(DMItems.GOLDEN_DRAGON_ARMOR);
        this.tag(ItemTags.PIGLIN_REPELLENTS)
                .add(DMBlocks.DRAGON_CORE.asItem());
        this.tag(DMItemTags.BATONS)
                .addTag(Tags.Items.RODS_WOODEN)
                .addTag(Tags.Items.BONES)
                .add(Items.BAMBOO);
        this.copy(DMBlockTags.DRAGON_EGGS, DMItemTags.DRAGON_EGGS);
        Consumer<Item> addToBows = this.tag(DMItemTags.DRAGON_SCALE_BOWS)::add;
        Consumer<Item> addToScales = this.tag(DMItemTags.DRAGON_SCALES)::add;
        for (DragonType type : DragonType.REGISTRY) {
            type.ifPresent(DragonScaleBowItem.class, addToBows);
            type.ifPresent(DragonScalesItem.class, addToScales);
        }
    }
}
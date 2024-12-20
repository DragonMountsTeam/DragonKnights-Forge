package net.dragonmounts.init;

import net.dragonmounts.DragonMounts;
import net.dragonmounts.block.DragonCoreBlock;
import net.dragonmounts.block.DragonNestBlock;
import net.dragonmounts.block.HatchableDragonEggBlock;
import net.dragonmounts.block.entity.DragonCoreBlockEntity;
import net.dragonmounts.registry.DragonType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Rarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.ToIntFunction;

import static net.dragonmounts.init.DMItemGroups.block;
import static net.dragonmounts.init.DMItems.ITEMS;
import static net.minecraft.block.AbstractBlock.Properties.of;

public class DMBlocks {
    static final ToIntFunction<BlockState> DRAGON_EGG_LUMINANCE = $ -> 1;
    public static final DeferredRegister<Block> BLOCKS = DragonMounts.create(ForgeRegistries.BLOCKS);
    public static final HatchableDragonEggBlock AETHER_DRAGON_EGG = registerDragonEgg("aether_dragon_egg", DragonTypes.AETHER, MaterialColor.COLOR_LIGHT_BLUE, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock ENCHANT_DRAGON_EGG = registerDragonEgg("enchant_dragon_egg", DragonTypes.ENCHANT, MaterialColor.COLOR_PURPLE, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock ENDER_DRAGON_EGG = registerDragonEgg("ender_dragon_egg", DragonTypes.ENDER, MaterialColor.COLOR_BLACK, block().rarity(Rarity.EPIC));
    public static final HatchableDragonEggBlock FIRE_DRAGON_EGG = registerDragonEgg("fire_dragon_egg", DragonTypes.FIRE, MaterialColor.FIRE, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock FOREST_DRAGON_EGG = registerDragonEgg("forest_dragon_egg", DragonTypes.FOREST, MaterialColor.COLOR_GREEN, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock ICE_DRAGON_EGG = registerDragonEgg("ice_dragon_egg", DragonTypes.ICE, MaterialColor.SNOW, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock MOONLIGHT_DRAGON_EGG = registerDragonEgg("moonlight_dragon_egg", DragonTypes.MOONLIGHT, MaterialColor.COLOR_BLUE, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock NETHER_DRAGON_EGG = registerDragonEgg("nether_dragon_egg", DragonTypes.NETHER, MaterialColor.NETHER, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock SCULK_DRAGON_EGG = registerDragonEgg("sculk_dragon_egg", DragonTypes.SCULK, MaterialColor.COLOR_BLACK, block().fireResistant().rarity(Rarity.RARE));
    public static final HatchableDragonEggBlock SKELETON_DRAGON_EGG = registerDragonEgg("skeleton_dragon_egg", DragonTypes.SKELETON, MaterialColor.QUARTZ, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock STORM_DRAGON_EGG = registerDragonEgg("storm_dragon_egg", DragonTypes.STORM, MaterialColor.WOOL, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock SUNLIGHT_DRAGON_EGG = registerDragonEgg("sunlight_dragon_egg", DragonTypes.SUNLIGHT, MaterialColor.COLOR_YELLOW, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock TERRA_DRAGON_EGG = registerDragonEgg("terra_dragon_egg", DragonTypes.TERRA, MaterialColor.DIRT, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock WATER_DRAGON_EGG = registerDragonEgg("water_dragon_egg", DragonTypes.WATER, MaterialColor.WATER, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock WITHER_DRAGON_EGG = registerDragonEgg("wither_dragon_egg", DragonTypes.WITHER, MaterialColor.COLOR_GRAY, block().rarity(Rarity.UNCOMMON));
    public static final HatchableDragonEggBlock ZOMBIE_DRAGON_EGG = registerDragonEgg("zombie_dragon_egg", DragonTypes.ZOMBIE, MaterialColor.TERRACOTTA_GREEN, block().rarity(Rarity.UNCOMMON));
    public static final DragonNestBlock DRAGON_NEST;
    public static final DragonCoreBlock DRAGON_CORE;

    static {
        BLOCKS.register("dragon_nest", (DRAGON_NEST = new DragonNestBlock())::getBlock);
        final AbstractBlock.IPositionPredicate testCore = ($, level, pos) -> {
            TileEntity entity = level.getBlockEntity(pos);
            return entity instanceof DragonCoreBlockEntity && ((DragonCoreBlockEntity) entity).isClosed();
        };
        BLOCKS.register("dragon_core", (
                DRAGON_CORE = new DragonCoreBlock(of(Material.PORTAL, MaterialColor.COLOR_BLACK)
                        .strength(2000, 600)
                        .sound(DragonCoreBlock.SOUND)
                        .dynamicShape()
                        .noOcclusion()
                        .isSuffocating(testCore)
                        .isViewBlocking(testCore)
                        .noDrops()
                )
        )::getBlock);
    }

    static HatchableDragonEggBlock registerDragonEgg(String name, DragonType type, MaterialColor color, Item.Properties props) {
        HatchableDragonEggBlock block = new HatchableDragonEggBlock(type, of(Material.EGG, color).strength(0.0F, 9.0F).lightLevel(DRAGON_EGG_LUMINANCE).noOcclusion());
        type.bindInstance(HatchableDragonEggBlock.class, block);
        ITEMS.register(name, new BlockItem(block, props)::getItem);
        BLOCKS.register(name, block::getBlock);
        return block;
    }
}
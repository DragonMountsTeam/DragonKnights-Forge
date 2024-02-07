package net.dragonmounts.api;

import net.dragonmounts.init.DMItems;
import net.dragonmounts.init.DragonTypes;
import net.dragonmounts.item.DragonScalesItem;
import net.dragonmounts.registry.DragonType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class DragonScaleTier implements IItemTier, IDragonTypified {
    public static final DragonScaleTier AETHER;
    public static final DragonScaleTier ENCHANT;
    public static final DragonScaleTier ENDER;
    public static final DragonScaleTier FIRE;
    public static final DragonScaleTier FOREST;
    public static final DragonScaleTier ICE;
    public static final DragonScaleTier MOONLIGHT;
    public static final DragonScaleTier NETHER;
    public static final DragonScaleTier SCULK;
    public static final DragonScaleTier STORM;
    public static final DragonScaleTier SUNLIGHT;
    public static final DragonScaleTier TERRA;
    public static final DragonScaleTier WATER;
    public static final DragonScaleTier ZOMBIE;

    static {
        Builder builder = new Builder(4, 2700, 8.0F, 5.0F).setEnchantmentValue(11);
        FIRE = builder.build(DragonTypes.FIRE);
        FOREST = builder.build(DragonTypes.FOREST);
        ICE = builder.build(DragonTypes.ICE);
        MOONLIGHT = builder.build(DragonTypes.MOONLIGHT);
        STORM = builder.build(DragonTypes.STORM);
        SUNLIGHT = builder.build(DragonTypes.SUNLIGHT);
        TERRA = builder.build(DragonTypes.TERRA);
        WATER = builder.build(DragonTypes.WATER);
        ZOMBIE = builder.build(DragonTypes.ZOMBIE);
        ENCHANT = builder.setEnchantmentValue(30).build(DragonTypes.ENCHANT);
        builder = new Builder(5, 3000, 8.0F, 6.0F).setEnchantmentValue(11);
        ENDER = builder.build(DragonTypes.ENDER);
        SCULK = builder.build(DragonTypes.SCULK);
        AETHER = new Builder(5, 2700, 8.0F, 5.0F).setEnchantmentValue(11).build(DragonTypes.AETHER);
        NETHER = new Builder(5, 2700, 8.0F, 6.0F).setEnchantmentValue(11).build(DragonTypes.NETHER);
    }

    public final DragonType type;
    public final int level;
    public final int uses;
    public final float speed;
    public final float damage;
    public final int enchantmentValue;
    public final LazyValue<Ingredient> repairIngredient;

    public DragonScaleTier(DragonType type, Builder builder) {
        this.type = type;
        this.level = builder.level;
        this.uses = builder.uses;
        this.speed = builder.speed;
        this.damage = builder.damage;
        this.enchantmentValue = builder.enchantmentValue;
        this.repairIngredient = builder.repairIngredient == null ? new LazyValue<>(
                () -> Ingredient.of(type.getInstance(DragonScalesItem.class, DMItems.ENDER_DRAGON_SCALES))
        ) : builder.repairIngredient;
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Nonnull
    @Override
    public final Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public final DragonType getDragonType() {
        return this.type;
    }

    public static class Builder {
        public final int level;
        public final int uses;
        public final float speed;
        public final float damage;
        public int enchantmentValue = 1;
        public LazyValue<Ingredient> repairIngredient = null;

        public Builder(int level, int uses, float speed, float damage) {
            this.level = level;
            this.uses = uses;
            this.speed = speed;
            this.damage = damage;
        }

        public Builder setEnchantmentValue(int enchantmentValue) {
            this.enchantmentValue = enchantmentValue;
            return this;
        }

        public Builder setRepairIngredient(Supplier<Ingredient> supplier) {
            this.repairIngredient = new LazyValue<>(supplier);
            return this;
        }

        public DragonScaleTier build(DragonType type) {
            return new DragonScaleTier(type, this);
        }
    }
}

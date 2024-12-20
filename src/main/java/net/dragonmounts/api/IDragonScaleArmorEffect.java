package net.dragonmounts.api;

import net.dragonmounts.capability.ArmorEffectManager;
import net.dragonmounts.capability.IArmorEffectManager;
import net.dragonmounts.registry.CooldownCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts.util.TimeUtil.stringifyTick;

public interface IDragonScaleArmorEffect extends IArmorEffect {
    default void appendTriggerInfo(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> tooltips) {
        tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_piece_4"));
    }

    default void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> tooltips) {}

    class Advanced extends CooldownCategory implements IDragonScaleArmorEffect {
        public final int cooldown;
        protected String description;

        public Advanced(int cooldown) {
            this.cooldown = cooldown;
        }

        protected String getDescription() {
            if (this.description == null) {
                this.description = Util.makeDescriptionId("tooltip.armor_effect", this.getSerializedName());
            }
            return this.description;
        }

        public final void appendCooldownInfo(List<ITextComponent> tooltips) {
            int value = ArmorEffectManager.getLocalCooldown(this);
            if (value > 0) {
                tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_remaining_cooldown", stringifyTick(value)));
            } else if ((value = this.cooldown) > 0) {
                tooltips.add(new TranslationTextComponent("tooltip.dragonmounts.armor_effect_cooldown", stringifyTick(value)));
            }
        }

        @Override
        public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> tooltips) {
            tooltips.add(StringTextComponent.EMPTY);
            this.appendTriggerInfo(stack, world, tooltips);
            tooltips.add(new TranslationTextComponent(this.getDescription()));
            this.appendCooldownInfo(tooltips);
        }

        public Advanced withRegistryName(String name) {
            super.setRegistryName(name);
            return this;
        }

        @Override
        public boolean activate(IArmorEffectManager manager, PlayerEntity player, int level) {
            return level > 3;
        }
    }
}

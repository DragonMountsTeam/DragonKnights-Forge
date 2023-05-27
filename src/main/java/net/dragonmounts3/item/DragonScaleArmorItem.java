package net.dragonmounts3.item;

import net.dragonmounts3.api.DragonScaleMaterial;
import net.dragonmounts3.api.DragonType;
import net.dragonmounts3.api.IDragonTypified;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dragonmounts3.DragonMounts.ITEM_TRANSLATION_KEY_PREFIX;

public class DragonScaleArmorItem extends ArmorItem implements IDragonTypified {
    private static final String[] TRANSLATION_KEY_SUFFIX = new String[]{"boots", "leggings", "chestplate", "helmet"};
    private final String translationKey;
    protected DragonType type;

    public DragonScaleArmorItem(
            DragonScaleMaterial material,
            EquipmentSlotType slot,
            Properties properties
    ) {
        super(material, slot, properties);
        this.type = material.getDragonType();
        this.translationKey = ITEM_TRANSLATION_KEY_PREFIX + "dragon_scale_" + TRANSLATION_KEY_SUFFIX[slot.getIndex()];
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@Nonnull ItemStack stack, @Nullable World world, List<ITextComponent> components, @Nonnull ITooltipFlag flag) {
        components.add(this.type.getText());
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return this.translationKey;
    }

    @Override
    public DragonType getDragonType() {
        return this.type;
    }
}

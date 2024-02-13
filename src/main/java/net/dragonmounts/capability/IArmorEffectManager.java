package net.dragonmounts.capability;

import net.dragonmounts.api.IArmorEffect;
import net.dragonmounts.registry.CooldownCategory;
import net.minecraft.nbt.CompoundNBT;

public interface IArmorEffectManager {

    @SuppressWarnings("UnusedReturnValue")
    int stackLevel(IArmorEffect effect);

    @SuppressWarnings("UnusedReturnValue")
    int setLevel(IArmorEffect effect, int level);

    int getLevel(IArmorEffect effect, boolean filtered);

    boolean isActive(IArmorEffect effect);

    void setCooldown(CooldownCategory category, int cooldown);

    int getCooldown(CooldownCategory category);

    void tick();

    CompoundNBT saveNBT();

    void readNBT(CompoundNBT nbt);

    void sendInitPacket();

    interface Provider {
        default ArmorEffectManager dragonmounts$getManager() {
            throw new NullPointerException();
        }
    }
}

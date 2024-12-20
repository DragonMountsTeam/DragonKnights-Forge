package net.dragonmounts.client.variant;

import net.dragonmounts.entity.dragon.TameableDragonEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.ResourceLocation;

import static net.dragonmounts.DragonMounts.makeId;

public abstract class VariantAppearance {
    public final static String TEXTURES_ROOT = "textures/entities/dragon/";
    public final static ResourceLocation DEFAULT_CHEST = makeId(TEXTURES_ROOT + "chest.png");
    public final static ResourceLocation DEFAULT_SADDLE = makeId(TEXTURES_ROOT + "saddle.png");
    public final static ResourceLocation DEFAULT_DISSOLVE = makeId(TEXTURES_ROOT + "dissolve.png");
    public final float positionScale;
    public final float renderScale;

    public VariantAppearance(float modelScale) {
        this.renderScale = modelScale;
        this.positionScale = modelScale / 16.0F;
    }

    public abstract boolean hasTailHorns(TameableDragonEntity dragon);

    public abstract boolean hasSideTailScale(TameableDragonEntity dragon);

    public abstract boolean hasTailHornsOnShoulder();

    public abstract boolean hasSideTailScaleOnShoulder();

    public abstract ResourceLocation getBody(TameableDragonEntity dragon);

    public abstract RenderType getGlow(TameableDragonEntity dragon);

    public abstract RenderType getDecal(TameableDragonEntity dragon);

    public abstract RenderType getGlowDecal(TameableDragonEntity dragon);

    public abstract RenderType getBodyForShoulder();

    public abstract RenderType getGlowForShoulder();

    public abstract RenderType getBodyForBlock();

    public abstract RenderType getGlowForBlock();

    public ResourceLocation getChest(TameableDragonEntity dragon) {
        return DEFAULT_CHEST;
    }

    public ResourceLocation getSaddle(TameableDragonEntity dragon) {
        return DEFAULT_SADDLE;
    }

    public RenderType getDissolve(TameableDragonEntity dragon) {
        return RenderType.dragonExplosionAlpha(DEFAULT_DISSOLVE, dragon.deathTime / dragon.getMaxDeathTime());
    }
}

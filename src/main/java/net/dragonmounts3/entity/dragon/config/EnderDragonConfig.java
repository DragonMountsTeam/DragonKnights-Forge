package net.dragonmounts3.entity.dragon.config;

import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.dragonmounts3.inits.ModSounds;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;

public class EnderDragonConfig extends DragonConfig {
    public EnderDragonConfig() {
        super();
        addImmunity(DamageSource.MAGIC);
        addImmunity(DamageSource.HOT_FLOOR);
        addImmunity(DamageSource.LIGHTNING_BOLT);
        addImmunity(DamageSource.WITHER);

    }

    @Override
    public void onEnable(TameableDragonEntity dragon) {
    }

    @Override
    public void onDisable(TameableDragonEntity dragon) {
    }

    @Override
    public void onDeath(TameableDragonEntity dragon) {
    }

    @Override
    public SoundEvent getLivingSound(TameableDragonEntity dragon) {
        return ModSounds.ENTITY_DRAGON_BREATHE.get();
    }

    @Override
    public SoundEvent getRoarSoundEvent(TameableDragonEntity dragon) {
        return SoundEvents.ENDER_DRAGON_GROWL;
    }

    @Override
    public boolean canChangeBreed() {
        return false;
    }
/*
    @Override
    public void onLivingUpdate(EntityTameableDragon dragon) {

    }


    @Override
    public void continueAndUpdateBreathing(World world, Vec3d origin, Vec3d endOfLook, BreathNode.Power power, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getBreathAffectedAreaEnd().continueBreathing(world, origin, endOfLook, power, dragon);
        dragon.getBreathHelper().getBreathAffectedAreaEnd().updateTick(world);
    }

    @Override
    public void spawnBreathParticles(World world, BreathNode.Power power, int tickCounter, Vec3d origin, Vec3d endOfLook, EntityTameableDragon dragon) {
        dragon.getBreathHelper().getEmitter().setBeamEndpoints(origin, endOfLook);
        dragon.getBreathHelper().getEmitter().spawnBreathParticlesforEnderDragon(world, power, tickCounter);
    }*/

    @Override
    public double getMaxHealth() {
        return super.getMaxHealth() + 10D;
    }

    @Override
    public BasicParticleType getSneezeParticle() {
        return ParticleTypes.PORTAL;
    }

    @Override
    public BasicParticleType getEggParticle() {
        return ParticleTypes.PORTAL;
    }
}
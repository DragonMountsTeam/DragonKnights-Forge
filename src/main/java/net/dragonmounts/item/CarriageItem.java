package net.dragonmounts.item;

import net.dragonmounts.entity.CarriageEntity;
import net.dragonmounts.registry.CarriageType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityPredicates;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Predicate;

public class CarriageItem extends Item {
    private static final Predicate<Entity> ENTITY_PREDICATE = EntityPredicates.NO_SPECTATORS.and(Entity::isPickable);
    public final CarriageType type;

    public CarriageItem(CarriageType type, Properties props) {
        super(props);
        this.type = type;
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable IRecipeType<?> recipeType) {
        return 1000;
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        RayTraceResult rayTraceResult = getPlayerPOVHitResult(level, player, RayTraceContext.FluidMode.NONE);
        if (rayTraceResult.getType() != RayTraceResult.Type.MISS) {
            Vector3d vector3d = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);
            if (!list.isEmpty()) {
                Vector3d vector3d1 = player.getEyePosition(1.0F);
                for (Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (axisalignedbb.contains(vector3d1)) {
                        return ActionResult.pass(itemstack);
                    }
                }
            }
            if (rayTraceResult.getType() == RayTraceResult.Type.BLOCK) {
                Vector3d vector = rayTraceResult.getLocation();
                CarriageEntity entity = new CarriageEntity(level, vector.x, vector.y, vector.z);
                entity.setCarriageType(this.type);
                entity.yRot = player.yRot;
                if (level.noCollision(entity, entity.getBoundingBox().inflate(-0.1D))) {
                    if (!level.isClientSide) {
                        level.addFreshEntity(entity);
                        if (!player.abilities.instabuild) {
                            itemstack.shrink(1);
                        }
                    }
                    player.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.sidedSuccess(itemstack, level.isClientSide());
                }
                return ActionResult.fail(itemstack);
            }
        }
        return ActionResult.pass(itemstack);
    }
}

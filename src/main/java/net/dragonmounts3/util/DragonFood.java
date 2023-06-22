package net.dragonmounts3.util;

import net.dragonmounts3.api.IDragonFood;
import net.dragonmounts3.entity.dragon.TameableDragonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Hand;

import javax.annotation.Nullable;
import java.util.HashMap;

public class DragonFood implements IDragonFood {
    private static final HashMap<Item, IDragonFood> REGISTRY = new HashMap<>();
    public static final DragonFood RAW_MEAT = new DragonFood(1500, 2);
    public static final DragonFood COOKED_MEAT = new DragonFood(2500, 3);

    public static final IDragonFood HONEY_BOTTLE = (dragon, player, stack, hand) -> {
        if (dragon.isAgeLocked()) {
            dragon.setAgeLocked(false);
        } else {
            dragon.ageUp(100, true);
        }
        dragon.setHealth(dragon.getHealth() + 1);
        if (!player.abilities.instabuild) {
            stack.shrink(1);
            ItemStack resultStack = new ItemStack(Items.GLASS_BOTTLE);
            if (!player.inventory.add(resultStack)) {//PlayerInventory.getFreeSlot() won't check the offhand slot
                if (stack.isEmpty()) {
                    player.setItemInHand(hand, resultStack);
                } else {
                    player.drop(resultStack, false);
                }
            }
        }
    };

    public static final IDragonFood POISONOUS_POTATO = (dragon, player, stack, hand) -> {
        if (!dragon.isAgeLocked()) {
            dragon.setAgeLocked(true);
        }
        if (!player.abilities.instabuild) {
            stack.shrink(1);
        }
    };

    static {
        bind(Items.COD, RAW_MEAT);
        bind(Items.SALMON, RAW_MEAT);
        bind(Items.TROPICAL_FISH, RAW_MEAT);
        bind(Items.COD_BUCKET, RAW_MEAT);
        bind(Items.SALMON_BUCKET, RAW_MEAT);
        bind(Items.TROPICAL_FISH_BUCKET, RAW_MEAT);
        bind(Items.CHICKEN, RAW_MEAT);
        bind(Items.BEEF, RAW_MEAT);
        bind(Items.MUTTON, RAW_MEAT);
        bind(Items.RABBIT, RAW_MEAT);
        bind(Items.PORKCHOP, RAW_MEAT);
        bind(Items.COOKED_SALMON, COOKED_MEAT);
        bind(Items.COOKED_COD, COOKED_MEAT);
        bind(Items.COOKED_CHICKEN, COOKED_MEAT);
        bind(Items.COOKED_BEEF, COOKED_MEAT);
        bind(Items.COOKED_MUTTON, COOKED_MEAT);
        bind(Items.RABBIT_STEW, COOKED_MEAT);
        bind(Items.COOKED_RABBIT, COOKED_MEAT);
        bind(Items.COOKED_PORKCHOP, COOKED_MEAT);
        bind(Items.HONEY_BOTTLE, HONEY_BOTTLE);
        bind(Items.POISONOUS_POTATO, POISONOUS_POTATO);
    }

    public static void bind(Item item, IDragonFood food) {
        if (REGISTRY.containsKey(item)) {
            throw new IllegalArgumentException();
        }
        REGISTRY.put(item, food);
    }

    @Nullable
    public static IDragonFood get(Item item) {
        if (item instanceof IDragonFood) {
            return (IDragonFood) item;
        }
        if (REGISTRY.containsKey(item)) {
            return REGISTRY.get(item);
        }
        return null;
    }

    public static boolean test(Item item) {
        return item instanceof IDragonFood || REGISTRY.containsKey(item);
    }

    public final int age;
    public final int health;

    public DragonFood(int age, int health) {
        this.age = age;
        this.health = health;
    }

    @Override
    public void eat(TameableDragonEntity dragon, PlayerEntity player, ItemStack stack, Hand hand) {
        Item item = stack.getItem();
        if (this.age != 0) {
            dragon.ageUp(this.age, true);
        }
        if (this.health != 0) {
            dragon.setHealth(dragon.getHealth() + this.health);
        }
        if (!player.abilities.instabuild) {
            Item result = null;
            if (item instanceof SoupItem) {
                result = Items.BOWL;
            } else if (item instanceof BucketItem) {
                result = Items.BUCKET;
            }
            stack.shrink(1);
            if (result != null) {
                ItemStack resultStack = new ItemStack(result);
                if (!player.inventory.add(resultStack)) {//PlayerInventory.getFreeSlot() won't check the offhand slot
                    if (stack.isEmpty()) {
                        player.setItemInHand(hand, resultStack);
                    } else {
                        player.drop(resultStack, false);
                    }
                }
            }
        }
    }
}
package mineward.utils.features;

import config.practical.manager.ConfigValue;
import mineward.utils.utils.location.Location;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EntityHandler {

    private static final int REFRESH_TICK = 20;
    private static final String CRYSTAL_STRING = "Ancient Crystal";

    @ConfigValue
    public static boolean highlightCrystals = true;

    @ConfigValue
    public static int crystalColor = 0xffff0000;

    @ConfigValue
    public static boolean highlightChests = true;

    @ConfigValue
    public static int chestColor = 0xffff00ff;


    private static volatile ArrayList<Vec3d> crystals = new ArrayList<>();
    private static volatile ArrayList<Vec3d> chests = new ArrayList<>();

    private static int tick = 0;

    public static void update(@NotNull MinecraftClient client) {

        tick++;
        if (tick < REFRESH_TICK) return;
        tick = 0;

        ClientWorld world = client.world;
        if (world == null) return;

        ArrayList<Vec3d> tempCrystals = new ArrayList<>();
        ArrayList<Vec3d> tempChests = new ArrayList<>();

        for (Entity entity : client.world.getEntities()) {
            if (entity instanceof ArmorStandEntity armorStand) {
                ItemStack item = armorStand.getEquippedStack(EquipmentSlot.HEAD);
                if (!item.isOf(Items.PLAYER_HEAD)) continue;

                Text text = entity.getName();
                if (text.getString().contains(CRYSTAL_STRING)) {
                    tempCrystals.add(armorStand.getPos());
                } else if (Location.inAncient()) {
                    tempChests.add(armorStand.getPos());
                }

            }
        }

        crystals = tempCrystals;
        chests = tempChests;
    }

    public static void clear() {
        crystals.clear();
        chests.clear();
    }

    public static ArrayList<Vec3d> getCrystals() {
        return crystals;
    }

    public static ArrayList<Vec3d> getChests() {
        return chests;
    }
}

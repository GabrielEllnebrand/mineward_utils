package mineward.utils.render;

import java.util.HashMap;

import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.TypedActionResult;

public class Cooldown {

    public static HashMap<Ability, String> activeCooldowns = new HashMap<>();

    public static void register() {

        UseItemCallback.EVENT.register((player, world, hand) -> {
            checkRightClickItems(player.getMainHandStack().getName().toString());
            return TypedActionResult.pass(ItemStack.EMPTY);
        });
        
    }

    /**
     * runs the functions that run on tick
     * @param client Minecraft client
     */
    public static void runTickFunctions(MinecraftClient client){
        updateTime(client.getTickDelta());

        if(client.player == null){
            return;
        }
        
        if(client.player.handSwinging){
            checkLeftClickItems(client.player.getMainHandStack().getName().toString());
        }
    }

    /**
     * Checks if the used items name matches any that have a cooldown
     * 
     * @param item
     */
    public static void checkRightClickItems(String item) {
        if (item.contains("Dwarven Axe")) {
            add("Anchored", 3, 14, 0xffeef26a);
        } else if (item.contains("Arngrim Greataxe")) {
            add("Vortex of Rage", 15, 15, 0xff922b21);
        } else if (item.contains("Snowy Cauldron")) {
            add("Cauldron", 10, 10, 0xff5dade2);
        } else if (item.contains("Stoneheart Sledgehammer")) {
            add("Anchored", 3, 20, 0xffaeb6bf);
        } else if (item.contains("Warhammer of the Stone Order")) {
            add("Anchored", 3, 15, 0xfff7dc6f);
        } else if (item.contains("Templar Sword")) {
            add("Templar Cross", 8, 12, 0xffd5dbdb);
        } else if (item.contains("Excalibur")) {
            add("For the King", 15, 12, 0xfff1c40f);
        } else if (item.contains("Magmurrd")) {
            add("Whirl of Eternal Flames", 0, 1, 0xffa93226);
        } else if (item.contains("Apollark")) {
            add("Northern Wind", 5, 20, 0xff3498db);
        } else if (item.contains("Gatekeeper's Spellbook")) {
            add("Blaze power", 0, 10, 0xffcc4e35);
        }
    }

    /**
     * Checks if the swung items name matches any that have a cooldown
     * 
     * @param item
     */
    public static void checkLeftClickItems(String item) {
        if (item.contains("Desert Winds")) {
            add("Burrow", 0, 5, 0xffcc4e35);
        } else if (item.contains("Deathbloom")) {
            add("Entanglement", 4, 12, 0xffcc4e35);
        } else if (item.contains("Elderbomb")) {
            add("Ancient Judgement", 0, 7, 0xff95a5a6);
        }
    }

    /**
     * Adds the ability to cooldowns if there is no duplicate
     * 
     * @param name     String
     * @param duration double
     * @param cooldown double
     */
    public static void add(String name, double duration, double cooldown, int color) {
        if (!activeCooldowns.containsValue(name)) {
            activeCooldowns.put(new Ability(duration, cooldown, color), name);
        }
    }

    /**
     * Updates the time for all activeAbilities and removes if cooldown is over
     * 
     * @param tickDelta Double
     */
    public static void updateTime(double tickDelta) {
        try {
            if (activeCooldowns.isEmpty()) {
                return;
            }
            activeCooldowns.forEach((ability, name) -> {
                ability.increaseTime(tickDelta);
                if (ability.CooldownOver()) {
                    activeCooldowns.remove(ability);
                }
            });
        } catch (Exception e) {

        }
    }

}

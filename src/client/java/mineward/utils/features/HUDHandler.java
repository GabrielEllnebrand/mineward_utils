package mineward.utils.features;

import config.practical.manager.ConfigValue;

public class HUDHandler {

    @ConfigValue
    public static boolean hideHunger = false;

    @ConfigValue
    public static boolean hideArmourBar = false;

    @ConfigValue
    public static boolean hideHearts = false;

    @ConfigValue
    public static boolean hideActionBar = false;

    @ConfigValue
    public static boolean renderHealthBar = false;

    @ConfigValue
    public static boolean renderHealthNumber = false;

    @ConfigValue
    public static boolean renderManaBar = false;

    @ConfigValue
    public static boolean renderManaNumber = false;

    public static double currentHealth = 0;
    public static double maxHealth = 0;

    public static double currentMana = 0;
    public static double maxMana = 0;

    public static double currentBlaze = 0;
    public static double maxBlaze = 0;

}

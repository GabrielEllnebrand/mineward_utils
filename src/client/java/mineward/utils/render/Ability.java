package mineward.utils.render;

public class Ability {
    private double duration, cooldown, time;
    int hexColor;
    public Ability(double duration, double cooldown, int hexColor){
        this.duration = duration;
        this.cooldown = cooldown;
        this.hexColor = hexColor;
        this.time = 0;
    } 

    public double getDuration() {
        return duration;
    }

    public double getCooldown() {
        return cooldown;
    }

    public double getTime() {
        return time;
    }

    public void increaseTime(double amount){
        this.time += amount;
    }

    public boolean CooldownOver(){
        return time > cooldown;
    }
}

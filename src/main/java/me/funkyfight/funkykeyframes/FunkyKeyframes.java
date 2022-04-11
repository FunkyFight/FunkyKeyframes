package me.funkyfight.funkykeyframes;

import org.bukkit.plugin.java.JavaPlugin;

public final class FunkyKeyframes extends JavaPlugin {


    public static JavaPlugin INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

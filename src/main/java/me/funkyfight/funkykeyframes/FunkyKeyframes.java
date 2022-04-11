package me.funkyfight.funkykeyframes;

import me.funkyfight.funkykeyframes.cmd.TestCommand;
import me.funkyfight.funkykeyframes.keyframerelated.Keyframe;
import me.funkyfight.funkykeyframes.keyframerelated.Project;
import org.bukkit.plugin.java.JavaPlugin;

public final class FunkyKeyframes extends JavaPlugin {


    public static JavaPlugin INSTANCE;
    @Override
    public void onEnable() {
        INSTANCE = this;

        getCommand("keyframe-tester").setExecutor(new TestCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package me.funkyfight.funkykeyframes.cmd;

import me.funkyfight.funkykeyframes.keyframerelated.Keyframe;
import me.funkyfight.funkykeyframes.keyframerelated.Project;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicReference;

public class TestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            // Project
            Project project = new Project("test");

            // Keyframe 1
            Location location = player.getLocation();
            Keyframe keyframe = new Keyframe(20*10, Keyframe.Curve.EASE_OUT_BOUNCE, value -> {
                System.out.println("K1");
            });

            // Keyframe 2
            Keyframe keyframe2 = new Keyframe(20*3, Keyframe.Curve.EASE_OUT_BOUNCE, value -> {
                System.out.println("K2");
            });

            project.keyframe(keyframe, 0);
            project.keyframe(keyframe2, 10);
            project.play();
        }
        return false;
    }
}

package me.funkyfight.funkykeyframes.keyframerelated;

import me.funkyfight.funkykeyframes.FunkyKeyframes;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class Project {

    private String name;
    private HashMap<Integer, ArrayList<Keyframe>> keyframes;
    private JavaPlugin INSTANCE;

    private ArrayList<Entity> entities;

    public Project(String name, JavaPlugin plugin) {
        this.name = name;
        this.INSTANCE = plugin;
    }

    @FunctionalInterface
    public interface onFinish {
        void onFinish();
    }


    public void keyframe(Keyframe keyframe, Integer timeInSeconds) {
        if (keyframes == null) {
            keyframes = new HashMap<>();
        }
        if (keyframes.get(timeInSeconds) == null) {
            keyframes.put(timeInSeconds, new ArrayList<>());
        }
        keyframes.get(timeInSeconds).add(keyframe);
    }

    public void registerEntity(Entity entity) {
        if(entities == null) {
            entities = new ArrayList<>();
        }
        entities.add(entity);
    }

    public void unregisterEntity(Entity entity) {
        entities.remove(entity);
    }

    public void unregisterAllEntities() {
        entities.clear();
    }

    public ArrayList<Entity> getRegisteredEntities() {
        return entities;
    }

    public void play(onFinish onFinish) {
        // Firstly, we need to get the current time in seconds of the animation
        final int[] timeInSeconds = {0};
        keyframes.forEach((integer, keyframes1) -> {
            for (Keyframe keyframe : keyframes1) {
                if((keyframe.ticks / 20) + integer > timeInSeconds[0]) {
                    timeInSeconds[0] = (keyframe.ticks / 20) + integer;
                }
            }
        });

        // Now, we need to play the animation
        new BukkitRunnable() {

            private int current_tick_playing = 0;

            @Override
            public void run() {
                current_tick_playing++;

                keyframes.forEach((integer, keyframes1) -> {
                    for (Keyframe keyframe : keyframes1) {
                        if(current_tick_playing/20 >= integer) {
                            keyframe.execute();
                        }
                    }
                });

                if(current_tick_playing/20 >= timeInSeconds[0]) {
                    onFinish.onFinish();
                    cancel();
                }
            }
        }.runTaskTimer(INSTANCE, 0, 1);

    }



}

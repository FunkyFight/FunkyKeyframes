package me.funkyfight.funkykeyframes.keyframerelated;

import me.funkyfight.funkykeyframes.FunkyKeyframes;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;

public class Project {

    private String name;
    private HashMap<Integer, ArrayList<Keyframe>> keyframes;

    public Project(String name) {
        this.name = name;
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

    public void play() {
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
                    cancel();
                }
            }
        }.runTaskTimer(FunkyKeyframes.INSTANCE, 0, 1);

    }



}

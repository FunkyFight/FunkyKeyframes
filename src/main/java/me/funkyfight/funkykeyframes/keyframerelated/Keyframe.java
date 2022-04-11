package me.funkyfight.funkykeyframes.keyframerelated;

import static java.lang.Math.PI;
import static java.lang.Math.cos;

public class Keyframe {

    public enum Curve {
        EASE_IN_SINE,
        EASE_IN_QUINT,
        EASE_OUT_BOUNCE
    }

    private Float progression = 0f; // Current progression of this keyframe
    private Float step; // Duration of this keyframe
    public Integer ticks;
    public Curve curve;
    public KeyframeInterface keyframeInterface;
    private afterInterface after;
    private boolean isAfterDone = false;

    public Keyframe(Integer duration, Curve curve, KeyframeInterface onUpdate) {
        this.ticks = duration;
        this.curve = curve;
        this.keyframeInterface = onUpdate;
        this.step = 1f / (float)ticks;
    }

    public Keyframe(Integer duration, Curve curve, KeyframeInterface onUpdate, afterInterface after) {
        this.ticks = duration;
        this.curve = curve;
        this.keyframeInterface = onUpdate;
        this.step = 1f / (float)ticks;
        this.after = after;
    }



    @FunctionalInterface
    public interface KeyframeInterface {
        void update(double value);
    }

    @FunctionalInterface
    public interface afterInterface {
        void after();
    }


    public void execute() {
        // Add step to the current progression
        progression += this.step;

        // If the progression is greater or equal than 1, do nothing
        if (this.progression >= 1) {
            if(!isAfterDone) {
                after.after();
                isAfterDone = true;
            }
            return;
        }

        double newval;

        // Calculate the value of the keyframe
        switch(this.curve) {
            case EASE_IN_SINE:
                keyframeInterface.update(easeInSine(progression + step));
                break;
            case EASE_IN_QUINT:
                keyframeInterface.update(easeInQuint(progression + step));
                break;
            case EASE_OUT_BOUNCE:
                keyframeInterface.update(easeOutBounce(progression + step));
                break;
        }

    }


    // Curve functions
    // Divide 1 by the number of seconds*20 (for ticks) to get the step for each ticks
    // Then multiply by the number of blocks to get the distance
    private Double easeInSine(Float x) {
        return 1 - cos((x * PI) / 2);
    }

    private float easeInQuint(Float x) {
        return x * x * x * x * x;
    }

    private float easeOutBounce(Float x) {
        float n1 = 7.5625f;
        float d1 = 2.75f;

        if (x < 1 / d1) {
            return n1 * x * x;
        } else if (x < 2 / d1) {
            return n1 * (x -= 1.5f / d1) * x + 0.75f;
        } else if (x < 2.5 / d1) {
            return n1 * (x -= 2.25f / d1) * x + 0.9375f;
        } else {
            return n1 * (x -= 2.625f / d1) * x + 0.984375f;
        }
    }
}

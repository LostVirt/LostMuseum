package org.dreambot.behaviour.museum;

import org.dreambot.Main;
import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.dialogues.Dialogues;
import org.dreambot.api.methods.interactive.GameObjects;
import org.dreambot.api.script.Unobfuscated;
import org.dreambot.api.wrappers.interactive.GameObject;
import org.dreambot.framework.Leaf;
import org.dreambot.util.handlers.WalkHandler;
import org.dreambot.util.museum.Display;
import org.dreambot.util.museum.Location;
import org.dreambot.util.museum.Museum;
import org.dreambot.util.time.Timing;

@Unobfuscated
public class InteractPlaque extends Leaf<Main> {
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public int onLoop() {
        if (Dialogues.inDialogue() && Dialogues.continueDialogue()) {
            return Timing.sleepLogNormalInteraction();
        }

        Location location = getLocation();
        if (location != null) {
            Display display = getDisplay(location);
            if (display != null) {
                GameObject plaque = GameObjects.getTopObjectOnTile(display.getTile());
                if (plaque != null && plaque.distance() < 6) {
                    if (plaque.interact("Study")) {
                        MethodProvider.sleepUntil(() -> Museum.getQuizParent() != null && Museum.getQuizParent().isVisible(), 2500 + Timing.sleepLogNormalInteraction());
                    }
                    return Timing.sleepLogNormalSleep();
                }

                WalkHandler.walkTo(6, display.getTile());
                return Timing.sleepLogNormalSleep();
            }

            WalkHandler.walkTo(6, location.area.getCenter());
        }
        return Timing.sleepLogNormalSleep();
    }

    public static Location getLocation() {
        return Museum.completionOrder.keySet().stream().filter(l -> !l.isLocationCompleted()).findFirst().orElse(null);
    }

    public static Display getDisplay(Location location) {
        return Museum.completionOrder.get(location).stream().filter(d -> !d.isCompleted()).findFirst().orElse(null);
    }
}

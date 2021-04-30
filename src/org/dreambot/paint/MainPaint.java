package org.dreambot.paint;

import org.dreambot.Main;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.paint.framework.CustomPaint;
import org.dreambot.paint.framework.PaintInfo;
import org.dreambot.util.API;

import java.awt.*;

public class MainPaint implements PaintInfo {
    ScriptManifest manifest;

    public MainPaint(ScriptManifest manifest) {
        this.manifest = manifest;
    }

    @Override
    public String[] getPaintInfo() {
        return new String[] {
                manifest.name() + " V" + manifest.version(),
                "Current Branch: " + API.currentBranch,
                "Current Leaf: " + API.currentLeaf,
                "Runtime: " + (Main.startTimer != null ? Main.startTimer.formatTime() : "")
        };
    }

    public final CustomPaint getPaint = new CustomPaint(this,
            CustomPaint.PaintLocations.TOP_LEFT_PLAY_SCREEN, new Color[]{new Color(255, 251, 255)},
            "Trebuchet MS",
            new Color[]{new Color(50, 50, 50, 255)},
            new Color[]{new Color(28, 28, 29)},
            1, false, 5, 3, 0);
}

package org.dreambot;

import org.dreambot.api.methods.MethodProvider;
import org.dreambot.api.methods.settings.PlayerSettings;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.behaviour.MuseumBranch;
import org.dreambot.behaviour.OrlandoLeaf;
import org.dreambot.behaviour.museum.InteractPlaque;
import org.dreambot.behaviour.museum.Solve;
import org.dreambot.framework.Tree;
import org.dreambot.paint.MainPaint;
import org.dreambot.util.museum.Display;
import org.dreambot.util.museum.Location;
import org.dreambot.util.museum.Museum;
import org.dreambot.util.time.Timing;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;

@ScriptManifest(category = Category.MINIGAME, author = "LostVirt", name = "LostMuseum", description = "Completes the Varrock Museum Quiz", version = 1)
public class Main extends AbstractScript {

    public static Timer startTimer;
    private MainPaint mainPaint;


    @Override
    public void onStart() {
        startTimer = new Timer();
        mainPaint = new MainPaint(getManifest());

        MethodProvider.log("Generating Random Pattern: ");
        Museum.completionOrder = new LinkedHashMap<Location, List<Display>>(){{
            Museum.getShuffledLocations().forEach(location -> {
                List<Display> displays = Museum.getShuffledDisplays(location);
                StringBuilder builder = new StringBuilder();
                displays.forEach(d -> builder.append(", ").append(d.toString()));
                MethodProvider.log(location + builder.toString());
                put(location, displays);
            });
        }};

        instantiateTree();
    }

    private final Tree<Main> tree = new Tree<>();
    private void instantiateTree() {
        tree.addBranches(
                new OrlandoLeaf(),
                new MuseumBranch().addLeafs(
                        new Solve(),
                        new InteractPlaque()
                )
        );
    }

    @Override
    public int onLoop() {
        if (PlayerSettings.getBitValue(3688) == 1) {
            MethodProvider.log("[COMPLETED] -> Museum Quiz");
            stop();
            return Timing.sleepLogNormalSleep();
        }

        return tree.onLoop();
    }

    private final RenderingHints aa = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    @Override
    public void onPaint(Graphics graphics) {
        Graphics2D gg = (Graphics2D) graphics;
        gg.setRenderingHints(aa);

        gg.setColor(Color.WHITE);
        gg.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
        if (mainPaint != null) mainPaint.getPaint.paint(gg);
    }
}

package org.dreambot.framework;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Unobfuscated;
import org.dreambot.util.API;
import org.dreambot.util.time.Timing;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Unobfuscated
public abstract class Branch<T extends AbstractScript> extends Leaf<T> {

    public final List<Leaf<T>> children;

    public Branch() {
        this.children = new LinkedList<>();
    }


    public Branch<T> addLeafs(Leaf<T>... leaves) {
        Collections.addAll(this.children, leaves);
        return this;
    }


    @Override
    public int onLoop() {
        return children.stream()
                .filter(c -> Objects.nonNull(c) && c.isValid())
                .findAny()
                .map(tLeaf -> {
                    API.currentBranch = this.getClass().getSimpleName();
                    API.currentLeaf = tLeaf.getClass().getSimpleName();
                    return tLeaf.onLoop();
                }).orElse((int) Timing.sleepLogNormalSleep());

    }
}

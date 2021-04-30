package org.dreambot.framework;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Unobfuscated;

@Unobfuscated
public abstract class Leaf<T extends AbstractScript> {
    public abstract boolean isValid();

    public abstract int onLoop();
}

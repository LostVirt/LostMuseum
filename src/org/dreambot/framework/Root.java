package org.dreambot.framework;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Unobfuscated;

@Unobfuscated
public class Root<T extends AbstractScript> extends Branch<T> {
    @Override
    public boolean isValid() {
        return true;
    }
}

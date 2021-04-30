package org.dreambot.framework;

import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Unobfuscated;

import java.util.List;
import java.util.stream.Collectors;

@Unobfuscated
public class Tree<T extends AbstractScript> {
    private final Root<T> root;

    public Tree() {
        root = new Root<>();
    }

    public Leaf<T> addBranches(Leaf<T>... leaves) {
        root.addLeafs(leaves);
        return root;
    }

    public void clear() {
        root.children.clear();
    }

    public List<String> getActiveBranches() {
        return root.children.stream().map(tLeaf -> tLeaf.getClass().getSimpleName()).collect(Collectors.toList());
    }

    public int onLoop() {
        return root.onLoop();
    }
}

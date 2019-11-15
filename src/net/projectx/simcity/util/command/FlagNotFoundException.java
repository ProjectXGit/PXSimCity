package net.projectx.simcity.util.command;

import com.google.common.base.Joiner;

import java.util.List;

public class FlagNotFoundException extends Exception {
    public FlagNotFoundException(List<String> expected, String given) {
        super("Expected " + Joiner.on(" / ").join(expected) + " but got '" + given + "'!");
    }
}


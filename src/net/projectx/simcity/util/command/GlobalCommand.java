package net.projectx.simcity.util.command;


import java.lang.reflect.Method;
import java.util.Arrays;

public abstract class GlobalCommand<S> {
    private CommandSubMap<S> commandMap;
    private Class playerClass;

    public GlobalCommand(PXCommand command, Object function, Method method, Class playerClass) {
        this.commandMap = new CommandSubMap<>(new CommandProcessor<>(this, command, function, method));
        this.playerClass = playerClass;
    }

    public void addSubCommand(PXCommand command, Object function, Method method) {
        commandMap.putCommand(new CommandProcessor<>(this, command, function, method), Arrays.copyOfRange(command.parent(), 1, command.parent().length));
    }

    public void process(S sender, String[] args) {
        commandMap.run(sender, args);
    }

    public Class getPlayerClass() {
        return playerClass;
    }

    public abstract boolean checkPermission(S sender, String permission);
}


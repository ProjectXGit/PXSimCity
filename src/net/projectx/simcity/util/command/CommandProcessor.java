package net.projectx.simcity.util.command;


import com.google.common.base.Defaults;
import com.google.common.base.Joiner;
import net.projectx.simcity.main.Data;
import net.projectx.simcity.util.TypeUtils;
import net.projectx.simcity.util.executor.ThreadExecutor;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class CommandProcessor<S> {
    private GlobalCommand<S> masterCommand;
    private PXCommand command;
    private Object function;
    private Method method;
    private Class[] methodParameters;

    public CommandProcessor(GlobalCommand<S> masterCommand, PXCommand command, Object function, Method method) {
        this.masterCommand = masterCommand;
        this.command = command;
        this.function = function;
        this.method = method;
        this.methodParameters = method.getParameterTypes();
    }

    public void prepareProcess(S sender, int state, String[] args) {
        FlagList.FilterResult flags = null;
        try {
            flags = FlagList.generate(command, args);
        } catch (FlagNotFoundException e) {
            // masterCommand.getDispatcher().warning(sender, "warning.wrongflag", masterCommand.getDispatcher().buildList(command.flags()));
        }
        List<String> wildcards = new LinkedList<>();
        for (int i = 0; i < command.parent().length; i++) {
            String parentCommand = command.parent()[i];
            if (parentCommand.equals("%")) {
                wildcards.add(args[i - 1]);
            }
        }
        if (command.name().equals("%")) {
            wildcards.add(args[args.length - 1]);
        }
        process(sender, flags, wildcards.size(), TypeUtils.appendArrays(wildcards.toArray(new String[wildcards.size()]), Arrays.copyOfRange(args, state, args.length)));
    }

    public void process(S sender, FlagList.FilterResult flags, int wildcards, String[] args) {
        if (command.noConsole() && !masterCommand.getPlayerClass().isInstance(sender)) {
            System.out.println("This command isn't executable for the Console!");
            return;
        }

        if (command.requiresConsole() && masterCommand.getPlayerClass().isInstance(sender)) {
            ((Player) sender).sendMessage(Data.consoleonly);
            return;
        }

        //Permission checken
        boolean hasPermission = false;
        if (command.permissions().length > 0) {
            for (String permission : command.permissions()) {
                if (masterCommand.checkPermission(sender, permission) || masterCommand.checkPermission(sender, "px." + permission)) {
                    hasPermission = true;
                    break;
                }
            }
        } else {
            hasPermission = true;
            /*
            List<String> permParts = new ArrayList<>(Arrays.asList(command.parent()));
            permParts.add(command.name());
            String permission = permParts.stream().map(perm -> perm.equals("%") ? "any" : perm).collect(Collectors.joining("."));
            hasPermission = masterCommand.checkPermission(sender, "aw." + permission);
            */
        }
        if (!hasPermission) {
            ((Player) sender).sendMessage(Data.noperm);
            return;
        }

        //Parameter mit denen der command aufgerufen wird
        final Object[] params = new Object[methodParameters.length];
        params[0] = sender;
        int start = 0;

        //Flags filtern
        if (flags != null) {
            params[1] = flags.getFlags();
            args = flags.getArgs();
            start = 1;
        }

        //Argumentanzahl
        if (command.minArgs() > -1 && args.length - wildcards < command.minArgs() || command.maxArgs() > -1 && args.length - wildcards > command.maxArgs()) {
            ((Player) sender).sendMessage("§cBenutzung: " + command.usage());
            return;
        }

        int j = 0;

        //Parameter nach Typ filtern
        for (int i = start + 1; i < methodParameters.length; i++) {
            try {
                if (j < args.length) {
                    if (methodParameters[i] == String.class) {
                        params[i] = args[j];
                    } else {
                        params[i] = TypeUtils.convert(methodParameters[i], args[j]);
                    }
                } else {
                    params[i] = Defaults.defaultValue(methodParameters[i]);
                }
                j++;
            } catch (Exception ex) {
                ((Player) sender).sendMessage("§cBenutzung: " + command.usage());
                return;
            }
        }

        //übrige Argumente zu einem String zusammenfügen
        if (args.length > methodParameters.length - (2 + start) && methodParameters[methodParameters.length - 1] == String.class) {
            params[methodParameters.length - 1] = Joiner.on(' ').join(Arrays.asList(args).subList(methodParameters.length - (2 + start), args.length));
        }

        //command Ausführen
        if (command.runAsync()) {
            ThreadExecutor.executeAsync(() -> execute(sender, params));
        } else {
            execute(sender, params);
        }
    }

    private void execute(S sender, Object[] params) {
        try {
            Object result = method.invoke(function, params);
            if (result instanceof Boolean) {
                boolean booleanResult = (boolean) result;
                if (!booleanResult) {
                    ((Player) sender).sendMessage("§cBenutzung: " + command.usage());
                }
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public PXCommand getCommand() {
        return command;
    }
}


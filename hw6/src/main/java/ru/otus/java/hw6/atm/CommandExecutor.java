package ru.otus.java.hw6.atm;

import java.util.SortedMap;
import java.util.TreeMap;

public class CommandExecutor {

    private int currentEventId;

    SortedMap<Integer, Command> state = new TreeMap<>();

    public void execute(Command command) {
        command.execute();
        state.put(currentEventId++, command);
    }

    public void resetToEventId(int eventId) {
        state = state.headMap(eventId);
    }

    public int getBalanceForAtm(Atm atm) {
        atm.toInitialStateInternal();
        state.values()
            .stream()
            .filter(command -> command.getAtm() == atm)
            .forEach(
                command -> command.execute()
            );
        return atm.getInternalBalance();
    }
}

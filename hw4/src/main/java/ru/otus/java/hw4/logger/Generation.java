package ru.otus.java.hw4.logger;

public enum Generation {
    YOUNG("end of minor GC"),
    OLD("end of major GC");

    private String gcMesage;

    Generation(String logString) {
        this.gcMesage = logString;
    }

    public static Generation parseGeneration(String gcMesage) {
        if (YOUNG.gcMesage.equals(gcMesage)) {
            return YOUNG;
        }
        if (OLD.gcMesage.equals(gcMesage)) {
            return OLD;
        }
        else return null;
    }
}
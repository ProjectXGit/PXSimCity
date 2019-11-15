package net.projectx.simcity.util.executor;

public class SyncExecutionException extends Exception {
    private final String info;

    public SyncExecutionException(String info) {
        this.info = info;
    }

    public void printInfo() {
        System.out.println("[AtomicsWorld] Reporting... " + info + " Stack Trace is next");
        printStackTrace();
        System.out.println("[AtomicsWorld] Finished reporting");
    }
}

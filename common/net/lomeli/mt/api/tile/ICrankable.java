package net.lomeli.mt.api.tile;

public interface ICrankable {
    public void doWork();

    public void incrementStep();

    public int getSteps();

    public void setSteps(int steps);
}

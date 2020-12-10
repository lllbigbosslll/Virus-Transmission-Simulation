package Simulation;

public class Person {
    int x;
    int y;
    boolean isPositive = false;
    boolean isHealed = false;
    int positiveRemainDays = 0;

    public Person(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isPositive() {
        return isPositive;
    }

    public boolean isHealed() {
        return isHealed;
    }

    public int getPositiveRemainDays() {
        return positiveRemainDays;
    }
}
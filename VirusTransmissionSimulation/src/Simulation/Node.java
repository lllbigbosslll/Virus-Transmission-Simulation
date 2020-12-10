package Simulation;

public class Node{
    boolean flag;
    Person person = null;
    int x, y;
    public Node(int x, int y, boolean flag){
        this.x = x;
        this.y = y;
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }

    public Person getPerson() {
        return person;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
/**
 * Created by pawel on 05.02.15.
 */
public class Event {
    private double time;
    private Type type;

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    private boolean isUsed;

    public Event(double occurTime,Type type){
        time = occurTime;
        this.type = type;
        isUsed = false;
    }

    public void writeEvent(){
        System.out.println("Time: "+time+" type: "+type);
    }

    public double getTime(){
        return time;
    }

    public Type getType(){
        return type;
    }
}

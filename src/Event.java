/**
 * Created by pawel on 05.02.15.
 */
public class Event {
    private double time;
    private Type type;

    public Event(double occurTime,Type type){
        time = occurTime;
        this.type = type;
    }

    public void writeEvent(){
        System.out.println("Time: "+time+" type: "+type);
    }

    public double getTime(){
        return time;
    }


}

import java.util.Random;

/**
 * Created by pawel on 05.02.15.
 */
public class Source implements Comparable<Source> {
    private double cycleTime;
    private double startTime;
    private double lastCycleStartTime;
    private int numberOfEventsInCycle;
    private int currentEventNumber;
    private double packageTime;
    private Event event;


    public Source(){
        cycleTime = Parameters.getCycleTime();
        startTime = generateStartTime();
        lastCycleStartTime = startTime;
        numberOfEventsInCycle = Parameters.getNumberOfPackagesInCycle();
        packageTime = Parameters.getPackageTime();
        currentEventNumber = 0;
        generateNextEvent();
    }



    private double generateStartTime(){
        Random r = new Random();
        return r.nextDouble()*cycleTime;
    }

    public void generateNextEvent(){
        if(currentEventNumber<numberOfEventsInCycle){
            event = new Event(lastCycleStartTime+packageTime*currentEventNumber,Type.PACKAGE_ARRIVE);
            currentEventNumber++;
        } else {
            lastCycleStartTime+=cycleTime;
            currentEventNumber=1;
            event = new Event(lastCycleStartTime,Type.PACKAGE_ARRIVE);
        }

    }

    public void writeSource(){
        System.out.println("Cycle time: "+ cycleTime+" startTime: "+startTime+" Last cycle start time: "+lastCycleStartTime);
    }

    public Source firstSource() {
        startTime=0;
        lastCycleStartTime=startTime;
        event = new Event(0,Type.PACKAGE_ARRIVE);
        return this;
    }

    public Event getEvent(){
        return event;
    }

    public boolean isBefore(Source src){
        return event.getTime()<src.getEvent().getTime();
    }

    public Event getEventAndGenerateNew(){
        Event ret = event;
        generateNextEvent();
        return ret;
    }

    @Override
    public int compareTo(Source source) {
        return event.getTime() < source.getEvent().getTime() ? -1 : (event.getTime() == source.getEvent().getTime() ? 0 : 1);
    }
}

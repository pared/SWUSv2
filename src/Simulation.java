
import java.util.*;

/**
 * Created by pawel on 05.02.15.
 */
public class Simulation {

    private static List<Source> sources;

    private double timer;
    private Queue queue;

    public void run() throws Exception{
        for (int i =0;i<Parameters.getNumberOfSimulations();i++){
            initialize();
            simulate();
        }
        Parameters.writeSimulationStats();
    }

    private void initialize(){
        sources = new ArrayList<Source>();
        queue = new Queue(Parameters.getPackageServiceTime(),Parameters.getQueueSize());
        timer = 0;
        for (int i = 0;i<Parameters.getNumberOfFlows();i++){
            sources.add(i !=0 ? new Source() : (new Source()).firstSource());
        }
    }
    private void simulate(){
        while(timer<Parameters.getSimulationTime()){
            Event evt=getNextEvent();
            //evt.writeEvent();
            handleEvent(evt);
            Parameters.packageSent();
            timer = evt.getTime();
        }

    }

    private void handleEvent(Event event){
        switch (event.getType()){
            case PACKAGE_ARRIVE:
                queue.handleArrivingPackage(event);
                break;
            case END_OF_SERVICE:
                queue.handleEndOfService(event);
                break;
        }
    }

    // getNextEvent gets next event from sources and eventlist in time order its NOT the same function as Source.getNextEvent()
    private Event getNextEvent() {
        Source nextSourceInTimeOrder = getNextSourceInTimeOrder();

        return compareNextSourceEventWithQueueEvent(nextSourceInTimeOrder);
    }

    private Event compareNextSourceEventWithQueueEvent(Source source) {
        if(queue.haveEvent()){
            return queue.getEvent().getTime()<source.getEvent().getTime() ? queue.getEventAndRemoveFromQueue() : source.getEventAndGenerateNew();
        } else {
            return source.getEventAndGenerateNew();
        }

    }


    private Source getNextSourceInTimeOrder(){
        return Collections.min(sources);

    }



    public double getTimer(){
        return timer;
    }
}

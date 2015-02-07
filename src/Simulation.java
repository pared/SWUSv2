
import java.util.*;

/**
 * Created by pawel on 05.02.15.
 */
public class Simulation {

    private static List<Source> sources=new ArrayList<Source>();
    private static List<Event> events=new ArrayList<Event>();
    private double timer;

    public void run() throws Exception{
        for (int i =0;i<Parameters.getNumberOfSimulations();i++){
            initialize();
            simulate();
            getSimulationStats();
        }
        System.out.println(Parameters.packagesSent);
    }

    private void initialize(){
        timer = 0;
        for (int i = 0;i<Parameters.getNumberOfFlows();i++){
            sources.add(i !=0 ? new Source() : (new Source()).firstSource());
            //sources.get(i).writeSource();
        }
    }
    private void simulate(){
        while(timer<Parameters.getSimulationTime()){
            Event evt=getNextEvent();
            //evt.writeEvent();
            Parameters.packageSent();
            timer = evt.getTime();
        }

    }
    // getNextEvent gets next event from sources and eventlist in time order its NOT the same function as Source.getNextEvent()
    private Event getNextEvent() {
        Source nextSourceInTimeOrder = getNextSourceInTimeOrder();
        //TODO remember about eventList
        return nextSourceInTimeOrder.getEventAndGenerateNew();

    }

    private Source getNextSourceInTimeOrder(){
        Source ret = Collections.min(sources);
        return ret;
    }

    private void getSimulationStats(){

    }
}

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pawel on 08.02.15.
 */
public class Queue {
    private List<Pack> packages;
    private double packageServiceTime;
    private int maxSize;
    private boolean isBusy;
    private Event event;

    public Queue(double packageHandlingTime,int maxSize){
        packages = new ArrayList<Pack>();
        this.packageServiceTime = packageHandlingTime;
        this.maxSize = maxSize;
        isBusy = false;

    }



    public void handleArrivingPackage(Event event) {
        if(isBusy){
            if (packages.size()<maxSize){
                packages.add(new Pack(event.getTime()));
            } else {
                Parameters.packageLost();
            }
        } else {
            this.event = new Event(event.getTime()+packageServiceTime,Type.END_OF_SERVICE);
            isBusy = true;
        }
    }


    public void handleEndOfService(Event event){
        if(isBusy){
            isBusy=false;
            if(packages.isEmpty()){

            } else {
                handleFirstPackage(event.getTime());
                isBusy = true;
            }
        } else {

        }
    }

    private void handleFirstPackage(double lastPackageEndOfServiceTime){
        Parameters.addDelay(lastPackageEndOfServiceTime-packages.get(0).getArrivalTime());
        event = new Event(lastPackageEndOfServiceTime+packageServiceTime,Type.END_OF_SERVICE);
        packages.remove(0);
    }

    public Event getEvent(){
        return event;
    }

    public Event getEventAndRemoveFromQueue(){
        event.setUsed(true);
        return event;
    }

    public boolean isBusy(){
        return isBusy;
    }


    public boolean haveEvent(){
        if(event!=null)
        return !event.isUsed();
        return false;
    }


}

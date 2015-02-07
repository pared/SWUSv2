import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Parameters {
    private static long endOfSimulation;
    private static int numberOfFlows;
    private static int C;
    private static int queueSize;
    private static int bitPerPackage;
    private static int packagesPerCycle;
    private static int t_on;
    private static int t_off;
    private static int numberOfSimulations;
    private static double averageRate;
    private static double peakRate;
    private static double packageTime;

    public static int packagesSent=0;


    public static void parseSimulationParameters(String s) throws Exception {
        InputStream stream=inputStreamFrom(s);
        Scanner scanner = new Scanner(stream);
        while(scanner.hasNext()){
            String line=scanner.nextLine();
            setParameter(line);
        }
        countBitRatesAndPackageTime();
    }

    private static void countBitRatesAndPackageTime() {
        peakRate = bitPerPackage*packagesPerCycle/t_on*1000000;
        averageRate = peakRate/(t_on+t_off)*t_on;
        packageTime = t_on/packagesPerCycle;
    }

    private static void setParameter(String line){
        if (line.contains("end_of_simulation[us]")){
            endOfSimulation = Long.parseLong(line.split(" ")[1]);
        } else if (line.contains("number_of_flows")){
            numberOfFlows = Integer.parseInt(line.split(" ")[1]);
        } else if(line.contains("C[bit/s]")){
            C = Integer.parseInt(line.split(" ")[1]);
        } else if(line.contains("queue_size")){
            queueSize = Integer.parseInt(line.split(" ")[1]);
        } else if (line.contains("bit_per_package")){
            bitPerPackage = Integer.parseInt(line.split(" ")[1]);
        } else if (line.contains("packages_per_cycle")){
            packagesPerCycle = Integer.parseInt(line.split(" ")[1]);
        } else if (line.contains("time_ON[us]")){
            t_on = Integer.parseInt(line.split(" ")[1]);
        } else if (line.contains("time_OFF[us]")){
            t_off = Integer.parseInt(line.split(" ")[1]);
        } else if (line.contains("number_of_simulations")){
            numberOfSimulations = Integer.parseInt(line.split(" ")[1]);
        }

    }

    private static FileInputStream inputStreamFrom(String s)throws Exception{
        URL resource = Parameters.class.getResource(s);
        File file = new File(resource.getFile());
        FileInputStream inputStream = new FileInputStream(file);
        return inputStream;
    }

    public static void writeParameters(){
        System.out.println("Bits per package: "+bitPerPackage+" bit");
        System.out.println("Packages per cycle: "+packagesPerCycle);
        System.out.println("T_ON: "+t_on+" [us]");
        System.out.println("T_OFF: "+t_off+" [us]");
        System.out.println("Number of flows: "+numberOfFlows);
        System.out.println("Single flow Average rate: "+averageRate/1000000+" [Mbit/s]");
        System.out.println("Single flow Peak rate: "+peakRate/1000000+" [Mbit/s]");
        System.out.println("Server C: "+C/1000000+" [Mbit/s]");
        System.out.println("Queue size: "+queueSize);
        System.out.println("End of simulation time: "+endOfSimulation/1000000+" s");
        System.out.println("Number of simulations: "+numberOfSimulations);
    }

    public static double getCycleTime(){
        return (double)t_on+t_off;
    }

    public static int getNumberOfSimulations(){
        return numberOfSimulations;
    }

    public static int getNumberOfFlows(){
        return numberOfFlows;
    }

    public static int getNumberOfPackagesInCycle() {
        return packagesPerCycle;
    }
    public static long getSimulationTime(){
        return endOfSimulation;
    }

    public static double getPackageTime(){
        return packageTime;
    }
    public static void packageSent(){
        packagesSent++;
    }
}

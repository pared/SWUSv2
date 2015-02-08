public class Main {

    public static void main(String[] args) throws Exception {
        Parameters.parseSimulationParameters("/res/file.in");
        Parameters.writeParameters();
        Simulation sim = new Simulation();
        sim.run();
    }
}

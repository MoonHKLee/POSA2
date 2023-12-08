import dispatcher.ReactorInitializer;
import nio.ProactorInitializer;

public class ServerInitializer {
    public static void main(String[] args) {
        ProactorInitializer proactorServer = new ProactorInitializer();
//        proactorServer.startServer();
        ReactorInitializer reactorServer = new ReactorInitializer();
        reactorServer.startServer();
    }
}

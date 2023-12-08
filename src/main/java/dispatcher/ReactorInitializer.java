package dispatcher;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.File;
import java.util.List;

public class ReactorInitializer {
    public void startServer() {
        int port = 5000;
        System.out.println("Server ON : " + port);

        Reactor reactor = new Reactor(port);

        try {
            Serializer serializer = new Persister();
            File source = new File("src/main/resources/HandlerList.xml");
            ServerListData serverListData = serializer.read(ServerListData.class, source);

            for (HandlerListData handlerListData : serverListData.getServer()) {
                if ("server1".equals(handlerListData.getName())) {
                    List<HandlerData> handlerList = handlerListData.getHandler();
                    for (HandlerData handler : handlerList) {
                        reactor.registerHandler(handler.getHeader(),
                                (EventHandler) Class.forName(handler.getHandler()).newInstance());
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        reactor.startServer();
    }
}

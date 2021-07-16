package proiect;

import java.io.DataOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Properties;

public class serverInfo {

    String osName = System.getProperty("os.name");
    String dataModel = System.getProperty("sun.arch.data.model");
    String vmVersion = System.getProperty("java.vm.version");
    String osArch = System.getProperty("os.arch");

    public void runServerInfo(DataOutputStream out) {

        String toReturn;
        toReturn = " osName = " + osName + " \ndata model = " + dataModel + " \nvmVersion = " + vmVersion + " \nosArch = " + osArch;

        try {
            out.writeUTF(toReturn);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

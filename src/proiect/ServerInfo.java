package proiect;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerInfo {
    //String osName = System.getProperty("os.name");
    //String dataModel = System.getProperty("sun.arch.data.model");
    //String vmVersion = System.getProperty("java.vm.version");
    //String osArch = System.getProperty("os.arch");

    public void runServerInfo(DataOutputStream out) {
        try {
            Process pb = new ProcessBuilder("lsb_release", "--description").start();
            BufferedReader in = new BufferedReader(new InputStreamReader(pb.getInputStream()));
            String line ;
            while ((line = in.readLine())!= null){
                out.writeUTF(line);
            }
            pb.wait();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

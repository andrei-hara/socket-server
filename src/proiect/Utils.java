package proiect;

import java.io.*;
import java.nio.file.Path;

public class Utils {

    public void compileCode(DataOutputStream out) {
        try {
            // proces pentru compilare (folosind shell bash + precizare director de lucru)
            Process compile = new ProcessBuilder("bash", "-c", "g++ -c -g *.cpp").directory(new File("/home/andrei/Desktop/app-java_server/unzip").getAbsoluteFile()).start();

            compile.waitFor();
            // exit status - codul pentru eroare
            int exitStatus = compile.exitValue();
            // afisare mesaj  de eroare la compilare
            if (exitStatus != 0){
                BufferedReader err = new BufferedReader(new InputStreamReader(compile.getErrorStream()));
                String error;
                while ((error = err.readLine()) != null) {
                    out.writeUTF(error);
                }
            }
            compile.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void linkEditCode(){
        try {
            Process linkEdit = new ProcessBuilder("bash", "-c", "g++ -g -o Program main.o").directory(new File("/home/andrei/Desktop/app-java_server/unzip").getAbsoluteFile()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runCode (DataOutputStream out){
        try {
            Process execute = new ProcessBuilder( "./Program").directory(new File("/home/andrei/Desktop/app-java_server/unzip").getAbsoluteFile()).start();
            BufferedReader in = new BufferedReader(new InputStreamReader(execute.getInputStream()));
            String line;
            while((line =in.readLine()) != null){
                out.writeUTF(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


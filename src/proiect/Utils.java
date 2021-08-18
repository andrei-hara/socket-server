package proiect;

import java.io.*;
import java.nio.file.Path;

public class Utils {
    public void compileCode(DataOutputStream out) {
        try {
            // proces pentru compilare (folosind shell bash + precizare director de lucru)
            Process compile = new ProcessBuilder("bash", "-c", "g++ -c -g *.cpp").directory(new File("/tmp/unzip").getAbsoluteFile()).start();

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

    public void removeCompfiles() {
        try {
            Process remove = new ProcessBuilder("bash", "-c", "rm *.cpp *.h *.o Program").directory(new File("/tmp/unzip").getAbsoluteFile()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void linkEditCode(){
        try {
            Process linkEdit = new ProcessBuilder("bash", "-c", "g++ -g -o Program main.o").directory(new File("/tmp/unzip").getAbsoluteFile()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void executeCode(DataOutputStream out) {
        try {
            Process execute = new ProcessBuilder( "./Program").directory(new File("/tmp/unzip").getAbsoluteFile()).start();
            BufferedReader in = new BufferedReader(new InputStreamReader(execute.getInputStream()));
            String line;
            while((line =in.readLine()) != null){
                out.writeUTF(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void readZipPath(DataOutputStream out, DataInputStream in) {
        try {
            // creare obiect de tip Unzip
            Unzip u = new Unzip();
            // Introducere path Zip
            out.writeUTF("Enter the ZIP archive path: ");
            String fileZip = in.readUTF();
            // path pentru unzip
              String destDir = "/tmp/unzip";
            // functie pentru extragere zip
             u.extractZip(fileZip, destDir);
            System.out.println("Unzipped successful!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




package utils;

import java.io.*;

public class File {

    private static BufferedReader br;

    public static String readFile(String name) {
        String line = "";
        try {
            FileReader fr = new FileReader(name);
            int c = fr.read();
            while(c != -1){
                line += (char)c;
                c = fr.read();
            }
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("Le fichier n'a pas été trouvé");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static void saveFile(String content, java.io.File file) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(file);
            fw.write(content.replace("\n", "\r\n"));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package aufgabe1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;
import java.util.Scanner;

public class TextUI {

    public TextUI(){}

    public static String param = " ";
    public static String param2 = " ";
    public static String cmd;
    public static String german = null;
    public static String english = null;
    public static Dictionary<String, String> dict;



    public static void main(final String[] args) throws IOException {

        //// Eingabe schleife
        Scanner command=new Scanner(System.in);
        while (true) {
            System.out.println("Bitte Befehl eingeben:");
            String eingabe = command.nextLine();
            String[] parts = eingabe.split(" ");
            cmd = parts[0];
            if (parts.length > 1) {
                if (parts[1] != null) {
                    param = parts[1];
                }
                if (parts.length > 2 ) {
                    if (parts[2] != null) {
                        param2 = parts[2];
                    }
                }
            }



            switch (cmd) {
                case "create": {
                    if (param.equals("HashDictionary")) {
                        createHashDictionary();
                    } else if (param.equals("BinaryTreeDictionary")) {
                        testBinaryTreeDictionary();
                    } else {
                        createSortedArrayDictionary();
                    }
                    break;
                }
                case "read": {
                    try {
                        int n = 0;
                        if (param.equals("dtengl.txt")) {
                               n = lineCounter();
                        } else {
                            n = Integer.parseInt(param);
                        }
                        if (n < 0) {
                            System.out.println("Bitte positive Zahl eingeben.");
                            break;
                        }
                        if (param.equals("dtengl.txt") && param2.equals(" ")) {
                            read(n, param);
                        } else if (param2.equals(" ")) {
                            read(n, param2);
                        } else {
                            read(n, param2);
                        }
                        break;

                    } catch (NumberFormatException ex) {
                        System.out.println("Ungueltige Zahl");
                        break;
                    }

                }
                case "p": {
                    print();
                    break;
                }
                case "s": {
                    if (param != null) {
                        search(param);
                    }
                    break;
                }
                case "i": {
                    insert(param, param2);
                    break;
                }
                case "r": {
                    remove(param);
                    break;
                }
                case "exit": {
                    System.exit(0);
                    break;
                }
                case "help": {
                    System.out.println("create Implementierung");
                    System.out.println("Legt ein Dictionary an. SortedArrayDictionary ist voreingestellt.");
                    System.out.println(" ");
                    System.out.println("read [n] Dateiname");
                    System.out.println("Liest die ersten n Einträge der Datei in das Dictionary ein.\n" +
                            "Wird n weggelassen, dann werden alle Einträge eingelesen.");
                    System.out.println("");
                    System.out.println("p");
                    System.out.println("Gibt alle Einträge des Dictionary in der Konsole aus (print).");
                    System.out.println("s deutsch ");
                    System.out.println(" ");
                    System.out.println("i deutsch englisch");
                    System.out.println("Fügt ein neues Wortpaar in das Dictionary ein (insert).");
                    System.out.println(" ");
                    System.out.println("r deutsch");
                    System.out.println("Löscht einen Eintrag (remove).");
                    System.out.println(" ");
                    System.out.println("exit");
                    System.out.println("beendet das Programm-");
                    System.out.println("");
                    break;
                }
                default: {
                    System.out.println("Falscher befehl! Tippe help für Hilfe!");
                    break;
                }
            }
        }
    }

    private static int lineCounter() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/Users/pfau/02_Sonstiges/02_intelij/01_Projekte/07_HTWG_SEM3_AlgoDat/src/aufgabe1/dtengl.txt"));
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            reader.close();
            System.out.println(lines);
            return lines;
        } catch (FileNotFoundException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }

        return 0;
    }



    public static void read(int n, String name) {
        //if (dict == null) return;
        String input;
        String path = "/Users/pfau/02_Sonstiges/02_intelij/01_Projekte/07_HTWG_SEM3_AlgoDat/src/aufgabe1/";
        String path2 = path + name;
        //System.out.println(path2);
        //System.out.println(name);
        try {
            BufferedReader br = new BufferedReader(new FileReader(path2));
            for (int i = 0; i < n; i++) {
                //while ((input = br.readLine()) != null) {
                input = br.readLine();
                //System.out.println(input);
                String[] parts = input.split(" ");
                //String part1 = parts[0];
                //String part2 = parts[1];
                german = parts[0];
                if (parts.length > 1) {
                    if (parts[1] != null) {
                        english = parts[1];
                    } else {
                        param = " ";
                    }
                }


                dict.insert(german, english);


                //} // end while
            }
        } // end try
        catch (IOException e) {
            System.err.println("Error: " + e);
        }

    }


    public static void createHashDictionary() {
        dict = new HashDictionary<>(3);
        //testDict(dict);
    }

    public static void createSortedArrayDictionary() {
        dict = new SortedArrayDictionary<>();
        //testDict(dict);
    }

    private static void testBinaryTreeDictionary() {
        dict = new BinaryTreeDictionary<>();
        //testDict(dict);
    }


    public static void print() {
        for (Dictionary.Entry<String, String> e : dict) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
    }

    public static void insert(String german, String english) {
        dict.insert(german, english);
    }

    public static void search(String key) {
        if (dict.search(key) == null){
            System.out.println("Eintrag nicht gefunden");
        } else {
            System.out.println(dict.search(key));
        }
    }

    public static void remove(String key) {
        dict.remove(key);
    }


}

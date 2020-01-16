package aufgabe1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Performance {

    public static String param = " ";
    public static String german = null;
    public static String english = null;
    public static Dictionary<String, String> dict;
    public static List<String> list_german = new ArrayList<>();
    public static List<String> list_english = new ArrayList<>();




    public static void main(final String[] args) throws IOException {


       /* System.out.println("SortedArrayDictionary mit 8000 Eintraegen");
        laufzeitSortetArrayDictionary8000();
        System.out.println(" ");
        System.out.println("SortedArrayDictionary mit 16000 Eintraegen");
        laufzeitSortetArrayDictionary16000();
        System.out.println(" ");
        System.out.println("----------------------");
        System.out.println(" ");
        System.out.println("HashDictionary mit 8000 Eintraegen");
        laufzeitHashDictionary8000();
        System.out.println(" ");
        System.out.println("HashDictionary mit 16000 Eintraegen");
        laufzeitHashDictionary16000();
        System.out.println(" ");
        System.out.println("----------------------");
        System.out.println(" ");*/
        System.out.println("BinaryTreeDictionary mit 8000 Eintraegen");
        laufzeitBinaryTreeDictionary8000();
        System.out.println(" ");
        System.out.println("BinaryTreeDictionary mit 16000 Eintraegen");
        laufzeitBinaryTreeDictionary16000();




    }

    public static void laufzeitBinaryTreeDictionary8000(){
        long start = System.nanoTime();
        dict = new BinaryTreeDictionary<>();
        read(8000, "dtengl.txt");
        long end = System.nanoTime();

        //In liste schreiben
        readinList(8000, "dtengl.txt");
        /////
        //laufzeit erfolg search
        long start1 = System.nanoTime();
        for (int a = 0;a < list_german.size(); a++) {
            dict.search(list_german.get(a));
        }
        long end1 = System.nanoTime();


        long start2 = System.nanoTime();
        for (int a = 0;a < list_english.size(); a++) {
            dict.search(list_english.get(a));
        }
        long end2 = System.nanoTime();

        list_german = new ArrayList<>();



        //Ausgabe
        double elapsedTime = (double) (end - start)/1.0e06;
        System.out.println(" Elapsed Time BinaryTreeDictionary8000: " + elapsedTime + " msec");
        double elapsedTime1 = (double) (end1 - start1)/1.0e06;
        System.out.println(" Elapsed time erfolgreiche Suche  " + elapsedTime1 + " msec");
        double elapsedTime2 = (double) (end2 - start2)/1.0e06;
        System.out.println(" Elapsed time Nicht erfolgreiche Suche  " + elapsedTime2 + " msec");
    }

    public static void laufzeitBinaryTreeDictionary16000(){
        long start = System.nanoTime();
        dict = new BinaryTreeDictionary<>();
        read(15896, "dtengl.txt");
        long end = System.nanoTime();


        //In liste schreiben
        readinList(15896, "dtengl.txt");
        /////
        //laufzeit erfolg search
        long start1 = System.nanoTime();
        for (int a = 0;a < list_german.size(); a++) {
            dict.search(list_german.get(a));
        }
        long end1 = System.nanoTime();


        long start2 = System.nanoTime();
        for (int a = 0;a < list_english.size(); a++) {
            dict.search(list_english.get(a));
        }
        long end2 = System.nanoTime();

        list_german = new ArrayList<>();



        //Ausgabe
        double elapsedTime = (double) (end - start)/1.0e06;
        System.out.println(" Elapsed time BinaryTreeDictionary16000:  " + elapsedTime + " msec");
        double elapsedTime1 = (double) (end1 - start1)/1.0e06;
        System.out.println(" Elapsed time erfolgreiche Suche  " + elapsedTime1 + " msec");
        double elapsedTime2 = (double) (end2 - start2)/1.0e06;
        System.out.println(" Elapsed time Nicht erfolgreiche Suche  " + elapsedTime2 + " msec");
    }


    public static void laufzeitHashDictionary8000(){
        long start = System.nanoTime();
        dict = new HashDictionary<>(3);
        read(8000, "dtengl.txt");
        long end = System.nanoTime();

        //In liste schreiben
        readinList(8000, "dtengl.txt");
        /////
        //laufzeit erfolg search
        long start1 = System.nanoTime();
        for (int a = 0;a < list_german.size(); a++) {
            dict.search(list_german.get(a));
        }
        long end1 = System.nanoTime();


        long start2 = System.nanoTime();
        for (int a = 0;a < list_english.size(); a++) {
            dict.search(list_english.get(a));
        }
        long end2 = System.nanoTime();

        list_german = new ArrayList<>();



        //Ausgabe
        double elapsedTime = (double) (end - start)/1.0e06;
        System.out.println(" Elapsed Time HashDictionary8000: " + elapsedTime + " msec");
        double elapsedTime1 = (double) (end1 - start1)/1.0e06;
        System.out.println(" Elapsed time erfolgreiche Suche  " + elapsedTime1 + " msec");
        double elapsedTime2 = (double) (end2 - start2)/1.0e06;
        System.out.println(" Elapsed time Nicht erfolgreiche Suche  " + elapsedTime2 + " msec");
    }

    public static void laufzeitHashDictionary16000(){
        long start = System.nanoTime();
        dict = new HashDictionary<>(3);
        read(15896, "dtengl.txt");
        long end = System.nanoTime();


        //In liste schreiben
        readinList(15896, "dtengl.txt");
        /////
        //laufzeit erfolg search
        long start1 = System.nanoTime();
        for (int a = 0;a < list_german.size(); a++) {
            dict.search(list_german.get(a));
        }
        long end1 = System.nanoTime();


        long start2 = System.nanoTime();
        for (int a = 0;a < list_english.size(); a++) {
            dict.search(list_english.get(a));
        }
        long end2 = System.nanoTime();

        list_german = new ArrayList<>();



        //Ausgabe
        double elapsedTime = (double) (end - start)/1.0e06;
        System.out.println(" Elapsed time HashDictionary16000:  " + elapsedTime + " msec");
        double elapsedTime1 = (double) (end1 - start1)/1.0e06;
        System.out.println(" Elapsed time erfolgreiche Suche  " + elapsedTime1 + " msec");
        double elapsedTime2 = (double) (end2 - start2)/1.0e06;
        System.out.println(" Elapsed time Nicht erfolgreiche Suche  " + elapsedTime2 + " msec");
    }

    public static void laufzeitSortetArrayDictionary8000(){
        long start = System.nanoTime();
        dict = new SortedArrayDictionary<>();
        read(8000, "dtengl.txt");
        long end = System.nanoTime();

        //In liste schreiben
        readinList(8000, "dtengl.txt");
        /////
        //laufzeit erfolg search
        long start1 = System.nanoTime();
        for (int a = 0;a < list_german.size(); a++) {
            dict.search(list_german.get(a));
        }
        long end1 = System.nanoTime();


        long start2 = System.nanoTime();
        for (int a = 0;a < list_english.size(); a++) {
            dict.search(list_english.get(a));
        }
        long end2 = System.nanoTime();

        list_german = new ArrayList<>();


        //Ausgabe // 1 second = 1_000_000_000 nano seconds
        double elapsedTime = (double) (end - start)/1.0e06;
        System.out.println(" Elapsed Time SortedArrayDictionary8000: " + elapsedTime + " msec");
        double elapsedTime1 = (double) (end1 - start1)/1.0e06;
        System.out.println(" Elapsed time erfolgreiche Suche  " + elapsedTime1 + " msec");
        double elapsedTime2 = (double) (end2 - start2)/1.0e06;
        System.out.println(" Elapsed time Nicht erfolgreiche Suche  " + elapsedTime2 + " msec");
    }

    public static void laufzeitSortetArrayDictionary16000(){
        long start = System.nanoTime();
        dict = new SortedArrayDictionary<>();
        read(15896, "dtengl.txt");
        long end = System.nanoTime();

        //In liste schreiben
        readinList(15896, "dtengl.txt");
        /////
        //laufzeit erfolg search
        long start1 = System.nanoTime();
        for (int a = 0;a < list_german.size(); a++) {
            dict.search(list_german.get(a));
        }
        long end1 = System.nanoTime();


        long start2 = System.nanoTime();
        for (int a = 0;a < list_english.size(); a++) {
            dict.search(list_english.get(a));
        }
        long end2 = System.nanoTime();

        list_german = new ArrayList<>();



        //Ausgabe
        double elapsedTime = (double) (end - start)/1.0e06;
        System.out.println(" Elapsed time SortedArrayDictionary16000:  " + elapsedTime + " msec");
        double elapsedTime1 = (double) (end1 - start1)/1.0e06;
        System.out.println(" Elapsed time erfolgreiche Suche  " + elapsedTime1 + " msec");
        double elapsedTime2 = (double) (end2 - start2)/1.0e06;
        System.out.println(" Elapsed time Nicht erfolgreiche Suche  " + elapsedTime2 + " msec");
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


    public static void readinList(int n, String name ) {
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
                String[] parts = input.split(" ");
                german = parts[0];
                if (parts.length > 1) {
                    if (parts[1] != null) {
                        english = parts[1];
                    } else {
                        param = " ";
                    }
                }


                list_english.add(english);
                list_german.add(german);




                //} // end while
            }
        } // end try
        catch (IOException e) {
            System.err.println("Error: " + e);
        }

    }




}

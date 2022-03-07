package main;

import caesar.Caesar;
import enums.CypherOptions;
import present.Present;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You didn't mention the cipher");
            return;
        }
        String cipher = args[0];
        if (cipher.equals("caesar")) {
            if (args.length >= 2) {
                if (args[1].equals("-e")) {
                    if (args.length == 4) {
                        Caesar caesar = new Caesar(CypherOptions.ENCRYPT, Integer.parseInt(args[2]), args[3]);
                    } else {
                        System.out.println("You didn't mention the source file.");
                    }
                } else if (args[1].equals("-d")) {
                    if (args.length == 4) {
                        Caesar caesar = new Caesar(CypherOptions.DECRYPT, Integer.parseInt(args[2]), args[3]);
                    } else {
                        System.out.println("You didn't mention the source file.");
                    }
                } else {
                    System.out.println("You didn't mention the option");
                }
            }
        } else if (cipher.equals("present")) {
            if (args.length >= 2) {
                if (args[1].equals("-e")) {
                    if (args.length == 4) {
                        //read key from file
                        //give file with plain text
                        Present present = new Present(CypherOptions.ENCRYPT, args[2], args[3]);
                    } else {
                        System.out.println("You either didn't mention key file or source file.");
                    }
                } else if (args[1].equals("-d")) {
                    if (args.length == 4) {
                        //read key from file
                        //give file with encrypted text
                        Present present = new Present(CypherOptions.DECRYPT, args[2], args[3]);
                    } else {
                        System.out.println("You either didn't mention key file or source file.");
                    }
                } else {
                    System.out.println("You didn't mention the option");
                }
            }
        } else {
            System.out.println("Unknown cipher");
        }
    }
}

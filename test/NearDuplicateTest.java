import java.util.Scanner;
import java.util.ArrayList;

public class NearDuplicateTest
{

    public static void main(String[] args)
    {
        int perms = 500;
        double s = .5;
        MinHash hash;
        String[] docs;
        NearDuplicates dups;
        boolean loop = true;
        String command, f1;
        Scanner in = new Scanner(System.in);
        if (args.length < 1) {
            System.out.println("must provide directory path");
            System.exit(-1);
        }

        if (args.length >= 2) {
            try {
                perms = Integer.parseInt(args[1]);
                s = Double.parseDouble(args[2]);
            } catch (ArrayIndexOutOfBoundsException ex) {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        hash = new MinHash(args[0], perms);
        docs = hash.allDocs();
        dups = new NearDuplicates(hash, s);
        try {
            while (loop) {
                System.out.print("\nCommand: ");
                command = in.next();
                switch (command) {
                case "exit":
                    in.close();
                    loop = false;
                    break;
                case "files":
                    printFiles(docs);
                    break;
                case "dups":
                    f1 = in.next();
                    getDuplicates(f1, dups);
                    break;
                case "findb":
                    perms = in.nextInt();
                    s = in.nextDouble();
                    System.out.print(NearDuplicates.findBands(s, perms));
                    break;
                default:
                    System.out.print("command not recognized");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printFiles(String[] docs) {
        System.out.print("Files:\n\t");
        for (int i = 0; i < docs.length; i++) {
            System.out.print(docs[i] + ", ");
        }
    }

    public static void getDuplicates(String file, NearDuplicates ndups)
    {
        ArrayList<String> dups = ndups.nearDuplicateDetector(file);
        if(dups == null)
            return;
        for (int i = 0; i < dups.size()-3; i+=3) {
            System.out.println("  " + dups.get(i) + ", " + dups.get(i+1) + ", " + dups.get(i+2));
        }
        if(dups.size()%3 == 2)
        {
            System.out.print("  " + dups.get(dups.size()-2) + ", " + dups.get(dups.size()-2));
        }
        if (dups.size() % 3 == 1) {
            System.out.print("  " + dups.get(dups.size() - 2));
        }
    }
}
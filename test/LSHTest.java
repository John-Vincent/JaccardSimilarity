import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LSHTest
{
    public static void main(String[] args)
    {
        int perms = 500, bands = 10;
        MinHash hash;
        String[] docs;
        LSH lsh;
        boolean loop = true;
        String command, f1;
        Scanner in = new Scanner(System.in);
        if (args.length < 1)
        {
            System.out.println("must provide directory path");
            System.exit(-1);
        }

        if (args.length >= 2)
        {
            try
            {
                perms = Integer.parseInt(args[1]);
                bands = Integer.parseInt(args[2]);
            }
            catch(ArrayIndexOutOfBoundsException ex)
            {

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        hash = new MinHash(args[0], perms);
        docs = hash.allDocs();
        lsh = new LSH(hash.minHashMatrix(), docs, bands);
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
                    getDuplicates(f1, lsh);
                    break;
                case "print":
                    printBands(lsh);
                    break;
                default:
                    System.out.print("command not recognized");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printFiles(String[] docs)
    {
        System.out.print("Files:\n\t");
        for (int i = 0; i < docs.length; i++)
        {
            System.out.print(docs[i] + ", ");
        }
    }

    public static void getDuplicates(String file, LSH lsh)
    {
        ArrayList<String> dups = lsh.nearDuplicatesOf(file);
        for (int i = 0; i < dups.size(); i++)
        {
            System.out.print(dups.get(i) + ", ");
        }
    }

    public static void printBands(LSH lsh)
    {
        HashMap<Integer, ArrayList<String>> map;
        Iterator<Integer> it;
        ArrayList<String> names;
        for( int i = 0; i < lsh.similar.size(); i++)
        {
            map = lsh.similar.get(i);
            it = map.keySet().iterator();
            while(it.hasNext())
            {
                System.out.print("{");
                names = map.get(it.next());
                for(String name: names)
                {
                    System.out.print(name + ", ");
                }
                System.out.print("}, ");
            }
            System.out.println();
        }
    }
}
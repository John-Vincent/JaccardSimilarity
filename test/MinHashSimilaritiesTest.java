
import java.util.Scanner;

public class MinHashSimilaritiesTest
{


    public static void main(String[] args)
    {
        int perms = 50;
        MinHashSimilarities sim;
        boolean loop = true;
        String command, f1, f2;
        Scanner in = new Scanner(System.in);
        if(args.length < 1)
        {
            System.out.println("must provide directory path");
            System.exit(-1);
        }

        if(args.length >= 2)
        {
            try
            {
                perms = Integer.parseInt(args[1]);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            sim = new MinHashSimilarities(args[0], perms);
            while(loop)
            {
                System.out.print("\nCommand: ");
                command = in.next();
                switch(command)
                {
                    case "exit":
                        in.close();
                        loop = false;
                        break;
                    case "files":
                        printFiles(sim);
                        break;
                    case "sim":
                        f1 = in.next();
                        f2 = in.next();
                        getSimilarities(f1, f2, sim);
                        break;
                    case "sig":
                        f1 = in.next();
                        getSignature(f1, sim);
                        break;
                    default:
                        System.out.print("command not recognized");
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void printFiles(MinHashSimilarities sim)
    {
        System.out.print("Files:\n\t");
        for (int i = 0; i < sim.filenames.length; i++) {
            System.out.print(sim.filenames[i] + ", ");
        }
    }

    public static void getSimilarities(String f1, String f2, MinHashSimilarities sim)
    {
        System.out.print("\texact: " + sim.exactJaccard(f1,f2) + "\n\tapprox: " + sim.approximateJaccard(f1, f2));
    }

    public static void getSignature(String file, MinHashSimilarities sim)
    {
        int[] sig = sim.minHashSig(file);
        System.out.print("\thash array: \n\t");
        for(int i = 0; i < sig.length; i++)
        {
            System.out.print(sig[i] + ", ");
        }
    }
}
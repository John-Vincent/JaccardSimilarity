public class MinHashTime
{


    public static void timer(String folder, int numPermutations)
    {
        long time = System.currentTimeMillis();
        MinHashSimilarities min = new MinHashSimilarities(folder, numPermutations);
        System.out.println((System.currentTimeMillis() - time) + " milliseconds to create the MinHashSimilarities");
        time = System.currentTimeMillis();
        for(int i = 0; i < min.filenames.length; i++)
        {
            for(int j = i+1; j < min.filenames.length; j++)
            {
                min.exactJaccard(min.filenames[i], min.filenames[j]);
            }
        }
        System.out.println((System.currentTimeMillis() - time) + " milliseconds to compute exact jaccard");
        time = System.currentTimeMillis();
        for (int i = 0; i < min.filenames.length; i++)
        {
            for (int j = i+1; j < min.filenames.length; j++)
            {
                min.approximateJaccard(min.filenames[i], min.filenames[j]);
            }
        }
        System.out.println((System.currentTimeMillis() - time) + " milliseconds to compute approximate jaccard");
    }

    public static void main(String[] args)
    {
        if (args.length < 2)
        {
            System.out.println("must provide folder, and numPermutations");
        }
        try
        {
            timer(args[0], Integer.parseInt(args[1]));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
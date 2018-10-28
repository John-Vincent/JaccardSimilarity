public class MinHashAccuracy
{
    public static void main(String[] args)
    {
        if(args.length < 3)
        {
            System.out.println("must provide folder, numPermutations, and error");
        }
        try
        {
            accuracy(args[0], Integer.parseInt(args[1]), Double.parseDouble(args[2]));
        }
        catch(Exception e)
        {

        }
    }

    public static void accuracy(String folder, int numPermutations, double error)
    {
        MinHashSimilarities min = new MinHashSimilarities(folder, numPermutations);
        String[] files = min.filenames;
        double approx, exact;

        for(int i = 0; i < files.length; i++)
        {
            System.out.print("comparing " + i + "/" + files.length + "\r");
            for(int j = 0; j < files.length; j++)
            {
                if(i != j)
                {
                    approx = min.approximateJaccard(files[i], files[j]);
                    exact = min.exactJaccard(files[i], files[j]);
                    if(Math.abs(approx-exact) > error)
                    {
                        System.out.println(files[i] + " " + files[j] + "             \n\r  epsilon " +
                            String.format("%.4f", Math.abs(approx - exact)) + " approx: " +
                            String.format("%.4f", approx) + " exact: " +
                            String.format("%.4f", exact)
                        );
                    }
                }
            }
        }
    }
}

import java.util.ArrayList;

public class NearDuplicates
{
    private LSH lsh;

    public NearDuplicates(String folder, int numPermutations, double s)
    {
        MinHash hash = new MinHash(folder, numPermutations);
        int bands = findBands(s, numPermutations);
        this.lsh = new LSH(hash.minHashMatrix(), hash.allDocs(), bands);
    }

    public NearDuplicates(MinHash hash, double s)
    {
        int bands = findBands(s, hash.numPermutations());
        this.lsh = new LSH(hash.minHashMatrix(), hash.allDocs(), bands);
    }

    public ArrayList<String> nearDuplicateDetector(String file)
    {
        return this.lsh.nearDuplicatesOf(file);
    }

    public static int findBands(double s, int k)
    {
        double ans = Double.MAX_VALUE, temp;
        int bands = 0, r;
        for(int b = 1; b <= k; b++)
        {
            r = k/b;
            temp = Math.abs(Math.pow(1f/b, 1f/r) - s);
            if(temp < ans)
            {
                ans = temp;
                bands = b;
            }
        }
        return bands;
    }
}
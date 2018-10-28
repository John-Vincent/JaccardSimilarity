public class MinHashSimilarities
{
    int[][] minHash;

    int[][] termDocument;

    String[] filenames;

    public MinHashSimilarities(String folder, int numPermutations)
    {
        MinHash minHash = new MinHash(folder, numPermutations);
        this.filenames = minHash.allDocs();
        this.minHash = minHash.minHashMatrix();
        this.termDocument = minHash.termDocumentMatrix();
    }

    public double exactJaccard(String file1, String file2)
    {
        int f1 = -1, f2 = -1;
        double union=0, intersect=0;

        for(int i= 0; i < this.filenames.length; i++)
        {
            if(this.filenames[i].equals(file1))
                f1 = i;
            if(this.filenames[i].equals(file2))
                f2 = i;
        }

        if(f1 == -1 || f2 == -1)
        {
            System.out.println("one of those files does not exist in the collection");
            return 0;
        }

        for(int i = 0; i < termDocument[f1].length; i++)
        {
            if(termDocument[f1][i] == 1 || termDocument[f2][i] == 1)
                union++;
            if(termDocument[f1][i] == 1 && termDocument[f2][i] == 1)
                intersect++;
        }
        return intersect/union;
    }

    public double approximateJaccard(String file1, String file2)
    {
        int f1 = -1, f2 = -1;
        double union = minHash[0].length, intersect = 0;

        for (int i = 0; i < this.filenames.length; i++)
        {
            if (this.filenames[i].equals(file1))
                f1 = i;
            if (this.filenames[i].equals(file2))
                f2 = i;
        }

        if(f1 == -1 || f2 == -1)
        {
            System.out.println("one of those files does not exist in the collection");
            return 0;
        }

        for(int i = 0; i < minHash[f1].length; i++)
        {
            if(minHash[f1][i] == minHash[f2][i])
                intersect++;
        }

        return intersect/union;
    }

    public int[] minHashSig(String fileName)
    {
        int f1 = -1;
        int[] ans = new int[minHash[0].length];
        for (int i = 0; i < this.filenames.length; i++)
        {
            if (this.filenames[i].equals(fileName))
                f1 = i;
        }
        if(f1 == -1)
            return null;
        for(int i = 0; i < ans.length; i++)
        {
            ans[i] = minHash[f1][i];
        }
        return ans;
    }
}
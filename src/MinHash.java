import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.ArrayList;

public class MinHash
{
    private File folder;

    private int numPermutations;

    private int[][] minHashMatrix;

    private int[][] termDocumentMatrix;

    private int numTerms;

    /**
     * @param folder the path to the folder with the document collection
     * @param numPermutations number of permutations to with min hash matrix
     */
    public MinHash(String folder, int numPermutations)
    {
        this.folder = new File(folder);
        if(!this.folder.isDirectory())
        {
            System.out.println("Warning: path provided is not a directory, please check the path");
        }
        this.numPermutations = numPermutations;
    }

    /**
     * returns the names of all files in the folder.
     */
    public String[] allDocs()
    {
        File[] files;
        ArrayList<String> names = new ArrayList<String>();
        String[] ans;
        try
        {
            files = this.folder.listFiles();
            if(files == null)
            {
                return null;
            }
            for(int i = 0; i < files.length; i++)
            {
                if(!files[i].isDirectory())
                {
                    names.add(files[i].getName());
                }
            }
            ans = new String[names.size()];
            for(int i = 0; i < names.size(); i++)
            {
                ans[i] = names.get(i);
            }
            return ans;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * returns the minHash Matrix of the document collection
     * @return
     */
    public int[][] minHashMatrix()
    {
        if(this.minHashMatrix == null)
            this.calcMinHash();
        return this.minHashMatrix;
    }

    /**
     * returns the term Document Matrix for the collection
     * @return
     */
    public int[][] termDocumentMatrix()
    {
        if(this.termDocumentMatrix == null)
            this.calcMinHash();
        return this.termDocumentMatrix;
    }

    /**
     * returns the number of permuations used to construct the matrices
     */
    public int numPermutations()
    {
        return numPermutations;
    }

    /**
     * this will construct both the minHashMatrix and the termDocumentMatrix
     * it does this by reading all the documents term by term assigning a integer
     * value to each term then calculating the hash value for each term in each document and storing the
     * min for each permutation
     */
    public void calcMinHash()
    {
        Preprocessor process;
        long[][] perm;
        String term;
        boolean first;
        int t;
        Iterator<String> it;
        HashMap<String, Integer> mapping = new HashMap<String, Integer>();
        String[] files = this.allDocs();
        Set<String> terms;
        ArrayList<HashMap<String, Boolean>> fileXterm = new ArrayList<HashMap<String, Boolean>>();
        t = 0;
        for(int i = 0; i < files.length; i++)
        {
            process = new Preprocessor(new File(this.folder, files[i]));
            fileXterm.add(new HashMap<String, Boolean>());
            term = process.nextTerm();
            while(term != null)
            {
                if(mapping.get(term) == null)
                {
                    mapping.put(term, t);
                    t++;
                }
                fileXterm.get(i).put(term, true);
                term = process.nextTerm();
            }
        }
        this.numTerms = mapping.keySet().size();
        perm = this.makePermutations(this.numTerms);
        this.termDocumentMatrix = new int[files.length][numTerms];
        this.minHashMatrix = new int[files.length][perm.length];
        for(int i = 0; i < files.length; i++)
        {
            terms = fileXterm.get(i).keySet();
            it = terms.iterator();
            first = true;
            while(it.hasNext())
            {
                term = it.next();
                t = mapping.get(term);
                this.termDocumentMatrix[i][t] = 1;
                for(int j = 0; j < perm.length; j++)
                {
                    if(first || this.minHashMatrix[i][j] > hashTerm(t, j, perm))
                        this.minHashMatrix[i][j] = hashTerm(t, j, perm);
                }
                first = false;
            }
        }
    }

    public int hashTerm(int term, int perm, long[][]permutations)
    {
        return (int)((permutations[perm][0] * term + permutations[perm][1]) % permutations[perm][2]);
    }

    public long[][] makePermutations(long next)
    {
        long[][] ans = new long[numPermutations][3];
        for (int i = 0; i < ans.length; i++) {
            next = nextPrime(next + 1);
            ans[i][2] = next;
            ans[i][0] = (long) (Math.random() * Long.MAX_VALUE);
            ans[i][0] = ans[i][0] % ans[i][2];
            ans[i][1] = (long) (Math.random() * Long.MAX_VALUE);
            ans[i][1] = ans[i][1] % ans[i][2];
        }
        return ans;
    }

    public static long nextPrime(long p) {
        while (!isPrime(p)) {
            p++;
        }
        return p;
    }

    public static boolean isPrime(long p) {
        long n = (long) Math.ceil(Math.sqrt(p));
        if (p < 2)
            return false;
        if (p == 2)
            return true;
        if (p % 2 == 0)
            return false;
        for (long i = 3; i <= n; i++) {
            if (p % i == 0)
                return false;
        }
        return true;
    }
}
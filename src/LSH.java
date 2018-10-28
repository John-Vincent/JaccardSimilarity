import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class LSH
{
    public ArrayList<HashMap<Integer, ArrayList<String>>> similar;

    private int[][] minHashMatrix;

    private String[] docNames;

    private int bands, bandLength;

    private int p, a, b;

    public LSH(int[][] minHashMatrix, String[] docNames, int bands)
    {
        int ans;
        this.bandLength = minHashMatrix[0].length / bands;
        this.bands = bands;
        this.minHashMatrix = minHashMatrix;
        HashMap<Integer,ArrayList<String>> map;
        ArrayList<String> bucket;
        this.docNames = docNames;
        similar = new ArrayList<HashMap<Integer, ArrayList<String>>>();
        for(int i = 0; i < bands; i++)
        {
            similar.add(new HashMap<Integer, ArrayList<String>>());
        }
        p = (int)MinHash.nextPrime(docNames.length);
        a = (int)(Integer.MAX_VALUE * Math.random()) % p;
        b = (int)(Integer.MAX_VALUE * Math.random()) % p;
        //for every document
        for(int i = 0; i < minHashMatrix.length; i++)
        {
            //for every band
            for(int j = 0; j < bands; j++)
            {
                ans = b;
                //hash the values in the band
                for(int k = j*bandLength; k < bandLength*(j+1); k++)
                {
                    ans = (a* minHashMatrix[i][k] + ans)%p;
                }
                //get the hash table for this band
                map = similar.get(j);
                //get the list of documents for this hash from the table
                bucket = map.get(ans);
                //if there hash not been a value for this hash then make a new bucket
                if(bucket == null)
                {
                    bucket = new ArrayList<String>();
                    map.put(ans,bucket);
                }
                //add this document name to the bucket
                bucket.add(docNames[i]);
            }
        }
    }

    /**
     * return an arraylist of all files that are in the same bucket as the document for any band
     */
    public ArrayList<String> nearDuplicatesOf(String docName)
    {
        HashMap<String,Boolean> files = new HashMap<String, Boolean>();
        ArrayList<String> names, ans = new ArrayList<String>();
        Iterator<String> it;
        String name;
        int index = -1, hash;
        //find the file index
        for(int i = 0; i < docNames.length; i++)
        {
            if(docNames[i].equals(docName))
            {
                index = i;
                break;
            }
        }
        if(index == -1)
        {
            System.out.println("file not found");
            return null;
        }
        //for each band
        for (int j = 0; j < bands; j++)
        {
            hash = b;
            // find hash for band
            for (int k = j * bandLength; k < bandLength * (j + 1); k++)
            {
                hash = (a * minHashMatrix[index][k] + hash) % p;
            }
            //get the bucket for this band
            names = similar.get(j).get(hash);
            //add all the files in this bucket to the answer set.
            for(int i = 0; i < names.size(); i++)
            {
                files.put(names.get(i), true);
            }
        }
        //convert the answer set to an ArrayList
        it = files.keySet().iterator();
        while(it.hasNext())
        {
            name = it.next();
            if(!name.equals(docName))
                ans.add(name);
        }
        return ans;
    }
}
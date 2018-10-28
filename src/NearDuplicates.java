

public class NearDuplicates
{


    public static int findBands(double s, double k)
    {
        double ans = 1000, temp, r;
        int bands = 0;
        for(int b = 1; b < k; b++)
        {
            r = k/b;
            temp = Math.abs(Math.pow(1/b, 1/r) - s);
            if(temp < ans)
            {
                ans = temp;
                bands = b;
            }
        }
        return bands;
    }
}
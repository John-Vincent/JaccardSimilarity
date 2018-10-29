

public class MinHashTest
{

    public static void main(String[] args)
    {
        if(args.length < 1)
        {
            System.out.println("must give a folder path");
            System.exit(-1);
        }
        MinHash hash = new MinHash(args[0], 50);
        System.out.println(hash.allDocs());
        int[][] test = hash.minHashMatrix();
        printArray(test);
        test = hash.termDocumentMatrix();
        printArray(test);
    }

    public static void printArray(int[][] array)
    {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j] + " ");
            }
            System.out.println("\n");
        }
    }
}
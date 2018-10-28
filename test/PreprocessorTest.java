
import java.io.File;

public class PreprocessorTest
{
    public static void main(String[] args)
    {
        if(args.length < 1)
        {
            System.out.print("must provide file");
            System.exit(-1);
        }
        Preprocessor test = new Preprocessor(new File(args[0]));
        String term = test.nextTerm();
        while(term!=null)
        {
            System.out.println(term);
            term = test.nextTerm();
        }
    }
}
import java.io.BufferedReader;
import java.util.Scanner;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preprocessor
{
    private BufferedReader reader;

    private boolean closed = false;

    public static final Pattern p = Pattern.compile("\\S{3,}");

    private Matcher m;

    private String name;

    public Preprocessor(File file)
    {
        this.name = file.getName();
        this.setReader(file, java.nio.charset.Charset.forName("UTF-8"));
    }

    public void setReader(File file, Charset set)
    {
        try
        {
            this.reader = Files.newBufferedReader(file.toPath(), set);
            this.reader.mark(10);
            this.reader.read();
            this.reader.reset();
        }
        catch (java.nio.charset.MalformedInputException ex)
        {
            if(set.equals(java.nio.charset.Charset.forName("UTF-8")))
                this.setReader(file, java.nio.charset.Charset.forName("windows-1252"));
            else
                ex.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.closed = true;
        }
    }

    /**
     * gets the next term from the file if one exist
     * otherwise returns null
     */
    public String nextTerm()
    {
        String line;
        try
        {
            //if the file is done reading return null
            if(closed)
                return null;
            //keep finding terms while the term found is "the"
            do
            {
                // while i cannot find a term read new lines and create new matchers to find terms
                while(m == null || !m.find())
                {
                    line = this.reader.readLine();
                    //file is done reading so close
                    if(line == null)
                    {
                        reader.close();
                        closed = true;
                        return null;
                    }
                    this.m = p.matcher(line.toLowerCase().replaceAll("[\\.,':;]", " "));
                }
            } while(m.group().equals("the"));
            return m.group();
        }
        catch(Exception e)
        {
            System.out.println("Error reading file: " + this.name);
            e.printStackTrace();
            return null;
        }
    }
}

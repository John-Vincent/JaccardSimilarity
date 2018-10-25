import java.io.BufferedReader;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Preprocessor
{

    public static void readFile(String filePath)
    {
        ArrayList<String> s = new ArrayList<String>();
        String string;
        String t;
        Pattern p = Pattern.compile("\\S{3,}");
        Matcher m;
        try {
            BufferedReader reader = Files.newBufferedReader(new File(filepath).toPath());
            string = reader.readLine();
            while (string != null) {
                m = p.matcher(string);
                while(m.find())
                {
                    t = m.group().toLowerCase();
                    t.replaceAll("[\\.,':;']", "");
                    if(!t.equals("the"))
                        s.add(t);
                }
                string = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }
}

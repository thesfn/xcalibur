package xcalibur.javaNative.classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler
{

    public static File createFile(String path)
    {
        int n = 0;
        File f = null;
        StringBuilder s = new StringBuilder();
        String[] strs = path.split("/");
        for(String str : strs)
        {
            if(n > 0)
            {
                if(n < (strs.length - 1))
                {
                    s.append("/");
                    s.append(str);
                    f = new File(s.toString().trim());
                    if(!f.exists() || !f.isDirectory()) f.mkdir();
                }
                else
                {
                    path = s.toString();
                    s.append("/");
                    s.append(str);
                    f = new File(s.toString().trim());
                    if(!f.exists() || !f.isFile()) f = new File(new File(path),str); else f = null;
                }
            }
            n++;
        }
        return f;
    }

    private static String systemNewLineToBreak(String string)
    {
        string = string.replaceAll("<br>",string);
        return string;
    }

    private static String breakToSystemNewLine(String string)
    {
        string = string.replaceAll(System.lineSeparator(),string);
        return string;
    }

    protected static final class destroy
    {

        boolean destroy(String path){
            File  f = new File(path);
            return f.delete();
        }

    }

    public static final class write
    {

        public write(String path, String backupPath, String string, boolean append)
        {
            if(backupPath != null && !backupPath.trim().isEmpty()) new write(backupPath,null,string,append);
            File f = new File(path);
            if(!f.exists() || !f.isFile()) f = createFile(path);
            execute(f,string,append);
        }

        public write(String path, String backupPath, String[] strings, boolean append)
        {
            if(backupPath != null && !backupPath.trim().isEmpty()) new write(backupPath,null,strings,append);
            File f = new File(path);
            if(!f.exists() || !f.isFile()) f = createFile(path);
            execute(f,strings,append);
        }

        public write(String path, String backupPath, List<String[]> list, boolean append)
        {
            if(backupPath != null && !backupPath.trim().isEmpty()) new write(backupPath,null,list,append);
            File f = new File(path);
            if(!f.exists() || !f.isFile()) f = createFile(path);
            execute(f,list,append);
        }

        private void execute(File file, String string, boolean append)
        {
            try
            {
                FileWriter w = new FileWriter(file,append);
                string = systemNewLineToBreak(string)+"\n";
                w.append(string);
                w.flush();
                w.close();
            }
            catch (IOException e)
            {
                //Do nothing
            }
        }

        private void execute(File file, String[] strings, boolean append)
        {
            try{

                if(strings.length > 0){
                    StringBuilder s = new StringBuilder();
                    int n = 0;
                    for(String str : strings)
                    {
                        if(str == null) str = "";
                        if(n > 0) s.append("\t");
                        str = systemNewLineToBreak(str);
                        s.append(systemNewLineToBreak(str));
                        n++;
                    }
                    s.append("\n");
                    FileWriter w = new FileWriter(file,append);
                    w.append(s.toString());
                    w.flush();
                    w.close();
                }
            }
            catch (IOException e)
            {
                //Do nothing
            }
        }

        private void execute(File file, List<String[]> list, boolean append)
        {
            try
            {
                StringBuilder s = new StringBuilder();
                for(String[] strs : list)
                {
                    int n = 0;
                    for(String str : strs)
                    {
                        if(str == null) str = "";
                        if(n > 0) s.append("\t");
                        s.append(systemNewLineToBreak(str));
                        n++;
                    }
                    s.append("\n");
                }
                FileWriter w = new FileWriter(file,append);
                w.append(s.toString());
                w.flush();
                w.close();
            }
            catch (IOException e)
            {
                //Do nothing
            }
        }

    }

    public static List<String[]> read(String path, String backupPath)
    {
        List<String[]> l = new ArrayList<>();
        File f = new File(path);
        if(f.exists() && f.isFile())
        {
            try
            {
                BufferedReader r = new BufferedReader(new FileReader(f));
                StringBuilder bldr = new StringBuilder();
                String str;
                while((str = r.readLine()) != null)
                {
                    if(!str.trim().isEmpty())
                    {
                        bldr.append(str);
                        bldr.append("\n");
                    }
                }
                r.close();
                Scanner scn = new Scanner(bldr.toString());
                while(scn.hasNextLine())
                {
                    str = breakToSystemNewLine(scn.nextLine());
                    l.add(str.split("\t"));
                }
                scn.close();
            }
            catch (IOException e)
            {
                //Do nothing
            }
        }
        if(l.size() == 0 && backupPath != null && !backupPath.trim().isEmpty())
        {
            l = read(backupPath,null);
            if(l.size() > 0) new write(path,null,l,false);
        }
        else if(l.size() > 0 && backupPath != null && !backupPath.trim().isEmpty())
        {
            new write(backupPath,null,l,false);
        }
        return l;
    }

    public static boolean exist(String path)
    {
        File f = new File(path);
        return (f.exists() && f.isFile());
    }
}
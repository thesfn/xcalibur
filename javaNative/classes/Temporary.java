package xcalibur.javaNative.classes;

import java.util.List;

public class Temporary
{

    public static class data
    {

        private static List<String[]> data;

        public static void set(List<String[]> list)
        {
            data = list;
        }

        public static String[] get(int position)
        {
            if(data != null) return data.get(position); else return null;
        }

        public static void destroy()
        {
            data = null;
        }
    }

    public static class images
    {

        private static List<String[]> data;

        public static void set(List<String[]> list)
        {
            data = list;
        }

        public static String[] get(int position)
        {
            if(data != null) return data.get(position); else return null;
        }

        public static List<String[]> getAll()
        {
            return data;
        }

        public static void destroy()
        {
            data = null;
        }
    }

    public static void destroy()
    {
        data.destroy();
        images.destroy();
    }

}

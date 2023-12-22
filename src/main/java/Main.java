import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;


public class Main {
    public static void main(String[] args) throws Exception {
        String folderPath = "C:/skillbox";
        File file = new File(folderPath);
        long timeStamp = System.currentTimeMillis();
        System.out.println(getHumanReadableFormat(getFolderSize(file)));
        System.out.println(System.currentTimeMillis() - timeStamp);
        //forkJoin
        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        timeStamp = System.currentTimeMillis();
        System.out.println(getHumanReadableFormat(pool.invoke(calculator)));
        System.out.println(System.currentTimeMillis() - timeStamp);
        System.out.println(getHumanReadableFormatWithoutLogarithms(240640));
        System.out.println("_______________________________________________________________________");
        System.out.println(getSizeFromHumanReadable("235K"));
        System.out.println(getSizeFromHumanReadable("23 MB"));


    }

    public static long getFolderSize(File folder) {
        long sum = 0;
        if(folder.isFile()){
            return folder.length();
        }
        File[] files = folder.listFiles();
        for(File f : files){
            sum = sum + getFolderSize(f);
        }
        return sum;
    }
    public static String getHumanReadableFormat(long fileSize){
        int logBase1024 = 1024;
        String[] unitName = new String[]{"B", "KB", "MB", "GB", "TB"};
        int nameNumber = (int)(Math.log(fileSize) / Math.log(logBase1024));
        double value = fileSize /(Math.pow(logBase1024, nameNumber));
        String result = String.format("%.2f", value);
        return result.concat(" ").concat(unitName[nameNumber]);
    }
    public static String getHumanReadableFormatWithoutLogarithms(long fileSize){
        String[] unitName = new String[]{"B", "KB", "MB", "GB", "TB"};
        for (int i = 0; i < unitName.length; i++){
            double value = fileSize / Math.pow(1024, i);
            if(value < 1024){
                return Math.round(value) + " " + unitName[i];
            }
        }
        return "Size too big";
    }
    public static long getSizeFromHumanReadable(String size){
        HashMap<Character, Long> char2multipliers = getMultipliers();
        char sizeFactor = size.replaceAll("[0-9\\s+]+", "")
                        .charAt(0);
        long multiplier = char2multipliers.get(sizeFactor);
        long length = Long.valueOf(size.replaceAll("[^0-9]", ""));
        return length * multiplier;
    }
    private static HashMap<Character, Long> getMultipliers(){
        char[] multipliers = new char[]{'B', 'K', 'M', 'G', 'T'};
        HashMap<Character, Long> char2multipliers = new HashMap<>();
        for(int i = 0; i < multipliers.length; i ++){
            char2multipliers.put(multipliers[i], (long) Math.pow(1024, i));
        }

        return char2multipliers;
    }

}

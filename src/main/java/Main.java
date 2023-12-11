import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;


public class Main {
    public static void main(String[] args) throws Exception {
        String folderPath = "C:\\Spring";
        File file = new File(folderPath);
        long timeStamp = System.currentTimeMillis();
        System.out.println(getFolderSize(file));
        System.out.println(System.currentTimeMillis() - timeStamp);
        //nio2
//        long size = Files.walk(Path.of("C:\\skillbox\\FilesProject")).mapToLong(p -> p.toFile().length()).sum();
//        System.out.println(size);
        //forkJoin
        FolderSizeCalculator calculator = new FolderSizeCalculator(file);
        ForkJoinPool pool = new ForkJoinPool();
        timeStamp = System.currentTimeMillis();
        System.out.println(pool.invoke(calculator));
        System.out.println(System.currentTimeMillis() - timeStamp);

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
}

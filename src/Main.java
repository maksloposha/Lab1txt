import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        sorting();
        //unification("A.txt","B.txt","C.txt");
        long endTime = System.currentTimeMillis();
        System.out.println(endTime-startTime);

    }
    public static void unification(String fileA,String fileB,String fileC){
        long numBytesB = 0;
        long numBytesC = 0;
        File A = new File(fileA);
        while (A.length()<18){
            int a;
            long[] b = readFile(fileB, numBytesB);
            long[] c = readFile(fileC, numBytesC);
            if(b[0]<c[0]){
                writeFile(fileA,a = (int)b[0]);
                numBytesB = b[1];
            }else{
                writeFile(fileA,a = (int)c[0]);
                numBytesC = c[1];
            }
        }
    }
    public static boolean isSorted(ArrayList<Integer> list){
        if(list.size() <= 1) return true;
        Iterator<Integer> iter = list.iterator();
        Integer current, previous = iter.next();
        while (iter.hasNext()) {
            current =  iter.next();
            if (!IntegerComparatror.compare(previous, current)) {
                return false;
            }
            previous = current;
        }
        return true;
    }
    public static void sorting(){
        ArrayList<Integer> fileBArray = new ArrayList<>();
        ArrayList<Integer> fileCArray = new ArrayList<>();
        boolean fileB = true;
        long count = 0;
        while(true){
           long[] arr =  readFile("A.txt",count);
           if(arr[0] == -1) {
               for(int item: fileBArray) writeFile("B.txt",item);
               for(int item: fileCArray) writeFile("C.txt",item);
               break;
           }
           count = arr[1];
           long a = arr[0];
           if(fileB){
               fileBArray.add((int)a);
               if(!isSorted(fileBArray)){
                   fileB = false;
                   fileCArray.add(fileBArray.remove(fileBArray.size()-1));
                   for(int item: fileBArray) writeFile("B.txt",item);
                   fileBArray.clear();
               }
           }else{
               fileCArray.add((int)a);
               if(!isSorted(fileCArray)){
                   fileB = true;
                   fileBArray.add(fileCArray.remove(fileCArray.size()-1));
                   for(int item: fileCArray) writeFile("C.txt",item);
                   fileCArray.clear();
               }
           }
        }
    }
    public static void fileGeneration(){
        try (FileOutputStream fos = new FileOutputStream("A.txt")) {
            File file = new File("A.txt");
            while (file.length() <= 1_000_024)
                fos.write((Integer.toString((int) (Math.random() * 1000)) + " ").getBytes());
            fos.close();
            System.out.println(file.length());
        } catch (IOException ex) {

            System.out.println(ex.getMessage());
        }
    }
    public static long[] readFile(String fileName, long numBytes){
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            fileInputStream.skipNBytes(numBytes);
            int resultNum = 0;
            int a ;
            int count = 0;
            while ((a = fileInputStream.read()) != ' '){
                if(a == -1) break;
                resultNum = resultNum*10 + ((int) a - 48);
                count++;
            }
            fileInputStream.close();
            return new long[]{resultNum,count + numBytes + 1};
        }catch (IOException ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }
    public static void writeFile(String fileName, int number){
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileName),true);
            fileOutputStream.write((Integer.toString(number) + " ").getBytes());
            fileOutputStream.close();
        }catch (IOException exception){
            System.out.println(exception.toString());
        }

    }
}
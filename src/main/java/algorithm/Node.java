package algorithm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by manatea on 2016/11/30.
 */
public class Node {
    public static void importAndExportResult(List<Vector> result, String fileName){
        try {
            File file = new File(fileName);
//            BufferedReader in =  new InputStreamReader(new FileInputStream(file)));
//            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            RandomAccessFile out = new RandomAccessFile(file,"rw");
            out.seek(file.length());
            StringBuilder str = new StringBuilder();
            for(Vector vec : result){
                str.append(" [ ").append(vec.getX()).append(" ")
                        .append(vec.getY()).append(" ")
                        .append(vec.getZ()).append(" ] ;");
            }
            out.writeUTF(str.toString());
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        List a= new ArrayList();
        a.add(new Vector(3,5,1));
        a.add(new Vector(7,2,1));
        a.add(new Vector(3,5,1));
        a.add(new Vector(7,2,1));
        System.out.println("success");
        importAndExportResult(a,"1");
    }
}

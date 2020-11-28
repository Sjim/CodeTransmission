import java.io.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Vector;

public class ReadVec {
    public Vector<Vector<Integer>> vecOfmutation = new Vector<>();
    public Vector<Vector<Integer>> vecOfBranch = new Vector<>();
    public final Vector<File> fileList = new Vector<>();
    public void read(String path) throws IOException {

        File outerFolder = new File(path);
        for (File innerFolder:
                Objects.requireNonNull(outerFolder.listFiles())) {
            if(!innerFolder.isFile()){
                String tempPath = innerFolder.getPath()+"\\vector";
                File folder = new File(tempPath);
                for (File test:
                        Objects.requireNonNull(folder.listFiles())) {
                    if(test.getName().contains("Test")){
                        File fileOfBranch = new File(test.getPath()+"\\branch-vector.txt");
                        File fileOfMutation = new File(test.getPath()+"\\mutation-vector.txt");
                        Vector<Integer> branch = file2Vector(fileOfBranch);
                        vecOfBranch.add(branch);
                        Vector<Integer> mutation = file2Vector(fileOfMutation);
                        vecOfmutation.add(mutation);
                    }
                }
                File testFolder = new File(innerFolder.getPath()+"\\src\\test\\java\\net\\mooctest");
                fileList.addAll(Arrays.asList(Objects.requireNonNull(testFolder.listFiles())));
            }
        }
    }
    public static Vector<Integer> file2Vector(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String string = reader.readLine();
        Vector<Integer> vec = new Vector<>();
        String[]temp =string.substring(1,string.length()-1).split(", ");
        for (String s:
                temp) {
            vec.add(Integer.parseInt(s));
        }
        return vec;
    }
    public static int[][] vec2Array(Vector<Vector<Integer>> vectors) {
        int[][] res = new int[vectors.size()][];
        for (int i = 0; i < vectors.size(); i++) {
            Object[] objects = vectors.get(i).toArray();
            int size = vectors.get(i).size();
            int[] temp = new int[size];
            for (int j = 0; j < size; j++) {
                temp[j] = (int) objects[j];
            }
            res[i] = temp;
        }
        return res;
    }
    public static void main(String[] args){

    }
}

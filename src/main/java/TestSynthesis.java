import java.io.*;
import java.util.Arrays;
import java.util.Set;
import java.util.Vector;

public class TestSynthesis {
    private Vector<File> tests;
    private Vector<String> lines;
    public void filter(Vector<File>fileList,int[] selectVec){
        tests = new Vector<File>();
        for (int i = 0; i < selectVec.length; i++) {
            if(selectVec[i] == 1) tests.add(fileList.get(i));
        }
    }
    public void output() throws IOException {
        File test = new File("MyTest.java");
        if(!test.exists()) test.createNewFile();
        BufferedWriter bf = new BufferedWriter(new FileWriter(test.getAbsoluteFile()));
        lines = new Vector<>();
        lines.add("package net.mooctest;");
        lines.add("public class MyTest{");
        for (File f:
             tests) {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line = reader.readLine();
            while(line!=null){
                if (line.contains("package")){
                    line = reader.readLine();
                    continue;
                }
                if(line.contains("import")){
                    if(!contains(lines,line)){
                        lines.add(1,line);
                    }else{
                        line = reader.readLine();
                        continue;
                    }
                }
               else if(!line.contains("public class")){
                   lines.add(line);
               }
                line = reader.readLine();
            }
            lines.remove("}");
        }
        lines.add("}");
//        int num =0;
//        for (int i = 0; i < lines.size(); i++) {
//            if(lines.get(i).contains("public void")){
//                lines.set(i,"    public void test"+ num +"(){");
//                num++;
//            }
//        }
        for (String s:
             lines) {
            bf.write(s+"\n");
        }
        bf.close();
    }
    public static boolean contains(Vector<String> vector,String s){
        for (String temp :
             vector) {
            if(temp.equals(s)){
                return true;
            }
        }
        return false;
    }
    public static void main(String[] args){
        ReadVec readVec = new ReadVec();
        String path = "G:\\study\\自动化测试\\round2-selected-vector-reports\\01-CMD";
        try {
            readVec.read(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] vecOfTests = ReadVec.vec2Array(readVec.vecOfBranch);
        //需要覆盖的向量 默认全1
        int[] pathToCover = new int[readVec.vecOfBranch.get(0).size()];
        Arrays.fill(pathToCover, 1);

        FromAtom2Test fromAtom2Test = new FromAtom2Test(vecOfTests,pathToCover);
        fromAtom2Test.select();

        fromAtom2Test.print(fromAtom2Test.selectVec);
        TestSynthesis testSynthesis = new TestSynthesis();
        testSynthesis.filter(readVec.fileList,fromAtom2Test.selectVec);
        try {
            testSynthesis.output();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

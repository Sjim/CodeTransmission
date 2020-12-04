import com.github.javaparser.ast.CompilationUnit;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Vector;

public class TestSynthesis {
    private Vector<File> testFiles;
    private Vector<CU> tests = new Vector<>();
    private String path;
    private static final String BRANCH = "branch";
    private static final String MUTATION = "mutation";
    public void filter(Vector<File>fileList,int[] selectVec){
        testFiles = new Vector<>();
        for (int i = 0; i < selectVec.length; i++) {
            if(selectVec[i] == 1) testFiles.add(fileList.get(i));
        }
    }
    public void output(String testName,String type) throws IOException {
        for (int i = 0; i < tests.size(); i++) {
            File folder = new File(path+"\\TestsGenerated\\"+testName);
            if(!folder.exists()) folder.mkdirs();
            File test = new File(path+"\\TestsGenerated\\"+testName+"\\MyTest_"+type+i+".java");
            if(!test.exists()) test.createNewFile();
            BufferedWriter bf = new BufferedWriter(new FileWriter(test.getAbsoluteFile()));
            bf.write(tests.get(i).getTest().toString());
            bf.close();
        }
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
    public void parseTest(){
        for (File file:
             testFiles) {
            CU cu = new CU();
            cu.CodeTransformer(file);
            tests.add(cu);
        }
    }
    public void synthesis(){
        for (int i = 0; i < tests.size(); i++) {
            for (int j = i+1; j < tests.size(); j++) {
                CU a = tests.get(i);
                CU b = tests.get(j);
                if(a!=null && b!=null&&a.composable(b)){
                    a.compose(b);
                    tests.set(j,null);
                }
            }
        }
        tests.removeIf(Objects::isNull);
    }
    public void run(String dirName,String type){
        ReadVec readVec = new ReadVec();
        //FIXME 修改源文件所在绝对地址
        //String dirPath = "D:\\Unpack\\round2-selected-vector-reports\\";
        path ="G:\\study\\自动化测试\\round2-selected-vector-reports\\";
        try {
            readVec.read(path+dirName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[][] vecOfTests;
        int[] pathToCover;
        if(type.equals(BRANCH)){
            vecOfTests = ReadVec.vec2Array(readVec.vecOfBranch);
            //需要覆盖的向量 默认全1
            pathToCover = new int[readVec.vecOfBranch.get(0).size()];
        }else {
            vecOfTests = ReadVec.vec2Array(readVec.vecOfmutation);
            //需要覆盖的向量 默认全1
            pathToCover = new int[readVec.vecOfmutation.get(0).size()];
        }
        Arrays.fill(pathToCover, 1);
        FromAtom2Test fromAtom2Test = new FromAtom2Test(vecOfTests,pathToCover);
        fromAtom2Test.select();
        fromAtom2Test.print(fromAtom2Test.selectVec);

        filter(readVec.fileList,fromAtom2Test.selectVec);
        parseTest();
        synthesis();
        try {
            output(dirName,type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        TestSynthesis testSynthesis = new TestSynthesis();
        //修改 以生成不同测试
        testSynthesis.run("03-ALU",BRANCH);
        testSynthesis.run("03-ALU",MUTATION);

    }
}
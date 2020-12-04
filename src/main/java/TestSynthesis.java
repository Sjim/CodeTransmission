import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.Vector;

public class TestSynthesis {
    private Vector<File> testFiles;
    private Vector<Vector<String>> lines;
    private Vector<Test> tests = new Vector<>();
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
            Test t = tests.get(i);
            for (String s:
                 t.imp) {
                bf.write(s+"\n");
            }
            bf.write("public class MyTest_"+type+i+"(){\n");
            for(String s:t.before){
                bf.write("\t"+s+"\n");
            }
            for (String s:t.tests) {
                bf.write("\t"+s+"\n");
            }
            bf.write("}\n");
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
    public void parseTest(FromFileToLines fromFileToLines){
        for (File file:
             testFiles) {
            fromFileToLines.CodeTransformer(file);
            Test t = fromFileToLines.getTest();
            tests.add(t);
        }
    }
    public void synthesis(){
        for (int i = 0; i < tests.size(); i++) {
            for (int j = i+1; j < tests.size(); j++) {
                Test a = tests.get(i);
                Test b = tests.get(j);
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
        String dirPath = "D:\\Unpack\\round2-selected-vector-reports\\";
        try {
            readVec.read(dirPath+dirName);
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
        path = dirPath;
        filter(readVec.fileList,fromAtom2Test.selectVec);
        FromFileToLines fromFileToLines = new FromFileToLines();
        parseTest(fromFileToLines);
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
        testSynthesis.run("02-DataLog",BRANCH);
        testSynthesis.run("02-DataLog",MUTATION);

    }
}
//TODO fromFileTOLines 把文件转化为Test[]
//TODO 把Test[]中before相同的import加起来，tests加起来合成最终Test[]
//TODO 从Test[]生成最终MyTest文件
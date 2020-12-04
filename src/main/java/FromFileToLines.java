import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Vector;

public class FromFileToLines {
    private File javaFile;
    private String javaPath;
    private CompilationUnit cu = new CompilationUnit();
    private Test test;

    public void CodeTransformer(File javaFile) {
        this.javaFile = javaFile;
        this.javaPath = javaFile.getAbsolutePath();
        try {
            this.cu = StaticJavaParser.parse(javaFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error in TestCodeTransformer, constructor");
            e.printStackTrace();
        }
    }

    public Test getTest() {
        test = new Test();
        if (cu.getPackageDeclaration().isPresent()) test.imp.add(cu.getPackageDeclaration().get().toString());
        for (ImportDeclaration i :
                cu.getImports()) {
            test.imp.add(i.toString());
        }
        ClassOrInterfaceDeclaration classOrInterface = (ClassOrInterfaceDeclaration) cu.getType(0);
        for (BodyDeclaration<?> b :
                classOrInterface.getMembers()) {
            if(!b.getAnnotations().toString().contains("@Test"))
            test.before.add(b.toString());
            else test.tests.add(b.toString());
        }

        return test;
    }

    public static void main(String[] args) {
        FromFileToLines fromFileToLines = new FromFileToLines();
        File f = new File("D:\\Unpack\\round2-selected-vector-reports\\TestsGenerated\\02-DataLog\\MyTest_branch.java");

        fromFileToLines.CodeTransformer(f);
        Test t = fromFileToLines.getTest();
    }
}

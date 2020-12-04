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

public class CU {
    private CompilationUnit cu = new CompilationUnit();
    private Vector<String> fields = new Vector<>();
    private Vector<String> tests = new Vector<>();
    public void CodeTransformer(File javaFile) {
        try {
            this.cu = StaticJavaParser.parse(javaFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error in TestCodeTransformer, constructor");
            e.printStackTrace();
        }
        for(BodyDeclaration<?> bodyDeclaration:cu.getType(0).getMembers()){
            if(bodyDeclaration.isFieldDeclaration()){
                fields.add(bodyDeclaration.toString());
            }else if(bodyDeclaration.isMethodDeclaration()&&bodyDeclaration.toString().contains("@Before")){
                fields.add(bodyDeclaration.toString());
            }else {
                tests.add(bodyDeclaration.toString());
            }
        }
    }
    public CompilationUnit getTest() {
        return cu;
    }
    public boolean composable(CU cu){

        if(fields.size()!=cu.fields.size()) return false;
        for (int i = 0; i < cu.fields.size(); i++) {
            if(!cu.fields.get(i).equals(fields.get(i))) return false;
        }
        return true;
    }
    public void compose(CU cu){
        this.tests.addAll(cu.tests);
        for (BodyDeclaration<?> bodyDeclaration:
             cu.getTest().getType(0).getMembers()) {
            if (bodyDeclaration.toString().contains("@Test")) this.cu.getType(0).addMember(bodyDeclaration);
        }
        for (ImportDeclaration i:
             cu.getTest().getImports()) {
            if(!this.cu.getImports().contains(i)) this.cu.addImport(i);
        }
    }
}

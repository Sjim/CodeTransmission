package visitor.collector;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import util.JunitUtil;

import java.util.List;

/**
 *
 * TestMethodCollector can collect test method from cu. It extends VoidVisitorAdapter
 * And treat MethodDeclaration as targets.
 * @see VoidVisitorAdapter
 * @see MethodDeclaration
 *
 * @author QRX
 * @email QRXwzx@outlook.com
 * @date 2020-04-22
 */
public class TestMethodCollector extends VoidVisitorAdapter<List<MethodDeclaration>> {

    @Override
    public void visit(MethodDeclaration md, List<MethodDeclaration> list) {
        super.visit(md, list);
        if(JunitUtil.isTestAnnotatedMethod(md) && JunitUtil.hasAssert(md))
            list.add(md);
    }
}

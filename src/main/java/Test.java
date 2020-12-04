import java.util.Vector;

public class Test {
    Vector<String> imp = new Vector<>();
    Vector<String> before = new Vector<>();
    Vector<String> tests = new Vector<>();
    public boolean composable(Test t){
        if(before.size()!=t.before.size()) return false;
        for (int i = 0; i < before.size(); i++) {
            if(!before.get(i).equals(t.before.get(i))) return false;
        }
        return true;
    }
    public void compose(Test t){
        tests.addAll(t.tests);
        for (String s:
             t.imp) {
            if(!TestSynthesis.contains(imp,s)) imp.add(s);
        }
    }
}

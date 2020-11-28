import java.io.IOException;
import java.util.Arrays;
public class FromAtom2Test {
    //结果向量
    public int[] selectVec;
    //可选择的覆盖路径或方法
    private final int[] pathToCover;
    //所有测试覆盖向量集
    private final int[][] vecOfTests;
    FromAtom2Test(int[][]v, int[]p){
        vecOfTests = v;
        pathToCover = p;
    }

    public void select(){
        int[]pathLeft =Arrays.copyOf(pathToCover,pathToCover.length);
        int sizeOfTests = vecOfTests.length;
        selectVec = new int[sizeOfTests];
        for(int i = 0; i< sizeOfTests; i++) selectVec[i] = 0;
        int[][] temp = {pathLeft};
        int max = getMax(vecOfTests,pathToCover);
        while(getMax(temp,pathToCover)>pathToCover.length-max){
            incGreed(pathLeft);
            temp = new int[][]{pathLeft};
        }
        System.out.println();
    }
    /*
        the incGreed method to find tests
        增量贪心
        @param pathLeft
        the path left to cover
     */
    private void incGreed(int[] pathLeft){
        int max = 0;
        for (int[] vec:
             vecOfTests) {
            int temp=getSum(vec,pathLeft);
            if (temp>max) max=temp;
        }
        for (int i = 0; i < vecOfTests.length; i++) {
            if(getSum(vecOfTests[i],pathLeft)==max){
                selectVec[i] = 1;
                for (int j = 0; j < pathLeft.length; j++) {
                    if(vecOfTests[i][j]==1) pathLeft[j] = 0;
                }
                return;
            }

        }
    }
    //求单个测试向量集覆盖数量和
    private int getSum(int[]vec,int[]pathLeft){
        int temp=0;
        for (int i = 0; i < pathLeft.length; i++) {
            temp+=(vec[i]+pathLeft[i])/2;
        }
        return temp;
    }
    //求所有测试向量集覆盖数量和
    private int getMax(int[][]vecOfTests,int[] pathToCover){
        int max=0;
        for(int index=0;index<vecOfTests[0].length;index++){
            if(pathToCover[index]==1){
                int isCover = 0;
                for (int[] test:vecOfTests) {
                    isCover = (isCover+test[index]+1)/2;
                }
                max+=isCover;
            }
        }
        return max;
    }
    public void print(int[] a){
        for (int i:
             a) {
            System.out.print(i+" ");
        }
    }

}

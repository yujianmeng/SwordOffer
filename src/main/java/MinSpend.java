import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by yujian on 2018/3/13.
 * 在一个圆形操场的周围摆放着n(n<=100)堆石子，现将石子有次序的合并在一堆。
 * 规定每次只能选相邻的2堆石子合并成一个新堆，并将新的一堆石子数量记为该次合并的费用。
 * 试设计一个算法，计算出将n堆石子合并成一堆，花费最少的费用
 */
public class MinSpend {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int tests = scanner.nextInt();
        int[] minSps = new int[tests];
        for (int j = 0; j < tests; j++) {
            int n = scanner.nextInt();
            List<Integer> wList = new LinkedList<Integer>();
            int i =0;
            while (i<n){
                wList.add(scanner.nextInt());
                i++;
            }
            minSps[j] = minSpend(wList);
        }
        for (int i = 0; i < tests; i++) {
            System.out.println(minSps[i]);
        }

    }
    private static int minSpend(List<Integer> wList){
        if (wList.size()==1){
            return 0;
        }
        int minSp = wList.get(0)+wList.get(1);
        int index=0;
        for (int i = 0; i < wList.size()-1; i++) {
            if (wList.get(i)+wList.get(i+1)<minSp){
                minSp = wList.get(i)+wList.get(i+1);
                index = i;
            }
        }
        wList.set(index,minSp);
        wList.remove(index+1);
        return minSp+minSpend(wList);
    }
}

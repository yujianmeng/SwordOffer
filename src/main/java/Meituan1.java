import java.util.Scanner;

/**
 * Created by yujian on 2018/3/22.
 * 给出两个相同长度的由字符 a 和 b 构成的字符串，
 * 定义它们的距离为对应位置不同的字符的数量。
 * 如串”aab”与串”aba”的距离为 2；串”ba”与串”aa”的距离为 1；
 * 串”baa”和串”baa”的距离为 0。下面给出两个字符串 S 与 T，其中 S 的长度不小于 T 的长度。
 * 我们用|S|代表 S 的长度，|T|代表 T 的长度，那么在 S 中一共有|S|-|T|+1 个与 T 长度相同的子串，
 * 现在你需要计算 T 串与这些|S|-|T|+1 个子串的距离的和。
 */
public class Meituan1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        String t = scanner.nextLine();
        int sum = 0;
        String tmp;
        for (int i = 0; i < s.length()-t.length()+1; i++) {
            tmp = s.substring(i,t.length()+i);
            for (int j = 0; j < t.length(); j++) {
                if (tmp.charAt(j)!=t.charAt(j)){
                    sum++;
                }
            }
        }
        System.out.println(sum);
    }
}

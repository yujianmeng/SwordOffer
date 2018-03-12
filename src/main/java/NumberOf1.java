/**
 * Created by yujian on 2018/3/12.
 * 二进制中 1 的个数
 */
public class NumberOf1 {

    public int NumberOf1(int n) {
        int cnt = 0;
        while (n != 0) {
            cnt++;
            n &= (n - 1);
        }
        return cnt;
    }
}

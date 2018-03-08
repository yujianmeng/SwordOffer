/**
 * Created by yujian on 2018/3/
 * 请实现一个函数，将一个字符串中的空格替换成“%20”。
 * 例如，当字符串为 We Are Happy. 则经过替换之后的字符串为 We%20Are%20Happy。
 */
public class ReplaceSpace {

    public String replaceSpace(StringBuffer str){
        int n = str.length();
        for (int i = 0; i < n; i++) {
            if (str.charAt(i)==' '){
                str.append("  ");
            }
        }

        int originalIndex = n-1;
        int newIndex = str.length()-1;
        while (newIndex>originalIndex&&originalIndex>0){
            if (str.charAt(originalIndex)==' '){
                str.setCharAt(newIndex--,'0');
                str.setCharAt(newIndex--,'2');
                str.setCharAt(newIndex--,'%');
            }
            else {
                str.setCharAt(newIndex--,str.charAt(originalIndex));
            }
            originalIndex--;
        }
        return str.toString();
    }
}

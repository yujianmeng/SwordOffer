
import java.util.*;

public class MinMaxTime {

/** 请完成下面这个函数，实现题目要求的功能 **/
    /** 当然，你也可以不按照这个模板来作答，完全按照自己的想法来 ^-^  **/
    static String getMinMaxTime(String[] inputStr) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        String minTime;
        String maxTime="";
        for (int i = 0; i < inputStr.length; i++) {
            nums.add(Integer.valueOf(inputStr[i]));
        }
        Collections.sort(nums);
        if (nums.get(0)*10+nums.get(1)>24||nums.get(2)*10+nums.get(3)>59||nums.get(4)*10+nums.get(5)>59){
            return "N/A";
        }
        minTime = (nums.get(0)*10+nums.get(1))+""+(nums.get(2)*10+nums.get(3))+""+(nums.get(4)*10+nums.get(5))+"";

        int i=0;
        while (i<nums.size()&&nums.get(i)<=2){
            i++;
        }
        maxTime = maxTime+nums.get(i-1)+"";
        nums.remove(i-1);

        i=0;
        while (i<nums.size()&&nums.get(i)<4){
            i++;
        }
        maxTime = maxTime+nums.get(i-1)+"";
        nums.remove(i-1);

        i=0;
        while (i<nums.size()&&nums.get(i)<=5){
            i++;
        }
        maxTime = maxTime+nums.get(i-1)+"";
        nums.remove(i-1);

        i=0;
        while (i<nums.size()&&nums.get(i)<=9){
            i++;
        }
        maxTime = maxTime+nums.get(i-1)+"";
        nums.remove(i-1);

        i=0;
        while (i<nums.size()&&nums.get(i)<=5){
            i++;
        }
        maxTime = maxTime+nums.get(i-1)+"";
        nums.remove(i-1);

        i=0;
        while (i<nums.size()&&nums.get(i)<=9){
            i++;
        }
        maxTime = maxTime+nums.get(i-1)+"";
        nums.remove(i-1);

        if (minTime.equals(maxTime))
            return minTime;

        else return minTime+","+maxTime;
    }

    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        String res;

        int _inputStr_size = 0;
        _inputStr_size = Integer.parseInt(in.nextLine().trim());
        String[] _inputStr = new String[_inputStr_size];
        String _inputStr_item;
        for(int _inputStr_i = 0; _inputStr_i < _inputStr_size; _inputStr_i++) {
            try {
                _inputStr_item = in.nextLine();
            } catch (Exception e) {
                _inputStr_item = null;
            }
            _inputStr[_inputStr_i] = _inputStr_item;
        }

        res = getMinMaxTime(_inputStr);
        System.out.println(res);
    }
}
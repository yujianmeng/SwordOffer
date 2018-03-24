import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by yujian on 2018/3/23.
 */
public class NewDay {
    static int days[]={31,28,31,30,31,30,31,31,30,31,30,31};
    static int year = 2018, mon = 3, day = 23;

    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = df.format(new Date());
        year = Integer.parseInt(date.substring(0,4));
        mon = Integer.parseInt(date.substring(4,6));
        day = Integer.parseInt(date.substring(6,8));
        Scanner scanner = new Scanner(System.in);
        int num = scanner.nextInt();
        getYMD(num);
    }


    public static void getYMD(int num){

        if ((year % 4 == 0 && year % 100 == 0) || year % 400 == 0)
            days[1] = 29; //闰年二月29
        else
            days[1] = 28;//平年二月28
        day += num;
        while (day > days[mon - 1]) //天数超过当前月最搜索大天数，要过月
        {
            day -= days[mon - 1];
            mon += 1;
            if (mon > 12) //月满则变年
            {
                mon = 1;
                year += 1; //年变后，要重新确认下一年是否闰年
                if ((year % 4 == 0 && year % 100 == 0) || year % 400 == 0)
                    days[1] = 29;
                else
                    days[1] = 28;
            }
        }
        System.out.println(year+""+mon+""+day+"");
    }
}

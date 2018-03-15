import java.util.Scanner;

/**
 * Created by yujian on 2018/3/13.
 */
public class RabbitNums {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        int i = 0;
        while (i<n){
            nums[i] = getRabbitNums(scanner.nextInt());
            i++;
        }
        for (int j = 0; j < n; j++) {
            System.out.println(nums[j]);
        }
    }

    private static int getRabbitNums(int month){
        if (month<3){
            return 1;
        }
        int[] rabbitNums = new int[month+1];
        rabbitNums[1] = 1;
        rabbitNums[2] = 1;
        for (int i = 3; i < rabbitNums.length; i++) {
            rabbitNums[i] = rabbitNums[i-1]+rabbitNums[i-2];
        }
        return rabbitNums[rabbitNums.length-1];
    }
}

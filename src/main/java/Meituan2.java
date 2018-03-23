import java.util.Scanner;

/**
 * Created by yujian on 2018/3/22.
 */
public class Meituan2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int minN = 0;
        String nums= scanner.nextLine();
        for (int i = 1; i < 10; i++) {
            if (!nums.contains(i+"")){
                minN = i;
                System.out.println(minN);
                return;
            }
        }

        char[] chars = nums.toCharArray();
        int[] numbers = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            numbers[i] = new Integer(chars[i]);
        }
        quick_sort(numbers,0,numbers.length-1);
        if (!nums.contains(0+"")){
            minN = numbers[0]*10;
            System.out.println(minN);
            return;
        }

        int i = 0;
        for (i = 0; i < numbers.length-1; i++) {
            if (numbers[i]!=numbers[i+1]){
                break;
            }
        }
        int minNum = numbers[0];
        for (int j = 1; j <= i+2; j++) {
            minN = (int)(minN + minNum*Math.pow(10,j));
        }
        System.out.println(minN);
    }

    private static void quick_sort(int[] arr,int left,int right){
        if (left<right){
            int x = arr[left];
            int l = left;
            int r = right;
            while (l<r) {
                while (l < r && arr[r] >= x) {
                    r--;
                }
                if (l < r) {
                    arr[l++] = arr[r];
                }
                while (l < r && arr[l] < x) {
                    l++;
                }
                if (l < r) {
                    arr[r--] = arr[l];
                }
            }
            arr[l] = x;
            quick_sort(arr,left,l-1);
            quick_sort(arr,l+1,right);
        }
    }
}

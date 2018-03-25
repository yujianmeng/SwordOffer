import java.util.Scanner;

/**
 * Created by yujian on 2018/3/24.
 */
public class TouTiao1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] nums = new int[n];
        int k = scanner.nextInt();
        int sumK=0;
        int i =0;
        while (i<n){
            nums[i] = scanner.nextInt();
            i++;
        }
        quick_sort(nums,0,nums.length-1);
        for (int j = 0; j < nums.length; j++) {
            if (j!=0){
                if (nums[j]!=nums[j-1]) {
                    int t = nums[j] + k;
                    if (queryN(nums, t)) {
                        sumK++;
                    }
                }
            }
            else {
                int t = nums[j] + k;
                if (queryN(nums, t)) {
                    sumK++;
                }
            }
        }
        System.out.println(sumK);
    }

    private static boolean queryN(int[] nums,int t){
        for (int i = 0; i < nums.length; i++) {
            if (nums[i]==t){
                return true;
            }
        }
        return false;
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

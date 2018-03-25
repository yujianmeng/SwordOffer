import java.util.Scanner;

/**
 * Created by yujian on 2018/3/24.
 */
public class TouTiao5 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        int[] hs = new int[N];
        int K = scanner.nextInt();
        int H = scanner.nextInt();
        int maxH=0;
        int i = 0;
        while (i<N){
            hs[i] = scanner.nextInt();
            i++;
        }
        quick_sort(hs,0,hs.length-1);
        int j = 0;
        while (j<K){
            if (Math.abs(maxH-hs[j])<=H&&hs[j]>maxH) {
                maxH = calcuH(maxH, hs[j]);
                j++;
            }
            else {
                break;
            }
        }
        System.out.println(maxH);

    }

    private static int calcuH(int chu,int tiaobanH){
        int h = chu+(tiaobanH-chu)*2;
        return h;
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


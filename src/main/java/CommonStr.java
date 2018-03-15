import java.util.Scanner;

/**
 * Created by yujian on 2018/3/13.
 */
public class CommonStr {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] results = new int[n];
        int i = 0;
        scanner.nextLine();
        while (i<n){
            String str1 = scanner.nextLine();
            String str2 = scanner.nextLine();
            results[i] = getCommonStr(str1,str2);
            i++;
        }
        for (int j = 0; j < n; j++) {
            System.out.println(results[j]);
        }
    }
    private static int getCommonStr(String str1, String str2){
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        for (int i = 0; i < str2.length(); i++) {
            for (int j = 0,k=str2.length()-i; k!=str2.length()+1; j++,k++) {
                String tmp = str2.substring(j,k);
                if (str1.contains(tmp)){
                    return tmp.length();
                }
            }
        }
        return 0;
    }
}

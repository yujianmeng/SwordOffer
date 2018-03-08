/**
 * Created by yujian on 2018/3/7.
 * 在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。
 * 数组中某些数字是重复的，但不知道有几个数字是重复的。也不知道每个数字重复几次。
 * 请找出数组中任意一个重复的数字。 例如，如果输入长度为 7 的数组 {2, 3, 1, 0, 2, 5, 3}，
 * 那么对应的输出是第一个重复的数字 2。
 */
public class DuplicateNum {

    public static boolean findDuplicateNum(int[] numbers, int length, int[] duplication){
        for (int i = 0; i <length; i++) {
            while (numbers[i]!=i && numbers[i]!=numbers[numbers[i]]){
                swap(numbers, i, numbers[i]);
            }
            if (numbers[i]!=i && numbers[i]==numbers[numbers[i]]){
                duplication[0] = numbers[i];
                return true;
            }
        }
        return false;
    }

    private static void swap(int[] numbers, int i, int j){
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    public static void main(String[] args) {
        int numbers[] = {6,1,4,7,5,3,6,2};
        int length = numbers.length;
        int[] duplication = new int[length];
        boolean result = findDuplicateNum(numbers, length, duplication);
        if (result){
            System.out.println(duplication[0]);
        }
    }
}

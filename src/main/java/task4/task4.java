package task4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Ближайшим числом для каждого из элементов массива будет среднее арифметическое этого массива
 * поэтому нет необходимости отнимать или прибавлять по единице к каждому числу
 */
public class task4 {
    public static void main(String[] args) {
        List<Integer> nums = getNums(args[0]);
        int average = getAverage(nums);
        int stepsQuantity = 0;

        //Количество шагов рассчитывается для каждого числа в массиве
        for (Integer num : nums) {
            //Число из массива - среднее арифметическое = количество шагов
            stepsQuantity += Math.abs(num - average);
        }
        System.out.print(stepsQuantity);
    }

    /**
     * @param filePath - путь к файлу с числами
     * @return - массив чисел из файла
     */
    private static List<Integer> getNums(String filePath) {
        List<Integer> nums = new ArrayList<>();

        try (BufferedReader numsFile = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = numsFile.readLine()) != null) {
                nums.add(Integer.parseInt(line));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return nums;
    }

    /**
     * @param nums - массив чисел
     * @return - среднее арифметическое массива
     */
    private static int getAverage(List<Integer> nums){
        return nums.stream()
                .reduce(Integer::sum)
                .get() / nums.size();
    }
}

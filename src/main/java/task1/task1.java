package task1;

import java.util.*;

public class task1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] strArr = scanner.nextLine().split(" ");
        int n = Integer.parseInt(strArr[0]);
        int m = Integer.parseInt(strArr[1]);
        System.out.println(getPathCircleArray(n, m));
    }

    /**
     * Инициализирует массив от единицы до n
     * @param n
     * @return
     */
    private static int[] initArray(int n){
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i+1;
        }
        return array;
    }

    /**
     * Конвертирует проинициализированный массив в двусвязный список
     * @param array
     * @return
     */
    private static LinkedList<Integer> initLinkedList(int[] array){
        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int j : array) {
            linkedList.add(j);
        }
        return linkedList;
    }

    /**
     * Метод проходит по очереди circleArray длинной n с шагом m.
     * В результате выводит путь с первыми цифрами каждого пути.
     * @param n
     * @param m
     * @return
     */
    private static StringBuilder getPathCircleArray(int n, int m){
        //Инициализируется очередь
        LinkedList<Integer> circleArray = initLinkedList(initArray(n));
        ArrayList<Integer> result = new ArrayList<>();
        int[] interval = new int[m];

        //Первый шаг делается вне цикла while
        //Так как первая цифра результата всегда 1
        for (int i = 0; i < m-1; i++) {
            //Удаляем из начала очереди первую цифру и сохраняем её
            int x = circleArray.removeFirst();
            interval[i] = x;
            //Добавляем сохранённую цифру в конец очереди
            circleArray.add(x);
        }
        //Оставляем последнюю цифру шага без удаления
        //Так как с неё начнётся следующий шаг
        interval[m-1] = circleArray.getFirst();
        result.add(interval[0]);

        while (!(circleArray.getFirst() == 1)){
            for (int i = 0; i < m-1; i++) {
                int x = circleArray.removeFirst();
                interval[i] = x;
                circleArray.add(x);
            }
            interval[m-1] = circleArray.getFirst();
            result.add(interval[0]);
        }

        //Создаём строку в виде ответа из массива цифр
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer num :
                result) {
            stringBuilder.append(num);
        }

        return stringBuilder;
    }
}

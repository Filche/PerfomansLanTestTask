package task3;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.List;

public class task3 {
    private static final String TESTS = "tests";
    private static final String VALUES = "values";
    private static final List<Value> VALUE_LIST = new ArrayList<>();
    private static final List<Test> TEST_LIST = new ArrayList<>();

    public static void main(String[] args) {
        getValues(args[0]);
        getTests(args[1]);
        TEST_LIST.forEach(task3::setValues);
        writeReport(args[2]);
    }

    /**
     * Добавляем в массив VALUE_LIST значения из файла
     * @param filePath - путь к файлу
     */
    private static void getValues(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;

        //Получаем из файла данные в виде дерева
        try {
            jsonNode = objectMapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Извлекаем каждое значение Value и добавляем его в массив
        for (JsonNode valueNode : jsonNode.path(VALUES)) {
            Value value = new Value();
            value.setId(valueNode.path("id").intValue());
            value.setValue(valueNode.path("value").textValue());
            VALUE_LIST.add(value);
        }
    }

    /**
     * Добавляем в массив TEST_LIST значения из файла
     * @param filePath - путь к файлу
     */
    private static void getTests(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode;

        try {
            rootNode = mapper.readTree(new File(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (JsonNode testNode : rootNode.path(TESTS)) {
            TEST_LIST.add(parseNode(testNode));
        }
    }

    /**
     *
     * @param jsonNode - нода, которую нужно пропарсить
     * @return - пропарсенные данные в виде объекта типа Test
     */
    private static Test parseNode(JsonNode jsonNode) {
        Test test = new Test();
        test.setId(jsonNode.path("id").intValue());
        test.setTitle(jsonNode.path("title").textValue());
        test.setValue(jsonNode.path("value").textValue());

        if (jsonNode.has("values")) {
            List<Test> currentTest = new ArrayList<>();
            for (JsonNode node : jsonNode.path("values")) {
                currentTest.add(parseNode(node));
            }
            test.setValues(currentTest);
        }

        return test;
    }

    /**
     * Формирует массив значений для дальнейшего сохранения
     * @param test - объект типа Test
     */
    private static void setValues(Test test) {
        for (Value value : VALUE_LIST) {
            if (test.getId() == value.getId()) {
                test.setValue(value.getValue());
                VALUE_LIST.remove(value);
                break;
            }
        }

        if (test.getValues() != null) {
            for (Test nestedTest : test.getValues()) {
                setValues(nestedTest);
            }
        }
    }

    /**
     * Сохраняет данные в файл report.json
     * @param filePath - путь к файлу report
     */
    private static void writeReport(String filePath) {
        Map<String, Object> map = new HashMap<>();
        map.put(TESTS, TEST_LIST);
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(filePath), map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

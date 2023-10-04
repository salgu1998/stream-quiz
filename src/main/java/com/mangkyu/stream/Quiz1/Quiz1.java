package com.mangkyu.stream.Quiz1;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Quiz1 {

    // 1.1 각 취미를 선호하는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz1() throws IOException {
        List<String[]> csvLines = readCsvLines();

        Map<String, Integer> hobbyCountMap = new HashMap<>();
        csvLines.stream()
                .flatMap(data -> Arrays.stream(data[1].trim().split(":")))
                .forEach(hobby -> hobbyCountMap.merge(hobby, 1, (oldValue, newValue) -> ++oldValue));
        return hobbyCountMap;
    }

    // 1.2 각 취미를 선호하는 정씨 성을 갖는 인원이 몇 명인지 계산하여라.
    public Map<String, Integer> quiz2() throws IOException {
        List<String[]> csvLines = readCsvLines();

        HashMap<String, Integer> jungHobby = new HashMap<>();

        csvLines.stream()
                .filter(data -> data[0].startsWith("정"))
                .flatMap(data -> Arrays.stream(data[1].trim().split(":")))
                .forEach(hobby -> jungHobby.merge(hobby, 1, (oldValue, newValue) -> ++oldValue));
        return jungHobby;
    }

    // 1.3 소개 내용에 '좋아'가 몇번 등장하는지 계산하여라.
    public int quiz3() throws IOException {
        List<String[]> csvLines = readCsvLines();

        return csvLines.stream()
                .map(data -> countMessage(data[2], "좋아"))
                .reduce(0, Integer::sum);
    }

    private int countMessage(String message, String target) {
        int index = message.indexOf(target);

        if (index == -1) {
            return 0;
        } else {
            return 1 + countMessage(message.substring(index + 1), target);
        }
    }

    private List<String[]> readCsvLines() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(getClass().getResource("/user.csv").getFile()));
        csvReader.readNext();
        return csvReader.readAll();
    }

}

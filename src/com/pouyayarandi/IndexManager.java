package com.pouyayarandi;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pouya on 3/17/20.
 */
public class IndexManager {

    String questionsFileAddress = "QueryResults-Questions.csv";
    String answersFileAddress = "QueryResults-Answers.csv";

    private CSVParser questionParser;
    private CSVParser answerParser;
    private List<Question> questions = new ArrayList<>();
    private List<Answer> answers = new ArrayList<>();

    IndexManager() throws IOException {
        FileInputStream questionFile = new FileInputStream(questionsFileAddress);
        InputStreamReader questionInput = new InputStreamReader(questionFile);
        questionParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(questionInput);

        FileInputStream answerFile = new FileInputStream(answersFileAddress);
        InputStreamReader answerInput = new InputStreamReader(answerFile);
        answerParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(answerInput);
    }

    Question[] getQuestions() {
        return questions.toArray(new Question[questions.size()]);
    }

    Answer[] getAnswers() {
        return answers.toArray(new Answer[answers.size()]);
    }

    void indexData() throws IOException {
        for (CSVRecord record : questionParser)
            questions.add(new Question(record));

        for (CSVRecord record : answerParser)
            answers.add(new Answer(record));
    }
}

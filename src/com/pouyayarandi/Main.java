package com.pouyayarandi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            IndexManager indexManager = new IndexManager();
            System.out.println("Waiting for index (It may take a while) ...");
            indexManager.indexData();
            System.out.println("Index finished");
            SearchManager searchManager = new SearchManager(indexManager.getQuestions(), indexManager.getAnswers());

            int input = 4;
            QueryViewModel viewModel;
            switch (input) {
                case 1:
                    viewModel = QueryViewModel.searchBodyWithQuery("ios");
                    break;
                case 2:
                    viewModel = QueryViewModel.searchId("57842756");
                    break;
                case 3:
                    viewModel = QueryViewModel.searchPostsWithNotRelatedTags();
                    break;
                case 4:
                    viewModel = QueryViewModel.searchParentIdAndScore("54401851", 20);
                    break;
                case 5:
                    viewModel = QueryViewModel.searchBodyWithQuery("ios");
                    break;
                case 6:
                    Date lowerDate, upperDate;
                    try {
                        lowerDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-01-28 00:00:00");
                        upperDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2019-01-30 00:00:00");
                    } catch (java.text.ParseException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                    viewModel = QueryViewModel.searchDateAndBody("react", lowerDate, upperDate);
                    break;
                default:
                    return;
            }

            IndexRecord[] indexRecords;
            if (input > 0 && input <= 3)
                indexRecords = searchManager.searchQuestions(viewModel.query);
            else if (input > 3 && input <= 6)
                indexRecords = searchManager.searchAnswers(viewModel.query);
            else return;

            System.out.println(String.format("%d result(s) were found for: %s", indexRecords.length, viewModel.description));

            for (IndexRecord record: indexRecords) {
                Post question = record.post;
                //System.out.println(question.getTitle());
                System.out.println(question.getUser().getDisplayName());
                System.out.println(record.score);
                System.out.println("============================================================");
            }
        } catch (IOException exception) {
            System.out.println(exception.getLocalizedMessage());
        }
    }
}

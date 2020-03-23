package com.pouyayarandi;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SearchManager searchManager;
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            IndexManager indexManager = new IndexManager();
            System.out.println("Waiting for index (It may take a while) ...");
            indexManager.indexData();
            System.out.println("Index finished\n");
            searchManager = new SearchManager(indexManager.getQuestions(), indexManager.getAnswers());
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Enter the mode between 1 to 6 (Base on project description): ");
        int input = scanner.nextInt();
        QueryViewModel viewModel;
        switch (input) {
            case 1:
                System.out.print("Enter your query: ");
                viewModel = QueryViewModel.searchBodyWithQuery(scanner.next());
                break;
            case 2:
                System.out.print("Enter the question id: ");
                viewModel = QueryViewModel.searchId(scanner.next());
                break;
            case 3:
                viewModel = QueryViewModel.searchPostsWithNotRelatedTags();
                break;
            case 4:
                System.out.print("Enter the question id: ");
                String parentId = scanner.next();
                System.out.print("Enter the minimum score: ");
                viewModel = QueryViewModel.searchParentIdAndScore(parentId, scanner.nextInt());
                break;
            case 5:
                System.out.print("Enter your query: ");
                viewModel = QueryViewModel.searchBodyWithQuery(scanner.next());
                break;
            case 6:
                Date lowerDate, upperDate;
                System.out.print("Enter the lower date [e.g: 2019-01-27]: ");
                String lowerDateString = scanner.next() + " 00:00:00";
                System.out.print("Enter the upper date [e.g: 2019-01-27]: ");
                String upperDateString = scanner.next() + " 23:59:59";
                try {
                    lowerDate = simpleDateFormat.parse(lowerDateString);
                    upperDate = simpleDateFormat.parse(upperDateString);
                } catch (java.text.ParseException e) {
                    System.out.println(e.getMessage());
                    return;
                }
                if (lowerDate.getTime() > upperDate.getTime()) {
                    System.out.println("Error: invalid time range");
                    return;
                }
                System.out.print("Enter your query: ");
                viewModel = QueryViewModel.searchDateAndBody(scanner.next(), lowerDate, upperDate);
                break;
            default:
                System.out.println("Error: command is invalid");
                return;
        }

        IndexRecord[] indexRecords;
        if (input <= 3)
            indexRecords = searchManager.searchQuestions(viewModel.query);
        else
            indexRecords = searchManager.searchAnswers(viewModel.query);

        System.out.println(String.format("\nRESULT(S):\n%d result(s) were found for %s\n\n",
                indexRecords.length, viewModel.description));

        for (IndexRecord record: indexRecords) {
            record.post.printObject();
            System.out.println("\n");
        }
    }
}

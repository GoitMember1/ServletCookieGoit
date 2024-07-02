package org.example;

import java.util.Scanner;

public class HttpImageStatusCli {
    private HttpStatusImageDownloader downloader;

    public HttpImageStatusCli() {
        downloader = new HttpStatusImageDownloader();
    }

    public void askStatus() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter HTTP status code: ");
        String input = scanner.nextLine();
        try {
            int code = Integer.parseInt(input);
            downloader.downloadStatusImage(code);
            System.out.println("Image for HTTP status " + code + " downloaded successfully.");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        HttpImageStatusCli cli = new HttpImageStatusCli();
        cli.askStatus();
    }
}
//?
package ua.redrain47.hw11.util;

import java.util.Scanner;

import static ua.redrain47.hw11.messages.CommonMessages.WRONG_CHOICE_TEXT;
import static ua.redrain47.hw11.messages.CommonMessages.WRONG_FORMAT_TEXT;

public class IntegerAnswer {
    private Scanner scanner = new Scanner(System.in);

    public int enterAnswer(String invitingMessage, int lowerBound, int upperBound) {
        String input;
        int answer;

        while (true) {
            try {
                System.out.println(invitingMessage);
                input = scanner.nextLine();
                answer = Integer.parseInt(input);

                if (answer < lowerBound || answer > upperBound) {
                    System.out.println(WRONG_CHOICE_TEXT);
                    continue;
                }

                return answer;
            } catch (NumberFormatException e) {
                System.out.println(WRONG_FORMAT_TEXT);
            }
        }
    }
}

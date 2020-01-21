package ua.redrain47.hw11.view;

import java.util.Scanner;

import static ua.redrain47.hw11.messages.CommonMessages.*;

public abstract class BaseView {
    protected Scanner scanner = new Scanner(System.in);

    public abstract void run();
    public abstract void addData();
    public abstract void showData();
    public abstract void showAllData();
    public abstract void updateData();
    public abstract void deleteData();

    protected long enterId() {
        String input;
        long answer;

        while (true) {
            try {
                System.out.println(ENTER_ID_TEXT);
                input = scanner.nextLine();
                answer = Long.parseLong(input);

                if (answer < 1) {
                    System.out.println(NEG_ID_TEXT);
                    continue;
                }

                return answer;
            } catch (NumberFormatException e) {
                System.out.println(WRONG_FORMAT_TEXT);
            }
        }
    }
}

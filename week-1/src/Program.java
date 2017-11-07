import models.Book;
import repositories.BooksRepository;

import java.util.List;
import java.util.Scanner;

/**
 * Created by shai on 11/6/2017.
 */
public class Program {

    public static void main(String[] args) {

        BooksRepository.getInstance().addBook("example", "asda", "shai", "1910", 1);

        printWelcomeMessage();

        int userSelection = -1;

        while (userSelection != 9) {
            userSelection = printMenuAndGetUserInput();
            handleSelectedAction(userSelection);
        }
    }

    private static void printWelcomeMessage() {
        System.out.println("");
        System.out.println("WELCOME to the most amazing library management app");
        System.out.println("follow the instruction in the menu below");
        System.out.println("");
        System.out.println("");
    }

    private static void handleSelectedAction(int userSelection) {

        switch (userSelection) {
            case 1:
                handleAddBook();
                break;
            case 2:
                handleRemoveBook();
                break;
            case 3:
                handleAddStudent();
                break;
            case 4:
                handleBorrowBook();
                break;
            case 5:
                handleReturnBook();
                break;
            case 6:
                handleSearchBook();
                break;
            case 0:
                handleExit();
                break;
        }
    }

    private static void handleSearchBook() {

        System.out.print("Enter search term: ");

        Scanner scanner = new Scanner(System.in);

        String search = "";

        try {
            search = scanner.nextLine();
        } catch (Exception ignored) {
        }

        List<Book> searchResults = BooksRepository.getInstance().findBookByName(search);

        System.out.println("");
        System.out.println("NAME\t\tISBN\t\tINVENTORY");
        searchResults.forEach(book -> System.out.println(book.name + "\t\t" + book.isbn + "\t\t" + book.copies));
        System.out.println("");
    }

    private static void handleReturnBook() {

    }

    private static void handleBorrowBook() {

    }

    private static void handleRemoveBook() {
    }

    private static void handleAddBook() {

    }

    private static void handleExit() {
        System.out.println("Exiting");
        System.exit(0);
    }

    private static void handleAddStudent() {

    }

    private static int printMenuAndGetUserInput() {

        System.out.println("MENU");
        System.out.println("1) add book");
        System.out.println("2) remove book");
        System.out.println("3) add student");
        System.out.println("4) borrow book");
        System.out.println("5) return book");
        System.out.println("6) search books");
        System.out.println("0) exit");

        System.out.println("");
        System.out.print("Enter your selection: ");

        Scanner scanner = new Scanner(System.in);

        int selection = -1;

        try {
            selection = scanner.nextInt();
        } catch (Exception ignored) {
        }

        while (!isValidMenuInput(selection)) {

            System.out.print("Your input is invalid, try again: ");

            try {
                selection = scanner.nextInt();
            } catch (Exception ignored) {
            }
        }

        System.out.println("");

        return selection;
    }

    private static boolean isValidMenuInput(int selection) {
        return selection >= 0 && selection <= 6;
    }
}


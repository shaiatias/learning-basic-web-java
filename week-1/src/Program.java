import com.sun.xml.internal.ws.util.UtilException;
import models.Book;
import models.BookReservation;
import repositories.BookReservationsRepository;
import repositories.BooksRepository;
import repositories.StudentsRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Program {

    public static void main(String[] args) {

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
                handleAddBook(); // done
                break;

            case 2:
                handleRemoveBook(); // test
                break;

            case 3:
                handleAddStudent(); // done
                break;

            case 4:
                handleBorrowBook(); // test
                break;

            case 5:
                handleReturnBook(); // test
                break;

            case 6:
                handleSearchBook(); // done
                break;

            case 0:
                handleExit(); // done
                break;
        }
    }

    private static void handleSearchBook() {

        String search = Utils.getUserInput("Enter search term: ");

        List<Book> searchResults = BooksRepository.getInstance().findBookByName(search);

        System.out.println("");
        System.out.println("##\t\tNAME\t\tISBN\t\tINVENTORY");

        for (int i = 0; i < searchResults.size(); i++) {
            Book book = searchResults.get(i);
            System.out.println(i + "\t\t" + book.name + "\t\t" + book.isbn + "\t\t" + book.copies);
        }

        System.out.println("");

        if (searchResults.size() > 0) {

            int bookIndexSelection = Utils.getUserInputInt("Enter book ## to reserve it: ", Optional.of(1), Optional.of(searchResults.size()), Optional.of(0));
            Book selectedBook = searchResults.get(bookIndexSelection);

            boolean bookIsAvailable = BookReservationsRepository
                    .getInstance()
                    .isBookAvailable(bookReservation -> bookReservation.bookIsbn.equalsIgnoreCase(selectedBook.isbn));

            if (bookIsAvailable) {
                String studentId = Utils.getUserInput("Enter student id: ");
                BookReservationsRepository.getInstance().reserveBook(studentId, selectedBook.isbn);
                System.out.println("Book " + selectedBook.name + " is reserved successfully for student " + studentId);
            }

            else {
                System.out.println("Book is not available");
            }
        }
    }

    private static void handleReturnBook() {

        String isbn = Utils.getUserInput("Enter isbn: ");
        String studentId = Utils.getUserInput("Enter author: ");

        System.out.println("");

        Optional<BookReservation> reservation = BookReservationsRepository.getInstance().returnBook(isbn, studentId);

        reservation.ifPresent(bookReservation -> {

            if (bookReservation.from + (1000 * 60 * 60 * 24 * 2) > System.currentTimeMillis()) {
                System.out.println("user needs to be punished");
            }
        });
    }

    private static void handleBorrowBook() {

        List<String> categories = BooksRepository.getInstance().getAllCategories();
        String category = printBooksCategoriesMenu(categories);

        List<Book> books = BooksRepository.getInstance().findBooksByCategory(category)
                .stream()
                .filter(book -> BookReservationsRepository.getInstance().isBookAvailable(bookReservation -> bookReservation.bookIsbn.equals(book.isbn)))
                .collect(Collectors.toList());

        if (books.size() == 0) {
            System.out.println("no available books found in this category");
            return;
        }


        System.out.println("");
        System.out.println("##\t\tNAME\t\tISBN\t\tINVENTORY");

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            System.out.println(i + "\t\t" + book.name + "\t\t" + book.isbn + "\t\t" + book.copies);
        }

        System.out.println("");

        int bookIndexSelection = Utils.getUserInputInt("Enter book ## to reserve it: ", Optional.of(1), Optional.of(books.size()), Optional.of(0));
        Book selectedBook = books.get(bookIndexSelection);

        String studentId = Utils.getUserInput("Enter student id: ");
        BookReservationsRepository.getInstance().reserveBook(studentId, selectedBook.isbn);
        System.out.println("Book " + selectedBook.name + " is reserved successfully for student " + studentId);
    }

    private static String printBooksCategoriesMenu(List<String> categories) {

        System.out.println("Select category from the following options:");

        for (int i = 0; i < categories.size(); i++) {
            System.out.println(i + categories.get(i));
        }

        int selection = Utils.getUserInputInt("enter category id", Optional.of(0), Optional.of(categories.size()), Optional.empty());

        return categories.get(selection);
    }

    private static void handleRemoveBook() {

        String isbn = Utils.getUserInput("Enter isbn: ");

        Optional<Book> bookByIsbn = BooksRepository.getInstance().findBookByIsbn(isbn);

        if (!bookByIsbn.isPresent()) {
            System.out.println("Book is not found");
            return;
        }

        Book foundBook = bookByIsbn.get();

        boolean bookAvailable = BookReservationsRepository
                .getInstance()
                .isBookAvailable(bookReservation -> bookReservation.bookIsbn.equalsIgnoreCase(foundBook.isbn));

        if (!bookAvailable) {
            BooksRepository.getInstance().removeByIsbn(foundBook.isbn);
            System.out.println("Book is deleted " + foundBook.toString());
        } else {
            System.out.println("Book must be returned before removed");
        }
    }

    private static void handleAddBook() {
        String name = Utils.getUserInput("Enter name: ");
        String category = Utils.getUserInput("Enter category: ");
        String isbn = Utils.getUserInput("Enter isbn: ");
        String author = Utils.getUserInput("Enter author: ");
        String year = Utils.getUserInput("Enter year: ");

        System.out.println("");

        BooksRepository.getInstance().addBook(name, category, isbn, author, year);
    }

    private static void handleExit() {
        System.out.println("Exiting");
        System.exit(0);
    }

    private static void handleAddStudent() {
        String firstname = Utils.getUserInput("Enter firstname: ");
        String lastname = Utils.getUserInput("Enter lastname: ");
        String id = Utils.getUserInput("Enter id: ");
        String email = Utils.getUserInput("Enter email: ");

        System.out.println("");
        StudentsRepository.getInstance().addStudent(firstname, lastname, id, email);
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

        while (!Utils.isValidMenuInput(selection)) {

            System.out.print("Your input is invalid, try again: ");

            try {
                selection = scanner.nextInt();
            } catch (Exception ignored) {
            }
        }

        System.out.println("");

        return selection;
    }


    static class Utils {

        public static int getUserInputInt(String message, Optional<Integer> min, Optional<Integer> max, Optional<Integer> exitCode) {

            Scanner scanner = new Scanner(System.in);
            int response = -1;

            System.out.print(message);

            try {
                response = scanner.nextInt();
            } catch (Exception ignored) {
            }

            while ( response < min.orElse(Integer.MIN_VALUE) ||
                    response > max.orElse(Integer.MAX_VALUE) ||
                    response != exitCode.orElse(Integer.MIN_VALUE)) {

                if (response < min.orElse(Integer.MIN_VALUE)) {
                    System.out.print("Value is too low, try again: ");
                } else if (response > max.orElse(Integer.MAX_VALUE)) {
                    System.out.print("Value is too high, try again: ");
                } else {
                    System.out.print("Your input is invalid, try again: ");
                }

                try {
                    response = scanner.nextInt();
                } catch (Exception ignored) {
                }
            }

            return response;
        }

        public static String getUserInput(String message) {
            return getUserInput(message, Optional.empty());
        }

        public static String getUserInput(String message, Optional<String> errorMessage) {

            Scanner scanner = new Scanner(System.in);
            String response = "";


            System.out.print(message);

            try {
                response = scanner.nextLine();
            } catch (Exception ignored) {
            }

            while (response == null || response.length() == 0) {

                System.out.print(errorMessage.orElse("Your input is invalid, try again: "));

                try {
                    response = scanner.nextLine();
                } catch (Exception ignored) {
                }
            }

            return response;
        }

        public static boolean isValidMenuInput(int selection) {
            return selection >= 0 && selection <= 6;
        }
    }
}


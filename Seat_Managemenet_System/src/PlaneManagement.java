import java.util.Scanner;

/**
 * Manage seat allocation on plane
 * Enables buy seat , cancel seat, search , seat plan display and print tickets information
 */
public class PlaneManagement {
    private static final char[][] seatPlan = {
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N'}, // Row A
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'},         // Row B
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L'},         // Row C
            {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N'} // Row D
    };

    private static final int AVAILABLE = 0;
    private static final int SOLD = 1;
    private static final int[][] seat_Status = new int[4][];
    private static final int[] seats_PerRow = {14, 12, 12, 14};
    private static final Ticket[] tickets = new Ticket[Total_Seats()]; // Maximum number of possible tickets
    private static int ticket_Index = 0;

    public static void main(String[] args) {
        init_Seats();
        Scanner s = new Scanner(System.in);
        int choice;

        System.out.println("\n      Welcome to the Plane Management application");

        do {
            Menu(); // Display the Menu options
            while (true) {
                System.out.print("\nPlease select an option: ");

                if (s.hasNextInt()) {
                    choice = s.nextInt();
                    if (choice < 0 || choice > 6) {
                        System.out.println("Invalid choice. Please select an option from 0 to 6.");
                    }
                    else {
                        break; // Terminate loop on valid input
                    }
                } else {
                    System.out.println(" Input Invalid. Please enter a valid number.");
                    s.next(); // Discard invalid input
                }
            }

            handle_Choice(choice, s); // Handle the user's choice
        }
        while (choice != 0);

        System.out.println("Quitting the program...");
    }

    /**
     * Initialize seat status array by row and seat count
     */
    private static void init_Seats() {
        for (int i = 0; i < seat_Status.length; i++) {
            seat_Status[i] = new int[seats_PerRow[i]];
        }
    }

    /**
     * calculate total number of seats on the plane
     * @return the total number of seats
     */
    private static int Total_Seats() {
        int total_Seats = 0;
        for (int seats : seats_PerRow) {
            total_Seats += seats;
        }
        return total_Seats;
    }

    /**
     * Display menu options for the plane management
     */
    private static void Menu() {
        System.out.println("\n*********************************************************");
        System.out.println("*                       Menu Options                     *");
        System.out.println("*********************************************************");
        System.out.println("      1. Buy a seat");
        System.out.println("      2. Cancel a seat");
        System.out.println("      3. Find first available seat");
        System.out.println("      4. Show seating plan");
        System.out.println("      5. Print tickets information and total sales");
        System.out.println("      6. Search ticket");
        System.out.println("      0. Quit");
        System.out.println("*********************************************************");
    }

    /**
     * handle user's menu option choice
     * @param choice user's menu choice
     * @param scanner scanner object for user input
     */
    private static void handle_Choice(int choice, Scanner scanner) {
        switch (choice) {
            case 1:
                buy_seat(scanner); // process seat purchase option
                break;
            case 2:
                cancel_seat(scanner); // process seat cancellation option
                break;
            case 3:
                find_first_available(); // Process finding first available seat.
                break;
            case 4:
                show_seating_plan();  // Process show seating plan option.
                break;
            case 5:
                print_tickets_info(); // Process printing ticket information option.
                break;
            case 6:
                search_ticket(scanner); // Process ticket search option.
                break;
            case 0:
                break; // Exit the program
            default:
                System.out.println("Invalid choice");
        }
    }

    /**
     * purchase a seat based on user input
     * @param scanner Scanner object for user input.
     */
    private static void buy_seat(Scanner scanner) {
        char rowLetter = ' ';
        int seatNumber = 0;
        System.out.println(" \n                 **Buy a seat** \n");
        boolean valid_Input = false;
        while (!valid_Input) {

            System.out.print("Enter row letter (A-D): ");
            String rowInput = scanner.next();
            try {
                rowLetter = Character.toUpperCase(rowInput.charAt(0));
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Invalid row letter. Please enter a valid row letter between A and D.");
                continue;
            }
            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row letter. Please enter a valid row letter between A and D.");
                continue;
            }

            while (true) {
                System.out.print("Enter seat number (1-"+ seats_PerRow[rowLetter - 'A'] +"): ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input for seat number. Please enter a valid seat number.");
                    scanner.next(); // Discard the invalid input
                    continue;
                }
                seatNumber = scanner.nextInt();

                if (seatNumber < 1 || seatNumber > seats_PerRow[rowLetter - 'A']) {
                    System.out.println("Invalid seat number. Please enter a valid seat number between 1 and " + seats_PerRow[rowLetter - 'A'] + ".");
                    continue;
                }

                if (seat_Status[rowLetter - 'A'][seatNumber - 1] == SOLD) {
                    System.out.println("Selected seat is already sold. Please select another seat.");
                    continue;
                }

                valid_Input = true;
                break;
            }
        }


        System.out.print("    Enter your name: ");
        String name = scanner.next();
        System.out.print("    Enter your surname: ");
        String surname = scanner.next();
        String email;
        while (true) {
            System.out.print("    Enter your email: ");
            email = scanner.next();
            if (!checkEmail(email)) {
                System.out.println("  Invalid email format. Please enter an email in the format example@gmail.com.");
            } else {
                break;
            }
        }

        Person person = new Person(name, surname, email);

        double price = calculate_Price( seatNumber);
        Ticket ticket = new Ticket(String.valueOf(rowLetter), seatNumber, price, person);

        ticket.save(); // Save ticket information
        tickets[ticket_Index++] = ticket;
        seat_Status[rowLetter - 'A'][seatNumber - 1] = SOLD;

        System.out.println(" \n      Your seat purchased successfully!");
    }

    /**
     * validate email address format.
     * @param email the email address for validation
     * @return validity of the email address
     */
    private static boolean checkEmail(String email) {

        return email.contains("@") && email.contains(".");
    }

    /**
     * calculate seat price based on its index.
     * @param seatNumber seat number within the row for calculation
     * @return the price
     */

    private static double calculate_Price(int seatNumber) {
        int col_Index = seatNumber - 1;
        double price;

        if (col_Index >= 0 && col_Index < 5) {
            price = 200.0; // Columns 1-5
        } else if (col_Index >= 5 && col_Index < 9) {
            price = 150.0; // Columns 6-9
        } else {
            price = 180.0; // Columns 10-14
        }

        return price;
    }

    /**
     * cancel a seat based on user inputs
     * @param scanner Scanner object for user input
     */
    private static void cancel_seat(Scanner scanner) {
        char rowLetter = ' ';
        int seatNumber = 0;

        System.out.println("  \n                **Cancel a seat** \n");

        boolean valid_Input = false;
        while (!valid_Input) {
            System.out.print("Enter row letter (A-D): ");
            String rowInput = scanner.next();
            try {
                rowLetter = Character.toUpperCase(rowInput.charAt(0));
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Invalid row letter. Please enter a valid row letter between A and D.");
                continue;
            }

            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row letter. Please enter a valid row letter between A and D.");
                continue;
            }

            int row = rowLetter - 'A'; // Calculate row index here

            while (true) {
                System.out.print("Enter seat number (1-" + seats_PerRow[row] + "): ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input for seat number. Please enter a valid seat number.");
                    scanner.next(); // Consume the invalid input
                    continue;
                }
                seatNumber = scanner.nextInt();

                if (seatNumber < 1 || seatNumber > seats_PerRow[row]) {
                    System.out.println("Invalid seat number. Please enter a valid seat number between 1 and " + seats_PerRow[row] + ".");
                    continue;
                }

                if (seat_Status[row][seatNumber - 1] == AVAILABLE) {
                    System.out.println("\n   Seat is already available");
                    return;
                }

                valid_Input = true;
                break;
            }
        }

        // Delete a file
        Ticket canceledTicket = new Ticket(String.valueOf(rowLetter), seatNumber, 0, null);
        canceledTicket.delete_File();

        seat_Status[rowLetter - 'A'][seatNumber - 1] = AVAILABLE;
        System.out.println("  \n    Your seat canceled successfully!");
    }

    /**
     * Find and display location of first available seat
     */
    private static void find_first_available() {
        System.out.println(" \n    **Find first available seat**  \n");
        for (int i = 0; i < seat_Status.length; i++) {
            char rowLetter = (char) ('A' + i);
            for (int j = 0; j < seat_Status[i].length; j++) {
                if (seat_Status[i][j] == AVAILABLE) {
                    int seatNumber = j + 1;
                    System.out.println("First available seat: " +"Row Letter:"+ rowLetter + " Seat Number:"+seatNumber);
                    return;
                }
            }
        }
        System.out.println("Sorry,all seats have been sold");
    }

    /**
     * Display the seating plan
     */
    private static void show_seating_plan() {
        System.out.println("  \n                         **Seating Plan** \n     ");

        // Print column numbers
        System.out.print("       ");
        for (int i = 1; i <= seatPlan[0].length; i++) {
            System.out.printf("%-4d", i);
        }
        System.out.println();

        // Print rows with seat status
        for (int i = 0; i < seatPlan.length; i++) {
            char rowLetter = (char) ('A' + i);
            System.out.print("Row"+rowLetter + "   ");
            for (int j = 0; j < seatPlan[i].length; j++) {
                if (seat_Status[i][j] == AVAILABLE) {
                    System.out.print("O   ");
                } else {
                    System.out.print("X   ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Print tickets information and total sales
     */
    private static void print_tickets_info() {
        double total_Sales = 0;

        System.out.println(" \n              **Print tickets information and total sales** \n");
        for (int i = 0; i < ticket_Index; i++) {
            Ticket ticket = tickets[i];
            if (ticket != null) {
                ticket.print_ticket_info();
                System.out.println("---------------------------------------------");
                if (seat_Status[ticket.getRow().charAt(0) - 'A'][ticket.getSeat() - 1] == SOLD) {
                    total_Sales += ticket.getPrice();
                }
            }
        }

        System.out.println("\n     Total price of the tickets: £" + total_Sales);
    }

    /**
     * search ticket based on user input.
     * @param scanner scanner object for user input
     */
    private static void search_ticket(Scanner scanner) {
        char rowLetter = ' ';
        int seatNumber = 0;

        System.out.println("\n                *Search ticket* \n");

        boolean valid_Input = false;
        while (!valid_Input) {
            System.out.print("Enter row letter (A-D): ");
            String rowInput = scanner.next();
            try {
                rowLetter = Character.toUpperCase(rowInput.charAt(0));
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Invalid row letter. Please enter a valid row letter between A and D.");
                continue;
            }
            if (rowLetter < 'A' || rowLetter > 'D') {
                System.out.println("Invalid row letter. Please enter a valid row letter between A and D.");
                continue;
            }

            while (true) {
                System.out.print("Enter seat number (1-" + seats_PerRow[rowLetter - 'A'] + "): ");
                if (!scanner.hasNextInt()) {
                    System.out.println("Invalid input for seat number. Please enter a valid seat number.");
                    scanner.next(); // Discard the invalid input
                    continue;
                }
                seatNumber = scanner.nextInt();

                if (seatNumber < 1 || seatNumber > seats_PerRow[rowLetter - 'A']) {
                    System.out.println("Invalid seat number. Please enter a valid seat number between 1 and " + seats_PerRow[rowLetter - 'A'] + ".");
                    continue;
                }

                valid_Input = true;
                break;
            }
        }

        boolean found = false;
        for (int i = 0; i < ticket_Index; i++) {
            Ticket ticket = tickets[i];
            if (ticket.getRow().charAt(0) == rowLetter && ticket.getSeat() == seatNumber) {

                ticket.print_ticket_info();
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("\n   This seat is available.");
        }
    }
}
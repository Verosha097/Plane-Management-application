import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Ticket {
    private String row;
    private int seat;
    private double price;
    private Person person;

    // Constructor with parameters
    public Ticket(String row, int seat, double price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    /**
     * print ticket information and person information
     */
    public void print_ticket_info() {
        System.out.println(" \n          Tickets Information: \n");
        System.out.println("            Row: " + row + ", Seat: " + seat + ", Price: £" + price);
        System.out.println("\n          Person Information: \n");
        person.printPersonInfo();
    }

    /**
     * save ticket information to text file.
     */
    public void save() {
        String filename = row + seat + ".txt"; // Generate filename
        try (FileWriter myWriter = new FileWriter(filename)) {
            // Write ticket information to the file
            myWriter.write("Row: " + row + ", Seat: " + seat + ", Price: £" + price + "\n");
            myWriter.write("Passenger Information:\n");
            myWriter.write("Name: " + person.getName() + "\n");
            myWriter.write("Surname: " + person.getSurname() + "\n");
            myWriter.write("Email: " + person.getEmail() + "\n");
            myWriter.close(); // Close the FileWriter
            System.out.println("Ticket information saved to " + filename);
        } catch (IOException e) {
            System.out.println("Error occurred while saving ticket information to " + filename + ": " + e.getMessage());

        }
    }

    /**
     * Delete the file
     */
    public void delete_File() {
        String filename = row + seat + ".txt";
        File file = new File(filename);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("File " + filename + " deleted successfully.");
            } else {
                System.out.println("Failed to delete file " + filename + ".");
            }
        } else {
            System.out.println("File " + filename + " does not exist.");
        }
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
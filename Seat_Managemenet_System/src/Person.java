public class Person {
    private String name;
    private String surname;
    private String email;

    // Constructor with parameters
    public Person(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    // Getters and setters
    public String getName() {

        return name;
    }

    public String getSurname() {

        return surname;
    }

    public String getEmail() {

        return email;
    }
    public void setName(String name) {

        this.name = name;
    }

    public void setSurname(String surname) {

        this.surname = surname;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    /**
     * print person's information
     */
    public void printPersonInfo() {
        System.out.println("            name: " + getName() );
        System.out.println("            surname: " + getSurname() );
        System.out.println("            Email: " + getEmail()+"\n");
    }

}
public final class SEmployee {
    // Singleton example
    private final String firstName;
    private final String lastName;

    private static final SEmployee INSTANCE = new SEmployee("John", "Doe");

    private SEmployee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static SEmployee getInstance() {
        return INSTANCE;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public static void main(String[] args) {
        SEmployee emp1 = SEmployee.getInstance();
        SEmployee emp2 = SEmployee.getInstance();

        System.out.println(emp1.getFirstName() + " " + emp1.getLastName());
        System.out.println(emp1 == emp2);  // true → same instance
    }

}


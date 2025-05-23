import java.util.Collections;
import java.util.List;

public final class Employee {
    // Immutable class example

    private final String lastName;
    private final String firstName;
    private final String email;
    private final Integer password;
    private final Boolean flagged;
    private final List<Integer> list;

    public Employee(String lastName, String firstName, String email, Integer password, Boolean flagged, List<Integer> list) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.flagged = flagged;
        this.list = List.copyOf(list); // deep copy
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPassword() {
        return password;
    }

    public Boolean getFlagged() {
        return flagged;
    }

    public List<Integer> getList() {
        return list;
    }

    public static void main(String[] args) {
        List<Integer> inputList = List.of(1, 2, 3);
        Employee emp = new Employee("Doe",
                "John", "john.doe@example.com", 123456, false, inputList);

        System.out.println(emp.getFirstName() + " " + emp.getLastName());
        System.out.println("List: " + emp.getList());
    }
}

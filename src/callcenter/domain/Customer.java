package callcenter.domain;

public class Customer {

    private static final String DEFAULT_NAME_OF_CUSTOMER = "Customer";

    private final String name;

    public Customer(int id) {
        this.name = DEFAULT_NAME_OF_CUSTOMER + id;
        System.out.println(this.name + " called the call center.");
    }

    public String getName() {
        return name;
    }
}

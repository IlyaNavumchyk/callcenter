package callcenter.utils;

public class ReceptionTime {

    private static boolean isReceptionTime;

    public static void stopCallCenter() {
        System.out.println("CustomersQueue is closed.");
        isReceptionTime = false;
    }

    public static void startCallCenter() {
        System.out.println("CustomersQueue is opened.");
        isReceptionTime = true;
    }

    public static boolean getIsReceptionTime() {
        return isReceptionTime;
    }
}

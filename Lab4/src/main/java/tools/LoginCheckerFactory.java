package tools;

public class LoginCheckerFactory {
    private static LoginChecker checker= new LoginChecker();

    public static LoginChecker getChecker() {
        return checker;
    }
}

package testdata;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class BoardFactory {

    @DataProvider
    public Object[][] boardsData(Method method) {
        return new Object[][]{
                {"Test", "4filFDhN"},
                {"Test2", "Lsty6wRm"}
        };
    }
}

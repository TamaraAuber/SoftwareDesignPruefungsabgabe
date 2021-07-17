

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

public class JUnitTest {

    @Test
    public void testValidateEMail() {
        User User = new User();
        
        assertEquals(true, User.validateEMail("John.Doe@gmail.com"));
        assertEquals(false, User.validateEMail("JohnDoe.com"));
        assertEquals(false, User.validateEMail("John.Doe@com"));
    }
    
}

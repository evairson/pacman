import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

//Cette classe vérifie que l'implementation des tests fonctionnent 

public class ExempleTest {

    @Test
    public void exempleTest(){
        assertEquals(42, Integer.sum(19, 23));
    }
}
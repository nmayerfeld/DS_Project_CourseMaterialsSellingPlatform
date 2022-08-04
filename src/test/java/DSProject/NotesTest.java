package DSProject;
import DSProject.Notes.Thoroughness;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NotesTest{
    private Notes notes;

    @BeforeEach
    public void setUp(){
        this.notes = new Notes("Nayim", "Data Structures", "Nayim123", 100.99, Notes.Thoroughness.THOROUGH, 5678);
    }

    @Test
    public void testGetters(){
        assertEquals("Nayim", notes.getAuthorName());
        assertEquals("Data Structures", notes.getNameOfClass());
        assertEquals("Nayim123", notes.getSellerUsername());
        assertEquals(100.99, notes.getPrice());
        assertEquals(Thoroughness.THOROUGH, notes.getThoroughness());
        assertEquals(5678, notes.getProductID());
    }

    @Test
    public void testSetters(){
        notes.setClassName("Math For CS - Nayim's Favorite Class!!");
        notes.setPrice(1000);
        notes.setThoroughness(Thoroughness.POOR);
        
        assertEquals("Math For CS - Nayim's Favorite Class!!", notes.getNameOfClass());
        assertEquals(1000, notes.getPrice());
        assertEquals(Thoroughness.POOR, notes.getThoroughness());
    }
}
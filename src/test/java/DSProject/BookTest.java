package DSProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DSProject.Book.BookCondition;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest{
    private Book book;

    @BeforeEach
    public void setUp(){
        this.book = new Book("Harry Potter", "Hogwartz", "Dumbledore", 20.50, 12345, BookCondition.LIKENEW);
    }

    @Test
    public void testGetters(){
        assertEquals("Harry Potter", book.getBookTitle());
        assertEquals("Hogwartz", book.getNameOfClass());
        assertEquals("Dumbledore", book.getSellerUsername());
        assertEquals(20.50, book.getPrice());
        assertEquals(BookCondition.LIKENEW, book.getCondition());
        assertEquals(12345, book.getProductID());
    }

    @Test
    public void TestSetters(){
        book.setNameOfClass("Camp Half Blood");
        book.setPrice(26.75);
        book.setCondition(BookCondition.ACCEPTABLE);

        assertEquals("Camp Half Blood", book.getNameOfClass());
        assertEquals(26.75, book.getPrice());
        assertEquals(BookCondition.ACCEPTABLE, book.getCondition());
    }


}

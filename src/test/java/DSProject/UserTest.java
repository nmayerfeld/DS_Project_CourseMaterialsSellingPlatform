package DSProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import DSProject.Book.BookCondition;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private String userName1;
    private String password1;
    private String securityQuestion1;
    private String securityAnswer1;

    private String userName2;
    private String password2;
    private String securityQuestion2;
    private String securityAnswer2;

    private String userName3;
    private String password3;
    private String securityQuestion3;
    private String securityAnswer3;

    private User u1;
    private User u2;
    private User u3;

    private Book b;
    private Notes n;

    private String bookTitle;
    private String nameOfClass;
    private String sellerUsername;
    private Double price;
    private int productID;

    @BeforeEach
    public void init()
    {
        this.userName1="buches";
        this.password1="bbbbb";
        this.securityQuestion1="What's your favorite food?";
        this.securityAnswer1="sushi";

        this.userName2="Mayerfeld";
        this.password2="mmmmm";
        this.securityQuestion2="what's your favorite food?";
        this.securityAnswer2="Steak";

        this.userName3="bwymore";
        this.password3="cs";
        this.securityQuestion3="What is your least favorite class?";
        this.securityAnswer3="Math for CS";

        this.u1=new User(this.userName1,this.password1,this.securityQuestion1, this.securityAnswer1);
        this.u2=new User(this.userName2,this.password2,this.securityQuestion2, this.securityAnswer2);
        this.u3=new User(this.userName3,this.password3,this.securityQuestion3, this.securityAnswer3);

        this.bookTitle="Epp";
        this.nameOfClass="Math for CS";
        this.sellerUsername="buches";
        this.price=50.99;
        this.productID=1;
        this.b=new Book("Harry Potter", "Hogwartz", "Dumbledore", 20.50, 12345, BookCondition.LIKENEW);
        this.n=new Notes("Buchen", "Math for cs", "mayerfeld", 10.99, Notes.Thoroughness.POOR,2);

    }
    @Test
    public void testGetUsername()
    {
        assert u1.getUserName().equals(this.userName1);
        assert u2.getUserName().equals(this.userName2);
        assert u3.getUserName().equals(this.userName3);
    }
    @Test
    public void testGetPassword()
    {
        assert u1.getPassword().equals(this.password1);
        assert u2.getPassword().equals(this.password2);
        assert u3.getPassword().equals(this.password3);
    }
    @Test
    public void testSetPassword()
    {
        u1.setPassword("mathforcs");
        assert u1.getPassword().equals("mathforcs");
    }
    @Test
    public void testGetSecurityQuestion()
    {
        assert u1.getSecurityQuestion().equals(this.securityQuestion1);
        assert u2.getSecurityQuestion().equals(this.securityQuestion2);
        assert u3.getSecurityQuestion().equals(this.securityQuestion3);
    }
    @Test
    public void testSetSecurityQuestion()
    {
        u1.setSecurityQuestion("What is your favorite drink?");
        assert u1.getSecurityQuestion().equals("What is your favorite drink?");
    }
    public void testGetSecurityAnswer()
    {
        assert u1.getSecurityAnswer().equals(this.securityAnswer1);
        assert u2.getSecurityAnswer().equals(this.securityAnswer2);
        assert u3.getSecurityAnswer().equals(this.securityAnswer3);
    }
    @Test
    public void testSetSecurityAnswer()
    {
        this.u1.setSecurityAnswer("Freshly squeezed OJ");
        assert this.u1.getSecurityAnswer().equals("Freshly squeezed OJ");
    }
    @Test
    public void testAddAndGetProduct()
    {
        u1.addProduct(b);
        u1.addProduct(n);
        assert u1.getProduct(b);
        assert u1.getProduct(n);
    }
    @Test
    public void testGetRating()
    {
        Double average=0.0;
        for(Double i:this.u1.getCopyOfRatings())
        {
            average+=i;
        }
        average=average/u1.getCopyOfRatings().size();
        assert u1.getRating().equals(average);
    }
}
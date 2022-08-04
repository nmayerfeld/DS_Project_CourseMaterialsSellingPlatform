package DSProject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataBaseTest {
    private Database ds;
    private Notes n1;
    private Notes n2;
    private Notes n3;
    private Book b1;
    private Book b2;
    private Book b3;
    private User u1;
    private User u2;
    private User u3;
    private User u4;

    @BeforeEach
    public void init()
    {
        this.ds=new Database();
        this.n1=new Notes("Buchen", "Math for cs", "mayerfeld", 10.99, Notes.Thoroughness.POOR,1);
        this.n2=new Notes("Mayerfeld", "Math for cs", "buches", 9.99, Notes.Thoroughness.THOROUGH,2);
        this.n3=new Notes("Kohanchi", "Math for cs", "kohanchi", 4.99, Notes.Thoroughness.POOR,3);

        this.b1=new Book("Epp", "Math for cs", "mayerfeld", 99.99,4, Book.BookCondition.LIKENEW);
        this.b2=new Book("MIT", "Math for cs", "mayerfeld", 88.99,5, Book.BookCondition.GOOD);
        this.b3=new Book("Regis", "Intro to CS", "buches", 52.99,6, Book.BookCondition.POOR);

        this.u1=new User("buches","bbbb","Favorite food?", "sushi");
        this.u2=new User("mayerfeld","mmmmm","Favorite food?", "steak");
        this.u3=new User("kohanchi","kkkk","Favorite food?","chulent");
        ds.addUser(u1);
        ds.addUser(u2);
        ds.addUser(u3);
    }
    @Test
    public void testGenerateProductIDBeforeAddingProduct()
    {
        assertEquals(1,ds.generateProductID());
    }
    @Test
    public void testGenerateProductIDAfterAddingProduct()
    {
        ds.addNewItemForSale(u1, n1);
        assertEquals(1,ds.generateProductID());
        assertEquals(1,n1.getProductID());
    }
    @Test
    public void testAddProduct()
    {
        ds.addNewItemForSale(u2, n1);
        assertEquals(true, ds.checkIfItemIsForSale(n1));
        assert u2.getProduct(n1)==true;

    }
   
    @Test
    public void testSearchForBooks()
    {
        ds.addNewItemForSale(u1, n1);
        ds.addNewItemForSale(u1, n2);
        ds.addNewItemForSale(u1, n3);
        ds.addNewItemForSale(u1, b1);
        ds.addNewItemForSale(u1, b2);
        ds.addNewItemForSale(u1, b3);
        List<ClassMaterials> myList=ds.searchForSortedBooks("Math for cs");
        assertEquals(2,myList.size());
        assertEquals(true, myList.contains(b1));
        assertEquals(true, myList.contains(b2));
        //check that they are properly sorted- b1 is in better condition
        int counter =0;
        for(ClassMaterials b:myList)
        {
            if(counter==0)
            {
                assertEquals(b,b1);
            }
            counter++;
        }
    }
    @Test
    public void testSearchForNotes()
    {
        ds.addNewItemForSale(u1, n1);
        ds.addNewItemForSale(u1, n2);
        ds.addNewItemForSale(u1, n3);
        ds.addNewItemForSale(u1, b1);
        ds.addNewItemForSale(u1, b2);
        ds.addNewItemForSale(u1, b3);
        List<ClassMaterials> myList=ds.searchForSortedNotes("Math for cs");
        assertEquals(3,myList.size());
        assertEquals(true, myList.contains(n1));
        assertEquals(true, myList.contains(n2));
        assertEquals(true, myList.contains(n3));
        //check that they are properly sorted- n2 is in better condition
        int counter =0;
        for(ClassMaterials n:myList)
        {
            if(counter==0)
            {
                assertEquals(n,n2);
            }
            counter++;
        }
    }
    @Test
    public void testSearchForAll()
    {
        //Mordechai - need to make sure that they can all be the same user here
        ds.addNewItemForSale(u1, n1);
        ds.addNewItemForSale(u1, n2);
        ds.addNewItemForSale(u1, n3);
        ds.addNewItemForSale(u1, b1);
        ds.addNewItemForSale(u1, b2);
        ds.addNewItemForSale(u1, b3);
        List<ClassMaterials> mySet=ds.searchForSortedItems("Math for cs");
        assertEquals(5,mySet.size());
        int counter =0;
        for(ClassMaterials cm:mySet)
        {
            if(counter==0)
            {
                assertEquals(cm,b1);
                assertEquals(u2.getUserName(),cm.getSellerUsername());
            }
            else if(counter==1)
            {
                assertEquals(cm,b2);
            }
            else if(counter==2)
            {
                assertEquals(cm,n2);
                assertEquals(u1.getUserName() ,cm.getSellerUsername());
            }
            counter++;
        }
    }
    @Test
    public void testBuy()
    {
        //Mordecahi - make sure that they can all be the same user
        ds.addNewItemForSale(u1, n1);
        ds.addNewItemForSale(u1, n2);
        ds.addNewItemForSale(u1, n3);
        ds.addNewItemForSale(u1, b1);
        ds.addNewItemForSale(u1, b2);
        ds.addNewItemForSale(u1, b3);
        //Mordecahi - make sure it could be u2
        ds.removeItemForSale(u2, n1);
        //this.ds.buy(u2, n1);
        assertEquals(false,this.ds.checkIfItemIsForSale(n1));
        assertEquals(false, this.u2.getProduct(n1));
    }
}


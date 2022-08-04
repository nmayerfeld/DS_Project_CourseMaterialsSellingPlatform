package DSProject;

public class Book implements ClassMaterials{
    private String bookTitle;
    private String nameOfClass;
    private String sellerUsername;
    private Double price;
    private BookCondition condition;
    private int productID;

    /**
     *enum representing the condition of the given book
     */
    enum BookCondition{ 
        POOR, ACCEPTABLE, GOOD, LIKENEW, NEW
    };

    public Book(String bookTitle, String nameOfClass, String sellerUsername, Double price, int productID, BookCondition condition){
        this.bookTitle = bookTitle;
        this.nameOfClass = nameOfClass;
        this.sellerUsername = sellerUsername;
        this.price = price;
        this.condition = condition;
        this.productID = productID;
    }

    /**
     * @param newName the new name to set the class to
     */
    public void setNameOfClass(String newName){
        this.nameOfClass = newName;
    }

    /**
     *@param newPrice the new price of the book
    */
    public void setPrice (double newPrice){
        this.price = newPrice;
    }

    /**
     * 
     * @return the productID
     */
    @Override
    public int getProductID(){
        return this.productID;
    }

    /**
     * @return the title of the book
     */
    public String getBookTitle(){
        return this.bookTitle;
    }

    /**
     * @return the name of the class this book is used for
     */
    public String getNameOfClass(){
        return this.nameOfClass;
    }

    /**
     * @return the username of the seller of this book
     */
    @Override
    public String getSellerUsername(){
        return this.sellerUsername;
    }

    /**
     * @return the price of the book
    */
    @Override
    public Double getPrice(){
        return this.price;
    }

    /**
     * @param bc the new condition of the book (using the BookCondition enum)
     */
    public void setCondition(BookCondition bc){
        this.condition = bc;
    }

    /**
     * @return the condition of the book (will be from the book condiiton enum)
     */
    public BookCondition getCondition(){
        return condition;
    }

}

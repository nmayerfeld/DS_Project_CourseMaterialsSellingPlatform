package DSProject;
import DSProject.ClassMaterials;
public class Notes implements ClassMaterials {

    private String authorName;
    private String nameOfClass;
    private String sellerUserName;
    private double price;
    private Thoroughness th;
    private int productID;

    public Notes(String authorName, String nameOfClass, String sellerUserName, Double price, Thoroughness th, int productID){
        this.authorName = authorName;
        this.nameOfClass = nameOfClass;
        this.sellerUserName = sellerUserName;
        this.price = price;
        this.th = th;
        this.productID = productID;
    }
        
    /**
     *enum representing the thoroughness level of the notes
        **/
    enum Thoroughness{
        POOR, PASSABLE, THOROUGH
    };

    /**
     * @param newName the new name to reset the class name too
     */
    public void setClassName(String newName){
        this.nameOfClass = newName;
    }

    /**
     *@param newPrice the price to set the cost of the notes to
    **/
    public void setPrice (double newPrice){
        this.price = newPrice;
    }

    /**
     * @param th the thoroughness level (using the Thoroughness enum)
     **/
    public void setThoroughness(Thoroughness th){
        this.th = th;
    }

    /**
     * @return the product ID
     */
    @Override
    public int getProductID(){
        return this.productID;
    }
    
    /**
     * @return the name of the notes' author 
     **/
    public String getAuthorName(){
        return this.authorName;
    }

    /**
     * @return the name of the class these notes are for
     **/
    @Override
    public String getNameOfClass(){
        return this.nameOfClass;
    }

    /**
     *get the ID of the seller of these notes
    **/
    @Override
    public String getSellerUsername(){
        return this.sellerUserName;
    }

    /**
     *@return the price of the notes
    **/
    @Override
    public Double getPrice(){
        return this.price;
    }

    /**
     * @return the thoroughness of the notes
    **/
    public Thoroughness getThoroughness(){
        return this.th;
    }
}

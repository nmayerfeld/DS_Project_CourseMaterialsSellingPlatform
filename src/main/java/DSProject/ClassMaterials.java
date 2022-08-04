package DSProject;

public interface  ClassMaterials {
    /**
     * @return Name of the class these materials are for
     */
    String getNameOfClass();

    /**
     * @return the username of the one selling these materials
     */
    String getSellerUsername();

    /**
     * @return price of the materials
     */
    Double getPrice();

    /**
     * @return the productID of these materials
     */
    int getProductID();
}
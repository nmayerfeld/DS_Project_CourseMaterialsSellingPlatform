package DSProject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User {
    private String username;
    private String password;
    private String securityQuestion;
    private String securityAnswer;
    Set<Integer> stuffSelling; //storing by product id integer
    List<Double> ratings;


    public User (String username, String password, String securityQuestion, String securityAnswer){
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.stuffSelling = new HashSet<>();
        this.ratings = new ArrayList<>();

    }

    /**
     * @return username of the user
     */
    public String getUserName(){
        return this.username;
    }

    /**
     * @param newPassword 
     */
    public void setPassword(String newPassword){
        this.password = newPassword;
    }
    
    /**
     * @return the users password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * @param question set the security question for this user
     */
    public void setSecurityQuestion(String question){
        this.securityQuestion = question;
    }

    /**
     * @return user's security question
     */
    public String getSecurityQuestion(){
        return this.securityQuestion;
    }

    /**
     * @param answer set the answer to the user's security question
     */
    public void setSecurityAnswer(String answer){
        this.securityAnswer = answer;
    }

    /**
     * @return user's security answer
     */
    public String getSecurityAnswer(){
        return this.securityAnswer;
    }

    /**
     * @param cm - add the product id to the set of items this user is selling
     */
    public void addProduct(ClassMaterials cm){
        if(cm == null){
            throw new IllegalArgumentException("ClassMaterials was null");
        }

        int id = cm.getProductID();
        this.stuffSelling.add(id);
    }

    public void removeProduct(ClassMaterials cm){
        int id = cm.getProductID();
        this.stuffSelling.remove(id);
    }

    /**
     * @return is the user selling the given product?
     */
    public boolean getProduct(ClassMaterials cm){
        int id = cm.getProductID();
        if(this.stuffSelling.contains(id)){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @return Set of all the products user is selling
     */
    public Set<Integer> getAllProducts(){
        return this.stuffSelling;
    }

    /**
     * @return the average of the integers in the ratings list
     */
    public Double getRating(){
        double total = 0;
        for(Double r : this.ratings){
            total += r;
        }
        return total/this.ratings.size();
    }

    public void addRating(Double rating){
        this.ratings.add(rating);
    }

    /**
     * @return a copy of the ratings list
     */
    public List<Double> getCopyOfRatings(){
        return List.copyOf(ratings);
    }
}

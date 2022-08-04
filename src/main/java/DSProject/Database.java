package DSProject;

import java.util.*;

public class Database {

    /**
     * the comparators
     */
    class BookComparator implements Comparator<Book> {
        public int compare(Book b1, Book b2) {
            return b2.getCondition().ordinal() - b1.getCondition().ordinal();
        }
    }
    class NotesComparator implements Comparator<Notes> {
        public int compare(Notes n1, Notes n2) {
            return n2.getThoroughness().ordinal() - n1.getThoroughness().ordinal();
        }
    }

    private HashMap<Integer, Book> booksForSale; //productID to the product itself
    private HashMap<Integer, Notes> notesForSale; //productID to the product itself
    private HashMap<String, Set<Integer>> userToItemsSelling; //storing based on username to a set of productID's of what user is selling
    private HashMap<String,User> users; // storing user instances by username (so we can access account easily when you login with username)
    private Integer lastUsedProductID;

    public Database() {
        this.booksForSale=new HashMap<>();
        this.notesForSale=new HashMap<>();
        this.userToItemsSelling=new HashMap<>();
        this.users=new HashMap<>();
        this.lastUsedProductID=0;
    }
    public Database(HashMap<Integer, Book> booksForSale,HashMap<Integer, Notes> notesForSale,HashMap<String, Set<Integer>> userToItemsSelling,HashMap<String,User> users,Integer lastUsedProductID) {
        this.booksForSale=booksForSale;
        this.notesForSale=notesForSale;
        this.userToItemsSelling=userToItemsSelling;
        this.users=users;
        this.lastUsedProductID=lastUsedProductID;
    }
    /**
     * adds one to last used productID and returns it, and updates lastUsedProductID
     * @return lastUsedProductID+1
     */
    public int generateProductID(){
        return ++lastUsedProductID;
    }
    /**
     * uses command line+ scanner to converse with the user and create a new user account, and returns that user instance
     * @return the user which was created
     */
    private User createNewUser(Scanner myScanner) {
        Boolean validUsername=false;
        Boolean validPassword=false;
        Boolean securityQ=false;
        Boolean securityA=false;
        String username="";
        String password="";
        String securityQuestion="";
        String securityAnswer="";
        while(!validUsername) {
            System.out.println("Please enter a username");
            String tempUsername=myScanner.next();
            if(this.users.keySet().contains(tempUsername)) {
                System.out.println("That username is taken. Try another.");
            }
            else {
                username=tempUsername;
                validUsername=true;
            }
        }
        while(!validPassword) {
            System.out.println("Please enter a password");
            String tempPassword=myScanner.next();
            if(tempPassword.equals("")){
                continue;
            }
            else {
                System.out.println("Please enter your password again");
                String passwordConfirmation = myScanner.next();
                if(passwordConfirmation.equals(tempPassword)){
                    password=tempPassword;
                    validPassword=true;
                }else{
                    System.out.println("Passwords do not match");
                    continue;
                }
            }
        }
        while(!securityQ) {
            System.out.println("Please enter a security question");
            String tempQ=myScanner.next();
            securityQuestion=tempQ;
            securityQ=true;
        }
        while(!securityA) {
            System.out.println("Please enter the answer to your security question");
            String tempA=myScanner.next();
            securityAnswer=tempA;
            securityA=true;
        }
        User newUser=new User(username,password,securityQuestion,securityAnswer);
        return newUser;
    }
    /**
     * Uses CLI to converse with user in order to login
     * @return the user which was logged in, or null if the User enters the wrong password 4 times
     */
    private User login(Scanner myScanner) {
        User myUser=null;
        while(true) {
            System.out.println("Please enter your username: ");
            String tempUsername=myScanner.next();
            if(this.users.containsKey(tempUsername)) {
                myUser=this.users.get(tempUsername);
                break;
            }
            System.out.println("The username you entered is not in the system.  Please try again.");
        }
        int counter=0;
        System.out.println("Please enter your password: ");
        while(true) {
            String passwordEntered=myScanner.next();
            if(passwordEntered.equals(myUser.getPassword())) {
                System.out.println("Login successful.  Please wait.");
                break;
            }
            else {
                counter++;
                if(counter==4) {
                    return null;
                }
                System.out.println("Password entered is incorrect. Please try again.");
                System.out.println("You have: "+(4-counter)+" tries remaining");
            }
        }
        return myUser;
    }

    public User accessUsersAccount(Scanner myScanner) {
        System.out.println("Thank you for using our program!");
        Boolean hasAccount=checkIfUserHasAccountAlready(myScanner);
        User u;
        if(!hasAccount) {
            u=createNewUser(myScanner);
        }
        else {
            u=login(myScanner);
        }
        if(u!=null) {
            this.users.put(u.getUserName(),u);
        }
        return u;
    }
    private boolean checkIfUserHasAccountAlready(Scanner myScanner) {
        Boolean haveAccount=null;
        while(haveAccount==null) {
            System.out.println("Do you already have an account? Please answer either yes, or no");
            String answer=myScanner.next().toUpperCase();
            if(answer.equals("YES")) {
                haveAccount=true;
            }
            else if(answer.equals("NO")) {
                haveAccount=false;
            }
            else {
                System.out.println("you have failed to answer yes or no.  Please try again.");
            }
        }
        return haveAccount;
    }
    /**
     * Uses cli to find out what the user wants to do next, and calls the methods that will do it
     */
    public void discoverAndHandleNextAction(User u,Scanner myScanner) {
        boolean running=true;
        System.out.println("What do you want to do next?  You can enter \"add\" to add an item that you want to sell, \"search\" to search for items that are for sale, \"list\" to see a list of the items you are selling, \"all\" to see a list of all of the items for sale, \"remove\" to remove an item from the list of items that you are currently selling, or \"buy\" to buy an item that you found in your search");
        while(running) {
            String temp=myScanner.next().toUpperCase();
            if(temp.equals("ADD")) {
                System.out.println("What are you selling?  Please enter either book or notes.");
                while (true) {
                    String item = myScanner.next().toUpperCase();
                    if (item.equals("BOOK")) {
                        this.addCLI(item, u, myScanner);
                        running = false;
                        break;
                    } else if (item.equals("NOTES")) {
                        this.addCLI(item, u, myScanner);
                        running = false;
                        break;
                    } else {
                        System.out.println("you have failed to enter book or notes.  Please try again.  Enter the item you want to sell:");
                    }
                }
            }
            else if(temp.equals("SEARCH")) {
                System.out.println("please enter the name of the class you want to search for materials for: ");
                String classSearching = myScanner.next().toUpperCase();
                while (true) {
                    System.out.println("Please enter what you would like to search for, \"book\" \"notes\", or \"both\"");
                    String searchingFor = myScanner.next().toUpperCase();
                    if(searchingFor.equals("BOOK")) {
                        this.printCollection(this.searchForSortedBooks(classSearching));
                        running = false;
                        break;
                    }
                    else if(searchingFor.equals("NOTES")) {
                        this.printCollection(this.searchForSortedNotes(classSearching));
                        running = false;
                        break;
                    }
                    else if(searchingFor.equals("BOTH")) {
                        this.printCollection(this.searchForSortedItems(classSearching));
                        running = false;
                        break;
                    }
                    else
                    {
                        System.out.println("You have failed to properly enter what you want to search for.  Please try again.");
                    }
                }
            }
            else if(temp.equals("REMOVE")) {
                System.out.println("Please enter the productID of the product you want to remove: ");
                while (true) {
                    int number = myScanner.nextInt();
                    if (this.userToItemsSelling.get(u.getUserName()) != null){
                        if(this.userToItemsSelling.get(u.getUserName()).contains(number)){
                            if(this.booksForSale.get(number)!=null)
                            {
                                this.removeItemForSale(u,this.booksForSale.get(number));
                                running = false;
                                break;
                            }
                            else if(this.notesForSale.get(number)!=null)
                            {
                                this.removeItemForSale(u,this.notesForSale.get(number));
                                running = false;
                                break;
                            }
                        }else{
                            System.out.println("The productID you entered does not exist in the list of items you are selling.  Please try again");
                        }
                    } else {
                        System.out.println("The productID you entered does not exist in the list of items you are selling.  Please try again");
                    }
                }
            }
            else if(temp.equals("BUY")) {
                System.out.println("Please enter the product ID number of the product you would like to purchase");
                while (true) {
                    int productNumber = myScanner.nextInt();
                    if (this.booksForSale.containsKey(productNumber)) {
                        this.buy(u, this.booksForSale.get(productNumber));
                        running = false;
                        break;
                    } else if (this.notesForSale.containsKey(productNumber)) {
                        this.buy(u, this.notesForSale.get(productNumber));
                        running = false;
                        break;
                    } else {
                        System.out.println("The product ID number you entered does not match any of the products for sale. Please try again.");
                    }
                }
            }
            else if(temp.equals("LIST")) {
                this.listItemsYouAreSelling(u);
                running = false;
            }
            else if(temp.equals("ALL")) {
                this.listAllItems();
                running = false;
            }
            else{
                System.out.println("Your selection does not match any of the options.  Please enter: add, search, list, all, remove, or buy");
            }
        }
    }

    /**
     * Uses cli to put the User's item up for sale
     */
    public void addCLI(String item, User u, Scanner myScanner) {
        if(item.equals("BOOK")) {
            String title="";
            String className="";
            Double price=0.0;
            String condition="";
            Book.BookCondition bc= Book.BookCondition.LIKENEW; //can't be initialized to null so this is temporary
            System.out.println("Please enter the title: ");
            title=myScanner.next().toUpperCase();
            System.out.println("Please enter the name of the class this book is for with a dash in between words: ");
            className=myScanner.next().toUpperCase();
            System.out.println("Please enter the price you want to sell this book for with one decimal place (eg 50.9): ");
            price=myScanner.nextDouble();
            System.out.println("Please enter the condition of the book either NEW, LIKENEW, GOOD, ACCEPTABLE, POOR: ");
            condition=myScanner.next().toUpperCase();
            if(condition.equals("NEW")) {
                bc = Book.BookCondition.NEW;
            }
            else if(condition.equals("LIKENEW")) {
                bc = Book.BookCondition.LIKENEW;
            }
            else if(condition.equals("ACCEPTABLE")) {
                bc = Book.BookCondition.ACCEPTABLE;
            }
            else if(condition.equals("POOR"))
            {
                    bc= Book.BookCondition.ACCEPTABLE;
            }
            Book b=new Book(title,className,u.getUserName(),price,this.generateProductID(), bc);
            this.addNewItemForSale(u,b);
        }
        else if(item.equals("NOTES")) {
            String authorName="";
            String className="";
            Double price=0.0;
            String thouroughness="";
            Notes.Thoroughness nt= Notes.Thoroughness.THOROUGH;
            System.out.println("Please enter the name of the author of these notes: ");
            authorName=myScanner.next().toUpperCase();
            System.out.println("Please enter the name of the class these notes are for with a dash in between words: ");
            className=myScanner.next().toUpperCase();
            System.out.println("Please enter the price you want to sell these notes for with one decimal place (eg 50.9): ");
            price=myScanner.nextDouble();
            System.out.println("Please enter the thoroughness of the notes, either Thorough, passable, or poor: ");
            thouroughness=myScanner.next().toUpperCase();
            if(thouroughness.equals("THOROUGH")) {
                nt = Notes.Thoroughness.THOROUGH;
            }
            else if(thouroughness.equals("PASSABLE")) {
                nt = Notes.Thoroughness.PASSABLE;
            }
            else if(thouroughness.equals("POOR"))
            {
                    nt= Notes.Thoroughness.POOR;
            }
            Notes n=new Notes(authorName,className,u.getUserName(),price,nt,this.generateProductID());
            this.addNewItemForSale(u,n);
        }
    }
    /**
     * @param cm the item to add for sale
     * Need to add the item for sale to the set of items the user is selling as well
     */
    public void addNewItemForSale(User user, ClassMaterials cm){
        user.addProduct(cm);
        userToItemsSelling.put(user.getUserName(), user.getAllProducts());
        if(cm instanceof Book)
        {
            booksForSale.put(cm.getProductID(), (Book) cm);
        }
        else if(cm instanceof Notes)
        {
            notesForSale.put(cm.getProductID(), (Notes) cm);
        }
    }

    /**
     * 
     * @param cm 
     */
    public void removeItemForSale(User u,ClassMaterials cm){
        u.removeProduct(cm);
        Set<Integer> userStuff=u.getAllProducts();
        userStuff.remove(cm.getProductID());
        userToItemsSelling.put(u.getUserName(), userStuff);
        if(cm instanceof Book)
        {
            this.booksForSale.remove(cm.getProductID());
        }
        else if(cm instanceof Notes)
        {
            this.notesForSale.remove(cm.getProductID());
        }
    }

    /**
     * @param cm the item to check whether for sale
     * @return the boolean
     */
    public boolean checkIfItemIsForSale(ClassMaterials cm) {
        return this.booksForSale.values().contains(cm)||this.notesForSale.values().contains(cm);
    }

    /**
     * @param classname name of class which user is looking up books for
     * @return set of books for sale for that class, sorted by condition
     */
    public List<ClassMaterials> searchForSortedBooks(String classname) {
        BookComparator b=new BookComparator();
        List<Book> books=new ArrayList<>();
        for(Book bb:booksForSale.values()) {
            if(bb.getNameOfClass().equals(classname)) {
                books.add(bb);
            }
        }
        Collections.sort(books, b);
        List<ClassMaterials> results = new ArrayList<>();
        results.addAll(books);
        return results;
    }

    /**
     * 
     * @param classname name of class which user is looking up notes for 
     * @return set of notes for sale for that class, sorted by thoroughness
     */
    public List<ClassMaterials> searchForSortedNotes(String classname){
        NotesComparator n=new NotesComparator();
        List<Notes> notes=new ArrayList<>();
        for(Notes nn:notesForSale.values()) {
            if(nn.getNameOfClass().equals(classname)) {
                notes.add((nn));
            }
        }
        Collections.sort(notes,n);
        List<ClassMaterials> results=new ArrayList<>();
        results.addAll(notes);
        return results;
    }

    /**
     * searches the database for all items for sale under that class name
     * @return list of books sorted by condition followed by notes sorted by throughness
     */

    public List<ClassMaterials> searchForSortedItems(String classname) {
        BookComparator b=new BookComparator();
        NotesComparator n=new NotesComparator();
        List<Book> books=new ArrayList<>();
        List<Notes> notes=new ArrayList<>();
        List<ClassMaterials> items=new ArrayList<>();
        for(Book bb:booksForSale.values()) {
            if(bb.getNameOfClass().equals(classname)) {
                books.add(bb);
            }
        }
        for(Notes nn:notesForSale.values()) {
            if(nn.getNameOfClass().equals(classname)) {
                notes.add(nn);
            }
        }
        Collections.sort(books, b);
        Collections.sort(notes, n);
        items.addAll(books);
        items.addAll(notes);
        return items;
    }
    public void listAllItems()
    {
        for(Book b:this.booksForSale.values())
        {
            this.printItem(b);
        }
        for(Notes n:this.notesForSale.values())
        {
            this.printItem(n);
        }
    }

    /**
     * represents a buy, removing the item from the database as well as the user's list of items for sale
     * @return
     */

    public void buy(User u,ClassMaterials cm) {
        removeItemForSale(u, cm);
        System.out.println("Please rate your seller on a scale of 1 to 5 (with one being the lowest and five being the highest");
        Scanner myScanner=new Scanner(System.in);
        Double rating = myScanner.nextDouble();
        this.users.get(cm.getSellerUsername()).addRating(rating);
        System.out.println("Thank You For Your Purchase!");
    }
    /**
     * prints a list of items user is selling along with relevant info (let's user know the product number so they can remove it if they wish)
     * @return
     */
    private void listItemsYouAreSelling(User u) {
        Set<Integer>productIDs=this.userToItemsSelling.get(u.getUserName());
        for(Integer i:productIDs)
        {
            System.out.println(i);
        }
        ClassMaterials cm;
        for(Integer i:productIDs) {
            if(this.booksForSale.get(i)!=null)
            {
                cm=this.booksForSale.get(i);
            }
            else
            {
                cm=this.notesForSale.get(i);
            }
            this.printItem(cm);
        }
    }
    /**
     * prints the relevant info for this collection
     * @return
     */
    private void printCollection(Collection<ClassMaterials> cm) {
        for (ClassMaterials c:cm) {
            this.printItem(c);
        }
    }
    /**
     * prints the relevant info for this item
     * @return
     */
    private void printItem(ClassMaterials cm) {
        if(cm instanceof Book) {
            System.out.println("Book name: "+((Book) cm).getBookTitle());
            System.out.println("Class: "+cm.getNameOfClass());
            System.out.println("Seller: "+cm.getSellerUsername());
            System.out.println("Seller rating: "+this.users.get(cm.getSellerUsername()).getRating());
            System.out.println("Price: "+cm.getPrice());
            System.out.println("Condition: "+ ((Book) cm).getCondition());
            System.out.println("Product ID: "+cm.getProductID());
        }
        else if(cm instanceof Notes) {
            System.out.println("Author name: "+((Notes)cm).getAuthorName());
            System.out.println("Class: "+cm.getNameOfClass());
            System.out.println("Seller: "+cm.getSellerUsername());
            System.out.println("Seller rating: "+this.users.get(cm.getSellerUsername()).getRating());
            System.out.println("Price: "+cm.getPrice());
            System.out.println("Condition: "+ ((Notes) cm).getThoroughness());
            System.out.println("Product ID: "+cm.getProductID());
        }
        System.out.println();
    }

    /**
     * Adding this method to enable efficient testing
     */
    public void addUser(User user){
        this.users.put(user.getUserName(), user);
    }

    public HashMap<Integer, Book> getBooksForSale(){
       HashMap<Integer,Book> copy=new HashMap<>();
        for(Integer i:this.booksForSale.keySet())
        {
            copy.put(i,this.booksForSale.get(i));
        }
        return copy;
    }
    public HashMap<Integer, Notes> getNotesForSale(){
        HashMap<Integer,Notes> copy=new HashMap<>();
        for(Integer i:this.notesForSale.keySet())
        {
            copy.put(i,this.notesForSale.get(i));
        }
        return copy;
    }
    public HashMap<String, Set<Integer>> getUserToItemsSelling(){
        return this.userToItemsSelling;
    }

    public HashMap<String, User> getUsers(){
        HashMap<String,User> copy=new HashMap<>();
        for(String s:this.users.keySet())
        {
            copy.put(s,this.users.get(s));
        }
        return copy;
    }

    public Integer getLastUsedProductID(){
        Integer i=this.lastUsedProductID;
        return i;
    }
}
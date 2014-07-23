package compile_inc.compile;

//To create a new object of type Contact, use the constructor i.e.
//        Contact testContact = new Contact("John", "Gallagher", "johnjon8@gmail.com",
//                "2934 Belmont Ave Ardmore PA.", "2672401429", "yo this guy is awesome bro");
//        db.dbAddContact(testContact);

// Contact()
public class Contact {
    String firstName;
    String lastName;
    String email;
    String address;
    String phone_num;
    String description;
    int isSelected = 0;
    int id;

    //default constructor
    public Contact() {
        firstName = "";
        lastName = "";
        email = "";
        address = "";
        phone_num = "";
        description = "";
    }

    //constructor
    public Contact(String startFirstName, String startLastName, String startEmail,
                   String startAddress, String startPhone_num, String startDescription) {
        this.firstName = startFirstName;
        this.lastName = startLastName;
        this.email = startEmail;
        this.address = startAddress;
        this.phone_num = startPhone_num;
        this.description = startDescription;
    }

    public Contact(String startFirstName, String startLastName, String startEmail,
                   String startAddress, String startPhone_num) {
        this.firstName = startFirstName;
        this.lastName = startLastName;
        this.email = startEmail;
        this.phone_num = startPhone_num;
        this.address = startAddress;
    }

    //constructor with id value
    public Contact(int _id, String startFirstName, String startLastName, String startEmail,
                   String startAddress, String startPhone_num, String startDescription) {
        this.firstName = startFirstName;
        this.lastName = startLastName;
        this.email = startEmail;
        this.address = startAddress;
        this.phone_num = startPhone_num;
        this.id = _id;
        this.description = startDescription;
    }

    //the next bunch of functions will be assigners/mutators
    //sets id
    public void setID(int _id) {
        this.id = _id;
    }

    //the next set of functions will be accessors
    //gets thi ID of the contact
    public int getId() {
        return this.id;
    }

    //gets the first name
    public String getFirstName() {
        return this.firstName;
    }

    //sets the first name
    public void setFirstName(String startFirstName) {
        this.firstName = startFirstName;
    }

    //gets last name
    public String getLastName() {
        return this.lastName;
    }

    //sets the last name
    public void setLastName(String startLastName) {
        this.lastName = startLastName;
    }

    //gets email
    public String getEmail() {
        return this.email;
    }

    //sets the email
    public void setEmail(String startEmail) {
        this.email = startEmail;
    }

    //gets address
    public String getAddress() {
        return this.address;
    }

    //sets the address
    public void setAddress(String startAddress) {
        this.address = startAddress;
    }

    //gets phone number
    public String getPhone_num() {
        return this.phone_num;
    }

    //sets the phone number
    public void setPhone_num(String startPhone_num) {
        this.phone_num = startPhone_num;
    }

    //gets the description
    public String getDescription() {
        return this.description;
    }

    //sets the description
    public void setDescription(String startDescription) {
        this.description = startDescription;
    }

    public int isSelected() {
        return this.isSelected;
    }

    public void setSelected(int x) {
        this.isSelected = x;
    }

}

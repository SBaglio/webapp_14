package gym_in_tech.webapp_14.pojos;

public class User {

    private int id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String username;
    private String password;
    private String role;
    private String signupDate;
    private boolean enabled;
    private int allenamentiUsufruiti;

    public User() {
        this.id = 0;
        this.firstName = "";
        this.lastName = "";
        this.birthDate= "";
        this.email = "";
        this.username = "";
        this.password = "";
        this.role = "";
        this.signupDate="";
        this.enabled= true;
        this.allenamentiUsufruiti=0;
    }

    public User(String firstName, String lastName, String birthDate, String email, String username, String password, String role) {
        this.id = 0;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate=birthDate;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.signupDate="";
        this.enabled = true;
        this.allenamentiUsufruiti= 0;
    }

    public int getId() {return id;}
    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String getEmail() {return email;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getRole() {return role;}
    public String getBirthDate() {return birthDate;}
    public String getSignupDate(){return signupDate;}
    public boolean isEnabled() {return enabled;}
    public int getAllenamentiUsufruiti() {
        return allenamentiUsufruiti;
    }

    public void setId(int id) {this.id = id;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
    public void setLastName(String lastName) {this.lastName = lastName;}
    public void setEmail(String email) {this.email = email;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setRole(String role) {this.role = role;}
    public void setBirthDate(String birthDate) {this.birthDate=birthDate;}
    public void setSignupDate(String signupDate) {this.signupDate = signupDate;}
    public void setAllenamentiUsufruiti(int allenamentiUsufruiti) {
        this.allenamentiUsufruiti = allenamentiUsufruiti;
    }
    public void setEnabled(boolean enabled) {this.enabled = enabled;}


}

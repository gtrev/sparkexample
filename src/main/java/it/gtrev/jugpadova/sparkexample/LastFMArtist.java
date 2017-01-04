package it.gtrev.jugpadova.sparkexample;

public class LastFMArtist {
    //user-mboxsha1 \t gender (m|f|empty) \t age (int|empty) \t country (str|empty) \t signup (date|empty)
    private String userMboxSHA1;

    public String getUsermboxsha1() {
	return userMboxSHA1;
    }

    public void setUsermboxsha1(String userMboxSHA1) {
	this.userMboxSHA1 = userMboxSHA1;
    }

    private String gender;

    public String getGender() {
	return gender;
    }

    public void setGender(String gender) {
	this.gender = gender;
    }

    private String age;

    public String getAge() {
	return age;
    }

    public void setAge(String age) {
	this.age = age;
    }

    private String country;

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    private String signup;

    public String getSignup() {
	return signup;
    }

    public void setSignup(String signup) {
	this.signup = signup;
    }
    
}

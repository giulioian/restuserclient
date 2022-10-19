package giulio.ld.test;

public class User {
	private int id;
	private String email;
	private String firstName;
	private String lastName;
	private String avatar;
	
	/*public User(int id, String email, String firstName, String lastName) {
	      this.id = id;
	      this.email = email;
	      this.firstName = firstName;
	      this.lastName = lastName;
	  }*/

	  public int getId() {
	      return this.id;
	  }
	  public void setId(int id) {
	      this.id = id;
	  }
	  public String getEmail() {
		  return this.email;
	  }
	  public void setEmail(String email) {
		  this.email = email;
	  }
	  
	  public String getFirst_name() {
		  return this.firstName;
	  }
	  public void setFirst_name(String first_name) {
		  this.firstName = first_name;
	  }
	  public String getLast_name() {
		  return this.lastName;
	  }
	  public void setLast_name(String last_name) {
		  this.lastName = last_name;
	  }
	  public String getAvatar() {
		  return this.avatar;
	  }
	  public void setAvatar(String avatar) {
		  this.avatar = avatar;
	  }
}

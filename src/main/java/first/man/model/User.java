package first.man.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int userid;
	@NotBlank(message = "this is a not blank field")
	@Size(min = 3,max = 8,message = "user must between 3 and 10 characters")
	private String name;
	@NotBlank(message = "this is a not blank field")
	@Size(min = 3,max = 8,message = "user must between 3 and 10 characters")
//	@Email(regexp = "^[a-zA-Z0-9+_,-]+@[a-zA-Z0-9+_,-]+$")
	private String email;
	private String password;
	private String role;
	private boolean enabled;
	private String imageurl;
	private String about;
	@OneToMany(cascade = CascadeType.ALL,fetch =  FetchType.EAGER)
	private List<Contact> contacts;
}

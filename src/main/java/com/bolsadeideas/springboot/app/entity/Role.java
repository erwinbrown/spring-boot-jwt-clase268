package com.bolsadeideas.springboot.app.entity;



import java.io.Serializable;


public class Role implements Serializable {
	
	
	private Long id;
	
	private Long user_id;
	
	private String authority;

	
	
	

	public Role() {
	
	}
	
	

	public Role(Long id, String authority, Long user_id) {
		
		this.id = id;
		this.authority = authority;
		this.user_id = user_id;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}
	
	private static final long serialVersionUID = -6175313236964450827L;
	
	

}

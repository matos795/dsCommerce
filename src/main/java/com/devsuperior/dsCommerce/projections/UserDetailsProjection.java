package com.devsuperior.dsCommerce.projections;

public interface UserDetailsProjection {

    String getUsername();
	String getPassword();
	Long getRoleId();
	String getAuthority();
    
}
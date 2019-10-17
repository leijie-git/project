package com.gw.systemManager.data;

import java.util.List;

public class GetUserRolesData {

	private Long id;
	private String roleName;
	private List<GetUserRolesData> hasRoles;
	private List<GetUserRolesData> noRoles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<GetUserRolesData> getHasRoles() {
		return hasRoles;
	}

	public void setHasRoles(List<GetUserRolesData> hasRoles) {
		this.hasRoles = hasRoles;
	}

	public List<GetUserRolesData> getNoRoles() {
		return noRoles;
	}

	public void setNoRoles(List<GetUserRolesData> noRoles) {
		this.noRoles = noRoles;
	}
}

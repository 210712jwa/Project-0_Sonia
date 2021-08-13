package com.revature.dto;

import java.util.Objects;

public class AddOrEditClientDTO {

	private String name;
	private int account_number;

public AddOrEditClientDTO() {
	super();
}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAccount_number() {
		return account_number;
	}

	public void setAccount_number(int account_number) {
		this.account_number = account_number;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account_number, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrEditClientDTO other = (AddOrEditClientDTO) obj;
		return account_number == other.account_number && Objects.equals(name, other.name);
	}

	


}

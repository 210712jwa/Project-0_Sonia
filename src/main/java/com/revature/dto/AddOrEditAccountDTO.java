package com.revature.dto;

import java.util.Objects;

public class AddOrEditAccountDTO {

	private String type_of_account;
	private int account_balance;
	private int client_id;

public AddOrEditAccountDTO() {
	super();
}

	public String getType_of_account() {
		return type_of_account;
	}

	public void setType_of_account(String type_of_account) {
		this.type_of_account = type_of_account;
	}

	public int getAccount_balance() {
		return account_balance;
	}

	public void setAccount_balance(int account_balance) {
		this.account_balance = account_balance;
	}

	public int getClient_id() {
		return client_id;
	}

	public void setClient_id(int client_id) {
		this.client_id = client_id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(account_balance, client_id, type_of_account);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddOrEditAccountDTO other = (AddOrEditAccountDTO) obj;
		return account_balance == other.account_balance && client_id == other.client_id
				&& Objects.equals(type_of_account, other.type_of_account);
	}

}

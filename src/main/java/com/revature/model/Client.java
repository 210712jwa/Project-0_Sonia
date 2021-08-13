package com.revature.model;

import java.util.List;
import java.util.Objects;

public class Client {
	
	private int id;
	private String name;
	private int accountnumber;
	
	List<Account>accounts;
	
public Client () {
	super();
}
public Client(int id,String name,int account_number) {
	super();
	this.id= id;
	this.name = name;
	this.accountnumber= account_number;
	
}

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public int getAccount_number() {
	return accountnumber;
}
public void setAccount_number(int account_number) {
	this.accountnumber = account_number;
}
//public List<Account> getAccounts() {
//	return accounts;
//}
public void setAccounts(List<Account> accounts) {
	this.accounts = accounts;
}
@Override
public int hashCode() {
	return Objects.hash(accountnumber, id, name);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Client other = (Client) obj;
	return accountnumber == other.accountnumber && id == other.id && Objects.equals(name, other.name);
}

@Override
public String toString() {
	return "Client [id=" + id + ", name=" + name + ", account_number=" + accountnumber + "]";
}
//public void setAccounts(List<Account> accounts) {
//	// TODO Auto-generated method stub
//	
//}


}

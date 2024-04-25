package com.test.modalDT;

import java.io.Serializable;


public class PersonDT implements Serializable {

    private static final long serialVersionUID = 1L;
	private String firstName;
    private String lastName;
    private int age;
    private PersonAddressDT address;
    private String cars[];

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public PersonAddressDT getAddress() { return address; }
    public void setAddress(PersonAddressDT address) { this.address = address; }

    public String[] getCars() { return cars; }
    public void setCars(String[] cars) { this.cars = cars; }
}

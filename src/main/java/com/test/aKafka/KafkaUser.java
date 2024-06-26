package com.test.aKafka;

public class KafkaUser {

	private String name;
    private String dept;
    private Long salary;

    public KafkaUser() {}
    public KafkaUser(String name, String dept, Long salary) {
        this.name = name;
        this.dept = dept;
        this.salary = salary;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Long getSalary() {
		return salary;
	}
    
	public void setSalary(Long salary) {
		this.salary = salary;
	}
	
	@Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("name='").append(name).append('\'');
        sb.append(", dept='").append(dept).append('\'');
        sb.append(", salary='").append(salary).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

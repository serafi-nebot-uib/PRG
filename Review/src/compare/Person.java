import java.lang.Comparable;

public class Person implements Comparable<Person> {
	private String name;
	private String lastName;
	private int age;

	public Person() {
	}

	public Person(String name, String lastName, int age) {
		this.name = name;
		this.lastName = lastName;
		this.age = age;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getAge() {
		return this.age;
	}
	
	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public int compareTo(Person person) {
		if (person == null || getAge() > person.getAge()) return 1;
		if (getAge() < person.getAge()) return -1;
		return 0;
	}

	@Override
	public String toString() {
		return String.format("%s %s (%d)", this.name, this.lastName, this.age);
	}
}

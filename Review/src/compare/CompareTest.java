import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class CompareTest {
	public static void main(String[] args) {
		CompareTest.comparator();
	}

	public static void comparable() {
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("Serafi", "Nebot Ginard", 21));
		persons.add(new Person("Tomeu", "Fuster", 19));
		persons.add(new Person("Joan", "Terrassa", 25));
		persons.add(new Person("Guillem", "Alcover", 30));
		persons.add(new Person("Xisco", "Gomila", 12));

		for (Person person : persons) System.out.println(person);
		System.out.println();
		Collections.sort(persons);
		for (Person person : persons) System.out.println(person);
	}

	public static void comparator() {
		List<Person> persons = new ArrayList<>();
		persons.add(new Person("Serafi", "Nebot Ginard", 19));
		persons.add(new Person("Tomeu", "Fuster", 21));
		persons.add(new Person("Joan", "Terrassa", 33));
		persons.add(new Person("Guillem", "Alcover", 45));
		persons.add(new Person("Xisco", "Gomila", 86));

		for (Person person : persons) System.out.println(person);
		System.out.println();
		Collections.sort(persons, new PersonAgeComparator());
		for (Person person : persons) System.out.println(person);
	}
}

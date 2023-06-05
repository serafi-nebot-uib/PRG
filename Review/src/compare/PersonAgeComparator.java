import java.util.Comparator;

public class PersonAgeComparator implements Comparator<Person> {
	@Override
	public int compare(Person firstPerson, Person secondPerson) {
		if (firstPerson == null && secondPerson == null) return 0;
		if (firstPerson == null || firstPerson.getAge() < secondPerson.getAge()) return -1;
		if (secondPerson == null || firstPerson.getAge() > secondPerson.getAge()) return 1;
		return 0;
	}
}

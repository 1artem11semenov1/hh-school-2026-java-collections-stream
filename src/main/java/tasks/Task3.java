package tasks;

import common.Person;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/*
Задача 3
Отсортировать коллекцию сначала по фамилии, по имени (при равной фамилии), и по дате создания (при равных фамилии и имени)
 */
public class Task3 {

  public static List<Person> sort(Collection<Person> persons) {
    return persons.stream()
            .sorted(Comparator
                    .comparing(Person::secondName, Task3::stringWithNullComparator)
                    .thenComparing(Person::firstName, Task3::stringWithNullComparator)
                    .thenComparing(Person::createdAt,
                            (instant1,instant2) -> {
                                if (instant1 == null && instant2 == null) return 0;
                                if (instant1 == null) return -1;
                                if (instant2 == null) return 1;
                                return instant1.compareTo(instant2);
                            })
            )
            .toList();
  }

  private static int stringWithNullComparator(String s1, String s2) {
    if (s1 == null && s2 == null) return 0;
    if (s1 == null) return -1;
    if (s2 == null) return 1;
    return s1.compareTo(s2);
  }
}

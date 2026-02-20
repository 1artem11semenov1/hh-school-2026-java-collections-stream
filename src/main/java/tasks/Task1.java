package tasks;

import common.Person;
import common.PersonService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимптотику работы
 */
public class Task1 {

  private final PersonService personService;

  public Task1(PersonService personService) {
    this.personService = personService;
  }

  public List<Person> findOrderedPersons(List<Integer> personIds) {
    Set<Person> persons = personService.findPersons(personIds);
    Map<Integer, Person> personMap = persons.stream()
            .collect(
                    Collectors.toMap(
                            Person::id,
                            Function.identity()
                    )
            );

    List<Person> sortedPersonList = new ArrayList<>();
    for (Integer personId : personIds) {
      sortedPersonList.add(personMap.get(personId));
    }

    return sortedPersonList;
  }
  // оценка сложности:
  // преобразование set -> map:
  //    в условии указано, что set несортированыый => либо HashSet, либо LinkedHashSet.
  //    просто пробегаем по бакетам и заносим в мапу пары <id, Person>
  //    => O(|persons|) => O(n)
  // заполнение sortedPersonList:
  //    пробегаем по personIds и для каждого id вынимаем за O(1) из Map значение с нужным ключем
  //    => O(|personIds|) => O(n)
  // -----------------------------------
  // ОБЩАЯ СЛОЖНОСТЬ O(n)
}

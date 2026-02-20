package tasks;

import common.Person;
import common.PersonService;
import common.PersonWithResumes;
import common.Resume;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
  Еще один вариант задачи обогащения
  На вход имеем коллекцию персон
  Сервис умеет по personId искать их резюме (у каждой персоны может быть несколько резюме)
  На выходе хотим получить объекты с персоной и ее списком резюме
 */
public class Task8 {
  private final PersonService personService;

  public Task8(PersonService personService) {
    this.personService = personService;
  }

  public Set<PersonWithResumes> enrichPersonsWithResumes(Collection<Person> persons) {
    Set<Resume> resumes = personService.findResumes(
            persons.stream()
                    .map(Person::id)
                    .collect(Collectors.toSet())
    );
    Map<Integer, Person> personMap = persons.stream()
            .collect(Collectors.toMap(
                    Person::id,
                    Function.identity()
                    )
            );

    Map<Person, Set<Resume>> personsResumesMap = new HashMap<>();
    persons.forEach(person -> personsResumesMap.put(person, new HashSet<>()));

    Person curPerson;
    for (Resume resume : resumes){
      curPerson = personMap.get(resume.personId());
      personsResumesMap.get(curPerson).add(resume);
    }

    Set<PersonWithResumes> personsResumes = new HashSet<>();
    personsResumesMap
            .forEach((key, value) -> personsResumes.add(new PersonWithResumes(key, value)));

    return personsResumes;
  }
}

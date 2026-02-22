package tasks;

import common.Area;
import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
Имеются
- коллекция персон Collection<Person>
- словарь Map<Integer, Set<Integer>>, сопоставляющий каждой персоне множество id регионов
- коллекция всех регионов Collection<Area>
На выходе хочется получить множество строк вида "Имя - регион". Если у персон регионов несколько, таких строк так же будет несколько
 */
public class Task6 {

  public static Set<String> getPersonDescriptions(Collection<Person> persons,
                                                  Map<Integer, Set<Integer>> personAreaIds,
                                                  Collection<Area> areas) {

    Map<Integer, String> areasNames = areas.stream()
            .collect(
                    Collectors.toMap(
                            Area::getId,
                            Area::getName
                    )
            );

    Set<String> nameAreaSet = new HashSet<>();
    for (Person person : persons){
        Set<Integer> areaIds = personAreaIds.get(person.id());
        areaIds.forEach(
                id -> nameAreaSet.add(String.join(" - ", person.firstName(), areasNames.get(id)))
        );
    }

    return nameAreaSet;
  }
}

package tasks;

import common.Area;
import common.Person;

import java.util.*;
import java.util.stream.Collectors;

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

    Map<Integer, String> personMap = persons.stream()
            .collect(
                    Collectors.toMap(
                            Person::id,
                            Person::firstName
                    )
            );
    Map<Integer, String> areasMap = areas.stream()
            .collect(
                    Collectors.toMap(
                            Area::getId,
                            Area::getName
                    )
            );

    Set<String> nameAreaSet = new HashSet<>();
    String name, area;
    for (Map.Entry<Integer, Set<Integer>> entry : personAreaIds.entrySet()){
      name = personMap.get(entry.getKey());
      for (Integer areaId : entry.getValue()){
        area = areasMap.get(areaId);
        nameAreaSet.add(String.join(" - ", name, area));
      }
    }
    return nameAreaSet;
  }
}

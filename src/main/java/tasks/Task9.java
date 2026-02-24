package tasks;

import common.Person;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
Далее вы увидите код, который специально написан максимально плохо.
Постарайтесь без ругани привести его в надлежащий вид
P.S. Код в целом рабочий (не везде), комментарии оставлены чтобы вам проще понять чего же хотел автор
P.P.S Здесь ваши правки необходимо прокомментировать (можно в коде, можно в PR на Github)
 */
public class Task9 {

  private long count;

  // Костыль, эластик всегда выдает в топе "фальшивую персону".
  // Конвертируем начиная со второй
  public List<String> getNames(List<Person> persons) {
    /*if (persons.size() == 0) {
      return Collections.emptyList();
    }
    persons.remove(0);*/
    //ПОЯСНЕНИЕ: вместо удаления первого элемента можно использовать skip(1),
    // а соответственно проверять непустоту List излишне
    return persons.stream().skip(1).map(Person::firstName).collect(Collectors.toList());
  }

  // Зачем-то нужны различные имена этих же персон (без учета фальшивой разумеется)
  public Set<String> getDifferentNames(List<Person> persons) {
    //ПОЯСНЕНИЕ: distinct не нужно, тк Set уже гарантирует уникальность своих элементов
    return new HashSet<>(getNames(persons));
  }

  // Тут фронтовая логика, делаем за них работу - склеиваем ФИО
  public String convertPersonToString(Person person) {
    // ПОЯСНЕНИЕ:
    // переписал через stream, убрал лишнее объявление переменной и заменил соединение через плюсики на joining
    // + в предыдущем соединении была ошибка:
    // вместо middleName второй раз использовалось secondName
    return Stream.of(person.firstName(), person.secondName(), person.middleName())
            .filter(Objects::nonNull)
            .collect(Collectors.joining(" "));
  }

  // словарь id персоны -> ее имя
  public Map<Integer, String> getPersonNames(Collection<Person> persons) {
    // ПОЯСНЕНИЕ: переписал через stream, убрал лишнее объявление переменной
    return persons.stream()
            .collect(Collectors.toMap(
                    Person::id,
                    this::convertPersonToString
            ));
  }

  // есть ли совпадающие в двух коллекциях персоны?
  public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
    // ПОЯСНЕНИЕ:
    // переделал, убрал сет уникальных, просто использовал contains, вместо ручной проверки вторым циклом
    boolean has = false;
    for (Person person : persons1){
      has = persons2.contains(person);
      if (has) break;
    }
    return has;
  }

  // Посчитать число четных чисел
  public long countEven(Stream<Integer> numbers) {
    // ПОЯСНЕНИЕ: избавился от прохода по массиву,
    // использовав функцию count, которая считает оставшиеся в стриме элементы
    return numbers.filter(num -> num % 2 == 0).count();
  }

  // Загадка - объясните почему assert тут всегда верен
  // Пояснение в чем соль - мы перетасовали числа, обернули в HashSet, а toString() у него вернул их в сортированном порядке
  void listVsSet() {
    List<Integer> integers = IntStream.rangeClosed(1, 10000).boxed().collect(Collectors.toList());
    List<Integer> snapshot = new ArrayList<>(integers);
    Collections.shuffle(integers);
    Set<Integer> set = new HashSet<>(integers);
    assert snapshot.toString().equals(set.toString());
    // ПОЯСНЕНИЕ:
    // реализация hashCode у интов:
    // public static int hashCode(int value) {
    //        return value;
    //    }
    // соответственно каждое число попадает в бакет, совпадающий со его значением,
    // до тех пор пока число меньше кол-ва корзин
    // Также, когда количество элементов >75% текущей емкости таблицы - емкость увеличивается в 2 раза
    // и хэши перерасчитываются.
    // В итоге, для нашего случая, имеем таблицу на 2^14 элементов (ближайшая степень двойки, вмещающая в себя 10000 элементов)
    // Соответственно, каждый элемент имеет свой уникальный бакет (без коллизий)
    // toString проходит по корзинам последовательно и выдает элементы в отсортированном порядке
  }
}

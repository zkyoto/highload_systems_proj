package ru.ifmo.cs.passport.api.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;
import ru.ifmo.cs.misc.Name;
import ru.ifmo.cs.misc.UserId;
import ru.ifmo.cs.passport.api.PassportClient;
import ru.ifmo.cs.passport.api.domain.PassportUser;
import ru.ifmo.cs.passport.domain.value.Role;

@Service
public class StubPassportClient implements PassportClient {
    private final Random uidGenerator = new Random();
    private final Map<UserId, PassportUser> cachedKnownUsers;

    public StubPassportClient() {
        this.cachedKnownUsers = new HashMap<>();
        createUserInCache(1, Role.SUPERVISOR);
    }

    private void createUserInCache(long userId, Role role) {
        UserId uid = UserId.of(userId);
        PassportUser passportUser = new PassportUser(uid, generateRandomName(), List.of(role));
        cachedKnownUsers.put(uid, passportUser);
    }

    @Override
    public PassportUser findPassportUser(UserId userId) {
        if (cachedKnownUsers.containsKey(userId)) {
            return cachedKnownUsers.get(userId);
        } else {
            PassportUser passportUser = new PassportUser(userId, generateRandomName(), List.of());
            cachedKnownUsers.put(userId, passportUser);
            return passportUser;
        }
    }

    @Override
    public UserId createUser(Name name, String roleSlug) {
        Role role = Role.R.fromValue(roleSlug);
        UserId randomUid = UserId.of(uidGenerator.nextLong());
        while (cachedKnownUsers.containsKey(randomUid)) {
            randomUid = UserId.of(uidGenerator.nextLong());
        }
        PassportUser passportUser = new PassportUser(randomUid, name, List.of(role));
        cachedKnownUsers.put(randomUid, passportUser);
        return randomUid;
    }

    @Override
    public UserId createUser(String fullName, String roleSlug) {
        return createUser(Name.of(fullName), roleSlug);
    }

    @Override
    public UserId createUser(String firstName, String lastName, String role) {
        return createUser(Name.of(firstName, lastName), role);
    }

    @Override
    public UserId createUser(String roleSlug) {
        return createUser(generateRandomName(), roleSlug);
    }

    private Name generateRandomName() {
        String randomFirstName = names.get((int) (Math.random() * names.size()) - 1);
        String randomLastName = surnames.get((int) (Math.random() * surnames.size()) - 1);
        return Name.of(randomFirstName, randomLastName);
    }

    private static final List<String> names = new ArrayList<>(Arrays.asList(
            "Александр", "Алексей", "Алина", "Анастасия", "Анна", "Антон",
            "Аркадий", "Арсений", "Артем", "Богдан", "Борис", "Вадим",
            "Валентин", "Валерия", "Василий", "Вера", "Виктор", "Виктория",
            "Владимир", "Владислав", "Галина", "Георгий", "Григорий", "Даниил",
            "Данил", "Дарья", "Денис", "Дмитрий", "Евгений", "Евгения",
            "Егор", "Екатерина", "Елена", "Елизавета", "Жанна", "Захар",
            "Зоя", "Иван", "Игнат", "Игорь", "Илья", "Инна", "Ирина",
            "Кирилл", "Кристина", "Ксения", "Лев", "Лидия", "Лилия",
            "Любовь", "Людмила", "Максим", "Маргарита", "Марина", "Мария",
            "Михаил", "Надежда", "Наталья", "Никита", "Николай", "Оксана",
            "Олег", "Ольга", "Павел", "Полина", "Роман", "Светлана",
            "Семен", "Сергей", "Станислав", "Степан", "Тамара", "Татьяна",
            "Тимофей", "Ульяна", "Федор", "Юлия", "Юрий", "Яна",
            "Ярослав", "Агния", "Алиса", "Алла", "Анатолий", "Андрей",
            "Анфиса", "Армен", "Белла", "Варвара", "Викентий", "Вивиан",
            "Геннадий", "Герасим", "Давид", "Диана", "Евдокия", "Емельян",
            "Ефим", "Ефросинья", "Зинаида", "Злата", "Изольда", "Иосиф",
            "Ипатий", "Ираклий", "Карина", "Клара", "Константин", "Кузьма",
            "Лариса", "Леонид", "Любава", "Матвей", "Нина", "Олеся",
            "Прохор", "Раиса", "Родион", "Ростислав", "Серафима", "Снежана",
            "Таисия", "Тимур", "Фекла", "Филипп", "Эвелина", "Эльвира",
            "Юлиан", "Юстина", "Яков", "Адриан", "Альбина", "Аристарх",
            "Афанасий", "Валентина", "Вениамин", "Вера",
            "Виолетта", "Виталий", "Глеб", "Евлалия", "Егорий", "Екатерина",
            "Есения", "Захарий", "Лидия", "Луиза", "Макар", "Мирон",
            "Надежда", "Нестор", "Павел", "Пелагея", "Платон", "Римма",
            "Савелий", "Серафим", "Софья", "Трофим", "Фаина", "Фома",
            "Харитон", "Эдуард", "Ювеналий", "Ян", "Ярополк"
    ));

    private static final List<String> surnames = new ArrayList<>(Arrays.asList(
            "Иванов", "Смирнов", "Кузнецов", "Попов", "Васильев", "Петров", "Соколов",
            "Михайлов", "Новиков", "Федоров", "Морозов", "Волков", "Алексеев", "Лебедев",
            "Семенов", "Егоров", "Павлов", "Козлов", "Степанов", "Николаев", "Орлов",
            "Андреев", "Макаров", "Никитин", "Захаров", "Зайцев", "Соловьев", "Борисов",
            "Яковлев", "Григорьев", "Романов", "Воробьев", "Сергеев", "Кузьмин", "Фролов",
            "Александров", "Дмитриев", "Королев", "Гусев", "Киселев", "Ильин", "Максимов",
            "Поляков", "Сорокин", "Виноградов", "Ковалев", "Белов", "Медведев", "Антонов",
            "Тарасов", "Жуков", "Баранов", "Филиппов", "Комаров", "Давыдов", "Беляев",
            "Герасимов", "Богданов", "Орлов", "Киселев", "Сидоров", "Матвеев", "Титов",
            "Марков", "Миронов", "Крылов", "Куликов", "Карпов", "Власов", "Мельников",
            "Денисов", "Гаврилов", "Тихонов", "Казаков", "Афанасьев", "Данилов", "Савельев",
            "Тимофеев", "Фомин", "Чернов", "Абрамов", "Мартынов", "Ефимов", "Федотов",
            "Щербаков", "Назаров", "Ларионов", "Ушаков", "Панфилов", "Копылов", "Мишин",
            "Гуляев", "Шарапов", "Лобанов", "Константинов", "Еремин", "Трофимов", "Леонов",
            "Соболев", "Ершов", "Никифоров", "Коротков", "Лазарев", "Маслов", "Литвинов",
            "Стаханов", "Цветков", "Елисеев", "Евсеев", "Колесников", "Белкин", "Пименов",
            "Гулев", "Кондратьев", "Борисов", "Прокофьев", "Рябов", "Грачев", "Дорофеев",
            "Горшков", "Воронов", "Зиновьев", "Голубев", "Орехов", "Евдокимов", "Анисимов",
            "Князев", "Рыбаков", "Селезнев", "Тетерин", "Рожков", "Селиверстов", "Мажоров",
            "Баженов", "Никулин", "Пирогов", "Дубров", "Винокуров", "Завьялов", "Дементьев",
            "Барсуков", "Лагутин", "Соболь", "Миронов", "Рассказов", "Нефедов", "Панков",
            "Чехов", "Гуляев", "Шульгин", "Шестаков", "Горбачев", "Журавлев", "Панин",
            "Пронин", "Гурьев", "Озеров", "Рогов", "Сухарев", "Демидов", "Зверев",
            "Агафонов", "Пахомов", "Молотов", "Еремин", "Островский", "Рыжов", "Шаповалов",
            "Макеев", "Савин", "Плотников", "Горелов", "Митрофанов", "Почтенный", "Якунин",
            "Румянцев", "Терентьев", "Обнорский", "Крюков", "Дроздов", "Снежков", "Бирюков",
            "Чесноков", "Пугачев", "Игнатов", "Гришин", "Коротков"
    ));

}

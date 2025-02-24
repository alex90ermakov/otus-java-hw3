import animals.Animal;
import data.AnimalFactory;
import db.MySQLConnector;
import tables.AnimalTable;
import utils.AnimalCreator;
import utils.InputIntValidator;
import utils.InputStringValidator;
import menu.Command;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    // Используем один экземпляр Scanner для всего класса.
    private static Scanner scanner = new Scanner(System.in);
    private static InputIntValidator intValidator = new InputIntValidator(scanner);
    private static InputStringValidator strValidator = new InputStringValidator(scanner);
    private static AnimalCreator animalCreator = new AnimalCreator(scanner, intValidator, strValidator);

    public static void main(String[] args) throws SQLException {

        AnimalTable animalTable = new AnimalTable();

        animalTable.delete();

        List<String> columnsAnimalTable = new ArrayList<>();
        columnsAnimalTable.add("id INT AUTO_INCREMENT PRIMARY KEY");
        columnsAnimalTable.add("type VARCHAR(20)");
        columnsAnimalTable.add("name VARCHAR(20)");
        columnsAnimalTable.add("color VARCHAR(20)");
        columnsAnimalTable.add("age INT");
        columnsAnimalTable.add("weight INT");

        animalTable.create(columnsAnimalTable);

        while (true) {
            List<String> commandNames = new ArrayList<>();
            for (Command commandData : Command.values()) {
                commandNames.add(commandData.name());
            }
            System.out.printf("Привет! Вводи команду (%s): ", String.join("/", commandNames));
            Command command = null;

            do {
                String input = scanner.nextLine().toUpperCase().trim();
                try {
                    command = Command.valueOf(input);
                } catch (IllegalArgumentException e) {
                    System.out.print("Неверная команда, попробуйте ещё: ");
                }
            } while (command == null);

            switch (command) {
                case ADD:
                    try {
                        Animal newAnimal = animalCreator.createAnimalWithData("Какое животное вы хотите добавить");
                        animalTable.write(newAnimal);
                        newAnimal.say();
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case LIST:
                    if (animalTable.isTableEmpty()) {
                        System.out.println("Список пуст. Добавьте животное.");
                    } else {
                        System.out.println("Выберите вариант вывода списка:\n1 - Весь список\n2 - Список по типам животных");
                        String filter = scanner.nextLine();

                        switch (filter) {
                            case "1":
                                ArrayList<Animal> animals = animalTable.read();
                                for (Animal animal : animals) {
                                    System.out.println(animal);
                                }
                                break;
                            case "2":
                                System.out.printf("Введите тип животного, который хотите вывести (%s): ", String.join(" / ", AnimalFactory.ANIMAL_TYPES));
                                String animalType = scanner.nextLine();

                                ArrayList<Animal> animalsByType = animalTable.read(animalType);
                                if (animalsByType.isEmpty()) {
                                    System.out.println("Животные заданного типа не найдены.");
                                } else {
                                    for (Animal animal : animalsByType) {
                                        System.out.println(animal);
                                    }
                                }
                                break;
                            default:
                                System.out.println("Неверный вариант фильтра.");
                                break;
                        }
                    }
                    break;

                case UPDATE:
                    if (animalTable.isTableEmpty()) {
                        System.out.println("Список пуст. Добавьте животное.");
                        break;
                    }

                    boolean isIdExists = false;

                    while (!isIdExists) {
                        System.out.print("Введите id животного (только цифры): ");
                        while (!scanner.hasNextInt()) {
                            System.out.print("Введите id животного (только цифры): ");
                            scanner.next();                         }
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        if (!animalTable.isIdExists(id)) {
                            System.out.println("Животное с id " + id + " не найдено, попробуйте другой id.");
                        } else {
                            Animal newAnimal = animalCreator.createAnimalWithData("На какое животное вы хотите изменить");
                            newAnimal.setId(id);
                            animalTable.update(newAnimal);
                            newAnimal.say();
                            isIdExists = true;
                        }
                    }
                    break;

                case EXIT:
                    System.out.println("Выход");
                    scanner.close();
                    MySQLConnector.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Неверная команда");
            }
        }
    }
}

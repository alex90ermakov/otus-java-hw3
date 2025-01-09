
import animals.Animal;
import menu.Command;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        //Добавмил сканнер
        Scanner scanner = new Scanner(System.in);
        //Создал ArrayList Animal
        List<Animal> animals = new ArrayList<>();
        //меню
        boolean menu = true;
        while (menu) {      //цикл для меню пока не выйдем командой exit.
            System.out.println("Привет! Вводи команду Add / List / Exit : ");

            String input = scanner.nextLine().toUpperCase().trim();

            Command command = Command.fromString(input);        //ввод комманд из строки input.

            if (command == null) {                               // проверяет чтобы в команде не был null.
                System.out.print("Неверная команда, попробуйте еще : ");
                continue;
            }
            //переключатель комманд.
            switch (command) {
                case ADD:
                    System.out.println("Выберите животное: cat/dog/duck");
                    input = scanner.nextLine();
                    String type;
                    boolean rightType = false;
                    do{
                        type = scanner.nextLine().trim().toUpperCase();
                      if ("CAT".equals(type) || "DOG".equals(type) || "DUCK".equals(type)){
                          rightType = true;
                      }else {
                          System.out.println("Неизвестное животное, попробуйте еще раз");
                      }
                    } while (!rightType);

                    String name;
                    int age;
                    int weight;
                    String color;

                    System.out.println("Введите имя животного");
                    name = scanner.nextLine().trim();
                    System.out.println("Введите возраст животного");
                    age = Integer.parseInt(scanner.nextLine());
                    System.out.println("Введите вес животного");
                    weight = Integer.parseInt(scanner.nextLine());
                    System.out.println("Введите цвет животного");
                    color = scanner.nextLine().trim().toUpperCase();
                    break;

                case LIST:
                    if (animals.isEmpty()) {
                        System.out.print("Список пуст, добавьте животное Add / Exit : ");
                    } else {
                        for (Animal animal : animals) {
                            System.out.println(animal.toString());
                        }
                        System.out.print("Вводи команду Add / List / Exit : ");
                    }
                    break;

                case EXIT:
                        System.out.println("Выход");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Не верная команда");;
            }
        }
    }
}

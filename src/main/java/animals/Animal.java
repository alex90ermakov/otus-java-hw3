package animals;

public abstract class Animal {
    //свойства
    private String name;
    private int age;
    private int weight;
    private String color;

    //геттеры и сеттеры


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //методы
    public void say(){
        System.out.println("Я говорю");
    }
    public void go(){
        System.out.println("Я иду");
    }
    public void drink(){
        System.out.println("Я пью");
    }
    public void eat(){
        System.out.println("Я ем");
    }

    //переопределение метода
    @Override
    public String toString(){
        return "Привет! Меня зовут " + name + ", мне " + age + " " + correctAge(age) + ", я вешу - " + weight + " кг, мой цвет - " + color;
    }

    private String correctAge(int age){
        if (age ==  1) {
            return "год";
        }
        else if (age >= 2 && age <= 4) {
            return "года";
        }
        else {
            return "лет";
        }

        //конструктор

    }


}

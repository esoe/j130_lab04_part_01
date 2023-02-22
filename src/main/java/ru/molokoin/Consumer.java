package main.java.ru.molokoin;
/**
 * Класс реализует потребителя, обладающего основными свойствами:
 * - имя
 * - скорость потребления (1 продукт в минуту .. час .. день)
 * - минимальный размер запасов, инициирующий поход за покупками
 * - максимальная потребность (объем закупки)
 */
public class Consumer {
    private String name;
    private int needs; //характеристика спроса - скорость потребления в сутки
    private int purchase; //
    private int reserve; //минимальны размер запасов
    public Consumer (String name){
        this.name = name;
    }
    /**
     * Получение продуктов со склада
     * - 
     * @param count
     */
    public void getProducts(int count){
        //int needs = needs(10);
    }
    /**
     * метод генерирует потребность потребителя в продукте
     * @return
     */
    public static int needs(int max){
        return (int) (Math.random() * ++max);
    }
}

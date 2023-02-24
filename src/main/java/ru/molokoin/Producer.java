package ru.molokoin;

import java.util.Date;

/**
 * Клас реализует функционал поставщика:
 * - производство продукции
 * - доставку на склад
 * Поставщик производит продукцию, по запросу склада передает ее на склад в течение суток.
 * 
 */
public class Producer extends Thread{
    public static int count = 0; //количество созданных поставщиков
    public String name; //наименование поставщика
    private Date berth; //дата создания : рождения поставщика
    private int current;// текущее количество продукции у поставщика
    private int max; //максимальное количество продукции, допустимое к хранению поставщиком, после чего производство останавливается
    private int min; //минимальный размер поставки
    private int power; //характеристика производства - скорость производства в сутки
    /**
     * Основной конструктор поставщика
     * - инициализация полей объекта
     */
    public Producer(){
        count++;
        name = "Supplier#" + count;
        current = 0;
        max = 100* App.random(10);
        if(max == 0){
            max = 100;
        }
        //произвожственные мозности варьируют от 1 до 10 единиц продукции в сутки
        power = App.random(10);
        if(power == 0){
            power = 1;
        }
        //минимальный размер поставки вариьрует от 10 до 20 (чаще всего равен 10)
        min = App.random(20);
        if (min < 10){
            min = 10;
        }
        System.out.println("Создан новый поставщик!");
        System.out.println(this.toString());
    }
    /**
     * Метод запускает течение времени (бесконечный цикл), для производителя ...
     * - ждем пока не будет достигнуто максимальное количество хранимых продуктов
     * - останавливаем производство
     */
    @Override
    public void run(){
        System.out.println("создан поток: " + Thread.currentThread().getName());
        berth = new Date();//запустили течение времени для поставщика
        System.out.println("Время: "+ berth.getTime() + " >>> Инициирован поставщик: " + name);
        while(true){
            try {
                tic();
            } catch (InterruptedException e) {
                System.out.println("Ошибка consumer.run() >>> " + e.getMessage());
            }
        }
    }
    /**
     * метод отрабатывает один день работы производителя-поставщика
     * @throws InterruptedException
     */
    public synchronized void tic() throws InterruptedException{
        this.wait(App.DAY_LENGHT);//прошли сутки
        //пересчитали текущее количество продукции у поставщика
        current = current + power;
        if (current > max){
            current = max;
        }
        //вывели данные о состоянии поставщика
        System.out.println(this.toString());
    }
    /**
     * Реализация отгрузки продукции на склад
     * 
     * @param request
     * @return
     * @throws InterruptedException
     */
    public synchronized int output(int request) throws InterruptedException{
        System.out.println(name + " >>> " + "Получен запрос на отгрузку продукции для (" + Thread.currentThread().getName() + "): " + request);
        int response = 0;
        //отгрузили все, что есть
        wait(500);
        response = current;
        current = 0;
        return response;
    }
    /**
     * Формируем строку отчета о текущем состоянии поставщика
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Текущее состояние поставщика: " + name + "\n");
        sb.append("Текущее количество продукции (current): " + current + "\n");
        sb.append("Скорость производства продукции (power): " + power + "\n");
        sb.append("Максимальный размер хранилища поставщика (max): " + max + "\n");
        return sb.toString();
    }

    /**
     * Тест раскраски текста в консоли
     * @param args
     */
    public static void main(String[] args) {
    // Declaring ANSI_RESET so that we can reset the color
    String ANSI_RESET = "\u001B[0m";
    // Declaring the color
    // Custom declaration
    String ANSI_YELLOW = "\u001B[33m";
  
        System.out.println(ANSI_YELLOW + "This text is colored" + ANSI_RESET);
        System.err.println("Экстренное сообщение!");
    }
}

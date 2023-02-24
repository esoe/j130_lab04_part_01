package ru.molokoin;

import java.util.ArrayList;
import java.util.List;

/**
 * Запуск работы склада, поставщиков, производителей и потребителей
 */
public class App {
    //TODO константы хорошо бы брать из файла настроек или запрашивать у пользователя при запуске программы. Для удобства пока размещены тут
    public static final long DAY_LENGHT = 1000;//длительность суток
    public static final int CONSUMERS_COUNT = 2; //количество потребителей, создаваемых программой
    public static final int WAREHOUSES_COUNT = 1; //количество складов, создаваемых системой
    public static final int PRODUCERS_COUNT = 1; //количество поставщиков, создаваемых системой
    
    private static List<Consumer> consumers = new ArrayList<>();
    private static List<Warehouse> warehouses = new ArrayList<>();
    private static List<Producer> producers = new ArrayList<>();
    public static void main(String[] args) {
        System.out.println("Запущено приложение>> CONSUMER : WAREHOUSE : SUPPLIER");
        //Создаем потребителей
        for(int i = 0; i < CONSUMERS_COUNT; i++) {
            consumers.add(new Consumer());
        }
        //Создаем склады
        for(int i = 0; i < WAREHOUSES_COUNT; i++) {
            warehouses.add(new Warehouse());
        }
        //Создаем поставщиков
        for(int i = 0; i < PRODUCERS_COUNT; i++) {
            producers.add(new Producer());
        }
        
        //Запускаем потоки потребителей
        for (Consumer consumer : consumers) {
            Thread t = new Thread(consumer);
            t.setName(consumer.name);
            t.start();
        }
        
        //Запускаем потоки складов
        for ( Warehouse warehouse : warehouses) {
            Thread t = new Thread(warehouse);
            t.setName(warehouse.name);
            t.start();
        }

        //Запускаем потоки поставщиков
        for ( Producer producer : producers) {
            Thread t = new Thread(producer);
            t.setName(producer.name);
            t.start();
        }

    }
    public static int random(int max){
        return (int) (Math.random() * ++max);
    }
    public static List<Warehouse> getWarehouses() {
        return warehouses;
    }
    public static List<Producer> getProducers() {
        return producers;
    }
    public static List<Consumer> getConsumers() {
        return consumers;
    }
}

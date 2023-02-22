package main.java.ru.molokoin;
/**
 * Запуск работы склада, поставщиков, производителей и потребителей
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Запущено приложение>> CONSUMER : SUPPLIER");
        for(int i = 0; i < 10; i++) {
            new Consumer();
         }

    }
}

package ru.molokoin;

import java.util.Date;

/**
 * Класс реализует потребителя, обладающего основными свойствами:
 * - имя
 * - скорость потребления (1 продукт в минуту .. час .. день)
 * - минимальный размер запасов, инициирующий поход за покупками
 * - максимальная потребность (объем закупки)
 */
public class Consumer extends Thread{
    public static int count = 0;//количество созданных потребителей
    public String name;//наименование потребителя
    private int current;// текущее количество продукции
    private int needspeed; //характеристика спроса - скорость потребления в сутки
    private int min; //минимальный размер запасов
    private int max; //максимальное количество продукции, допустимое к хранению потребителем
    private Date berth; //дата создания : рождения потребителя
    public Consumer (){
        count++;// проиндексировали счетчик потребителей
        name = "Consumer#" + count;//установили признаки идентификации потребителей
        current = App.random(30);//установили стартовый размер запасов продукции у потребителя
        needspeed = App.random(5);//установили скорость расходования продукции потребителем
        if (needspeed == 0){
            needspeed = 1;
        }
        min = needspeed * 2;//установили размер резерва, достаточный на два дня потребления
        max = needspeed * 10;//установили размер кладовки у потребителя
        System.out.println("Создан новый потребитель!");
        System.out.println(this.toString());
    }

    /**
     * Метод запускает течение времени (бесконечный цикл), для потребителя...
     * - ждем пока не будет достигнуто минимальное значение резерва
     * - затариваем продукцию со склада
     */
    @Override
    public void run(){
        System.out.println("создан поток: " + Thread.currentThread().getName());
        berth = new Date();//запустили течение времени для потребителя
        System.out.println("Время: "+ berth.getTime() + " >>> Инициирован потребитель: " + name);
        while(true){
            try {
                tic();
            } catch (InterruptedException e) {
                System.out.println("Ошибка consumer.run() >>> " + e.getMessage());
            }
        }
    }
    /**
     * метод отрабатывает один день жизни потребителя
     * @throws InterruptedException
     */
    public synchronized void tic() throws InterruptedException{
        this.wait(App.DAY_LENGHT);//прошли сутки
        //пересчитали текущее количество продукции у потребителя
        if ((current - needspeed)>0){
            current = current - needspeed;
        }else {
            current = 0;
        }
        //проверяем, нужно ли закупаться
        if (current < min){
            System.out.println(Thread.currentThread().getName() + " >>> Запросил пополнения запасов!");
            current = current + purchase();//закупились
        }
        //вывели данные о состоянии потребителя
        System.out.println(this.toString());
    }
    /**
     * Метод реализует процедуру закупки продукции со склада
     * @return
     * @throws InterruptedException
     */
    public int purchase() throws InterruptedException{
        int needs = max - current;//потребность
        int fact = 0;//фактически поставлено
        //опрашиваем склады на предмет наличия необходимого количества продукции
        for (Warehouse warehouse : App.getWarehouses()) {
            if (warehouse.check(needs)){
                fact = warehouse.output(needs);
                current = current + fact;
                System.out.println(Thread.currentThread().getName() + " >>> Получил со склада продукцию: " + fact + " ...");
                break;//в случае успеха, прерываем цикл
            }else {
                System.out.println(Thread.currentThread().getName() + " >>> " + "Получение продукции не удалось ...");
            }
        }
        return (fact);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Текущее состояние потребителя: " + name + "\n");
        sb.append("Текущее количество продукции (current): " + current + "\n");
        sb.append("Скорость расходования продукции (needspeed): " + needspeed + "\n");
        sb.append("Общая потребность (max - current): " + (max - current) + "\n");
        sb.append("минимальный размер запасов (min): " + min + "\n");
        
        return sb.toString();
    }
}

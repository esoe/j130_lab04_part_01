package main.java.ru.molokoin;
/**
 * Класс реализует потребителя, обладающего основными свойствами:
 * - имя
 * - скорость потребления (1 продукт в минуту .. час .. день)
 * - минимальный размер запасов, инициирующий поход за покупками
 * - максимальная потребность (объем закупки)
 */
public class Consumer extends Thread{
    public static int count = 0;
    private String name;
    private int current;// текущее количество продукции
    private int needspeed; //характеристика спроса - скорость потребления в сутки
    private int purchase; //объем закупки
    private int reserve; //минимальный размер запасов
    public Consumer (){
        count++;// проиндексировали счетчик потребителей
        name = "Cuncomer#" + count;//установили признаки идентификации потребителей
        current = needs(30);//установили стартовый размер запасов продукции у потребителя
        needspeed = needs(10);//установили скорость расходования продукции потребителем
        purchase = needs(70);//установили размер закупки продукции потребителем
        reserve = needs(5);//установили размер резерва, по достижении которого потребитель инициирует закупку
        System.out.println("Создан новый потребитель:");
        System.out.println(this.toString());
    }

    /**
     * Метод запускает течение времени (бесконечный цикл), для потребителя...
     * - ждем пока не будет достигнуто минимальное значение резерва
     * - затариваем продукцию со склада
     */
    @Override
    public void run(){
        while(true){
            try {
                if (current > reserve){
                    wait(3000);
                    current--;
                }else{
                    //осуществляем закупку
                    Warehouse.

                }
                
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + "\n");
        sb.append("текущее количество продукции (current): " + current + "\n");
        sb.append("скорость расходования продукции (needspeed): " + needspeed + "\n");
        sb.append("объем закупки (purchase): " + purchase + "\n");
        sb.append("минимальный размер запасов (reserve): " + reserve + "\n");
        
        return sb.toString();
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

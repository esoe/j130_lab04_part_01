package ru.molokoin;

import java.util.Date;

/**
 * Реализация процесса хранения продукции (самостоятельный поток):
 * - получение (input) продукции от поставщика
 * - передача (output) продукции потребителю
 * - проверка наличия продукции (check) проверка наличия продукции, необходимой потребителю
 * - (max) максимально допустимое количество продукции (объем склада)
 * - (min) минимальный объем продукции, достижение которого инициирует запрос к поставщикам
 */
public class Warehouse extends Thread{
    public static int count = 0;//количество функционирующих складов
    public String name;//наименование потребителя
    private Date berth; //дата создания склада
    private int current;//текущее количество продукции на складе
    private int min;//минимальное количество продукции на складе, по достижении которого склад инициирует закупку
    private int max;// максимальный объем продукции, допустимый к размещению на складе
    public Warehouse(){
        count++;
        name = "Warehouse#" + count;//установили признаки идентификации склада
        current = App.random(1000);
        min = 800;
        max = 1000;
        System.out.println("Создан новый склад!");
        System.out.println(this.toString());
    }
    /**
     * Метод запускает течение времени для склада
     */
    @Override
    public void run(){
        System.out.println("создан поток: " + Thread.currentThread().getName());
        berth = new Date();//запустили течение времени для склада
        System.out.println("Время: "+ berth.getTime() + " >>> Инициирован склад: " + name);
        while(true){
            try {
                tic();
            } catch (InterruptedException e) {
                System.out.println("Ошибка warehouse.run() >>> " + e.getMessage());
            }
        }
    }
    /**
     * метод отрабатывает один день существования склада
     * - проверка достаточности ресурсов
     * - в случае, если продукции меньше установленного порога, оповещает поставщиков о потребности в продукции и принимает продукцию, пока не заполнится склад
     * @throws InterruptedException
     */
    public synchronized void tic() throws InterruptedException{
        this.wait(App.DAY_LENGHT);//прошли сутки
        //проверили текущее количество продукции на складе
        if (current < min){
            System.out.println(Thread.currentThread().getName() + " >>> Запросил пополнения запасов!");
            current = current + input();//закупились
        }
        //вывели данные о состоянии склада
        System.out.println(this.toString());
    }
    /**
     * Метод реализует запрос к поставщикам о поставке продукции на склад
     * - 
     * @return
     */
    public int input(){
        return (max - current);

        // //
        // int needs = max - current;//потребность
        // int fact = 0;//фактически поставлено
        // //опрашиваем склады на предмет наличия необходимого количества продукции
        // for (Warehouse warehouse : App.getWarehouses()) {
        //     if (warehouse.check(needs)){
        //         fact = warehouse.output(needs);
        //         current = current + fact;
        //         System.out.println(Thread.currentThread().getName() + " >>> Получил со склада продукцию: " + fact + " ...");
        //         break;//в случае успеха, прерываем цикл
        //     }else {
        //         System.out.println(Thread.currentThread().getName() + " >>> " + "Получение продукции не удалось ...");
        //     }
        // }
        // return (fact);
    }
    /**
     * 
     * @param request
     * @return
     * @throws InterruptedException
     */
    public synchronized int output(int request) throws InterruptedException{
        System.out.println(name + " >>> " + "Получен запрос на отгрузку продукции для (" + Thread.currentThread().getName() + "): " + request);
        int response = 0;
        //проверка наличия продукции в необходимом количестве на складе
        if (check(request)){
            //ожидаем время осуществления отгрузки
            wait(300);
            current = current - request;
            return request;
        }else{// при отсутствии забираем остатки
            //ожидаем время, пока соберут заказ
            wait(500);
            response = current;
            current = 0;
        }
        return response;
    }
    /**
     * проверка наличия на складе продукции в необходимом количестве
     * @param request
     * @return
     */
    public Boolean check(int request){
        System.out.println(name + " ответ >>> " + Thread.currentThread().getName() + " Продукции на складе: " + current);
        return (current >= request);
    }
    /**
     * формирование строки, содержащей сведения о состоянии склада
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Текущее состояние склада: " + name + "\n");
        sb.append("Количество продукции (current): " + current + "\n");
        sb.append("Минимальный размер запасов (min): " + min + "\n");
        sb.append("Максимальный размер хранилища (max): " + max + "\n");
        return sb.toString();
    }

}

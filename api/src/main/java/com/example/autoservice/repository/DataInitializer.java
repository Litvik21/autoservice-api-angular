package com.example.autoservice.repository;

import com.example.autoservice.model.*;
import com.example.autoservice.service.CarService;
import com.example.autoservice.service.OrderService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final CarOwnerRepository carOwnerRepository;
    private final ProductRepository productRepository;
    private final TaskRepository taskRepository;
    private final MechanicRepository mechanicRepository;
    private final OrderService orderService;
    private final CarService carService;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        init();
    }

    private void init() {
        CarOwner carOwner = new CarOwner();
        carOwner.setId(556L);
        carOwner.setName("Muhamed");
        carOwner.setLastName("Muhamedchenko");
        carOwner.setPhoneNumber("0670405657");
//        carOwner.setCars(List.of(carSaved1));
        CarOwner saved1 = carOwnerRepository.save(carOwner);

        CarOwner carOwner2 = new CarOwner();
        carOwner2.setId(557L);
        carOwner2.setName("Oleg");
        carOwner2.setLastName("Olegchenko");
        carOwner2.setPhoneNumber("0679125630");
//        carOwner2.setCars(List.of(carSaved2));
        CarOwner saved2 = carOwnerRepository.save(carOwner2);

        CarOwner carOwner3 = new CarOwner();
        carOwner3.setId(558L);
        carOwner3.setName("Pavel");
        carOwner3.setLastName("Pavelchenko");
        carOwner3.setPhoneNumber("0679204857");
//        carOwner3.setCars(List.of(saveCar3));
        CarOwner saved3 = carOwnerRepository.save(carOwner3);

        Car car = new Car();
        car.setId(56L);
        car.setBrand("Nissan");
        car.setModel("Patrol");
        car.setYear("2019");
        car.setNumber("120398483281");
        car.setOwner(saved1);
        Car carSaved1 = carService.save(car);

        Car car2 = new Car();
        car2.setId(57L);
        car2.setBrand("Mazda");
        car2.setModel("CX-6");
        car2.setYear("2013");
        car2.setNumber("568484948290");
        car2.setOwner(saved2);
        Car carSaved2 = carService.save(car2);

        Car car3 = new Car();
        car3.setId(58L);
        car3.setBrand("Opel");
        car3.setModel("Omega");
        car3.setYear("2017");
        car3.setNumber("599494927383");
        car3.setOwner(saved3);
        Car saveCar3 = carService.save(car3);

        Product product1 = new Product();
        product1.setTitle("Аккумулятор 12V 60Ah");
        product1.setPrice(BigDecimal.valueOf(5400.00));

        Product product2 = new Product();
        product2.setTitle("Масло моторное 5W-30 синтетика");
        product2.setPrice(BigDecimal.valueOf(1800.00));

        Product product3 = new Product();
        product3.setTitle("Шины зимние R16");
        product3.setPrice(BigDecimal.valueOf(6400.00));

        Product product4 = new Product();
        product4.setTitle("Фильтр масляный");
        product4.setPrice(BigDecimal.valueOf(300.00));

        Product product5 = new Product();
        product5.setTitle("Свечи зажигания комплект");
        product5.setPrice(BigDecimal.valueOf(1500.00));

        Product product6 = new Product();
        product6.setTitle("Фара левая передняя");
        product6.setPrice(BigDecimal.valueOf(4500.00));

        Product product7 = new Product();
        product7.setTitle("Щётки стеклоочистителя комплект");
        product7.setPrice(BigDecimal.valueOf(800.00));

        Product product8 = new Product();
        product8.setTitle("Тормозные колодки передние");
        product8.setPrice(BigDecimal.valueOf(2000.00));

        Product product9 = new Product();
        product9.setTitle("Антифриз -40°C");
        product9.setPrice(BigDecimal.valueOf(600.00));

        Product product10 = new Product();
        product10.setTitle("Диск тормозной задний");
        product10.setPrice(BigDecimal.valueOf(2300.00));

        Product product11 = new Product();
        product11.setTitle("Ремень ГРМ");
        product11.setPrice(BigDecimal.valueOf(1200.00));

        Product product12 = new Product();
        product12.setTitle("Фильтр воздушный");
        product12.setPrice(BigDecimal.valueOf(400.00));

        Product product13 = new Product();
        product13.setTitle("Масло трансмиссионное 75W-90");
        product13.setPrice(BigDecimal.valueOf(2200.00));

        Product product14 = new Product();
        product14.setTitle("Комплект сцепления");
        product14.setPrice(BigDecimal.valueOf(8500.00));

        Product product15 = new Product();
        product15.setTitle("Амортизатор передний левый");
        product15.setPrice(BigDecimal.valueOf(4300.00));

        // Пример вывода товаров
        Product[] products = {product1, product2, product3, product4, product5,
                product6, product7, product8, product9, product10,
                product11, product12, product13, product14, product15};

        for (Product product : products) {
            productRepository.save(product);
        }


        Mechanic mechanic1 = new Mechanic();
        mechanic1.setId(95L);
        mechanic1.setName("Ivan");
        mechanic1.setLastName("Ivanov");
        mechanic1.setStatus(Mechanic.Status.BUSY);
        Mechanic saved1mech = mechanicRepository.save(mechanic1);

        Mechanic mechanic2 = new Mechanic();
        mechanic2.setId(96L);
        mechanic2.setName("Petr");
        mechanic2.setLastName("Petrov");
        mechanic2.setStatus(Mechanic.Status.BUSY);
        Mechanic saved2mech = mechanicRepository.save(mechanic2);

        Mechanic mechanic3 = new Mechanic();
        mechanic3.setId(97L);
        mechanic3.setName("Sergey");
        mechanic3.setLastName("Sergeyev");
        mechanic3.setStatus(Mechanic.Status.FREE);
        Mechanic saved3mech = mechanicRepository.save(mechanic3);

        Mechanic mechanic4 = new Mechanic();
        mechanic4.setId(98L);
        mechanic4.setName("Nikolay");
        mechanic4.setLastName("Nikolayov");
        mechanic4.setStatus(Mechanic.Status.FREE);
        Mechanic saved4mech = mechanicRepository.save(mechanic4);


        Task task1 = new Task();
        task1.setId(77L);
        task1.setTitle("change oil (use the best option)");
        task1.setType(Task.TypeOfTask.OIL_CHANGE);
        task1.setPrice(BigDecimal.valueOf(600.00));
        task1.setMechanic(saved1mech);
        task1.setPaymentStatus(Task.PaymentStatus.NOT_PAID);
        Task savedTask1 = taskRepository.save(task1);

        Task task2 = new Task();
        task2.setId(78L);
        task2.setTitle("full engine repair)");
        task2.setPaymentStatus(Task.PaymentStatus.NOT_PAID);
        task2.setType(Task.TypeOfTask.ENGINE_REPAIR);
        task2.setMechanic(saved2mech);
        task2.setPrice(BigDecimal.valueOf(5000.00));
        Task savedTask2 = taskRepository.save(task2);


        Order order1 = new Order();
        order1.setId(93L);
        order1.setProducts(List.of(product4, product2, product12, product13));
        order1.setStatus(Order.Status.PROCESS);
        order1.setCar(carSaved1);
        order1.setTasks(List.of(savedTask1));
        order1.setDescription("Быстрое ТО");
        order1.setDateReceived(LocalDate.now().minusDays(1));
        order1.setDateFinished(LocalDate.now());
        orderService.save(order1);

        Order order2 = new Order();
        order2.setId(94L);
        order2.setProducts(List.of(product1, product5, product9, product11));
        order2.setStatus(Order.Status.RECEIVED);
        order2.setCar(carSaved2);
        order2.setTasks(List.of(savedTask2));
        order2.setDescription("Перебрать мотор");
        order2.setDateReceived(LocalDate.now().minusDays(10));
        order2.setDateFinished(LocalDate.now().minusDays(1));
        orderService.save(order2);

        log.info("Initialized");
    }
}

package presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class ConsoleUI {
    private Presenter presenter;
    private ExecutorService executorService;
    private Future progressFuture;
    private CyclicBarrier cyclicBarrier = new CyclicBarrier(1);

    public ConsoleUI() {
        presenter = new Presenter();
        startInput();
    }

    public ConsoleUI startInput() {
        Scanner scanner = new Scanner(System.in);
        List list = currencyList();
        String base;
        String target = "";
        String inputValue = "";

        while (!inputValue.equals("exit")) {

            do {
                System.out.println("Enter from currency:");
                base = scanner.next().toUpperCase();
                if (!list.contains(base) && !base.equals("EXIT")) {
                    System.out.println("wrong name, try again or enter \"exit\" to exit");
                } else break;
            } while (!base.equals("EXIT"));

            if (base.equals("EXIT")) return null;

            do {
                System.out.println("Enter to currency");
                target = scanner.next().toUpperCase();
                if (!list.contains(target) || target.equals(base) && !target.equals("EXIT")) {
                    System.out.println("wrong name, try again or enter \"exit\" to exit");
                } else break;
            } while (!target.equals("EXIT"));

            if (target.equals("EXIT")) return null;

            getRate(base, target);
            setThreadToBarrier();
            while (!inputValue.equals("continue") && !inputValue.equals("exit")) {
                System.out.println("enter \"continue\" to continue program or \"exit\" to exit");
                inputValue = scanner.next();
                if (inputValue.equals("exit")) {
                    System.exit(0);
                }
            }

        }
        return this;
    }

    private void getRate(String base, String target) {
        executorService = Executors.newCachedThreadPool();

        showProgress();

        final Future<String> future = executorService.submit(
                () -> presenter.getRate(base, target)
        );

        String rate = "";
        try {
            rate = future.get();

        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.toString());
        }
        progressFuture.cancel(true);
        executorService.shutdownNow();
        System.out.println(rate);
    }

    private void showProgress() {
        progressFuture = executorService.submit(() -> {
            try {
                System.out.print("loading");
                while (true) {
                    System.out.print(". ");
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("");
                Thread.currentThread().interrupt();
            }
        });
    }

    private void setThreadToBarrier() {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            System.out.println(e);
        } catch (BrokenBarrierException e) {
            System.out.println(e);
        }
    }

    private static List currencyList() {
        return new ArrayList<String>() {
            {
                add("AUD");
                add("GBR");
                add("KRW");
                add("SEK");
                add("BGN");
                add("HKD");
                add("MXN");
                add("SGD");
                add("BRL");
                add("HRK");
                add("MYR");
                add("THB");
                add("CAD");
                add("HUF");
                add("NOK");
                add("TRY");
                add("CHF");
                add("IDR");
                add("NZD");
                add("USD");
                add("CNY");
                add("ILS");
                add("PHP");
                add("ZAR");
                add("CZK");
                add("INR");
                add("PLN");
                add("EUR");
                add("DKK");
                add("JPY");
                add("RON");
                add("RUB");
            }
        };
    }

}

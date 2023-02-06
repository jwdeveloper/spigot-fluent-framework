package jw.fluent.api.utilites.benchmark;

import java.util.function.Consumer;

public class Benchmarker
{

    public static long run(Consumer<Void> work)
    {
        var start = System.nanoTime();
        work.accept(null);
        var finish = System.nanoTime();
        var result =  finish - start;
        var inMilisec = result/Math.pow(10,6);
        System.out.println("Average time");
        System.out.println("Nano: " +result);
        System.out.println("Mili: " +inMilisec);
        return result;
    }

    public static long run(int repeat, Consumer<Void> work)
    {
       return run("Benchmark",repeat,work);
    }

    public static long  run(String name, int repeat, Consumer<Void> work)
    {
        long sum =0;
        for(int i=0;i<repeat;i++)
        {
            var start = System.nanoTime();
            work.accept(null);
            var finish = System.nanoTime();
            sum += finish - start;
        }
        var result = sum/repeat;
        var inMilisec = result/Math.pow(10,6);
        System.out.println("=============================================================");
        System.out.println("Benchmark: " +name+" for "+repeat+ " calls");
        System.out.println("Nanoseconds: " +sum);
        float mili = sum/1_000_000f;
        float second = mili/1000f;
        System.out.println("Miliseconds: " +mili);
        System.out.println("Seconds: " + second);
        System.out.println("Minecraft ticks: " + second*20f);



     //   System.out.println("Mili: " +inMilisec);
        return sum/repeat;
    }
}

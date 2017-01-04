package it.gtrev.jugpadova.sparkexample;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

public class SparkCore
{
    public static void main( String[] args ) {
	
	SparkConf conf = new SparkConf()
	    .setAppName("My App");
	JavaSparkContext sc = new JavaSparkContext(conf);

	JavaRDD<String> rdd = sc.textFile("/usr/share/dict/words");
	JavaRDD<String> filteredRdd1 = rdd.filter( s -> s.startsWith("progr") );
	JavaRDD<String> filteredRdd2 = rdd.filter( s -> s.startsWith("jug") );
	JavaRDD<String> unionRdd = filteredRdd1.union(filteredRdd2);

	JavaRDD<String> java7syntax = rdd.filter(new Function<String, Boolean>(){
		public Boolean call(String x) {return x.startsWith("progr"); }
	    });
	long cntJava7 = java7syntax.count();
	
	long cnt = filteredRdd1.count();

	System.out.println( "rdd1 -> counted " + cnt + " words" );

	long unionCnt = unionRdd.count();

	System.out.println( "union -> counted " + unionCnt + " words" );

	JavaRDD<String> rdd2 = sc.parallelize(Arrays.asList("pippo", "pluto", "paperino"));
	long cnt2 = rdd2.filter(s -> s.contains("pe")).count();

	System.out.println( "rdd2 -> counted " + cnt2 + " words" );

	JavaRDD<Integer> integerRdd = sc.parallelize(Arrays.asList(1,2,3,4));
	JavaRDD<Integer> multiRdd = integerRdd.map(n -> n*n);// {1,4,9,16}

	System.out.println(StringUtils.join(multiRdd.collect()));
	
	String[] vect = {"Programmare in Spark","è davvero","divertente"};
        JavaRDD<String> strTest = sc.parallelize(Arrays.asList(vect));
	JavaRDD<String[]> m = strTest.map(s -> s.split(" "));

	System.out.println(StringUtils.join(m.collect()));

	JavaRDD<String> fm = strTest.flatMap(s -> Arrays.asList(s.split(" ")).iterator());
	//	JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")));
	System.out.println(StringUtils.join(fm.collect()));

	JavaRDD<String> pippo = strTest.flatMap(s -> Arrays.asList(s.split(" ")).iterator());

	//	JavaRDD<String> rdd10 = strTest.flatMap();

	
	sc.close();
    }

    
}

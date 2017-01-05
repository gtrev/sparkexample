package it.gtrev.jugpadova.sparkexample;

import java.io.Serializable;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StringType;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

public class SparkSQL {

    public static void main (String[] argv) {
	SparkSession spark = SparkSession
	    .builder()
	    .appName("Java Spark SQL basic example")
	    .getOrCreate();

	Dataset<Row> rawDs = spark.read()
	    .format("com.databricks.spark.csv")
	    .option("delimiter", "\t")
	    .load("/var/tmp/lastfm-dataset-360K/usersha1-profile.tsv");

	rawDs.printSchema();
	
	// Encoders
	Encoder<LastFMPlays> lastFMEncoder = Encoders.bean(LastFMPlays.class);
	Encoder<LastFMArtist> lastFMArtistEnc = Encoders.bean(LastFMArtist.class);
	
	//user-mboxsha1 \t musicbrainz-artist-id \t artist-name \t plays
	StructField[] fields = {
	    DataTypes.createStructField("userMboxSHA1", DataTypes.StringType, true),
	    DataTypes.createStructField("musicbrainzArtistId", DataTypes.StringType, true),
	    DataTypes.createStructField("artistName", DataTypes.StringType, true),
	    DataTypes.createStructField("plays", DataTypes.StringType, true)
	};
	
	StructType lastFMType = DataTypes.createStructType(fields);

	StructField[] fieldsArtist = {
	    DataTypes.createStructField("userMboxSHA1", DataTypes.StringType, true),
	    DataTypes.createStructField("gender", DataTypes.StringType, true),
	    DataTypes.createStructField("age", DataTypes.StringType, true),
	    DataTypes.createStructField("country", DataTypes.StringType, true),
	    DataTypes.createStructField("signup", DataTypes.StringType, true)
	};
	StructType lastFMArtistType = DataTypes.createStructType(fieldsArtist);

	//
	Dataset<LastFMPlays> dsLastFmPlays = spark
	    .read()
	    .format("com.databricks.spark.csv")
	    .option("delimiter", "\t")
	    .schema(lastFMType)
	    .load("/var/tmp/lastfm-dataset-360K/usersha1-artmbid-artname-plays.tsv")
	    .as(lastFMEncoder)
	    ; 

        dsLastFmPlays.printSchema();

	Dataset<LastFMArtist> dsLastFMArtists = spark
	    .read()
	    .format("com.databricks.spark.csv")
	    .option("delimiter", "\t")
	    .schema(lastFMArtistType)
	    .load("/var/tmp/lastfm-dataset-360K/usersha1-profile.tsv")
	    .as(lastFMArtistEnc)
	    ;

	dsLastFMArtists.printSchema();

	Dataset<Row> joinedDS = dsLastFmPlays.join(dsLastFMArtists, "userMboxSHA1");
	
	joinedDS.printSchema();

	joinedDS.createOrReplaceTempView("lastfm");

	
	Dataset<Row> rowsDF = spark.sql("select * from lastfm where plays > 100 order by artistName");
	rowsDF.show();
    }
}

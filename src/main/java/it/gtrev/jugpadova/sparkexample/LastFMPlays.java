package it.gtrev.jugpadova.sparkexample;

import java.io.Serializable;

public class LastFMPlays { //implements Serializable {

    //    private static final long serialVersionUID = 1L;

    private String userMboxSHA1;
    public String getUsermboxsha1() { return userMboxSHA1; }
    public void setUsermboxsha1(String userMboxSHA1) { this.userMboxSHA1 = userMboxSHA1; }

    private String musicbrainzArtistId;
    public String getMusicbrainzartistid() { return musicbrainzArtistId; }
    public void setMusicbrainzartistid(String musicbrainzArtistId) { this.musicbrainzArtistId = musicbrainzArtistId; }

    private String artistName;
    public String getArtistname() { return artistName; }
    public void setArtistname(String artistName) { this.artistName = artistName; }

    private String plays;

    public String getPlays() {
	return plays;
    }

    public void setPlays(String plays) {
	this.plays = plays;
    }
    

    
}

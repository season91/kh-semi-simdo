package com.kh.simdo.movie.model.vo;

import java.sql.Date;

public class Movie {

	private String mvNo;
	private String mvTitle;
	private String mvTitleorg;
	private int score;
	private String director;
	private String genre;
	private Date releaseDate;
	private String plot;
	private String nation;
	private String thumbnail;
	private int runtime;
	private String rating;
	private String poster;

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getMvNo() {
		return mvNo;
	}

	public void setMvNo(String mvNo) {
		this.mvNo = mvNo;
	}

	public String getMvTitle() {
		return mvTitle;
	}

	public void setMvTitle(String mvTitle) {
		this.mvTitle = mvTitle;
	}

	public String getMvTitleorg() {
		return mvTitleorg;
	}

	public void setMvTitleorg(String mvTitleorg) {
		this.mvTitleorg = mvTitleorg;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@Override
	public String toString() {
		return "mvNo:" + mvNo + ", mvTitle:" + mvTitle + ", mvTitleorg:" + mvTitleorg + ", score:" + score
				+ ", director:" + director + ", genre:" + genre + ", releaseDate:" + releaseDate + ", plot:" + plot
				+ ", nation:" + nation + ", thumbnail:" + thumbnail + ", runtime:" + runtime + ", rating:" + rating
				+ ", poster:" + poster;
	}

}

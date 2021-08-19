package com.rivierasoft.personaldictionary.model;

public class Word {
    private int id;
    private String text;
    private String note;
    private boolean isFavorite;
    private int color;

    public Word(int id, String text, String note, boolean isFavorite, int color) {
        this.id = id;
        this.text = text;
        this.note = note;
        this.isFavorite = isFavorite;
        this.color = color;
    }

    public Word(String text, String note, boolean isFavorite, int color) {
        this.text = text;
        this.note = note;
        this.isFavorite = isFavorite;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}

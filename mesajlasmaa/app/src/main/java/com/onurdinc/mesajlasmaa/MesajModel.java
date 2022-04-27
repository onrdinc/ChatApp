package com.onurdinc.mesajlasmaa;

public class MesajModel {
    private String from,text;
    //Mesajlar bir root rootun datasını alabilmemiz için bir model oluşturmamız gerekiyor

    public MesajModel(){//Boş oluşturmak zorundayız yoksa hata verir

    }

    public MesajModel(String from, String text) {
        this.from = from;
        this.text = text;
        //nesneyi oluştururken set etmek için oluşturduk
    }
    //Datalar üzerinden bilgi alabilmek için get ve setleri de oluşturduk
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "MesajModel{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}

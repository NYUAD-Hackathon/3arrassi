package nyuad.nyuad_3arrassi;

public class Words {
    int id;
    String category;
    String arabic;
    String english;


    public Words(String category, String arabic, String english) {
        this.category = category;
        this.arabic = arabic;
        this.english = english;
    }

    public int getID(){
        return this.id;
    }

    public String getCategory(){
        return this.category;

    }
    public String getArabic(){
        return this.arabic;
    }

    public String getEnglish(){
        return this.english;
    }

    @Override
    public String toString(){
        return this.category + "\n" + this.arabic + "\n" + this.english;
    }
}


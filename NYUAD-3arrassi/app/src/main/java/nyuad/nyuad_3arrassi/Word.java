package nyuad.nyuad_3arrassi;


public class Word {
    int id;
    String category;
    String arabic;
    String english;
    String arabicPronounce;
    String englishPronounce;


    public Word(String category, String arabic, String english, String arabicPronounce, String englishPronounce) {
        this.category = category;
        this.arabic = arabic;
        this.english = english;
	this.arabicPronounce = arabicPronounce;
	this.englishPronounce = englishPronounce;
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

    public String getArabicPronounce(){
        return this.arabicPronounce;
    }

    public String getEnglishPronounce(){
        return this.englishPronounce;
    }

    @Override
    public String toString(){
        return this.category + "\n" + this.arabic + "\n" + this.english + "\n" + this.arabicPronounce + "\n" + englishPronounce;
    }
}

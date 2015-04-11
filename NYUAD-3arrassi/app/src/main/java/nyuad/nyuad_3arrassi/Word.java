package nyuad.nyuad_3arrassi;


public class Word {
    private int id;
    private String category;
    private String arabic;
    private String english;
    private String arabicPronounce;
    private String englishPronounce;


    public Word(String category, String arabic, String english, String arabicPronounce, String englishPronounce) {
        this.category = category;
        this.arabic = arabic;
        this.english = english;
	    this.arabicPronounce = "( " + arabicPronounce + " )";
	    this.englishPronounce = "( " + englishPronounce + " )";
    }

    public int getID(){
        return this.id;
    }

    public String getCategory(){  return this.category;  }
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


    public void setCategory(String category){  this.category = category;  }
    public void setArabic(String arabic){
        this.arabic = arabic;
    }
    public void setEnglish(String english){
        this.english = english;
    }
    public void setArabicPronounce(String arabicPronounce){  this.arabicPronounce = arabicPronounce;  }
    public void setEnglishPronounce(String englishPronounce){  this.englishPronounce = englishPronounce; }

}

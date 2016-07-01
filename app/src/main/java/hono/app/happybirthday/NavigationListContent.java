package hono.app.happybirthday;

/**
 * Created by hnk_1031 on 16/06/29.
 */
public class NavigationListContent {

    private String text;
    private int resId;

    public NavigationListContent(String text, int resId){
        this.text = text;
        this.resId = resId;
    }

    public String getText(){
        return  this.text;
    }

    public int getResId(){
        return this.resId;
    }

}

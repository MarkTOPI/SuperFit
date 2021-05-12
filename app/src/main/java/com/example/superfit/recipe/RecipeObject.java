package com.example.superfit.recipe;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class RecipeObject implements Parcelable {
    private String label;
    private Bitmap image;
    private String sourse;
    private String [] dietLabels;
    private String [] healthLabels;
    private String [] cautions;
    private JSONArray ingridientLines;
    private String calories;
    private String totalWeight;
    private String totalTime;
    private String [] digest;

    public RecipeObject (String label,String calories, Bitmap image, String sourse, String [] digest, JSONArray ingridientLines){
        setLabel(label);
        setCalories(calories);
        setImage(image);
        setDigest(digest);
        setSourse(sourse);
        setIngridientLines(ingridientLines);
    }

    private void setDigest(String[] digest) {
        this.digest = digest;
    }

    public String[] getDigest() {
        return digest;
    }

    public void setIngridientLines(JSONArray ingridientLines) {
        this.ingridientLines = ingridientLines;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setSourse(String sourse) {
        this.sourse = sourse;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCalories() {
        return calories;
    }

    public String getLabel() {
        return label;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getSourse() {
        return sourse;
    }

    public ArrayList<String> getIngridientLines() throws JSONException {
        ArrayList<String> mas = new ArrayList<>();
        for(int i=0; i<ingridientLines.length(); i++){
            mas.add(ingridientLines.get(i).toString());
        }
        return mas;
    }

    protected RecipeObject(Parcel in) throws JSONException {
            label = in.readString();
            image = (Bitmap) in.readValue(Bitmap.class.getClassLoader());
            sourse = in.readString();
            ingridientLines = in.readByte() == 0x00 ? null : new JSONArray(in.readString());
            calories = in.readString();
            totalWeight = in.readString();
            totalTime = in.readString();
            //in.readStringArray(digest);
            digest = in.createStringArray();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(label);
            dest.writeValue(image);
            dest.writeString(sourse);
            if (ingridientLines == null) {
                dest.writeByte((byte) (0x00));
            } else {
                dest.writeByte((byte) (0x01));
                dest.writeString(ingridientLines.toString());
            }
            dest.writeString(calories);
            dest.writeString(totalWeight);
            dest.writeString(totalTime);
            dest.writeStringArray(digest);
        }


        public static final Creator<RecipeObject> CREATOR = new Creator<RecipeObject>() {
            @Override
            public RecipeObject createFromParcel(Parcel in) {
                RecipeObject recipeObject = null;
                try {
                    recipeObject = new RecipeObject(in);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return recipeObject;
            }

            @Override
            public RecipeObject[] newArray(int size) {
                return new RecipeObject[size];
            }
        };
}

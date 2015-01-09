package com.example.victorarcasrios.drawmethis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mati on 9/01/15.
 */
public class Rectangle extends Figure implements Parcelable {

    private int base;
    private int height;

    public Rectangle(int base, int height){
        super('R');
        this.base = base;
        this.height = height;
    }

    public Rectangle(Parcel parcel){
        super('R');
        int [] data = new int[2];
        parcel.readIntArray(data);

        this.base = data[0];
        this.height = data[1];
    }

    public int getBase(){
        return base;
    }

    public int getHeight(){
        return height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(new int[]{base, height});
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        @Override
        public Rectangle createFromParcel(Parcel parcel) {
            return new Rectangle(parcel);
        }

        @Override
        public Rectangle[] newArray(int i) {
            return new Rectangle[i];
        }
    };
}

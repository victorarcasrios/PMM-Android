package com.example.victorarcasrios.drawmethis;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by mati on 9/01/15.
 */
public class Circle extends Figure implements Parcelable{

    private int radius;

    public Circle(int radius){
        super('C');
        this.radius = radius;
    }

    public Circle(Parcel parcel){
        super('C');
        radius = parcel.readInt();
    }

    public int getRadius(){
        return radius;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(radius);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){

        @Override
        public Circle createFromParcel(Parcel parcel) {
            return new Circle(parcel);
        }

        @Override
        public Circle[] newArray(int i) {
            return new Circle[1];
        }
    };
}

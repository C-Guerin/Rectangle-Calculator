package com.example.chris.rectanglecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    //Variables are declared here so they can be accessed anywhere in the class.
    TextView Area; //Instantiates the TextView but does not point it to a specific ID.
    TextView Perimeter; //Instantiates the TextView but does not point it to a specific ID.
    DecimalFormat df = new DecimalFormat(); //Instantiates the DecimalFormat class
    Float FlHeight = 0.0f;
    Float FlWidth = 0.0f;
    Float FlArea = 0.0f;
    Float FlPerimeter = 0.0f;
    Boolean BoolHasCalculated = false; //This boolean is used to determine if the user has pressed the calculate button.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        df.setMaximumFractionDigits(2); //Sets the maximum number of decimals of a float to 2.
        Area = (TextView)findViewById(R.id.TextAreaOutput); //Points the Area TextView to the dedicated area output TextView.
        Perimeter = (TextView)findViewById(R.id.TextPerimeterOutput);//Points the Area TextView to the dedicated perimeter output TextView.
    }

    public Float CastToFloat(String str) {
        //Takes the user input value in the form of a string and parses it to a float.
        float result = (0.0f); //result equals 0.0 as a default return value.
        if (str.contains(".")) { //If the string contains a point, create a float of the numbers left and right of the point.
            String[] StringArray = str.split("[.]");
            result = Float.parseFloat(StringArray[0] + "." + StringArray[1]);
        }
        else { //If the user does not input a string with a point, parse to a float with a decimal 0 on the end.
            result = Float.parseFloat(str + "." + 0);
            return result;
        }
        return result;
    }

    public void Calculate(View view) {
        //Subroutine for the Calculate button.
        //Instantiates EditText classes and points them to the IDs for the user input boxes for Height and Width.
        EditText Height = (EditText)findViewById(R.id.InputRectHeight);
        EditText Width = (EditText)findViewById(R.id.InputRectWidth);

        //Sets the value of the class-wide floats to the values that the user enters into the EditText boxes.
        FlHeight = CastToFloat(Height.getText().toString()); //Sets the rectangle's height to the user input for height. Parsed via CastToFloat.
        FlWidth = CastToFloat(Width.getText().toString()); //Sets the rectangle's Width to the user input for width. Parsed via CastToFloat.
        FlArea = FlWidth * FlHeight; //Area = Width x Height.
        FlPerimeter = (FlWidth * 2) + (FlHeight * 2); //Perimeter = (Width x 2) + (Height x 2).

        Area.setText(String.valueOf(df.format(FlArea))); //Sets the dedicated output TextView for Area to a string parsed FlArea to 2 decimal places.
        Perimeter.setText(String.valueOf(df.format(FlPerimeter))); //Sets the dedicated output TextView for Perimeter to a string parsed FlPerimeter to 2 decimal places.
        BoolHasCalculated = true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat("Area", FlArea); //Saves the value of FlArea under the key of "Area"
        outState.putFloat("Perimeter", FlPerimeter); //Saves the value of FlPerimeter under the key of "Perimeter"
        if (BoolHasCalculated == true) { //If the user has pressed the calculate button, save the associated boolean.
            outState.putBoolean("HasCalculated", BoolHasCalculated);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        FlArea = savedInstanceState.getFloat("Area"); //Sets FlArea to the value that was saved via the key of "Area".
        FlPerimeter = savedInstanceState.getFloat("Perimeter"); //Sets FlPerimeter to the value that was saved via the key of "Perimeter".
        BoolHasCalculated = savedInstanceState.getBoolean("HasCalculated"); //Sets Boolean BoolHasCalculated to the value that was saved via the key of "HasCalculated".
        if (savedInstanceState.getBoolean("HasCalculated") == true){
            //If the user pressed the calculate button before rotating the phone any number of times, populate the dedicated output TextViews.
            Area.setText(String.valueOf(df.format(savedInstanceState.getFloat("Area"))));
            Perimeter.setText(String.valueOf(df.format(savedInstanceState.getFloat("Perimeter"))));
        }
    }
}


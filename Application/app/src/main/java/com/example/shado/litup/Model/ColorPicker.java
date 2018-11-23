package com.example.shado.litup.Model;

import android.graphics.Color;

import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;

public class ColorPicker {

    public void createColorPicker(){
        new ChromaDialog.Builder()
                .initialColor(Color.GREEN)
                .colorMode(ColorMode.RGB) // There's also ARGB and HSV
                .onColorSelected(color -> /* do your stuff */)
                .create()
                .show(getSupportFragmentManager(), "ChromaDialog");
    }

}

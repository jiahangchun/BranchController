package com.jiahangchun.examples.src;

import com.gif4j.GifDecoder;
import com.gif4j.GifImage;
import com.gif4j.GifEncoder;

import java.io.File;
import java.io.IOException;

/**
 * The example demostrates adding a comment to a GIF image
 */

public class AddCommentsExample {

    public static void main(String[] args) {
        String gifPath = "/Users/hzmk/Desktop/test/222222/target/0.3/cat.gif";
        String comment = "啦啦啦啦啦啦啦啦啦啦";
        String outputGifFilePath = "/Users/hzmk/Desktop/test/222222/target/0.3/cat2.gif";
        File inputGifFile = new File(gifPath);
        File outputGifFile = null;
        outputGifFile = new File(outputGifFilePath);
        //read gif image from the file
        try {
            GifImage gifImage = GifDecoder.decode(inputGifFile);
            // add comment
            gifImage.addComment(comment);
            if (outputGifFile == null) {
                // write commented gif image to the same file
                GifEncoder.encode(gifImage, inputGifFile);
            } else {
                // write commented gif image to the specified file
                GifEncoder.encode(gifImage, outputGifFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

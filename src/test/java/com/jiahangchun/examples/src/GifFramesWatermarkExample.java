package com.jiahangchun.examples.src;

import com.gif4j.*;

import java.io.File;
import java.io.IOException;
import java.awt.*;

/**
 * This example demostrates how to add and align watermarks to separate Gif Image frames
 */

public class GifFramesWatermarkExample {

    public static void main(String[] args) {
        // change out directory if it is necessary
        File outputDir = new File("."+File.separator+"result");
        if (!outputDir.exists())
            outputDir.mkdirs();
        // create new watermark with 'watermark' slogan
        TextPainter painter = new TextPainter(new Font("Arial",Font.BOLD,12));
        painter.setForegroundPaint(Color.WHITE);
        Watermark watermark = new Watermark(painter.renderString(" watermark "),Watermark.LAYOUT_TOP_LEFT,1.0f);
        GifImage imageWithRelativeWatermarks = new GifImage(150,150);
        // set indefinite looping
        imageWithRelativeWatermarks.setLoopNumber(0);
        imageWithRelativeWatermarks.setDefaultDelay(300);
        imageWithRelativeWatermarks.addGifFrame(
                new GifFrame(TestUtils.genTestImage("frame with top-left watermark and 1.0f transparency",150,150))
                ,watermark);
        watermark.setLayoutConstraint(Watermark.LAYOUT_TOP_RIGHT);
        watermark.setTransparency(0.8f);
        imageWithRelativeWatermarks.addGifFrame(
                new GifFrame(TestUtils.genTestImage("frame with top-right watermark and 0.8f transparency",150,150))
                ,watermark);
        watermark.setLayoutConstraint(Watermark.LAYOUT_BOTTOM_LEFT);
        watermark.setTransparency(0.6f);
        imageWithRelativeWatermarks.addGifFrame(
                new GifFrame(TestUtils.genTestImage("frame with bottom-left watermark and 0.6f transparency",150,150))
                ,watermark);
        watermark.setLayoutConstraint(Watermark.LAYOUT_BOTTOM_RIGHT);
        watermark.setTransparency(0.4f);
        imageWithRelativeWatermarks.addGifFrame(
                new GifFrame(TestUtils.genTestImage("frame with bottom-right watermark and 0.4f transparency",150,150))
                ,watermark);
        watermark.setLayoutConstraint(Watermark.LAYOUT_COVER_CONSECUTIVELY);
        watermark.setTransparency(0.5f);
        imageWithRelativeWatermarks.addGifFrame(
                new GifFrame(TestUtils.genTestImage("frame with cover-consecutively watermark and 0.5f transparency",150,150))
                ,watermark);
        try {
            GifEncoder.encode(imageWithRelativeWatermarks, new File(outputDir, "WatermarksExample_relative_1.gif"), true);
        } catch (IOException e) {
        }

    }

}

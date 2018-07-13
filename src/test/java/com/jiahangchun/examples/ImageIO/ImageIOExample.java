package com.jiahangchun.examples.ImageIO;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.spi.ImageWriterSpi;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * This example demostrates using the Gif4J PRO Image I/O API integration
 */

public class ImageIOExample {

    public static void main(String[] args) {
        Iterator iterator = ImageIO.getImageWritersByFormatName("gif");
        while (iterator.hasNext()) {
            ImageWriter imageWriter = (ImageWriter) iterator.next();
            ImageWriterSpi spi = imageWriter.getOriginatingProvider();
            System.out.println("Vendor: "+spi.getVendorName());
            System.out.println("Format description: "
                    + imageWriter.getOriginatingProvider().getDescription(null)+"\n");
        }

        // change out directory if it is necessary
        File outputDir = new File("."+File.separator+"result");
        if (!outputDir.exists())
            outputDir.mkdirs();
        try {
            // read images. Here we read from files but it can be any source (internet, database etc.)
            BufferedImage[] images = new BufferedImage[4];
            for (int i=1;i<=4;i++)
                images[i-1] = ImageIO.read(ImageIOExample.class.getResourceAsStream("house_"+i+".jpg"));
            // Encode them using ImageIO API
            for (int i=1;i<=4;i++)
                ImageIO.write(images[i-1],"gif",new File(outputDir,"imageio_"+i+".gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

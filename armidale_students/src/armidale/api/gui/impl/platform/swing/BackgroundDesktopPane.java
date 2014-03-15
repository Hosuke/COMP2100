// Armidale, comp2110-exercise
//
// Copyright (C) 2000, 2001 Shayne R Flint
//
// shayne.flint@cs.anu.edu.au
//
// BackgroundDesktopPane.java,v 1.2 2001/08/17 02:26:26 shayne Exp
//

   package armidale.api.gui.impl.platform.swing;

   import java.awt.Graphics;
   import java.awt.MediaTracker;
   import javax.swing.JDesktopPane;
   import javax.swing.ImageIcon;
   import armidale.api.gui.ImageCallbackAdapter;
   import armidale.api.gui.*;
   import armidale.api.gui.constants.WallpaperModes;

   public class BackgroundDesktopPane extends JDesktopPane implements WallpaperModes {
     
      private Image wallpaper = null;
      private int   wallpaperMode;
      private int   tileWidth;
      private int   tileHeight;
   
     private class ImageDoneCallback extends ImageCallbackAdapter {
       public void imageChanged(Image image) {
         BackgroundDesktopPane.this.wallpaper = (ImageImpl)image;
         java.awt.Image theImage = ((ImageIcon)((ImageImpl)BackgroundDesktopPane.this.wallpaper).peer).getImage();
         BackgroundDesktopPane.this.tileWidth  = theImage.getWidth(null);
         BackgroundDesktopPane.this.tileHeight = theImage.getHeight(null);
         BackgroundDesktopPane.this.repaint();
       }
     }

      public void setWallpaper(Image wallpaper) {
        java.awt.Image theImage;
        ImageIcon      theImageIcon;
        this.wallpaper = (ImageImpl) wallpaper;
        if (wallpaper != null) {
          theImageIcon = (ImageIcon)((ImageImpl)wallpaper).peer;
          if (theImageIcon == null) {
            wallpaper.addImageCallback(new ImageDoneCallback());
          } else {
            theImage = theImageIcon.getImage();
            tileWidth  = theImage.getWidth(null);
            tileHeight = theImage.getHeight(null);
          }
        }
      }
      
      public void setWallpaperMode(int wallpaperMode) {
        this.wallpaperMode = wallpaperMode;
      }
      
      protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         if (wallpaper != null) {
           java.awt.Image theImage = ((ImageIcon)((ImageImpl)wallpaper).peer).getImage();
           if (theImage != null) {
              int width = this.getWidth();
              int height = this.getHeight();
              switch (wallpaperMode) {
                case NONE:
                  break;
                case TILED:
                  for (int i = 0; i < height; i += tileHeight) {
                    for (int j = 0; j < width; j += tileWidth) {
                      g.drawImage(theImage, j, i, tileWidth, tileHeight, this);
                    }
                  }
                  break;
                case SCALED:
                  g.drawImage(theImage, 0, 0, width, height, this);
                  break;
                case CENTER:
                  g.drawImage(theImage, (width-tileWidth)/2, (height-tileHeight)/2, tileWidth, tileHeight, this);
                  break;
                case NORTH:
                  g.drawImage(theImage, (width-tileWidth)/2, 0, tileWidth, tileHeight, this);
                  break;
                case NORTH_EAST:
                  g.drawImage(theImage, width-tileWidth, 0, tileWidth, tileHeight, this);
                  break;
                case EAST:
                  g.drawImage(theImage, width-tileWidth, (height-tileHeight)/2, tileWidth, tileHeight, this);
                  break;
                case SOUTH_EAST:
                  g.drawImage(theImage, width-tileWidth, height-tileHeight, tileWidth, tileHeight, this);
                  break;
                case SOUTH:
                  g.drawImage(theImage, (width-tileWidth)/2, height-tileHeight, tileWidth, tileHeight, this);
                  break;
                case SOUTH_WEST:
                  g.drawImage(theImage, 0, height-tileHeight, tileWidth, tileHeight, this);
                  break;
                case WEST:
                  g.drawImage(theImage, 0, (height-tileHeight)/2, tileWidth, tileHeight, this);
                  break;
                case NORTH_WEST:
                  g.drawImage(theImage, 0, 0, tileWidth, tileHeight, this);
                  break;
              }
           }
        }
      }
      
   }

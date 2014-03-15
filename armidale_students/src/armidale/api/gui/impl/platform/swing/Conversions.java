
package armidale.api.gui.impl.platform.swing;

import armidale.api.gui.constants.Compass;
import javax.swing.SwingConstants;


class Conversions {

  // Font
  /**
   *Description of the Method
   *
   * @param  font  Description of Parameter
   * @return       Description of the Returned Value
   * @since
   */
  public static java.awt.Font awtFont(armidale.api.gui.Font font) {
    int  fontStyle  = 0;
    if (font.isBold()) {
      fontStyle = fontStyle + java.awt.Font.BOLD;
    }
    if (font.isItalic()) {
      fontStyle = fontStyle + java.awt.Font.ITALIC;
    }
    return new java.awt.Font(font.getName(), fontStyle, font.getSize());
  }

  /**
   *Description of the Method
   *
   * @param  awtFont  Description of Parameter
   * @return          Description of the Returned Value
   * @since
   */
  public static armidale.api.gui.Font font(java.awt.Font awtFont) {
    return new armidale.api.gui.Font(awtFont.getName(), awtFont.getSize(), awtFont.isBold(), awtFont.isItalic());
  }


  // Color

  /**
   *Description of the Method
   *
   * @param  color  Description of Parameter
   * @return        Description of the Returned Value
   * @since
   */
  public static java.awt.Color awtColor(armidale.api.gui.Color color) {
    return new java.awt.Color(color.getRed(), color.getGreen(), color.getBlue());
  }

  /**
   *Description of the Method
   *
   * @param  awtColor  Description of Parameter
   * @return           Description of the Returned Value
   * @since
   */
  public static armidale.api.gui.Color color(java.awt.Color awtColor) {
    return new armidale.api.gui.Color(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue());
  }

  // Size
  /**
   *Description of the Method
   *
   * @param  size  Description of Parameter
   * @return       Description of the Returned Value
   * @since
   */
  public static java.awt.Dimension awtDimension(armidale.api.gui.Size size) {
    return new java.awt.Dimension(size.getWidth(), size.getHeight());
  }

  /**
   *Description of the Method
   *
   * @param  awtDimension  Description of Parameter
   * @return               Description of the Returned Value
   * @since
   */
  public static armidale.api.gui.Size size(java.awt.Dimension awtDimension) {
    return new armidale.api.gui.Size((int) awtDimension.getWidth(), (int) awtDimension.getHeight());
  }

  // Size
  /**
   *Description of the Method
   *
   * @param  size  Description of Parameter
   * @return       Description of the Returned Value
   * @since
   */
  public static java.awt.Point awtPoint(armidale.api.gui.Position position) {
    return new java.awt.Point(position.getX(), position.getY());
  }

  /**
   *Description of the Method
   *
   * @param  awtDimension  Description of Parameter
   * @return               Description of the Returned Value
   * @since
   */
  public static armidale.api.gui.Position position(java.awt.Point awtPoint) {
    return new armidale.api.gui.Position((int) awtPoint.x, (int) awtPoint.y);
  }

  // Alignment

  /**
   *Description of the Method
   *
   * @param  h  Description of Parameter
   * @param  v  Description of Parameter
   * @return    Description of the Returned Value
   * @since
   */
  public static int alignment(int h, int v) {
    if ((v == SwingConstants.TOP) && (h == SwingConstants.CENTER)) {
      return Compass.NORTH;
    } else if ((v == SwingConstants.TOP) && (h == SwingConstants.RIGHT)) {
      return Compass.NORTH_EAST;
    } else if ((v == SwingConstants.CENTER) && (h == SwingConstants.RIGHT)) {
      return Compass.EAST;
    } else if ((v == SwingConstants.BOTTOM) && (h == SwingConstants.RIGHT)) {
      return Compass.SOUTH_EAST;
    } else if ((v == SwingConstants.BOTTOM) && (h == SwingConstants.CENTER)) {
      return Compass.SOUTH;
    } else if ((v == SwingConstants.BOTTOM) && (h == SwingConstants.LEFT)) {
      return Compass.SOUTH_WEST;
    } else if ((v == SwingConstants.CENTER) && (h == SwingConstants.LEFT)) {
      return Compass.WEST;
    } else if ((v == SwingConstants.TOP) && (h == SwingConstants.LEFT)) {
      return Compass.NORTH_WEST;
    } else {
      return Compass.CENTER;
    }
  }

  /**
   *Description of the Method
   *
   * @param  alignment  Description of Parameter
   * @return            Description of the Returned Value
   * @since
   */
  public static int horizontalAlignment(int alignment) {
    switch (alignment) {
      case Compass.CENTER:
        return SwingConstants.CENTER;
      case Compass.NORTH:
        return SwingConstants.CENTER;
      case Compass.NORTH_EAST:
        return SwingConstants.RIGHT;
      case Compass.EAST:
        return SwingConstants.RIGHT;
      case Compass.SOUTH_EAST:
        return SwingConstants.RIGHT;
      case Compass.SOUTH:
        return SwingConstants.CENTER;
      case Compass.SOUTH_WEST:
        return SwingConstants.LEFT;
      case Compass.WEST:
        return SwingConstants.LEFT;
      case Compass.NORTH_WEST:
        return SwingConstants.LEFT;
      default:
        return 0;
    }
  }

  /**
   *Description of the Method
   *
   * @param  alignment  Description of Parameter
   * @return            Description of the Returned Value
   * @since
   */
  public static int verticalAlignment(int alignment) {
    switch (alignment) {
      case Compass.CENTER:
        return SwingConstants.CENTER;
      case Compass.NORTH:
        return SwingConstants.TOP;
      case Compass.NORTH_EAST:
        return SwingConstants.TOP;
      case Compass.EAST:
        return SwingConstants.CENTER;
      case Compass.SOUTH_EAST:
        return SwingConstants.BOTTOM;
      case Compass.SOUTH:
        return SwingConstants.BOTTOM;
      case Compass.SOUTH_WEST:
        return SwingConstants.BOTTOM;
      case Compass.WEST:
        return SwingConstants.CENTER;
      case Compass.NORTH_WEST:
        return SwingConstants.TOP;
      default:
        return 0;
    }
  }

}


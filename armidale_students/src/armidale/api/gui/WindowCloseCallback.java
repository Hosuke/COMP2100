package armidale.api.gui;

  import armidale.api.context.Context;
  import armidale.api.gui.constants.WindowCloseActions;
  import javax.swing.JFrame;
  
public class WindowCloseCallback extends WindowCallbackAdapter implements WindowCloseActions {

  Context context;
  int    onCloseAction;
  
  public WindowCloseCallback(Context context, int onCloseAction) {
    this.context = context;
    this.onCloseAction = onCloseAction;
  }
  
  public void windowClosing(Window window) {
    switch (onCloseAction) {
      case IGNORE_CLOSE:
        break;
      case HIDE_ON_CLOSE:
        window.hide();
        break;
      case DISPOSE_ON_CLOSE:
        window.dispose();
        break;
      case STOP_APPLICATION_ON_CLOSE:
        context.close();
        break;
    }    
  }

}


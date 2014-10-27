package org.thehecklers.monologfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.ControlBuilder;
import javafx.util.Builder;

import org.thehecklers.monologfx.MonologFX.ButtonAlignment;

/**
 *
 * @author Mark Heckler (mark.heckler@gmail.com, @MkHeck)
 * @param <B>
 */
public class MonologFXBuilder<B extends MonologFXBuilder<B>> extends ControlBuilder<B> implements Builder<MonologFX> {
    private final HashMap<String, Property> properties = new HashMap<>();
    private final List<MonologFXButton> buttons = new ArrayList<>();
    private final List<String> stylesheets = new ArrayList<>();
 
    protected MonologFXBuilder() {
    }

    /**
     * Creates and returns a MonologFX dialog box builder object upon which 
     * to set properties and eventually, create a MonologFX dialog box.
     */
    public static MonologFXBuilder create() {
        return new MonologFXBuilder();
    }

    /**
     * Public method used to add a button to a MonologFX dialog.
     * 
     * @param BUTTON A MonologFXButton object.
     * 
     * @see MonologFXButton
     */
    public final MonologFXBuilder button(final MonologFXButton BUTTON) {
        //properties.put("button", new SimpleObjectProperty<>(BUTTON));
        buttons.add(BUTTON);
        return this;
    }

    /**
     * Public method used to add a button to a MonologFX dialog.
     * 
     * @param BUTTON A MonologFXButton object.
     * 
     * @see MonologFXButton
     */
    public final MonologFXBuilder displayTime(final int DISPLAYTIME) {
        properties.put("displayTime", new SimpleIntegerProperty(DISPLAYTIME));
        return this;
    }
    
    /**
     * Sets the type of MonologFX dialog box to build/display.
     * 
     * @param TYPE One of the supported types of dialogs.
     * @see MonologFX.Type
     */
    public final MonologFXBuilder type(final MonologFX.Type TYPE) {
        properties.put("type", new SimpleObjectProperty<>(TYPE));
        return this;
    }

    /**
     * Sets the button alignment for the MonologFX dialog box. Default is CENTER.
     * 
     * @param ALIGNBUTTONS Valid values are LEFT, RIGHT, and CENTER.
     * 
     * @see ButtonAlignment
     */
    public final MonologFXBuilder buttonAlignment(final MonologFX.ButtonAlignment ALIGNBUTTONS) {
        properties.put("alignbuttons", new SimpleObjectProperty<>(ALIGNBUTTONS));
        return this;
    }

    /**
     * Sets the text displayed within the MonologFX dialog box. Word wrap 
     * ensures that all text is displayed.
     * 
     * @param MESSAGE String variable containing the text to display.
     */
    public final MonologFXBuilder message(final String MESSAGE) {
        properties.put("message", new SimpleStringProperty(MESSAGE));
        return this;
    }

    /**
     * Sets the modality of the MonologFX dialog box to build/display.
     * 
     * @param MODAL Boolean. A true value = APPLICATION_MODAL, false = NONE.
     */
    public final MonologFXBuilder modal(final boolean MODAL) {
        properties.put("modal", new SimpleBooleanProperty(MODAL));
        return this;
    }

    /**
     * Sets the text to be displayed in the title bar of the MonologFX dialog.
     * 
     * @param TITLE_TEXT String containing the text to place in the title bar.
     */
    public final MonologFXBuilder titleText(final String TITLE_TEXT) {
        properties.put("titleText", new SimpleStringProperty(TITLE_TEXT));
        return this;
    }

    /**
     * Sets x coordinate of the MonologFX dialog (if centering is not desired).
     * 
     * @param X_COORD Double representing the x coordinate to use for display.
     */
    public final MonologFXBuilder X(final double X_COORD) {
        properties.put("xCoord", new SimpleDoubleProperty(X_COORD));
        return this;
    }

    /**
     * Sets y coordinate of the MonologFX dialog (if centering is not desired).
     * 
     * @param Y_COORD Double representing the y coordinate to use for display.
     */
    public final MonologFXBuilder Y(final double Y_COORD) {
        properties.put("yCoord", new SimpleDoubleProperty(Y_COORD));
        return this;
    }

    /**
     * Allows developer to add stylesheet(s) for MonologFX dialog, supplementing 
     * or overriding existing styling.
     * 
     * @param STYLESHEET String variable containing the path/name of the 
     * stylesheet to apply to the dialog's scene and contained controls.
     */
    public final MonologFXBuilder stylesheet(final String STYLESHEET) {
        //properties.put("stylesheet", new SimpleStringProperty(STYLESHEET));
        stylesheets.add(STYLESHEET);
        return this;
    }

    /**
     * This is where the magic happens...or at least where it all comes 
     * together.  :-) Returns a MonologFX dialog, ready to display with
     * showDialog().
     * 
     * @return MonologFX A dialog.
     */
    @Override
    public MonologFX build() {
        final MonologFX CONTROL = new MonologFX();

        for (String key : properties.keySet()) {
            switch (key) {
                case "type":
                    CONTROL.setType(((ObjectProperty<MonologFX.Type>) properties.get(key)).get());
                    break;
                case "alignbuttons":
                    CONTROL.setButtonAlignment(((ObjectProperty<MonologFX.ButtonAlignment>) properties.get(key)).get());
                    break;
                case "displayTime":
                    CONTROL.setDisplayTime(((IntegerProperty) properties.get(key)).get());
                    break;
                case "message":
                    CONTROL.setMessage(((StringProperty) properties.get(key)).get());
                    break;
                case "modal":
                    CONTROL.setModal(((BooleanProperty) properties.get(key)).get());
                    break;
                case "titleText":
                    CONTROL.setTitleText(((StringProperty) properties.get(key)).get());
                    break;
                case "xCoord":
                    CONTROL.setX(((DoubleProperty) properties.get(key)).get());
                    break;
                case "yCoord":
                    CONTROL.setY(((DoubleProperty) properties.get(key)).get());
                    break;
            }
        }

        for ( MonologFXButton mb : buttons ) {
            CONTROL.addButton(mb);
        }
        
        for ( String ss : stylesheets ) {
            CONTROL.addStylesheet(ss);
        }
        
        return CONTROL;
    }
}

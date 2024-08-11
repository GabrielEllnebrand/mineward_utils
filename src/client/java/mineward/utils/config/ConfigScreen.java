package mineward.utils.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {

  Screen parent;

  // default
  protected ConfigScreen() {
    super(Text.literal("Mineward Utils"));
  }

  // TODO: implement modmenu support
  // modmenu
  protected ConfigScreen(Screen parent) {
    super(Text.literal("Mineward Utils"));
    this.parent = parent;
  }

  public ButtonWidget displayWaypoints;
  public ButtonWidget renderWaypoints;
  public ButtonWidget playerHead;
  public ButtonWidget renderPickups;
  public ButtonWidget displayCooldowns;
  public ButtonWidget displayPickupCount;

  private static int buttonWidth = 200;

  @Override
  protected void init() {

    buttonWidth = width / 4 - 5;

    displayWaypoints = ButtonWidget
        .builder(Text.literal(makeBoolText("Waypoint GUI", Config.displayWaypoints)), button -> {
          Config.displayWaypoints = !Config.displayWaypoints;
          Config.set("displayWaypoints", Config.displayWaypoints);
          displayWaypoints.setMessage(Text.literal(makeBoolText("Waypoint GUI", Config.displayWaypoints)));
        })
        .dimensions(width / 4 - 10, 20, buttonWidth, 20)
        .tooltip(Tooltip.of(Text.literal("Toggles the gui display for waypoints")))
        .build();

    renderWaypoints = ButtonWidget
        .builder(Text.literal(makeBoolText("Waypoint render", Config.renderWaypoints)), button -> {
          Config.renderWaypoints = !Config.renderWaypoints;
          Config.set("renderWaypoints", Config.renderWaypoints);
          renderWaypoints.setMessage(Text.literal(makeBoolText("Waypoint render", Config.renderWaypoints)));
        })
        .dimensions(width / 2 + 10, 20, buttonWidth, 20)
        .tooltip(Tooltip.of(Text.literal("Toggles the rendering of waypoints")))
        .build();

    playerHead = ButtonWidget
        .builder(Text.literal(makeBoolText("playerHead", Config.renderHead)), button -> {
          Config.renderHead = !Config.renderHead;
          Config.set("playerHead", Config.renderHead);
          playerHead.setMessage(Text.literal(makeBoolText("playerHead", Config.renderHead)));
        })
        .dimensions(width / 4 - 10, 45, buttonWidth, 20)
        .tooltip(Tooltip.of(Text.literal("Toggles the rendering of player heads")))
        .build();

    renderPickups = ButtonWidget
        .builder(Text.literal(makeBoolText("Pickups", Config.renderPickups)), button -> {
          Config.renderPickups = !Config.renderPickups;
          Config.set("playerHead", Config.renderPickups);
          renderPickups.setMessage(Text.literal(makeBoolText("Pickups", Config.renderPickups)));
        })
        .dimensions(width / 2 + 10, 45, buttonWidth, 20)
        .tooltip(Tooltip.of(Text.literal("Toggles the highlight of pickups")))
        .build();

    displayCooldowns = ButtonWidget
        .builder(Text.literal(makeBoolText("Cooldowns", Config.displayCooldowns)), button -> {
          Config.displayCooldowns = !Config.displayCooldowns;
          Config.set("displayCooldowns", Config.displayCooldowns);
          displayCooldowns.setMessage(Text.literal(makeBoolText("Cooldowns", Config.displayCooldowns)));
        })
        .dimensions(width / 4 - 10, 70, buttonWidth, 20)
        .tooltip(Tooltip.of(Text.literal("Toggles the display of cooldowns")))
        .build();

        displayPickupCount = ButtonWidget
        .builder(Text.literal(makeBoolText("Pickup count: ", Config.displayPickupCount)), button -> {
          Config.displayPickupCount = !Config.displayPickupCount;
          Config.set("displayPickupCount", Config.displayPickupCount);
          displayPickupCount.setMessage(Text.literal(makeBoolText("Pickup count: ", Config.displayPickupCount)));
        })
        .dimensions(width / 2 + 10, 70, buttonWidth, 20)
        .tooltip(Tooltip.of(Text.literal("Shows how many pickups youve interacted with")))
        .build();
    
  
    addDrawableChild(displayWaypoints);
    addDrawableChild(renderWaypoints);
    addDrawableChild(playerHead);
    addDrawableChild(renderPickups);
    addDrawableChild(displayCooldowns);
    addDrawableChild(displayPickupCount);
  }

  /**
   * Takes in a string and a boolean and creates a string corresponding to the
   * boolean value
   * 
   * @param string String
   * @param bool   Boolean
   * @return String + ON, if true else string + off
   */
  public String makeBoolText(String string, Boolean bool) {
    if (bool) {
      return string + ": ON";
    } else {
      return string + ": OFF";
    }
  }

  @Override
  public void close() {
    client.setScreen(parent);
  }
}

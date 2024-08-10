package mineward.utils;

import java.util.ArrayList;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Vec3d;

public class GwonkleHelper {

	static ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
	static float totalTickDelta = 0.0f;

	public static void register() {
		resetWaypoints();

		// resets waypoints on clicking chest
		UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
			if (waypoints.size() < 12) {
				resetWaypoints();
			}
			return ActionResult.PASS;
		});
	}

	public static void checkWaypoints(MinecraftClient client) {

		PlayerEntity clientPlayer = client.player;
		if (clientPlayer == null) {
			return;
		}

		totalTickDelta += client.getTickDelta();
		if (totalTickDelta < 2) {
			return;
		} else {
			totalTickDelta = 0;
		}

		ItemStack item = clientPlayer.getMainHandStack();
		int id = Item.getRawId(item.getItem());

		if (id == 1055) { // the id of empty map
			String tooltip = item.getName().getString();
			Vec3d user = clientPlayer.getPos();
			switch (tooltip) {
				case ">> ⓪ >500 blocks away <<":
					updateWaypoints(user, Integer.MAX_VALUE, 500);
					break;
				case ">> ⓪ 300-500 blocks away <<":
					updateWaypoints(user, 500, 300);
					break;
				case ">> ⓪ 100-300 blocks away <<":
					updateWaypoints(user, 300, 100);
					break;
				case ">> ⓪ 20-100 blocks away <<":
					updateWaypoints(user, 100, 20);
					break;
				case ">> ⓪ 0-20 blocks away <<":
					updateWaypoints(user, 20, 0);
					break;
				case "Treasure Map":
				case "Generating Map...":
					resetWaypoints();
					break;
				default:
					break;
			}
		}

	}

	/**
	 * Removes the waypoints that are not in range
	 * 
	 * @param user the users coords
	 * @param high the highest distance
	 * @param low  the lowest distance
	 */
	public static void updateWaypoints(Vec3d user, int high, int low) {
		try {
			for (int i = 0; i < waypoints.size(); i++) {
				double dis = utils.getDistance(user, waypoints.get(i).getPos());
				if (!fitsRange(high, low, dis)) {
					waypoints.remove(waypoints.get(i));
				}
			}
			if (waypoints.size() == 0) {
				resetWaypoints();
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Checks if the distance is in range
	 * 
	 * @param high the highest distance
	 * @param low  the lowest distance
	 * @param dis  the distance
	 * @return true if in range, else false
	 */
	public static boolean fitsRange(int high, int low, double dis) {
		double margin = 5;
		return dis <= high + margin && dis >= low - margin;
	}

	/**
	 * set the list waypoints to all possible locations
	 */
	public static void resetWaypoints() {
		waypoints.clear();
		waypoints.add(new Waypoint("Lush Island", new Vec3d(-863, 168, -584)));
		waypoints.add(new Waypoint("Upper Lighthouse", new Vec3d(-925, 212, -921)));
		waypoints.add(new Waypoint("Outside Lighthouse", new Vec3d(-920, 168, -911)));
		waypoints.add(new Waypoint("Geode", new Vec3d(-916, 171, -1096)));
		waypoints.add(new Waypoint("Leviathan Island", new Vec3d(-621, 171, -831)));
		waypoints.add(new Waypoint("Small Island", new Vec3d(-950, 168, -860)));
		waypoints.add(new Waypoint("Floating Island", new Vec3d(-999, 163, -630)));
		waypoints.add(new Waypoint("Campfire", new Vec3d(-759, 170, -923)));
		waypoints.add(new Waypoint("Next to Campfire", new Vec3d(-691, 170, -961)));
		waypoints.add(new Waypoint("Pond", new Vec3d(-797, 168, -1123)));
		waypoints.add(new Waypoint("Inner Lighthouse", new Vec3d(-933, 167, -922)));
		waypoints.add(new Waypoint("Quarry", new Vec3d(-590, 162, -583)));
		waypoints.add(new Waypoint("colosseum", new Vec3d(-790, 171, -757)));

		// waypoints.add(new Waypoint("Bottom Dome", -1017, 50, -955));
		// waypoints.add(new Waypoint("Upper Dome", -1040, 64, -1042));
	}

	public static ArrayList<Waypoint> getWaypoints() {
		return waypoints;
	}
}

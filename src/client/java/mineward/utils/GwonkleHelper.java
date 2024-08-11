package mineward.utils;

import java.util.ArrayList;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public abstract class GwonkleHelper {

	static ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
	static float totalTickDelta = 0.0f;

	/**
	 * Takes in a string and checks if the remaining waypoints are in range,
	 * if a waypoint is not in range remove it
	 * @param string String
	 */
	public static void checkWaypoints(String string) {
		MinecraftClient client = utils.getClient();
		PlayerEntity clientPlayer = client.player;
		if (clientPlayer == null) {
			return;
		}

		Vec3d user = clientPlayer.getPos();
		switch (string) {
			case "⓪ There is a treasure >500 blocks away":
				updateWaypoints(user, 500, Integer.MAX_VALUE);
				break;
			case "⓪ There is a treasure 400-500 blocks away":
				updateWaypoints(user, 400, 500);
				break;
			case "⓪ There is a treasure 300-400 blocks away":
				updateWaypoints(user, 300, 400);
				break;
			case "⓪ There is a treasure 200-300 blocks away":
				updateWaypoints(user, 200, 300);
				break;
			case "⓪ There is a treasure 100-200 blocks away":
				updateWaypoints(user, 100, 200);
				break;
			case "⓪ There is a treasure 20-100 blocks away":
				updateWaypoints(user, 20, 100);
				break;
			case "⓪ There is a treasure <20 blocks away":
				updateWaypoints(user, 0, 20);
				break;
			case "Drawing map...":
				resetWaypoints();
				break;
			default:
				break;
		}
		client = null;
	}

	/**
	 * Removes the waypoints that are not in range
	 * 
	 * @param user the users coords
	 * @param high the highest distance
	 * @param low  the lowest distance
	 */
	public static void updateWaypoints(Vec3d user, int low, int high) {
		try {
			for (int i = 0; i < waypoints.size(); i++) {
				double dis = utils.getDistance(user, waypoints.get(i).getPos());
				if (!fitsRange(low, high, dis)) {
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
	public static boolean fitsRange(int low, int high, double dis) {
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

package mineward.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class utils {

	public static final Logger LOGGER = LoggerFactory.getLogger("mineward-utils");
	static MinecraftClient client = MinecraftClient.getInstance();

	/**
	 * Converts Vec3d to BlockPos
	 * 
	 * @param pos Vec3d
	 * @return BlockPos
	 */
	public static BlockPos toBlockPos(Vec3d pos) {
		return new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ());
	}

	/**
	 * Gets the distance between two vertices
	 * 
	 * @param p1 Vec3d, the first vertex
	 * @param p2 Vec3d, the second vertex
	 * @return Double, the distance between them
	 */
	public static double getDistance(Vec3d p1, Vec3d p2) {
		return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2)
				+ Math.pow(p2.getZ() - p1.getZ(), 2));
	}

	/**
	 * Checks if 2 vertices are in distance
	 * 
	 * @param p1       Vec3d, the first vertex
	 * @param p2       Vec3d, the second vertex
	 * @param distance The distance to check
	 * @return True if in range, else false
	 */
	public static boolean inRange(Vec3d p1, Vec3d p2, double distance) {
		return distance > getDistance(p1, p2);
	}

	public static MinecraftClient getClient() {
		return client;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}

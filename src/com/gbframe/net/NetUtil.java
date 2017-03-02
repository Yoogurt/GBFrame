package com.gbframe.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import com.gbframe.util.UtilGlobal;

import android.Manifest.permission;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;

public class NetUtil {

	public static boolean isWifiEnableSafely(Context ctx) {

		if (UtilGlobal.sdkVersion > 16)
			return ctx.checkSelfPermission(permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
					&& ctx.getSystemService(WifiManager.class).isWifiEnabled();
		else
			return ctx.checkCallingPermission(permission.ACCESS_WIFI_STATE) == PackageManager.PERMISSION_GRANTED
					&& ctx.getSystemService(WifiManager.class).isWifiEnabled();
	}
	

	abstract class MulticastServerCallback {

		private MulticastSocket ms;

		protected abstract void onDataRecive(String ip, int port, byte[] data);

		final boolean send(String ip, int port, byte[] data) {

			if (ms != null && !ms.isClosed())
				try {
					DatagramPacket dp = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
					ms.send(dp);

					return true;
				} catch (IOException e) {
					return false;
				}
			else
				return false;
		}

		final void shutdown() {
			if (ms != null && !ms.isClosed())
				ms.close();
		}
	}

	public static void startMutilBroadCastServer(String ip, int port, MulticastServerCallback callback) {
		try {
			MulticastSocket mMulticastSocket = new MulticastSocket(port);

			mMulticastSocket.joinGroup(InetAddress.getByName(ip));

			mMulticastSocket.setReuseAddress(true);

			boolean mStop = false;

			DatagramPacket mDatagramPacket = new DatagramPacket(new byte[1024], 1024);

			callback.ms = mMulticastSocket;

			while (!mStop) {

				mMulticastSocket.receive(mDatagramPacket);

				byte[] recvBuffer = new byte[mDatagramPacket.getLength()];

				System.arraycopy(mDatagramPacket.getData(), 0, recvBuffer, 0, recvBuffer.length);

				callback.onDataRecive(mDatagramPacket.getAddress().getHostAddress(), mDatagramPacket.getPort(),
						recvBuffer);
			}

			mMulticastSocket.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

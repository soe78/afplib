package org.afplib.io;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.afplib.afplib.AfplibFactory;
import org.afplib.afplib.IPD;
import org.afplib.base.Triplet;

public class SDFHelper {

	public static IPD[] sdf2ipds(List<Triplet> triplets) {

		List<IPD> ipds = new LinkedList<>();

		StructuredFieldFactory factory = new StructuredFieldFactory();
		byte[] iddbuffer = new byte[32000];
		byte[] buffer = new byte[32000];

		int pos = 0;
		for(Triplet t : triplets) {

			int length = factory.binary_sdf(buffer, 0, Arrays.asList(t));

			if(pos + length >= iddbuffer.length) {
				ipds.add(createIPD(iddbuffer, pos));
				pos = 0;
			}

			System.arraycopy(buffer, 0, iddbuffer, pos, length);
			pos += length;

		}
		ipds.add(createIPD(iddbuffer, pos));

		return (IPD[]) ipds.toArray(new IPD[ipds.size()]);
	}

	private static IPD createIPD(byte[] iddbuffer, int pos) {
		byte[] buf = new byte[pos];
		System.arraycopy(iddbuffer, 0, buf, 0, pos);
		IPD ipd = AfplibFactory.eINSTANCE.createIPD();
		ipd.setIOCAdat(buf);
		return ipd;
	}

}

package jpp.qrcode.encode;

import jpp.qrcode.*;

public class Encoder {

	public static QRCode createFromString(String msg, ErrorCorrection correction) {
		DataEncoderResult res = DataEncoder.encodeForCorrectionLevel(msg,correction);
		Version version = res.version();
		boolean[][] matrix = PatternPlacer.createBlankForVersion(version);
		ReservedModulesMask mask = ReservedModulesMask.forVersion(version);
		byte[] by = DataStructurer.structure(res.bytes(),res.version().correctionInformationFor(correction));
		DataInserter.insert(matrix,mask,by);
		MaskPattern maskP = MaskSelector.maskWithBestMask(matrix,correction,mask);
		QRCode result = new QRCode(matrix,version,maskP,correction);


		return result;

	}
}
